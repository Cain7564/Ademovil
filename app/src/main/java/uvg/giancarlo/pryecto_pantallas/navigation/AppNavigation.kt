package uvg.giancarlo.pryecto_pantallas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import uvg.giancarlo.pryecto_pantallas.Pantallas.CategoryListScreen
import uvg.giancarlo.pryecto_pantallas.Pantallas.CreateRecipeScreen
import uvg.giancarlo.pryecto_pantallas.Pantallas.FavoritesScreen
import uvg.giancarlo.pryecto_pantallas.Pantallas.HomeScreen
import uvg.giancarlo.pryecto_pantallas.Pantallas.LogIn
import uvg.giancarlo.pryecto_pantallas.Pantallas.ProfileScreen
import uvg.giancarlo.pryecto_pantallas.Pantallas.RecipeDetailScreen
import uvg.giancarlo.pryecto_pantallas.Model.SessionManager

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val startDest = if (SessionManager.isLoggedIn(context)) "HomeScreen" else "LogIn"

    NavHost(
        navController = navController,
        startDestination = startDest
    ) {
        composable("LogIn") {
            LogIn(
                changeScreen = { route: String ->
                    navController.navigate(route)
                }
            )
        }
        composable("HomeScreen") {
            HomeScreen(
                changeScreen = { route: String ->
                    navController.navigate(route)
                }
            )
        }
        composable("ProfileScreen") {
            ProfileScreen(
                changeScreen = { route: String ->
                    navController.navigate(route)
                }
            )
        }
        composable("FavoritesScreen") {
            FavoritesScreen(
                changeScreen = { route: String ->
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = "CategoryListScreen/{category}",
            arguments = listOf(navArgument("category") { this.type = NavType.StringType })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            CategoryListScreen(
                changeScreen = { route: String ->
                    // Navegación correcta para rutas con parámetro
                    if (route.startsWith("CategoryListScreen/")) {
                        val cat = route.substringAfter("CategoryListScreen/")
                        navController.navigate("CategoryListScreen/$cat")
                    } else {
                        navController.navigate(route)
                    }
                },
                screen = Screen.CategoryListScreen(category)
            )
        }
        composable("RecipeDetailScreen") {
            RecipeDetailScreen(
                changeScreen = { route: String ->
                    navController.navigate(route)
                }
            )
        }
        composable("CreateRecipeScreen") {
            CreateRecipeScreen(
                changeScreen = { route: String ->
                    navController.navigate(route)
                }
            )
        }
    }
}