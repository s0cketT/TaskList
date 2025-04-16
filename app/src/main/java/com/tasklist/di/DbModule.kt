package com.tasklist.di

import androidx.room.Room
import com.tasklist.data.database.FavoritesDB
import com.tasklist.data.database.favorites.FavoritesDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {

    single<FavoritesDB> {
        Room.databaseBuilder(
            androidApplication(),
            FavoritesDB::class.java,
            "Favorites.db"
        ).build()
    }

    single<FavoritesDao> {
        get<FavoritesDB>().daoFavorite
    }

}


