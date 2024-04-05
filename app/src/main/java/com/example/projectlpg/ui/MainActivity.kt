package com.example.projectlpg.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.projectlpg.ui.theme.ProjectLPGTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectLPGTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    MainScreen()
                }
            }
        }
    }
}


