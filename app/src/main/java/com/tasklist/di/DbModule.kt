package com.tasklist.di

import androidx.room.Room
import com.tasklist.data.database.FavoritesDB
import com.tasklist.data.database.favorites.FavoritesDao
import org.koin.dsl.module


val databaseModule = module {

    single<FavoritesDB> {
        Room.databaseBuilder(
            get(),
            FavoritesDB::class.java,
            "Favorites.db"
        ).build()
    }

    single<FavoritesDao> {
        get<FavoritesDB>().daoFavorite
    }

}


