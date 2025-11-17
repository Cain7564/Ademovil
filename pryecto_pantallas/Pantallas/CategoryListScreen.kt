package uvg.giancarlo.pryecto_pantallas.Pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uvg.giancarlo.pryecto_pantallas.Model.DrinkRepository
import uvg.giancarlo.pryecto_pantallas.navigation.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import uvg.giancarlo.pryecto_pantallas.Pantallas.DrinksViewModel

@Composable
fun CategoryListScreen(
    changeScreen: (String) -> Unit,
    screen: Screen.CategoryListScreen
) {
    val category = screen.category
    val categoryId = DrinkRepository.categories.entries.find { it.value.equals(category, ignoreCase = true) }?.key
    val viewModel: DrinksViewModel = viewModel()
    val drinks = viewModel.drinks.collectAsState().value
    LaunchedEffect(categoryId) {
        if (categoryId != null) viewModel.loadDrinks(categoryId)
    }
    val title = if (category.isNotBlank()) category else DrinkRepository.selectedCategoryName ?: "Bebidas"

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "< Atrás",
                color = Color(0xFF6A1B9A),
                modifier = Modifier
                    .clickable {
                        changeScreen(Screen.HomeScreen.route)
                    }
                    .padding(8.dp)
            )

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.width(56.dp))
        }

        // Lista de bebidas
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(drinks) { drink ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            DrinkRepository.selectedDrinkName = drink.name
                            changeScreen(Screen.RecipeDetailScreen.route)
                        },
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = drink.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Etiqueta frozen si aplica
                                if (drink.frozen) {
                                    Text(
                                        text = "FROZEN",
                                        color = Color(0xFF0288D1),
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier
                                            .padding(start = 6.dp)
                                    )
                                }
                            }

                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = drink.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                maxLines = 3
                            )
                        }
                        Text(
                            text = "Ver",
                            color = Color(0xFF6A1B9A),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryListPreview() {
    CategoryListScreen(changeScreen = {}, screen = Screen.CategoryListScreen("Categoría de Ejemplo"))
}
