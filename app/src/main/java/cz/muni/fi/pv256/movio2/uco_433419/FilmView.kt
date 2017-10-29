package cz.muni.fi.pv256.movio2.uco_433419

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.film_item.view.*


class FilmView(context: Context, attrs : AttributeSet) : ConstraintLayout(context, attrs) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        filmImage.layout(0,0,filmImage.measuredWidth, filmImage.measuredHeight)
        filmName.layout(50, filmImage.measuredHeight - 50,
                filmImage.measuredWidth, filmImage.measuredHeight)
    }

    fun setFilmNameAndImage(text : String, coverPath: String){
        filmName.text = text
        val resId = resources.getIdentifier(coverPath, "drawable", context.packageName)
        filmImage.setImageResource(resId)
    }
}