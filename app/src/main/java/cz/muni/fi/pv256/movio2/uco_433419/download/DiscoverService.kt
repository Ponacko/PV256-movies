package cz.muni.fi.pv256.movio2.uco_433419.download

import cz.muni.fi.pv256.movio2.uco_433419.model.Film
import cz.muni.fi.pv256.movio2.uco_433419.model.FilmResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @author Tomáš Stolárik
 */
interface DiscoverService {
    @GET("discover/movie?api_key=8009a08149f286e1ef6f22da18708ae4")
    fun listFilms(@Query("primary_release_date.gte") sevenDaysAgo: String, @Query("primary_release_date.lte")
    sevenDaysFromNow: String): Call<FilmResponse>

    @GET("movie/{id}?api_key=8009a08149f286e1ef6f22da18708ae4")
    fun getFilm(@Path("id") id: Long): Call<Film>
}