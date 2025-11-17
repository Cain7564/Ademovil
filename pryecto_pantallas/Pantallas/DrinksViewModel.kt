package uvg.giancarlo.pryecto_pantallas.Pantallas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uvg.giancarlo.pryecto_pantallas.Model.Drink
import uvg.giancarlo.pryecto_pantallas.Model.DrinkRepository

class DrinksViewModel(app: Application) : AndroidViewModel(app) {
    private val _drinks = MutableStateFlow<List<Drink>>(emptyList())
    val drinks: StateFlow<List<Drink>> = _drinks

    fun loadDrinks(categoryId: Int, query: String = "") {
        viewModelScope.launch {
            val result = DrinkRepository.getDrinks(getApplication(), query, categoryId)
            _drinks.value = result
        }
    }
}
