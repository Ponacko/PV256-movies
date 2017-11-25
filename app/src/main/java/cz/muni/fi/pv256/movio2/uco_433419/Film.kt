package cz.muni.fi.pv256.movio2.uco_433419

import android.os.Parcel
import android.os.Parcelable

class Film (title: String, var releaseDate: Long, var popularity: Float,
           var coverPath: String, var backdrop: String) : ListItem(title), Parcelable {
    override val type = 0

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readFloat(),
            parcel.readString(),
            parcel.readString())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeLong(releaseDate)
        dest?.writeFloat(popularity)
        dest?.writeString(coverPath)
        dest?.writeString(backdrop)

    }

    companion object CREATOR : Parcelable.Creator<Film> {
        override fun createFromParcel(parcel: Parcel): Film = Film(parcel)

        override fun newArray(size: Int): Array<Film?> = arrayOfNulls(size)
    }
}