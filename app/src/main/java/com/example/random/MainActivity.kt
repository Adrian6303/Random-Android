package com.example.random

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.random.ui.theme.RandomTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    RandomGenerator()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RandomGenerator() {
    var randomNumber by remember { mutableIntStateOf(0) }
    var minValue by remember { mutableStateOf("0") }
    var maxValue by remember { mutableStateOf("100") }
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Random Number Generator",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(20.dp, 60.dp)
            )

            Row {
                Text(
                    text = "Enter the range of numbers:",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row {
                TextField(
                    value = minValue,
                    onValueChange = { if (it.length <= 9) minValue = it.filter { char -> char != '\n' } },
                    label = { Text("Min Value") },
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row {
                TextField(
                    value = maxValue,
                    onValueChange = { if (it.length <= 9) maxValue = it.filter { char -> char != '\n' } },
                    label = { Text("Max Value") },
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row {
                val scope = rememberCoroutineScope()
                Button(onClick = {
                    val min = minValue.toIntOrNull()
                    val max = maxValue.toIntOrNull()
                    keyboardController?.hide()
                    scope.launch {
                        if (min == null || max == null || min > max || min < 0 || max < 0) {
                            randomNumber = -1
                            snackbarHostState.showSnackbar("Invalid range. Please enter valid numbers.")
                        } else {
                            randomNumber = generateRandomNumber(min, max)
                        }
                    }

                }) {
                    Text(
                        text = "Generate Random Number", style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row {

                if (randomNumber >= 0) {
                    Text(
                        text = "Generated Number: $randomNumber",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    Text(
                        text = "Generated Number: ERROR",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            SnackbarHost(hostState = snackbarHostState)


        }

        Text(
            text = "© 2024 Schiopu Adrian",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(50.dp)
        )
    }

}

fun generateRandomNumber(min: Int, max: Int): Int {
    return (min..max).random()
}
