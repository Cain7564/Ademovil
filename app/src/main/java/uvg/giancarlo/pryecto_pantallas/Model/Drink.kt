package uvg.giancarlo.pryecto_pantallas.Model

data class Drink(
    val id: Int,
    val name: String,
    val categoryId: Int,
    val description: String = "",
    val ingredients: List<String> = emptyList(),
    val preparation: String = "",
    val frozen: Boolean = false,
    val userId: String? = null
)