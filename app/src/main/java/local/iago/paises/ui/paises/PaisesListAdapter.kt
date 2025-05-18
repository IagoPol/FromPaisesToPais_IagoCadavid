package local.iago.paises.ui.paises

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import local.iago.paises.R
import local.iago.paises.databinding.ItemPaisBinding
import local.iago.paises.model.Pais

// OJO: PaisesListAdapter no está siendo utilizado porque en su lugar estoy usando PaisesAdapter.
class PaisesListAdapter : ListAdapter<Pais, PaisesListAdapter.PaisViewHolder>(PaisDiffCallback()) {
    class PaisViewHolder(
        private val binding: ItemPaisBinding
        // private val onItemClickListener: (Int) -> Unit // se usa en el setOnClickListener
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(pais: Pais) {
            pais?.let {
                binding.ivFlag.setImageResource(R.drawable.randomflag)
                binding.tvNombre.text = it.nombre
                binding.tvAbreviatura.text = it.abreviatura
            }
        }
    } // fin del body de class PaisViewHolder

    private class PaisDiffCallback : DiffUtil.ItemCallback<Pais>() {
        override fun areItemsTheSame(oldItem: Pais, newItem: Pais): Boolean {
            return  oldItem.idPais == newItem.idPais
        }
        override fun areContentsTheSame(oldItem: Pais, newItem: Pais): Boolean {
            return  oldItem == newItem
        }
    } // fin de la clase interna PaisDiffCallback

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaisViewHolder {
        val inflador = LayoutInflater.from(parent.context)
        val b = ItemPaisBinding.inflate(inflador, parent, false)
        return PaisViewHolder(b)
    }

    override fun onBindViewHolder(holder: PaisViewHolder, position: Int) {
        val p = getItem(position)
        holder.bind(p)
    }
}