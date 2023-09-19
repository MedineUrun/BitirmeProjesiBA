package com.example.bitirmeprojesi.data.state

sealed class CategoryAddState {
    object Idle:CategoryAddState()
    object Loading: CategoryAddState()
    object Empty:CategoryAddState()
    object Success: CategoryAddState()
    object CategoryAlreadyExists: CategoryAddState()
    class Error(val throwable: Throwable): CategoryAddState()
}