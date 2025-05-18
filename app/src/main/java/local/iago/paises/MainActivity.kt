package local.iago.paises

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import local.iago.paises.databinding.ActivityMainBinding

/*
En raras ocasiones, la ejecucion de la aplica muestra el "pop-up message" de que la aplica no responde,
pero incluso cuando sale ese mensaje realmente la aplica sí responde satisfactoriamente.
Simplemente que hay que esperar a que cargue la lista de países i.e. el RecyclerView.
*/
class MainActivity : AppCompatActivity() { // viene por defecto
    // declaro la propiedad del binding indicando que se inicializará después (dentro del onCreate):
    private lateinit var binding: ActivityMainBinding // requiere activar el uso de binding, cosa
    // que yo hice en Project Structure -> Modules -> Enable ViewBinding -> True, lo cual automáticamente
    // pone el código pertinente en build.gradle.kts module:app.

    override fun onCreate(savedInstanceState: Bundle?) { // viene por defecto
        super.onCreate(savedInstanceState) // viene por defecto
        binding = ActivityMainBinding.inflate(layoutInflater) // inicializo el binding invocando el metodo inflate.
        enableEdgeToEdge() // viene por defecto
        setContentView(binding.root) // el setContentView viene por defecto pero con algo diferente dentro del paréntesis.
        // Osease fui yo el que puse dentro del paréntesis binding.root.

        /*
        El siguiente código viene por defecto con excepción de que yo cambié el param del metodo
        setOnApplyWindowInsetsListener, poniéndole binding.contenedorFragmento, ya que en
        activity_main.xml puse la línea de código: android:id="@+id/contenedor_fragmento"
        Al poner binding. AndroidStudio debe "acertar" el resto i.e. 'contenedorFragmento'.
        */
        ViewCompat.setOnApplyWindowInsetsListener(binding.contenedorFragmento) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    } // fin del onCreate
} // fin de la clase MainActivity