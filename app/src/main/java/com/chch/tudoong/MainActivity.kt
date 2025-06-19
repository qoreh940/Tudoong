package com.chch.tudoong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chch.tudoong.ui.main.MainScreen
import com.example.compose.TudoongTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
             val navController = rememberNavController()

            TudoongTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
                ) {
                    NavHost(navController = navController, startDestination = "main") {
                        composable ("main"){
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}