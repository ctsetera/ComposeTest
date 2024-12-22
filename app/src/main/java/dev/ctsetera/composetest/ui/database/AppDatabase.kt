package dev.ctsetera.composetest.ui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.ctsetera.composetest.ui.database.dao.UserDao
import dev.ctsetera.composetest.ui.database.entitiy.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}