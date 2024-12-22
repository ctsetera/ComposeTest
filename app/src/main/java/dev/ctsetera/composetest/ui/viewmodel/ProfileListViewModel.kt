package dev.ctsetera.composetest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.ctsetera.composetest.ui.model.UserModel
import dev.ctsetera.composetest.ui.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileListViewModel(
    private val userRepository: UserRepository,
    private val handle: SavedStateHandle,
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ProfileListViewModel(
                    userRepository = UserRepository(checkNotNull(this[APPLICATION_KEY])),
                    handle = createSavedStateHandle()
                )
            }
        }
    }

    private val mUserList: MutableStateFlow<List<UserModel>> =
        MutableStateFlow(handle.get<List<UserModel>>("USER_LIST") ?: listOf())
    val userList: StateFlow<List<UserModel>> = mUserList.asStateFlow()

    fun getUserList() = viewModelScope.launch(Dispatchers.IO) {
        if (userRepository.getUser().isEmpty()) {
            // Register dummy.
            for (i in 1..3) {
                userRepository.registerUser(
                    id = "@dummy_user_$i",
                    name = "Dummy User $i",
                    description = buildString {
                        append("I'm Dummy $i!\n".repeat(4))
                        append("I'm Dummy $i!")
                    },
                    icon = null,
                )
            }

            Log.d("ProfileListViewModel", "The dummy was successfully set.")
        }

        val users = userRepository.getUser()

        mUserList.update { users }
        handle["USER_LIST"] = users

        Log.d("ProfileListViewModel", "Get user list.")
    }
}