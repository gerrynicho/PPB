package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

fun calculate(num1: Double, operator: String, num2: Double): String {
    return when (operator) {
        "+" -> "${num1.plus(num2)}"
        "-" -> "${num1.minus(num2)}"
        "×" -> "${num1.times(num2)}"
        "÷" -> {
            if (num2 != 0.0) "${num1.div(num2)}"
            else "Error Bagi nol!"
        }
        else -> "Error"
    }
}

@Preview
@Composable
fun CalculatorApp() {
    CalculatorAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Calculator(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


@Composable
fun Calculator(
    modifier: Modifier = Modifier
) {
    var num1State by remember { mutableStateOf("")}
    var num2State by remember { mutableStateOf("")}
    val n1dis = remember(num1State) {
        num1State.toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString() ?: ""
    }
    val n2dis = remember(num2State) {
        num2State.toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString() ?: ""
    }
    var operatorState by remember { mutableStateOf("+")}
    var resultState by remember { mutableStateOf("")}

    LaunchedEffect5o(num1State, num2State, operatorState) {
        val n1 = num1State.toDoubleOrNull()
        val n2 = num2State.toDoubleOrNull()

        if(n1 != null && n2 != null) {
            val raw = calculate(n1, operatorState, n2)
            resultState = raw.toBigDecimalOrNull()?.stripTrailingZeros()?.toPlainString()?: raw
        } else {
            resultState = ""
        }
    }

    Column(
        modifier = modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.Center)
                        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.judul),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
        )
        OutlinedTextField(
            value = num1State,
            onValueChange = { newValue: String ->
                val dotCount = newValue.count { it == '.'}
                if (newValue.isEmpty() || newValue.all { it.isDigit() || it == '.' } && dotCount <= 1) {
                    num1State = newValue
                }
            },
            label = {Text("Angka Pertama")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )
        OutlinedTextField(
            value = num2State,
            onValueChange = { newValue: String ->
                val dotCount = newValue.count { it == '.'}
                if (newValue.isEmpty() || newValue.all { it.isDigit() || it == '.' } && dotCount <= 1) {
                    num2State = newValue
                }
            },
            label = {Text("Angka Kedua")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            singleLine = true
        )
        Row(
            modifier = modifier.fillMaxWidth().padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val operatorSymbol = listOf("+", "-", "×", "÷")
            operatorSymbol.forEach { op ->
                FilterChip(
                    selected = operatorState == op,
                    onClick = { operatorState = op},
                    label = { Text(text=op)}
                )
            }

        }

        Text("$n1dis $operatorState $n2dis")
        Text(
            text="=",
            fontSize = 20.sp,
        )
        Text(
            text=resultState,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 30.sp
        )
    }
}