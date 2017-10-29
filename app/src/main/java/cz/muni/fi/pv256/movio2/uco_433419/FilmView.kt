package cz.muni.fi.pv256.movio2.uco_433419

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.constraint.ConstraintLayout
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.graphics.Palette
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.film_item.view.*


class FilmView(context: Context, attrs : AttributeSet) : ConstraintLayout(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        filmImage.layout(0,0,filmImage.measuredWidth, filmImage.measuredHeight)
        filmName.layout(0, filmImage.measuredHeight - 75,
                filmImage.measuredWidth, filmImage.measuredHeight)
        filmRating.layout(filmImage.measuredWidth - 150 , filmImage.measuredHeight - 75,
                filmImage.measuredWidth, filmImage.measuredHeight)
    }

    fun setFilmProperties(film : Film){
        filmName.text = film.title
        val resId = resources.getIdentifier(film.coverPath, "drawable", context.packageName)
        val semitransparentColor = getColorFromResource(resId, 0.75f)
        val solidColor = getColorFromResource(resId, 1f)
        val transparentColor = getColorFromResource(resId, 0f)
        filmName.setBackgroundColor(semitransparentColor)
        filmImage.setImageResource(resId)

        filmRating.text = film.popularity.toString()
        val gradientDrawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(transparentColor, solidColor))
        filmRating.background = gradientDrawable
    }

    private fun getColorFromResource(resId : Int, opacity : Float) : Int{
        val bitmap = BitmapFactory.decodeResource(resources, resId)
        val palette = Palette.from(bitmap).generate()
        val color = palette.getDarkVibrantColor(0x000)
        return Color.argb(Math.round(Color.alpha(color) * opacity),
                Color.red(color), Color.green(color), Color.blue(color))
    }
}