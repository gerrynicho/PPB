package com.example.loginpage.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginpage.R
import com.example.loginpage.ui.theme.LoginPageTheme

private val LoginPurple = Color(0xFF6E4AA8)
private val LoginInk = Color(0xFF252231)
private val LoginMuted = Color(0xFF777080)

@Composable
fun LoginRoute() {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LoginScreen(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = { focusManager.clearFocus() },
        onForgotPasswordClick = { },
        onFacebookClick = { },
        onGoogleClick = { },
        onXClick = { },
    )
}

@Composable
fun LoginScreen(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onFacebookClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onXClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFAF9FB))
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .navigationBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 34.dp, vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        LoginIllustration(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Welcome Back",
            color = LoginInk,
            style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp,
            )
        )
        Text(
            text = "Login to your account",
            color = LoginMuted,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 13.sp,
                letterSpacing = 0.sp,
            )
        )

        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Email address") },
            singleLine = true,
            shape = RoundedCornerShape(2.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            ),
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Password") },
            singleLine = true,
            shape = RoundedCornerShape(2.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onLoginClick()
                }
            ),
        )

        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .width(96.dp)
                .height(42.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = LoginPurple),
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 12.sp,
                    letterSpacing = 0.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        TextButton(onClick = onForgotPasswordClick) {
            Text(
                text = "Forgot Password?",
                color = LoginMuted,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = 12.sp,
                    letterSpacing = 0.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "or sign in with",
            color = LoginMuted,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 12.sp,
                letterSpacing = 0.sp,
            )
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SocialIconButton(
                drawableRes = R.drawable.meta_logo,
                contentDescription = "Sign in with Meta",
                onClick = onFacebookClick,
            )
            SocialIconButton(
                drawableRes = R.drawable.google_logo,
                contentDescription = "Sign in with Google",
                onClick = onGoogleClick,
            )
            SocialIconButton(
                drawableRes = R.drawable.x_logo,
                contentDescription = "Sign in with X",
                onClick = onXClick,
            )
        }
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun SocialIconButton(
    drawableRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(46.dp)
            .shadow(elevation = 5.dp, shape = CircleShape, ambientColor = Color.Black.copy(alpha = 0.10f))
            .clip(CircleShape)
            .background(Color.White)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = drawableRes),
            contentDescription = null,
            modifier = Modifier.size(34.dp),
            contentScale = ContentScale.Fit,
        )
    }
}

@Composable
private fun LoginIllustration(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.login_illust),
        contentDescription = "Login illustration",
        modifier = modifier,
        contentScale = ContentScale.Fit,
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun LoginScreenPreview() {
    LoginPageTheme(dynamicColor = false) {
        LoginScreen(
            email = "test@gmail.com",
            password = "password",
            onEmailChange = { },
            onPasswordChange = { },
            onLoginClick = { },
            onForgotPasswordClick = { },
            onFacebookClick = { },
            onGoogleClick = { },
            onXClick = { },
        )
    }
}
