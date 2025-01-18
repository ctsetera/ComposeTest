package dev.ctsetera.composetest.model

import android.graphics.Bitmap

data class UserModel(
    val uid: Int,
    val id: String,
    val name: String,
    val description: String?,
    val icon: Bitmap?,
)