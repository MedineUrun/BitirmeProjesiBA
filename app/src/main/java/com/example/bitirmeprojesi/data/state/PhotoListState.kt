package com.example.bitirmeprojesi.data.state

import com.example.bitirmeprojesi.data.model.Photo

sealed class PhotoListState {
    object Idle : PhotoListState()
    object Loading : PhotoListState()
    object Empty : PhotoListState()
    class Result(val photos: List<Photo>) : PhotoListState()
    class Error(val throwable: Throwable? = null) : PhotoListState()
}
