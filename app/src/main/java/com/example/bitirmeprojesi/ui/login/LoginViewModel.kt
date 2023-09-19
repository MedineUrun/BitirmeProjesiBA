package com.example.bitirmeprojesi.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.repository.LoginRepository
import com.example.bitirmeprojesi.data.state.LoginMessageState
import com.example.bitirmeprojesi.data.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository): ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _message: MutableSharedFlow<LoginMessageState> = MutableSharedFlow()
    val message: SharedFlow<LoginMessageState> = _message

    fun login(userInfo: String, password: String) {
        viewModelScope.launch {
            if (!userInfo.isNullOrEmpty() && !password.isNullOrEmpty()) {
                runCatching {
                    _loginState.value = LoginState.Loading
                    if (userInfo.contains("@") && userInfo.contains(".com")) {
                        loginRepository.loginWithEmail(userInfo, password)?.let {
                            _loginState.value = LoginState.Result(it)
                        } ?: kotlin.run {
                            _message.emit(LoginMessageState.UserNotFound)
                        }
                    } else {
                        loginRepository.loginWithUserName(userInfo, password)?.let {
                            _loginState.value = LoginState.Result(it)
                        } ?: kotlin.run {
                            _message.emit(LoginMessageState.UserNotFound)
                        }
                    }
                }.onFailure {
                    _loginState.value = LoginState.Error(it)
                }
            } else {
                _message.emit(LoginMessageState.Empty)
            }
        }
    }

}
