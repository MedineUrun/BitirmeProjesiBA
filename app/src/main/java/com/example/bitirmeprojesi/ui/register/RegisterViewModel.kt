package com.example.bitirmeprojesi.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entitiy.User
import com.example.bitirmeprojesi.data.repository.RegisterRepository
import com.example.bitirmeprojesi.data.state.RegisterMessageState
import com.example.bitirmeprojesi.data.state.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
): ViewModel() {

    private val _registerState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Idle)
    val registerState: StateFlow<RegisterState> = _registerState

    private val _message: MutableSharedFlow<RegisterMessageState> = MutableSharedFlow()
    val message: SharedFlow<RegisterMessageState> = _message

    fun insert(userName:String, email: String,  password: String, confirm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var result: Long = -1
            if (!email.isNullOrEmpty() && !userName.isNullOrEmpty() && !password.isNullOrEmpty() && !confirm.isNullOrEmpty()) {
                if (email.contains("@") && email.contains(".com")) {
                if (password == confirm) {
                    val existingUser = registerRepository.getUserByUserNameOrEmail(userName, email)
                    if (existingUser == null) {
                        val user = User(userName = userName, email = email,  password = password)
                        kotlin.runCatching {
                            _registerState.emit(RegisterState.Loading)
                            result = registerRepository.register(user)
                            if (result > -1) {
                                _message.emit(RegisterMessageState.Success)
                            } else {
                                _message.emit(RegisterMessageState.UserAlreadyExists)
                            }
                        }.onSuccess {
                            _registerState.value = RegisterState.Success(user)
                        }.onFailure {
                            _registerState.value = RegisterState.Error(it)
                        }
                    } else {
                        if (existingUser.userName == userName || existingUser.email == email ){
                            _message.emit(RegisterMessageState.UserNameAndEmailAlreadyUsage)
                        }
                    }
                } else {
                    _message.emit(RegisterMessageState.PasswordsNotEquals)
                }
                } else {
                    _message.emit((RegisterMessageState.InvalidEmail))
                }
            } else {
                _message.emit(RegisterMessageState.Empty)
            }
        }
    }

}