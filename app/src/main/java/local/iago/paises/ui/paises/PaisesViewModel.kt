package local.iago.paises.ui.paises

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import local.iago.paises.model.Pais
// import local.iago.paises.repositories.PaisDbRepository
import local.iago.paises.repositories.PaisRepository

/*
El ViewModel recolecta el StateFlow del Repositorio y lo expone a la UI.
También maneja el estado de carga (_loadingState).
Esta clase recoge un repositorio de tipo mi interfaz PaisRepository (interfaz genérica).
*/
class PaisesViewModel(private val r: PaisRepository): ViewModel() {
    /*
    Creo un MutableStateFlow privado (_paises) para almacenar la lista de países
    y una propiedad (paises) de solo lectura (no Mutable) para acceder (por eso no es private) a ella.
    */
    private val _paises = MutableStateFlow<List<Pais>>(emptyList())
    val paises: StateFlow<List<Pais>> get() = _paises

    /*
    Propiedad estado de carga  y su copia no privada ni Mutable.
    Se crea un MutableStateFlow para el estado de carga, así podemos mostrar un ProgressBar
    mientras se carga la lista de países del repositorio.
    He creado una variable (estadoDeCarga) que indica si está cargando la lista de países o no,
    para poder mostrar un ProgressBar mientras se carga la lista de países del repositorio.
    */
    private val _estadoDeCarga = MutableStateFlow(false)
    val estadoDeCarga: StateFlow<Boolean> get() = _estadoDeCarga

    // cargamos los datos al iniciar el ViewModel
    init {getPaises()} // un bloque init que lo único que hace es invocar la función getPaises()

    // Defino la función getPaises(), la cual puede ser privada, no hace falta que sea accesible desde fuera de la clase.
    private fun getPaises() { // si no le pongo private, me sale el aviso de que puede ser private.
        /*
        viewModelScope está automáticamente disponible en cualquier viewModel que importe la biblioteca lifecycle-viewmodel-ktx.
        A este respecto, decir que en build.gradle.ktx (Module:app) tengo la dependencia: androidx.lifecycle:lifecycle-viewmodel-ktx
        Simplemente invocamos viewModelScope.launch para iniciar una corutina en este "scope".
        */
        viewModelScope.launch {
            _estadoDeCarga.value = true
            try { // bloque try catch finally. En el try recolecto el Flow<List<Pais>> de tal modo que listaPaises es un List<Pais>.
                r.getAll().collect {listaPaises -> // getAll retorna Flow<List<Pais>>, collect me permite trabajar con List<Pais>.
                    _paises.value = listaPaises // listaPaises es de tipo List<Pais> (no puedo asignarle un Flow al value de _paises).
                } // fin del collect
            } catch (e: Exception) { Log.e("PaisesViewModel", "${e.message}") } // en caso de excepción logueo este mensaje.
            finally { _estadoDeCarga.value = false } // al final vuelvo siempre a poner a false el valor de _estadoDeCarga.
        } // fin del launch
    } // fin de fun getPaises()

} // fin de la clase PaisesViewModel