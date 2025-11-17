package uvg.giancarlo.pryecto_pantallas.navigation


import kotlinx.serialization.Serializable

sealed interface Screen {
    val route: String

    @Serializable
    data object LogIn : Screen {
        override val route: String get() = "LogIn"
    }

    @Serializable
    data object HomeScreen : Screen {
        override val route: String get() = "HomeScreen"
    }

    @Serializable
    data object CreateRecipeScreen : Screen {
        override val route: String get() = "CreateRecipeScreen"
    }

    @Serializable
    data object ProfileScreen : Screen {
        override val route: String get() = "ProfileScreen"
    }

    @Serializable
    data object FavoritesScreen : Screen {
        override val route: String get() = "FavoritesScreen"
    }
    // Quitar @Serializable para evitar el error
    data class CategoryListScreen(val category: String) : Screen {
        override val route: String get() = "CategoryListScreen/$category"
    }

    @Serializable
    data object RecipeDetailScreen : Screen {
        override val route: String get() = "RecipeDetailScreen"
    }
}
