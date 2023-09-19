package com.example.bitirmeprojesi.ui.categoryAdd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitirmeprojesi.data.entitiy.Category
import com.example.bitirmeprojesi.data.repository.CategoryRepository
import com.example.bitirmeprojesi.data.state.CategoryAddState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryAddViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
):ViewModel(){

    private val _categoryAddState:MutableStateFlow<CategoryAddState> = MutableStateFlow(CategoryAddState.Idle)
    val categoryAddState: StateFlow<CategoryAddState> = _categoryAddState

    fun categoryAdd(categoryName:String){
        viewModelScope.launch(Dispatchers.IO) {
            var result:Long = -1
            if (!categoryName.isNullOrEmpty()){
                val existingCategory = categoryRepository.getCategoryByCategoryName(categoryName)
                if (existingCategory == null){
                    val category = Category(categoryName = categoryName)
                    runCatching {
                        _categoryAddState.emit(CategoryAddState.Loading)
                        result = categoryRepository.insert(category)
                        if (result > -1) {
                            _categoryAddState.emit(CategoryAddState.Success)
                        } else {
                            _categoryAddState.emit(CategoryAddState.CategoryAlreadyExists)
                        }
                    }.onSuccess {
                        _categoryAddState.value = CategoryAddState.Success
                    }.onFailure {
                        _categoryAddState.value = CategoryAddState.Error(it)
                    }
                }else{
                    _categoryAddState.emit(CategoryAddState.CategoryAlreadyExists)
                }
            }else{
                _categoryAddState.emit(CategoryAddState.Empty)
            }
        }

    }


}