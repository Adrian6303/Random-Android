package com.example.random

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.random.ui.theme.RandomTheme
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RandomGenerator()
                }
            }
        }
    }
}

@Composable
fun RandomGenerator() {
    var randomNumber by remember { mutableStateOf(0) }
    var minValue by remember { mutableStateOf("") }
    var maxValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                onValueChange = { minValue = it },
                label = { Text("Min Value") },
                modifier = Modifier.padding(16.dp)
            )
        }
        Row{
            TextField(
                value = maxValue,
                onValueChange = { maxValue = it },
                label = { Text("Max Value") },
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row {

            Button(onClick = {
                val min = minValue.toIntOrNull() ?: 0
                val max = maxValue.toIntOrNull() ?: 100
                randomNumber = generateRandomNumber(min, max)
            }) {
                Text(
                    text = "Generate Random Number",
                    style = MaterialTheme.typography.titleLarge
                    )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {

            Text(
                text = "Generated Number: $randomNumber",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

fun generateRandomNumber(min: Int, max: Int): Int {
    return (min..max).random()
}
