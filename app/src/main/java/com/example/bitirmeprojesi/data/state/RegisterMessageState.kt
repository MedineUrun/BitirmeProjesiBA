package com.example.bitirmeprojesi.data.state

sealed class RegisterMessageState{
    object Idle:RegisterMessageState()
    object UserAlreadyExists:RegisterMessageState()
    object Success:RegisterMessageState()
    object Empty:RegisterMessageState()
    object PasswordsNotEquals:RegisterMessageState()
    object UserNameAndEmailAlreadyUsage:RegisterMessageState()
    object InvalidEmail:RegisterMessageState()

}