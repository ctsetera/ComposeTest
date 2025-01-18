package dev.ctsetera.composetest.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.ctsetera.composetest.database.entitiy.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Insert
    fun insert(vararg users: UserEntity)

    @Update
    fun update(vararg users: UserEntity)

    @Query("DELETE FROM UserEntity WHERE uid = :uid")
    fun deleteByUid(uid: Int)
}