package cz.muni.fi.pv256.movio2.uco_433419

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Tomas on 19. 10. 2017.
 */
class Film(private var title: String, private var releaseDate: Long, private var popularity: Float,
           private var coverPath: String, private var backdrop: String) : Parcelable {

    fun getTitle(): String = title

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