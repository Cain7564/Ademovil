package uvg.giancarlo.pryecto_pantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import uvg.giancarlo.pryecto_pantallas.navigation.AppNavigation
import uvg.giancarlo.pryecto_pantallas.ui.theme.Pryecto_PantallasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pryecto_PantallasTheme {
                AppNavigation()
            }
        }
    }
}