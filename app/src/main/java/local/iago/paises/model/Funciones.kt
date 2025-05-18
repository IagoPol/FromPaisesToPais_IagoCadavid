package local.iago.paises.model

import android.graphics.BitmapFactory
import android.widget.ImageView
import local.iago.paises.R

fun ImageView.setImageFromBytes(imageBytes: ByteArray?) {
    if (imageBytes != null) {
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        this.setImageBitmap(bitmap) // al poner this, se pone en blanco ImageView (inicialmente está en gris)
    } // setImageBitmap es un metodo de android.widget.ImageView que retorna void y toma como param un Bitmap.
    // lo que hace es: sets a bitmap (el param) as the content of this ImageView.
    else {
        this.setImageResource(R.drawable.flagwithemblem) // bandera de Moldavia con emblema del país.
    }
}