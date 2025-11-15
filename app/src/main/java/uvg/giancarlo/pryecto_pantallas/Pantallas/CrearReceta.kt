package uvg.giancarlo.pryecto_pantallas.Pantallas

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uvg.giancarlo.pryecto_pantallas.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(changeScreen: (String) -> Unit, onBackClick: () -> Unit = {}) {
    var recipeName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comparte tu Receta", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackClick()
                        changeScreen(Screen.ProfileScreen.route)
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = { changeScreen(Screen.HomeScreen.route)  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(50.dp),
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
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción Corta") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
            item {
                Text(
                    "Ingredientes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                /* ... Componentes de ingredientes ... */
            }
            item {
                Text(
                    "Pasos de Preparación",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                /* ... Componentes de pasos ... */
            }
            item {
                Button(onClick = { } ) {
                    Text("Subir Imagen")
                }
            }
        }
    }
}