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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chch.tudoong.presentation.ui.main.MainScreen
import com.chch.tudoong.presentation.viewmodel.TudoongViewModel
import com.example.compose.TudoongTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                            val viewModel : TudoongViewModel = hiltViewModel()
                            MainScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}