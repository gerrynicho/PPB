# News App — Android (Jetpack Compose)

## Preview

The app has four main screens:
- **Home News** — top headlines in a scrollable card list
- **Detail News** — full article view with image, title, description, and content
- **Search News** — search results filtered by keyword
- **Loading & Error State** — spinner while loading, error message with a Retry button

---

## 1. Project Architecture

```
com.newsapp
├── data
│   ├── api
│   │   ├── ApiService.kt
│   │   └── RetrofitClient.kt
│   │
│   ├── model
│   │   ├── Article.kt
│   │   └── NewsResponse.kt
│   │
│   └── repository
│       └── NewsRepository.kt
│
├── ui
│   ├── screens
│   │   ├── HomeScreen.kt
│   │   └── DetailScreen.kt
│   │
│   └── components
│       └── NewsCard.kt
│
├── viewmodel
│   └── NewsViewModel.kt
│
├── navigation
│   └── NavGraph.kt
│
└── MainActivity.kt
```

---

## 2. Dependencies

```kotlin
dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.0")
    implementation("androidx.navigation:navigation-compose:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("io.coil-kt:coil-compose:2.7.0")
}
```

---

## 3. AndroidManifest

Add internet permission:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

---

## 4. Data Models

### `Article.kt`

```kotlin
data class Article(
    val title: String,
    val description: String?,
    val content: String?,
    val author: String?,
    val urlToImage: String?,
    val publishedAt: String
)
```

### `NewsResponse.kt`

```kotlin
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
```

---

## 5. API Service

### `ApiService.kt`

```kotlin
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
```

---

## 6. Retrofit Client

### `RetrofitClient.kt`

```kotlin
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://newsapi.org/v2/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
```

---

## 7. Repository Layer

### `NewsRepository.kt`

```kotlin
class NewsRepository {
    suspend fun getNews() =
        RetrofitClient.apiService.getTopHeadlines(
            apiKey = "YOUR_API_KEY"
        )
}
```

---

## 8. UI State

```kotlin
sealed class NewsUiState {
    object Loading : NewsUiState()
    data class Success(val articles: List<Article>) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}
```

---

## 9. ViewModel

### `NewsViewModel.kt`

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val repository = NewsRepository()

    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            try {
                _uiState.value = NewsUiState.Loading
                val response = repository.getNews()
                _uiState.value = NewsUiState.Success(response.articles)
            } catch (e: Exception) {
                _uiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }
}
```

---

## 10. News Card Component

### `NewsCard.kt`

```kotlin
@Composable
fun NewsCard(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column {
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
```

---

## 11. Home Screen

### `HomeScreen.kt`

```kotlin
@Composable
fun HomeScreen(
    viewModel: NewsViewModel,
    onDetailClick: (Article) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    when (state) {
        is NewsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is NewsUiState.Success -> {
            val articles = (state as NewsUiState.Success).articles
            LazyColumn {
                items(articles) { article ->
                    NewsCard(article = article) {
                        onDetailClick(article)
                    }
                }
            }
        }

        is NewsUiState.Error -> {
            val error = state as NewsUiState.Error
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(error.message)
                Button(onClick = { viewModel.loadNews() }) {
                    Text("Retry")
                }
            }
        }
    }
}
```

---

## 12. Detail Screen

### `DetailScreen.kt`

```kotlin
@Composable
fun DetailScreen(article: Article) {
    Column {
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Text(
            article.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            article.description ?: "",
            modifier = Modifier.padding(16.dp)
        )
        Text(
            article.content ?: "",
            modifier = Modifier.padding(16.dp)
        )
    }
}
```

---

## 13. Navigation

### `NavGraph.kt`

```kotlin
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val viewModel = viewModel<NewsViewModel>()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(viewModel = viewModel) { article ->
                // TODO: navigate to detail
            }
        }
    }
}
```

---

## 14. MainActivity

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavGraph()
            }
        }
    }
}
```

---

## Notes for Claude Code

- Replace `"YOUR_API_KEY"` in `NewsRepository.kt` with a real key from [newsapi.org](https://newsapi.org)
- The PDF shows a **Search** tab and **Saved** tab in the top nav — these are **not yet implemented** in the code above and will need to be added
- Navigation from `HomeScreen` to `DetailScreen` is stubbed in `NavGraph.kt` — the `onDetailClick` lambda needs wiring up with `navController.navigate(...)` and argument passing (e.g. via `Json.encodeToString(article)` or a shared ViewModel)
- The app uses **Material3** — make sure `material3` is in your dependencies if not already present