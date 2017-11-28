package cz.muni.fi.pv256.movio2.uco_433419

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author Tomáš Stolárik
 */
interface DiscoverService {
    @GET("discover/movie?api_key=8009a08149f286e1ef6f22da18708ae4")
    fun listFilms(@Query("primary_release_date.gte") sevenDaysAgo: String, @Query("primary_release_date.lte") sevenDaysFromNow: String): Call<FilmResponse>
}