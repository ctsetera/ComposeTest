package dev.ctsetera.composetest.viewmodel.screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.ctsetera.composetest.R
import dev.ctsetera.composetest.repository.UserRepository
import dev.ctsetera.composetest.state.ProfileListUiState
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

    private object HandlerKey {
        const val UI_STATE = "UI_STATE"
    }

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

    private val mUiState: MutableStateFlow<ProfileListUiState> =
        MutableStateFlow(
            handle.get<ProfileListUiState>(HandlerKey.UI_STATE) ?: ProfileListUiState(
                isLoading = false,
                emptyList(),
            )
        )
    val uiState: StateFlow<ProfileListUiState> = mUiState.asStateFlow()

    fun getUserList() = viewModelScope.launch(Dispatchers.IO) {
        val ioErrorMessages = mutableListOf<Pair<Int, List<String>>>()

        Log.d("ProfileListViewModel", "Getting user list.")

        if (userRepository.getUserList().isEmpty()) {
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

        try {
            updateUiState(
                ProfileListUiState(
                    isLoading = true,
                    userList = userRepository.getUserList(),
                )
            )
        } catch (e: Exception) {
            Log.d("ProfileListViewModel", "Failed to get user list.")
            e.printStackTrace()

            ioErrorMessages.add(Pair(R.string.error_unknown, emptyList()))

            updateUiState(
                ProfileListUiState(
                    isLoading = false,
                    userList = emptyList(),
                    ioErrorMessage = ioErrorMessages
                )
            )

            return@launch
        }

        Log.d("ProfileListViewModel", "Got user list.")
    }

    private fun updateUiState(uiState: ProfileListUiState) {
        mUiState.update { uiState }
        handle[HandlerKey.UI_STATE] = uiState
    }
}