package com.tochukwu.moviepractice.data.remote

import com.tochukwu.moviepractice.data.remote.model.MovieResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("/")
    suspend fun getMovies(
        @Query("s") movieSearchQuery: String,
        @Query("r") returnType: String = "json"
    ): Response<MovieResultDto>
}

/**

@GET("/")
suspend fun getMovies(
@Query("s") movieSearchQuery: String,
@Query("r") returnType: String = "json"
): Response<MovieResultDto>

/**94e0db81a5msh73023859a138990p19da88jsn64bf7eff790b
 *
 * 'x-rapidapi-host': 'movie-database-imdb-alternative.p.rapidapi.com',
'x-rapidapi-key': '94e0db81a5msh73023859a138990p19da88jsn64bf7eff790b'**/

}
        **/