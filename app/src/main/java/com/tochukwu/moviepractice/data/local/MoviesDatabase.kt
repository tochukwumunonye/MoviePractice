package com.tochukwu.moviepractice.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tochukwu.moviepractice.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class  MoviesDatabase  : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}