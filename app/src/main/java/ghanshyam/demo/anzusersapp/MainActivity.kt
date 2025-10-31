package ghanshyam.demo.anzusersapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import ghanshyam.demo.anzusersapp.core.navigation.NavGraph
import ghanshyam.demo.anzusersapp.ui.theme.UsersListAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UsersListAppTheme {
                    NavGraph()
            }
        }
    }
}
