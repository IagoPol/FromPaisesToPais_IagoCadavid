package local.iago.paises.ui.paises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
// import local.iago.paises.R
import local.iago.paises.databinding.ItemPaisBinding
import local.iago.paises.model.Pais
import local.iago.paises.model.setImageFromBytes // lo usa el let de fun bind de class PaisViewHolder

class PaisesAdapter( // la alternativa es PaisesListAdapter. Ahora mismo el proyecto está usando PaisesAdapter.
    private val onItemClickListener: (Int) -> Unit // se usa en el onCreateViewHolder de esta clase.
): RecyclerView.Adapter<PaisesAdapter.PaisViewHolder>() {
    private var paises: List<Pais> = emptyList()

    /*
    El RecyclerView espera que una vista de elemento esté envuelta en una instancia de ViewHolder.
    Un ViewHolder almacena una referencia a la vista de un elemento.
    El ViewHolder contendrá las vistas en el layout para un solo item de lista.
    Dentro de la clase PaisesAdapter, defino un ViewHolder agregando una clase PaisViewHolder
    que herede de RecyclerView.ViewHolder.
    Mi clase interna PaisViewHolder tiene dos propiedades y params de su constructor:
    'binding' (lo básico para el RecyclerView) y 'onItemClickListener' (para ir de la lista a la vista de detalle).
    Más en concreto sobre el binding de PaisViewHolder:
    En el constructor de PaisViewHolder, se recibe una propiedad de tipo binding para almacenarlo.
    AndroidStudio sobre PaisViewHolder ha de asumir que el binding que paso a su constructor tiene el tipo ItemPaisBinding.
    Nota: el ViewHolder puede declararse dentro o fuera de la clase Adapter.
    En este caso, como la lógica es sencilla, lo hice dentro del Adapter. Al ser interna,
    el Adapter hereda de RecyclerView.Adapter<PaisesAdapter.PaisViewHolder>()
    La clase base ViewHolder luego retendrá la vista en una propiedad llamada itemView.
    Un RecyclerView nunca crea Views por sí mismo. Siempre crea ViewHolders, que llevan consigo sus itemViews.
    */
    class PaisViewHolder(
        private val binding: ItemPaisBinding,
        private val onItemClickListener: (Int) -> Unit // se usa en el setOnClickListener
    ): RecyclerView.ViewHolder(binding.root) { // Inmediatamente, se pasa la vista raíz como argumento
        // al constructor de RecyclerView.ViewHolder.
        fun bind(pais: Pais) {
            pais?.let { // "unnecessary safe call on a non-null receiver of type Pais" (solo un aviso)
                // A cada ImageView/TextView,
                // le asigno el valor de la propiedad que sea de la instancia que sea de la clase Pais.
                // binding.ivFlag.setImageResource(R.drawable.randomflag)
                binding.ivFlag.setImageFromBytes(it.heraldica.flag)
                binding.tvNombre.text = it.nombre
                binding.tvAbreviatura.text = it.abreviatura
                binding.root.setOnClickListener { onItemClickListener(pais.idPais) }
            } // fin del let.
        } // fin del body de la función bind de la clase PaisViewHolder.

    } // fin del body de class PaisViewHolder

    /*
    A continuación creo:
    un metodo updateList que permite actualizar la lista de paises y notificar al Adapter que los datos han cambiado.
    Este metodo se llamará desde el PaisesFragment cuando se recojan los datos del ViewModel (en launch del onViewCreated).
    Además, el metodo invoca a notifyDataSetChanged() para que la lista se actualice.
    */
    /*
    Defino una función para actualizar la lista:
    Osease creo un metodo updateList que permite actualizar la lista de paises
    y notificar al Adapter que los datos han cambiado. Para esto invoco notifyDataSetChanged(), que
    me lanza al aviso: "It will always be more efficient to use more specific change events if you can.
    Rely on notifyDataSetChanged as a last resort."
    */
    fun actualizarLista(nuevosPaises: List<Pais>){ // esta función se invoca en PaisesFragment, que es donde concretamos qué pasamos como param.
        paises = nuevosPaises // más en concreto, esta función la invocamos en el launch del onViewCreated de PaisesFragment.
        notifyDataSetChanged() // el param que le pasamos a esta función es el List<Pais> obtenido con el collect llamado desde 'paises' de PaisesViewModel,
    } // lo cual es una copia de _paises, a quien le asignamos como valor el List<Pais> derivado de aplicar el collect sobre getAll() de PaisRepository.

    // Sobreescribo las 3 funciones de RecyclerView.Adapter :
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        val inflador = LayoutInflater.from(parent.context)
        // Inflo y vinculo un ItemPaisBinding:
        val b = ItemPaisBinding.inflate(inflador,parent,false)
        // y paso el binding resultante a una nueva instancia de PaisViewHolder:
        return PaisViewHolder(b, onItemClickListener) // añado el segundo param onItemClickListener para...
    } //... poder ir desde la lista a la vista de detalle del país seleccionado.

    // onBindViewHolder(…) llama a bind(…) en el ViewHolder para vincular los datos del país con la vista:
    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val unPais = paises[position]
        holder.bind(unPais)
    }

    /*
    El metodo getItemCount() devuelve el número de elementos en la lista de países.
    Cuando el RecyclerView necesite saber cuántos elementos hay en el conjunto de datos que lo respalda
    (por ejemplo, cuando se inicia),
    llamará a Adapter.getItemCount().
    */
    override fun getItemCount() = paises.size

}