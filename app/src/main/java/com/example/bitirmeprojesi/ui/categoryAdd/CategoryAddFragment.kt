package com.example.bitirmeprojesi.ui.categoryAdd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.state.CategoryAddState
import com.example.bitirmeprojesi.databinding.FragmentCategoryAddBinding
import com.example.bitirmeprojesi.showAlert
import kotlinx.coroutines.launch


class CategoryAddFragment : Fragment(R.layout.fragment_category_add) {

    private lateinit var binding: FragmentCategoryAddBinding
    private val viewModel: CategoryAddViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoryAddBinding.bind(view)

        listeners()
        observeCategoryAddState()

    }

    private fun listeners() {
        binding.btnCategoryAddTwo.setOnClickListener {
            viewModel.categoryAdd(
            binding.editTextCategoryAdd.text.toString().trim()
            )
        }
    }

    private fun observeCategoryAddState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.categoryAddState.collect{
                    when(it){
                        CategoryAddState.Idle->{}
                        CategoryAddState.Loading->{}
                        CategoryAddState.Empty->{
                            requireActivity().showAlert(R.string.fill_in_the_blank)
                        }
                        CategoryAddState.Success->{
                            requireActivity().showAlert(R.string.register_successful)
                            binding.editTextCategoryAdd.text?.clear()
                        }
                        CategoryAddState.CategoryAlreadyExists->{
                            requireActivity().showAlert(R.string.category_already_exists)
                        }
                        is CategoryAddState.Error->{
                            requireActivity().showAlert(R.string.somethings_wrong)
                        }
                    }
                }
            }
        }
    }


}