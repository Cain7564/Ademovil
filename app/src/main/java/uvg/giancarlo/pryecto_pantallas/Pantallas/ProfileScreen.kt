package uvg.giancarlo.pryecto_pantallas.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import uvg.giancarlo.pryecto_pantallas.navigation.Screen
import uvg.giancarlo.pryecto_pantallas.Model.SessionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(changeScreen: (String) -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val username = SessionManager.getUserName(context) ?: "Usuario"
    val email = SessionManager.getUserEmail(context) ?: "email@ejemplo.com"

    val headerBrush = Brush.horizontalGradient(colors = listOf(Color(0xFF6A1B9A), Color(0xFF8E44AD)))

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(headerBrush)) {
            IconButton(onClick = {
                changeScreen(Screen.HomeScreen.route)
            }, modifier = Modifier.padding(12.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
            }

            Text(text = "Mi Perfil", modifier = Modifier.align(Alignment.Center), color = Color.White, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        Card(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Avatar", modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF1C40F))
                    .padding(20.dp), tint = Color.White)

                Spacer(Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(username, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text(email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                    Spacer(Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { changeScreen(Screen.CreateRecipeScreen.route) }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)), shape = RoundedCornerShape(12.dp)) {
                            Icon(Icons.Default.Create, contentDescription = "Crear", tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Crear", color = Color.White)
                        }

                        OutlinedButton(onClick = { /* Acción de editar perfil */ }, shape = RoundedCornerShape(12.dp)) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                            Spacer(Modifier.width(8.dp))
                            Text("Editar")
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            ProfileStat("Recetas", "12")
            ProfileStat("Favoritos", "42")
            ProfileStat("Seguidores", "128")
        }

        Spacer(Modifier.height(18.dp))

        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(selected = selectedTabIndex == 0, onClick = { selectedTabIndex = 0 }, text = { Text("Mis Recetas") })
            Tab(selected = selectedTabIndex == 1, onClick = { selectedTabIndex = 1 }, text = { Text("Guardados") })
        }

        Spacer(Modifier.height(12.dp))

        if (selectedTabIndex == 0) {
            Text("Mostrando mis recetas...", modifier = Modifier.fillMaxWidth().padding(16.dp), textAlign = TextAlign.Center)
        } else {
            Text("Mostrando recetas guardadas...", modifier = Modifier.fillMaxWidth().padding(16.dp), textAlign = TextAlign.Center)
        }

        Spacer(Modifier.height(20.dp))

        // Botón cerrar sesión estilizado
        Button(onClick = {
            SessionManager.clearSession(context)
            changeScreen(Screen.LogIn.route)
        }, modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)), shape = RoundedCornerShape(12.dp)) {
            Text("Cerrar sesión", color = Color.White)
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
        Text(label, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}
