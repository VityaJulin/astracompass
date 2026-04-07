package com.vityajulin.astracompass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.vityajulin.astracompass.ui.screens.CompassViewModel
import com.vityajulin.astracompass.ui.screens.MainScreen
import com.vityajulin.astracompass.ui.theme.AstracompassTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AstracompassTheme {
                // Получаем ViewModel через hiltViewModel()
                val viewModel: CompassViewModel = hiltViewModel()
                MainScreen(viewModel)
            }
        }
    }
}
