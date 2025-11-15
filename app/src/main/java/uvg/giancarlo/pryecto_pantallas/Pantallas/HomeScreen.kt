package uvg.giancarlo.pryecto_pantallas.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uvg.giancarlo.pryecto_pantallas.Model.Drink
import uvg.giancarlo.pryecto_pantallas.Model.DrinkRepository
import uvg.giancarlo.pryecto_pantallas.R
import uvg.giancarlo.pryecto_pantallas.navigation.Screen

@Composable
fun HomeScreen(
    changeScreen: (String) -> Unit,
    onProfileClick: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {},
) {
    var searchQuery by remember { mutableStateOf("") }
    val allDrinks = DrinkRepository.getAllDrinks()
    val filteredDrinks = remember(searchQuery) {
        if (searchQuery.isBlank()) allDrinks
        else allDrinks.filter { drink ->
            drink.name.contains(searchQuery, ignoreCase = true) ||
            drink.ingredients.any { it.contains(searchQuery, ignoreCase = true) }
        }
    }

    // Paleta temática (morados y acentos aqua)
    val gradient = Brush.verticalGradient(colors = listOf(Color(0xFFF7F3FB), Color(0xFFF1F8FB)))

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF6A1B9A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Logotipo",
                            modifier = Modifier.size(28.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Drinkspiration",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF222222)
                        )
                        Text(text = "Recetas y bebidas", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                }

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil de usuario",
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            onProfileClick()
                            changeScreen(Screen.ProfileScreen.route)
                        }
                        .padding(8.dp),
                    tint = Color(0xFF6A1B9A)
                )
            }
            Spacer(Modifier.height(20.dp))
        }

        item {
            // Search box estilizado
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                placeholder = { Text("Buscar bebidas, ingredientes...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon", tint = Color(0xFF6A1B9A)) },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFF6A1B9A)
                )
            )
            Spacer(Modifier.height(20.dp))
        }

        item {
            Text(text = "Categorías", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                item {
                    CategoryCard("Cocktails", Color(0xFF8E44AD), onClick = {
                        onCategoryClick("Cocktails")
                        changeScreen(Screen.CategoryListScreen("Cocktails").route)
                    })
                }
                item {
                    CategoryCard("Mocktails", Color(0xFF1ABC9C), onClick = {
                        onCategoryClick("Mocktails")
                        changeScreen(Screen.CategoryListScreen("Mocktails").route)
                    })
                }
                item {
                    CategoryCard("Shakes", Color(0xFFF1C40F), onClick = {
                        onCategoryClick("Shakes")
                        changeScreen(Screen.CategoryListScreen("Shakes").route)
                    })
                }
                item {
                    CategoryCard("Frozen", Color(0xFF00BFFF), onClick = {
                        onCategoryClick("Frozen")
                        changeScreen(Screen.CategoryListScreen("Frozen").route)
                    })
                }
            }
            Spacer(Modifier.height(24.dp))
        }

        item {
            Text(text = "Recetas destacadas", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            // Tarjeta grande de receta destacada
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C3E)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("Mojito de Fresa", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.ExtraBold)
                        Spacer(Modifier.height(8.dp))
                        Text("Refrescante y afrutado", color = Color(0xFFBDBDBD))
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row {
                            repeat(5) { Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFF1C40F), modifier = Modifier.size(18.dp)) }
                        }
                        Spacer(Modifier.weight(1f))
                        Button(onClick = {
                            // navegar a detalle
                            changeScreen(Screen.RecipeDetailScreen.route)
                        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), shape = RoundedCornerShape(16.dp)) {
                            Text("Ver receta", color = Color.White)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }

        item {
            Text(text = "Recetas recientes", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            val lastDrinkName = DrinkRepository.selectedDrinkName
            val lastDrink = lastDrinkName?.let { name ->
                DrinkRepository.getDrinkByName(name)
            }
            if (lastDrink != null) {
                RecentRecipeCard(
                    name = lastDrink.name,
                    onClick = {
                        changeScreen(Screen.RecipeDetailScreen.route)
                    },
                    changeScreen = { screen -> changeScreen(screen) }
                )
            } else {
                RecentRecipeCard(
                    name = "Mojito de Fresa",
                    onClick = {
                        changeScreen(Screen.RecipeDetailScreen.route)
                    },
                    changeScreen = { screen -> changeScreen(screen) }
                )
            }
            Spacer(Modifier.height(30.dp))
        }

        item {
            Text(text = "Resultados de búsqueda", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            if (filteredDrinks.isEmpty()) {
                Text("No se encontraron bebidas con ese nombre o ingrediente.", color = Color.Gray, modifier = Modifier.padding(16.dp))
            } else {
                filteredDrinks.forEach { drink ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { changeScreen(Screen.RecipeDetailScreen.route) },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(drink.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text(drink.description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                            Spacer(Modifier.height(4.dp))
                            Text("Ingredientes: " + drink.ingredients.joinToString(", "), style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
internal fun CategoryCard(name: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width = 140.dp, height = 180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = name, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(text = "Explorar", color = Color.White.copy(alpha = 0.85f))
        }
    }
}

@Composable
internal fun RecentRecipeCard(
    name: String = "Mocktail",
    onClick: (String) -> Unit,
    changeScreen: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick(name) },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF1C40F)), contentAlignment = Alignment.Center) {
                Text("🍓", fontSize = 28.sp)
            }

            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Favorite, contentDescription = "Likes", tint = Color(0xFFE91E63), modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("534 • 4 pasos", color = Color.Gray)
                }
            }

            Button(onClick = { changeScreen(Screen.RecipeDetailScreen.route) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)), shape = RoundedCornerShape(12.dp)) {
                Text("Ver", color = Color.White)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, widthDp = 360, heightDp = 780)
fun HomeScreenPreview() {
    HomeScreen(changeScreen = {})
}