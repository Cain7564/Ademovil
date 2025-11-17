package uvg.giancarlo.pryecto_pantallas.Pantallas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import uvg.giancarlo.pryecto_pantallas.Model.Drink
import uvg.giancarlo.pryecto_pantallas.Model.DrinkRepository
import uvg.giancarlo.pryecto_pantallas.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(changeScreen: (String) -> Unit, onBackClick: () -> Unit = {}) {
    var recipeName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf("") }
    var preparation by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedCategory by remember { mutableStateOf<Int?>(null) }

    // Launcher para seleccionar imagen de la galería
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val purpleColor = Color(0xFF6A1B9A)
    val orangeColor = Color(0xFFE65100)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comparte tu Receta", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                        changeScreen(Screen.ProfileScreen.route)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = purpleColor
                )
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    // Validar que todos los campos estén llenos
                    if (recipeName.isNotBlank() &&
                        description.isNotBlank() &&
                        ingredients.isNotBlank() &&
                        preparation.isNotBlank() &&
                        selectedCategory != null) {

                        // Crear nueva bebida
                        val newDrink = Drink(
                            id = (DrinkRepository.getAllDrinks().maxOfOrNull { it.id } ?: 0) + 1,
                            name = recipeName,
                            categoryId = selectedCategory!!,
                            description = description,
                            ingredients = ingredients.split("\n").filter { it.isNotBlank() },
                            preparation = preparation,
                            frozen = selectedCategory == 4 // Si es categoría Frozen
                        )

                        // Agregar al repositorio
                        DrinkRepository.addDrink(newDrink)

                        // Navegar a HomeScreen
                        changeScreen(Screen.HomeScreen.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = orangeColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Publicar Receta")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = recipeName,
                    onValueChange = { recipeName = it },
                    label = { Text("Nombre de la Bebida") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.5f),
                        focusedLabelColor = purpleColor,
                        cursorColor = purpleColor
                    )
                )
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción Corta") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.5f),
                        focusedLabelColor = purpleColor,
                        cursorColor = purpleColor
                    )
                )
            }

            // Selector de Categoría 2x2
            item {
                Text(
                    "Categoría",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryButton(
                            text = "Cocktail",
                            categoryId = 1,
                            isSelected = selectedCategory == 1,
                            onClick = { selectedCategory = 1 },
                            modifier = Modifier.weight(1f),
                            purpleColor = purpleColor
                        )
                        CategoryButton(
                            text = "Mocktail",
                            categoryId = 2,
                            isSelected = selectedCategory == 2,
                            onClick = { selectedCategory = 2 },
                            modifier = Modifier.weight(1f),
                            purpleColor = purpleColor
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryButton(
                            text = "Shakes",
                            categoryId = 3,
                            isSelected = selectedCategory == 3,
                            onClick = { selectedCategory = 3 },
                            modifier = Modifier.weight(1f),
                            purpleColor = purpleColor
                        )
                        CategoryButton(
                            text = "Frozen",
                            categoryId = 4,
                            isSelected = selectedCategory == 4,
                            onClick = { selectedCategory = 4 },
                            modifier = Modifier.weight(1f),
                            purpleColor = purpleColor
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = ingredients,
                    onValueChange = { ingredients = it },
                    label = { Text("Ingredientes (uno por línea)") },
                    placeholder = { Text("Ej:\n1 ½ oz Tequila\nJugo de naranja\nHielo") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.5f),
                        focusedLabelColor = purpleColor,
                        cursorColor = purpleColor
                    )
                )
            }

            item {
                OutlinedTextField(
                    value = preparation,
                    onValueChange = { preparation = it },
                    label = { Text("Pasos de Preparación") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = purpleColor,
                        unfocusedBorderColor = purpleColor.copy(alpha = 0.5f),
                        focusedLabelColor = purpleColor,
                        cursorColor = purpleColor
                    )
                )
            }

            // Cuadro punteado para mostrar imagen
            item {
                Text(
                    "Imagen de la Bebida",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(
                            width = 2.dp,
                            color = purpleColor.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            imagePickerLauncher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "IMAGEN",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = purpleColor.copy(alpha = 0.5f),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Toca para seleccionar",
                                fontSize = 14.sp,
                                color = purpleColor.copy(alpha = 0.3f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Espaciador final
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun CategoryButton(
    text: String,
    categoryId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    purpleColor: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(45.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) purpleColor else Color.White,
            contentColor = if (isSelected) Color.White else purpleColor
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (!isSelected) ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp) else null
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}