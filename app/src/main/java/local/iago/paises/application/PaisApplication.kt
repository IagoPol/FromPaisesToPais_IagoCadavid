package local.iago.paises.application

import android.app.Application // clase base para mantener el estado de la aplicación global.
import local.iago.paises.repositories.PaisDbRepository
import local.iago.paises.repositories.PaisRepository

// mi clase PaisApplication extiende de la clase de android Application.
class PaisApplication : Application() {
    /*
    PaisApplication solamente contiene una propiedad llamada 'repository' de tipo la interfaz
    PaisRepository (que también fue creada por mí). Los miembros de esta interfaz son sobreescritos
    por PaisDbRepository (una clase creada por mí).
    */
    /*
    Al emplear by lazy, la inicialización ocurre vagamente ("lazily") y es "thread-safe" por defecto.
    Debido al by lazy, la variable NO es inicializada cuando es declarada. Esto se debe a que by lazy
    difiere la inicialización de la variable hasta que sea accedida por primera vez.
    El bloque "lazy" es ejecutado SOLO LA PRIMERA VEZ que la variable es accedida.
    El resultado del bloque se almacena en caché, por lo que los accesos posteriores devuelven el mismo valor.
    */
    val repository: PaisRepository by lazy { // by lazy solamente se puede usar con 'val' (variables inmutables).
        PaisDbRepository.getInstance(this) // llamo al metodo "estático" getInstance de  mi clase PaisDbRepository
    }
    /*
    La propiedad delegada más popular es lazy. Implements el patrón de la propiedad lazy, tal que
    pospone la inicialización de valor de solo lectura (val) hasta que este valor es necesitado por primera vez.
    */
} // fin de la clase PaisApplication.