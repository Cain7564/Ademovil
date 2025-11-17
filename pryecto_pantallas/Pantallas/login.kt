package uvg.giancarlo.pryecto_pantallas.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import uvg.giancarlo.pryecto_pantallas.R
import uvg.giancarlo.pryecto_pantallas.navigation.Screen
import uvg.giancarlo.pryecto_pantallas.Model.SessionManager

@Composable
fun LogIn(modifier: Modifier = Modifier,
        changeScreen: (String) -> Unit
) {
    var selectedTab by remember { mutableStateOf("signup") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bar_logoo),
                contentDescription = "Bar Logo",
                modifier = Modifier
                    .height(50.dp)
                    .padding(end = 12.dp)
            )

            Text(
                text = "Tu bebida ideal a un click de distancia",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(0.dp)
            )
        }

        Text(
            text = "Bienvenidos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Botones de selección Sign Up / Log In
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Botón Sign Up
            Button(
                onClick = { selectedTab = "signup" },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == "signup") Color(0xFFD9C5FF) else Color(0xFFD9D9D9)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = if (selectedTab == "signup") Color.Black else Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botón Log In
            Button(
                onClick = { selectedTab = "login" },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == "login") Color(0xFFD9C5FF) else Color(0xFFD9D9D9)
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Log In",
                    color = if (selectedTab == "login") Color.Black else Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar campos según la selección
        if (selectedTab == "signup") {
            SignUpForm(changeScreen = changeScreen)
        } else {
            LogInForm(changeScreen = changeScreen)
        }
    }
}

@Composable
fun SignUpForm(changeScreen: (String) -> Unit)
{
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputText(
            value = username,
            onValueChange = { username = it },
            label = "Usuario"
        )

        InputText(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        InputText(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true
        )

        InputText(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirmar Contraseña",
            isPassword = true
        )

        if (error.isNotBlank()) {
            Text(text = error, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                // Validaciones básicas
                error = ""
                if (username.isBlank() || email.isBlank() || password.isBlank()) {
                    error = "Todos los campos son obligatorios"
                    return@Button
                }
                if (password != confirmPassword) {
                    error = "Las contraseñas no coinciden"
                    return@Button
                }

                val registered = SessionManager.registerUser(context, email.trim(), username.trim(), password)
                if (!registered) {
                    error = "Ya existe un usuario con ese email"
                    return@Button
                }

                // Guardar sesión y navegar
                SessionManager.saveSession(context, email = email.trim(), username = username.trim())
                changeScreen(Screen.HomeScreen.route)
            },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF865FDE)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Registrarse",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun LogInForm(changeScreen: (String) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputText(
            value = email,
            onValueChange = { email = it },
            label = "Email"
        )

        InputText(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            isPassword = true
        )

        if (error.isNotBlank()) {
            Text(text = error, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(56.dp),
            onClick = {
                error = ""
                if (email.isBlank() || password.isBlank()) {
                    error = "Por favor complete email y contraseña"
                    return@Button
                }

                val valid = SessionManager.validateCredentials(context, email.trim(), password)
                if (!valid) {
                    error = "Credenciales inválidas"
                    return@Button
                }

                // recuperar username registrado y guardar sesión
                val username = SessionManager.getRegisteredUserName(context, email.trim()) ?: "Usuario"
                SessionManager.saveSession(context, email = email.trim(), username = username)
                changeScreen(Screen.HomeScreen.route)
            },

            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF865FDE)
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = "Iniciar Sesión",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun InputText(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF89dff0),
            unfocusedBorderColor = Color.Gray
        )
    )
}