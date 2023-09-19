package com.example.bitirmeprojesi.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.repository.ProfileRepository
import com.example.bitirmeprojesi.data.state.UserUpdateState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
):ViewModel() {

    private val _userUpdateState: MutableSharedFlow<UserUpdateState> = MutableSharedFlow()
    val userUpdateState: SharedFlow<UserUpdateState> = _userUpdateState.asSharedFlow()

    fun updateUser(userName: String, email: String, password: String, userId: Int) {
        viewModelScope.launch {
            runCatching {
                val existingUser = profileRepository.getUserById(userId)
                if (existingUser == null) {
                    _userUpdateState.emit(UserUpdateState.UserNotFound)
                } else {
                    if (profileRepository.getUserByUserNameOrEmail(userName, email)?.id  == existingUser.id) {
                        existingUser.userName = userName
                        existingUser.email = email
                        existingUser.password = password
                        profileRepository.profileUpdate(existingUser)
                        _userUpdateState.emit(UserUpdateState.Success)
                    } else {
                        _userUpdateState.emit(UserUpdateState.UserAlreadyExist)
                    }
                }
            }.onFailure {
                _userUpdateState.emit(UserUpdateState.Error(it))
            }
        }
    }
}
