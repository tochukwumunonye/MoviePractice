package com.tochukwu.moviepractice.data.local.model

import com.tochukwu.moviepractice.domain.model.Movie
import com.tochukwu.moviepractice.util.DomainMapper

class MovieEntityMapper : DomainMapper<MovieEntity, Movie> {

    override fun mapToDomainModel(model: MovieEntity): Movie {
        return Movie(
            pk = model.pk,
            title = model.title,
            year =  model.year,
            poster = model.poster
        )
    }

    override fun mapFromDomainModel(domainModel: Movie): MovieEntity {
        return MovieEntity(
            pk = domainModel.pk,
            title = domainModel.title,
            year = domainModel.year,
            poster = domainModel.poster
        )
    }

    fun fromEntityListToDomain(movieEntityList: List<MovieEntity>): List<Movie> {
        return movieEntityList.map { mapToDomainModel(it) }
    }

    fun fromDomainListToEntity(movieList: List<Movie>): List<MovieEntity> {
        return movieList.map { mapFromDomainModel(it) }
    }
}