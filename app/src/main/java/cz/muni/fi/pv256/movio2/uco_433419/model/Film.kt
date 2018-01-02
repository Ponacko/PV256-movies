package cz.muni.fi.pv256.movio2.uco_433419.model

import android.os.Parcel
import android.os.Parcelable

class Film(var id: Long, original_title: String, var release_date: String, var popularity: Float,
           var poster_path: String, var backdrop_path: String, var overview: String) : ListItem(original_title), Parcelable {


    override val type = 0

    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readString(),
            parcel.readString(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeLong(id)
        dest?.writeString(original_title)
        dest?.writeString(release_date)
        dest?.writeFloat(popularity)
        dest?.writeString(poster_path ?: "poster_path")
        dest?.writeString(backdrop_path ?: "poster_path")
        dest?.writeString(overview)

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Film

        if (release_date != other.release_date) return false
        if (original_title != other.original_title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = release_date.hashCode()
        result = 31 * result + original_title.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film? = Film(parcel)

        override fun newArray(size: Int): Array<Film?> = arrayOfNulls(size)
    }
}