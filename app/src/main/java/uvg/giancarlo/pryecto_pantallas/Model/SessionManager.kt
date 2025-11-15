package uvg.giancarlo.pryecto_pantallas.Model

import android.content.Context
import java.security.MessageDigest

object SessionManager {
    private const val PREFS_NAME = "app_prefs"
    private const val KEY_LOGGED_IN = "is_logged_in"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_USERNAME = "user_name"

    // pref keys per user: user_<email>_password, user_<email>_username
    private fun userPasswordKey(email: String) = "user_${email}_password"
    private fun userNameKey(email: String) = "user_${email}_username"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // Guarda la sesión activa (usuario actual)
    fun saveSession(context: Context, email: String = "", username: String = "") {
        prefs(context).edit().apply {
            putBoolean(KEY_LOGGED_IN, true)
            putString(KEY_EMAIL, email)
            putString(KEY_USERNAME, username)
            apply()
        }
    }

    fun clearSession(context: Context) {
        prefs(context).edit().apply {
            putBoolean(KEY_LOGGED_IN, false)
            remove(KEY_EMAIL)
            remove(KEY_USERNAME)
            apply()
        }
    }

    fun isLoggedIn(context: Context): Boolean = prefs(context).getBoolean(KEY_LOGGED_IN, false)

    fun getUserEmail(context: Context): String? = prefs(context).getString(KEY_EMAIL, null)

    fun getUserName(context: Context): String? = prefs(context).getString(KEY_USERNAME, null)

    // Registrar un usuario. Devuelve true si se registró correctamente, false si ya existe.
    fun registerUser(context: Context, email: String, username: String, password: String): Boolean {
        val p = prefs(context)
        val pwKey = userPasswordKey(email)
        if (p.contains(pwKey)) return false // usuario ya existe
        val hashed = hash(password)
        p.edit().apply {
            putString(pwKey, hashed)
            putString(userNameKey(email), username)
            apply()
        }
        return true
    }

    // Validar credenciales
    fun validateCredentials(context: Context, email: String, password: String): Boolean {
        val p = prefs(context)
        val stored = p.getString(userPasswordKey(email), null) ?: return false
        return stored == hash(password)
    }

    // Obtener el username registrado para un email
    fun getRegisteredUserName(context: Context, email: String): String? = prefs(context).getString(userNameKey(email), null)

    // Hash simple SHA-256 (no salting para simplicidad; recomendaría sal y algoritmos más robustos en producción)
    private fun hash(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val bytes = md.digest(input.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
