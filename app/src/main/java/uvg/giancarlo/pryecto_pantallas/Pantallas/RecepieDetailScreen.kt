package uvg.giancarlo.pryecto_pantallas.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import uvg.giancarlo.pryecto_pantallas.Model.DrinkRepository
import uvg.giancarlo.pryecto_pantallas.navigation.Screen


@Composable
fun RecipeDetailScreen(
    changeScreen: (String) -> Unit,
    drinkId: Int? = null,
    recipeName: String? = null,
    onBack: () -> Unit = {}
) {
    val effectiveRecipeName = recipeName ?: DrinkRepository.selectedDrinkName
    val drink = when {
        drinkId != null -> DrinkRepository.getDrinkById(drinkId)
        effectiveRecipeName != null -> DrinkRepository.getDrinkByName(effectiveRecipeName)
        else -> null
    }

    val isFavorite = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "< Atrás", modifier = Modifier.clickable {
                onBack(); changeScreen(Screen.HomeScreen.route)
            }.padding(8.dp), color = Color(0xFF6A1B9A))

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { /* compartir */ }) { Icon(Icons.Filled.Share, contentDescription = "Compartir", tint = Color(0xFF6A1B9A)) }
        }

        if (drink == null) {
            Text("Receta no encontrada", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 32.dp))
            return@Column
        }

        // Header con imagen placeholder de vaso y degradado ligero
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Brush.horizontalGradient(listOf(Color(0xFF8E44AD), Color(0xFF0288D1)))), contentAlignment = Alignment.Center)
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.12f)), contentAlignment = Alignment.Center) {
                    Text("🍹", fontSize = 42.sp)
                }

                Spacer(Modifier.height(10.dp))
                Text(drink.name, style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                Text(text = drink.description, color = Color(0xFFE8EAF6), textAlign = TextAlign.Center)
            }
        }

        Spacer(Modifier.height(12.dp))

        // chips y acciones
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row {
                AssistChip(onClick = {}, label = { Text(DrinkRepository.getCategoryName(drink.categoryId), color = Color(0xFF4A148C)) }, colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFEEE7F6)))
                Spacer(Modifier.width(8.dp))
                if (drink.frozen) AssistChip(onClick = {}, label = { Text("FROZEN", color = Color(0xFF01579B)) }, colors = AssistChipDefaults.assistChipColors(containerColor = Color(0xFFE1F5FE)))
            }

            IconButton(onClick = { isFavorite.value = !isFavorite.value }) {
                if (isFavorite.value) Icon(Icons.Filled.Favorite, contentDescription = "Favorito", tint = Color(0xFFE91E63)) else Icon(Icons.Outlined.FavoriteBorder, contentDescription = "No favorito")
            }
        }

        Spacer(Modifier.height(12.dp))

        // Secciones: ingredientes y preparación con estilo de tarjetas
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Ingredientes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    if (drink.ingredients.isEmpty()) {
                        Text("No hay ingredientes listados", color = Color.Gray)
                    } else {
                        for ((i, ing) in drink.ingredients.withIndex()) {
                            Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
                                Text(text = "${i+1}.", fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
                                Text(text = ing)
                            }
                        }
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Preparación", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text(text = drink.preparation.ifBlank { "Instrucciones no disponibles." })
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { /* iniciar temporizador */ }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)), shape = RoundedCornerShape(10.dp)) {
                            Icon(Icons.Filled.Notifications, contentDescription = "Timer", tint = Color.White)
                            Spacer(Modifier.width(6.dp))
                            Text("Iniciar", color = Color.White)
                        }

                        OutlinedButton(onClick = { /* compartir */ }) {
                            Text("Compartir")
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailPreview() {
    RecipeDetailScreen(changeScreen = {}, drinkId = 100)
}