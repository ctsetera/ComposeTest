package dev.ctsetera.composetest.state

import dev.ctsetera.composetest.model.UserModel
import java.io.Serializable

data class ProfileListUiState(
    val isLoading: Boolean,
    val userList: List<UserModel>,
    val ioErrorMessage: List<Pair<Int, List<String>>> = emptyList(),
) : Serializable
