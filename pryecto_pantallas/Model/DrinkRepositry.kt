package uvg.giancarlo.pryecto_pantallas.Model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DrinkRepository {

    // IDs de categorías - convenio simple
    // 1 -> Cocktails, 2 -> Mocktails, 3 -> Shakes, 4 -> Frozen
    val categories = mapOf(
        1 to "Cocktails",
        2 to "Mocktails",
        3 to "Shakes",
        4 to "Frozen"
    )

    private const val CURRENT_USER_ID = "usuario_ejemplo@uvg.edu.gt"

    private val userFavorites = mutableSetOf<Int>()

    var selectedCategoryName: String? = null
    var selectedDrinkName: String? = null

    // Mapeo opcional para mostrar solo bebidas "destacadas" por categoría
    val featuredByCategory = mapOf(
        1 to listOf(100)
    )

    // Lista mutable para poder agregar bebidas
    private val drinks = mutableListOf(
        Drink(
            id = 100,
            name = "Tequila Sunrise",
            categoryId = 1,
            description = "Cóctel clásico a base de tequila y jugo de naranja con granadina que crea un efecto degradado parecido a un amanecer.",
            ingredients = listOf("1 ½ oz Tequila", "Jugo de naranja", "¼ oz Granadina", "Hielo", "Guinda o rodaja de limón para decorar"),
            preparation = "En un vaso alto con hielo, agregar el tequila y llenar con jugo de naranja. Añadir la granadina al final para que se asiente y decorar.",
            userId = "admin" // Receta de ejemplo
        ),
        Drink(
            id = 101,
            name = "Tequila Sunrise Frozen",
            categoryId = 4,
            description = "Versión frozen del clásico Tequila Sunrise: todos los sabores pero servidos en textura frappé/helada.",
            ingredients = listOf("1 ½ oz Tequila", "120 ml Jugo de naranja", "¼ oz Granadina", "1 taza de hielo picado", "Guinda/rodaja de limón para decorar"),
            preparation = "En una licuadora poner el hielo picado, el tequila y el jugo de naranja. Licuar hasta obtener textura frozen. Servir en vaso frío y añadir la granadina lentamente para crear el efecto de color; decorar.",
            frozen = true,
            userId = "admin"
        ),
        Drink(
            id = 102,
            name = "Tequila Sunrise Mocktail",
            categoryId = 2,
            description = "Versión sin alcohol que mantiene el perfil de cítricos y el efecto visual del Tequila Sunrise.",
            ingredients = listOf("120 ml Jugo de naranja", "¼ oz Granadina", "30 ml Agua con gas o soda (opcional)", "Hielo", "Guinda/rodaja de limón"),
            preparation = "En un vaso alto con hielo, verter el jugo de naranja y la soda si se desea. Añadir la granadina al final para que se asiente y decorar con guinda o limón."
        ),
        Drink(
            id = 103,
            name = "Mojito de Fresa",
            categoryId = 1,
            description = "Mojito con un toque de fresa: refrescante y afrutado.",
            ingredients = listOf("8 hojas de hierbabuena", "2 cucharadas de azúcar", "1/2 lima (jugo)", "60 ml Ron blanco", "6-8 fresas", "Agua con gas", "Hielo"),
            preparation = "Macerar la hierbabuena con el azúcar y el jugo de lima, añadir las fresas troceadas y machacar ligeramente. Agregar ron, hielo y completar con agua con gas. Revolver y servir.",
            frozen = false
        )
    )

    // --- Nuevas funciones de Favoritos ---

    fun isFavorite(drinkId: Int): Boolean {
        return userFavorites.contains(drinkId)
    }

    fun toggleFavorite(drinkId: Int) {
        if (userFavorites.contains(drinkId)) {
            userFavorites.remove(drinkId)
        } else {
            userFavorites.add(drinkId)
        }
    }

    fun getFavoriteDrinks(): List<Drink> {
        return drinks.filter { userFavorites.contains(it.id) }
    }

    // --- Funciones para Mis Recetas ---

    // Función para agregar nueva bebida (Actualizada para asignar al usuario actual)
    fun addDrink(drink: Drink) {
        // Asigna el userId al usuario actual al crear la receta
        val newDrinkWithUser = drink.copy(userId = CURRENT_USER_ID)
        drinks.add(newDrinkWithUser)
    }

    // Devuelve las recetas creadas por el usuario actual
    fun getMyRecipes(): List<Drink> {
        return drinks.filter { it.userId == CURRENT_USER_ID }
    }

    // --- Funciones existentes ---

    fun getDrinksByCategoryId(categoryId: Int): List<Drink> =
        drinks.filter { it.categoryId == categoryId }

    fun getCategoryName(categoryId: Int): String = categories[categoryId] ?: "Categoría"

    // Obtener bebida por id
    fun getDrinkById(id: Int): Drink? = drinks.find { it.id == id }

    // Obtener bebida por nombre (case-insensitive)
    fun getDrinkByName(name: String): Drink? =
        drinks.find { it.name.equals(name, ignoreCase = true) }

    // Obtener todas las bebidas
    fun getAllDrinks(): List<Drink> = drinks.toList()

    suspend fun getDrinks(context: Context, query: String, categoryId: Int? = null): List<Drink> = withContext(
        Dispatchers.IO) {
        categoryId?.let { drinks.filter { it.categoryId == categoryId } } ?: drinks
    }

    // Ejemplo de función local
    fun getLocalDrinks(): List<Drink> {
        return listOf() // Retorna una lista vacía o datos locales
    }
}