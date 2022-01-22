package com.opalynskyi.cleanmovies.core.movies.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.opalynskyi.cleanmovies.data.EntityMapper
import com.opalynskyi.cleanmovies.data.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.data.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.data.RemoteMoviesDataSource
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.entities.Movie
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MoviesRepositoryTest {

    private lateinit var repository: MoviesRepository

    @Mock
    lateinit var remoteDataSource: RemoteMoviesDataSource
    @Mock
    lateinit var localDataSource: LocalMoviesDataSource
    @Mock
    lateinit var mapper: EntityMapper<Movie, Movie>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository =
            MoviesRepositoryImpl(remoteDataSource, localDataSource, mapper)
    }

    @Test
    fun `getMovies() calls the remoteDataSource once`() {
        whenever(remoteDataSource.getMovies(any(), any())).thenReturn(
            Single.just(MoviesFactory.makeListOfMovieEntities(3))
        )
        repository.getMovies(
            START_DATE,
            END_DATE
        )
        verify(remoteDataSource, times(1)).getMovies(
            START_DATE,
            END_DATE
        )
    }

    @Test
    fun `if remoteDataSource fails, getMovies() calls localDataSource`() {
        whenever(remoteDataSource.getMovies(any(), any())).thenReturn(
            Single.error(RuntimeException("Network error"))
        )
        repository.getMovies(
            START_DATE,
            END_DATE
        ).test()
        verify(localDataSource, times(1)).getAll()
    }

    @Test
    fun `if remoteDataSource succeeds, getMovies() calls saves result to local storage`() {
        whenever(remoteDataSource.getMovies(any(), any())).thenReturn(
            Single.just(MoviesFactory.makeListOfMovieEntities(3))
        )
        whenever(mapper.mapFromEntity(any())).thenReturn(MoviesFactory.makeMovie())
        repository.getMovies(
            START_DATE,
            END_DATE
        ).test()
        verify(localDataSource, times(1)).saveAll(any())
    }

    @Test
    fun `check getMovies() returns expected values`() {
        whenever(remoteDataSource.getMovies(any(), any())).thenReturn(
            Single.just(MoviesFactory.makeListOfMovieEntities(3))
        )
        val movie = MoviesFactory.makeMovie()
        whenever(mapper.mapFromEntity(any())).thenReturn(movie)
        repository.getMovies(
            START_DATE,
            END_DATE
        ).test()
            .assertValueAt(0) { result -> result == listOf(movie, movie, movie) }
    }

    @Test
    fun `check getMovies() completes with errors`() {
        whenever(remoteDataSource.getMovies(any(), any())).thenReturn(
            Single.just(MoviesFactory.makeListOfMovieEntities(3))
        )
        repository.getMovies(
            START_DATE,
            END_DATE
        ).test()
            .assertNoErrors()
    }


    @Test
    fun getFavourites() {
        repository.getFavourites().test()
        verify(localDataSource, times(1)).getFavourites()
    }

    @Test
    fun addToFavourites() {
        val id = DataFactory.randomInt()
        repository.addToFavourites(id).test()
        verify(localDataSource, times(1)).addToFavourites(id)
    }

    @Test
    fun removeFromFavourites() {
        val id = DataFactory.randomInt()
        repository.removeFromFavourites(id).test()
        verify(localDataSource, times(1)).removeFromFavourites(id)
    }

    companion object {
        private const val START_DATE = "2018-10-10"
        private const val END_DATE = "2018-12-10"
    }
}