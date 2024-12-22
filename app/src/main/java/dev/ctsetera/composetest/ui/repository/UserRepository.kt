package dev.ctsetera.composetest.ui.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Room
import dev.ctsetera.composetest.ui.database.AppDatabase
import dev.ctsetera.composetest.ui.database.entitiy.UserEntity
import dev.ctsetera.composetest.ui.model.UserModel
import java.io.ByteArrayOutputStream


class UserRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "database-name"
    ).build()

    fun getUser(): List<UserModel> {
        val users = db.userDao().getAll()

        return users.map {
            UserModel(
                uid = it.uid,
                id = it.id,
                name = it.name,
                description = it.description,
                icon = if (it.profileImage == null) {
                    null
                } else {
                    BitmapFactory.decodeByteArray(it.profileImage, 0, it.profileImage.size)
                },
            )
        }
    }

    fun registerUser(
        id: String,
        name: String,
        description: String?,
        icon: Bitmap?,
    ) {
        db.userDao().insert(
            UserEntity(
                id = id,
                name = name,
                description = description,
                profileImage = if (icon == null) {
                    null
                } else {
                    val output = ByteArrayOutputStream()
                    icon.compress(Bitmap.CompressFormat.JPEG, 100, output)
                    output.toByteArray()
                },
            )
        )
    }

    fun editUser(
        uid: Int,
        id: String,
        name: String,
        description: String?,
        icon: Bitmap?,
    ) {
        db.userDao().update(
            UserEntity(
                uid = uid,
                id = id,
                name = name,
                description = description,
                profileImage = if (icon == null) {
                    null
                } else {
                    val output = ByteArrayOutputStream()
                    icon.compress(Bitmap.CompressFormat.JPEG, 100, output)
                    output.toByteArray()
                },
            )
        )
    }

    fun deleteUser(
        uid: Int,
    ) {
        db.userDao().deleteByUid(uid = uid)
    }
}