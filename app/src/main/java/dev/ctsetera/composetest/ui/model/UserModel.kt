package dev.ctsetera.composetest.ui.model

import android.graphics.Bitmap

data class UserModel(
    val uid: Int,
    val id: String,
    val name: String,
    val description: String?,
    val icon: Bitmap?,
)