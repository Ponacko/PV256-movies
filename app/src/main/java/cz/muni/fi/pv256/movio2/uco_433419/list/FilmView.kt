package cz.muni.fi.pv256.movio2.uco_433419.list

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.graphics.Palette
import android.util.AttributeSet
import com.squareup.picasso.Picasso
import cz.muni.fi.pv256.movio2.uco_433419.model.Film
import kotlinx.android.synthetic.main.film_item.view.*


class FilmView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        filmImage.layout(0, 0, filmImage.measuredWidth, filmImage.measuredHeight)
        filmName.layout(0, filmImage.measuredHeight - 75,
                filmImage.measuredWidth, filmImage.measuredHeight)
        filmRating.layout(filmImage.measuredWidth - 150, filmImage.measuredHeight - 75,
                filmImage.measuredWidth, filmImage.measuredHeight)
    }


    fun setFilmProperties(film: Film) {
        filmName.text = film.original_title
        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w500" +
                        film.poster_path)
                .into(filmImage)
        filmRating.text = film.popularity.toString()
//        val semitransparentColor = getColorFromResource(bitmap, 0.75f)
//        val solidColor = getColorFromResource(bitmap, 1f)
//        val transparentColor = getColorFromResource(bitmap, 0f)
//        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
//                intArrayOf(transparentColor, solidColor))
//        filmRating.background = gradientDrawable
    }

    private fun getColorFromResource(bitmap: Bitmap, opacity: Float): Int {
        val palette = Palette.from(bitmap).generate()
        val color = palette.getDarkVibrantColor(0x000)
        return Color.argb(Math.round(Color.alpha(color) * opacity),
                Color.red(color), Color.green(color), Color.blue(color))
    }
}