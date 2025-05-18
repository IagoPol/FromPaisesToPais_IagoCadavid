package local.iago.paises.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PaisEntity")
data class Pais ( // las propiedades deben denominarse IGUAL que en el archivo json de SampleApis website.
    val nombre: String,
    val poblacion: Int,
    val abreviatura: String, // dicho requerimiento es necesario con Retrofit, no si pongo los datos a mano en MainActivity.
    val capital: String, // si con Retrofi no cumplo dicho requerimiento, entonces en ejecución no aparecen nombres ni abreviaturas de países.
    val divisa: String,
    val telefono: String,
    @PrimaryKey val idPais: Int,
    @Embedded // sin esto sale en ejecución el error: "Cannot figure out how to save this field into database. You can consider adding a type converter for it."
    val heraldica: Heraldica // gracias al @Embedded NO he necesitado usar TypeConverters.
)