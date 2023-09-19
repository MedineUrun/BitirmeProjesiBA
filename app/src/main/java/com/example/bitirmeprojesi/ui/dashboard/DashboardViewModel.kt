package com.example.bitirmeprojesi.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entitiy.Category
import com.example.bitirmeprojesi.data.repository.CategoryRepository
import com.example.bitirmeprojesi.data.repository.PhotoRepository
import com.example.bitirmeprojesi.data.state.CategoryAddState
import com.example.bitirmeprojesi.data.state.DashboardState
import com.example.bitirmeprojesi.data.state.PhotoListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val photoRepository: PhotoRepository
):ViewModel() {

    private val _dashboardState: MutableStateFlow<DashboardState> = MutableStateFlow(DashboardState.Idle)
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    private val _photoListState:MutableStateFlow<PhotoListState> = MutableStateFlow(PhotoListState.Idle)
    val photoListState:StateFlow<PhotoListState> = _photoListState

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val result:List<String>
                result = categoryRepository.getCategories()
                if (result.isNullOrEmpty()){
                    _dashboardState.emit(DashboardState.Empty)
                }
                _dashboardState.emit(DashboardState.Success(result))
            }.onFailure {
                _dashboardState.emit(DashboardState.Error)
            }
        }
    }

    fun getAllPhotos(selectedCategory:String){
        viewModelScope.launch {
            runCatching {
                _photoListState.value = PhotoListState.Loading
                val photos = photoRepository.getAllPhotos(selectedCategory)
                if (photos.isEmpty()) _photoListState.value = PhotoListState.Empty
                else _photoListState.value = PhotoListState.Result(photos)
            }.onFailure {
                _photoListState.value = PhotoListState.Error(it)
            }
        }
    }





}