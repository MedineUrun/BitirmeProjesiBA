package com.example.bitirmeprojesi.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.bitirmeprojesi.R
import com.example.bitirmeprojesi.data.model.Photo
import com.example.bitirmeprojesi.data.state.DashboardState
import com.example.bitirmeprojesi.data.state.PhotoListState
import com.example.bitirmeprojesi.databinding.FragmentDashboardBinding
import com.example.bitirmeprojesi.showAlert
import com.example.bitirmeprojesi.ui.PhotoDetail.PhotoBottomFragment
import com.example.bitirmeprojesi.ui.PhotoDetail.PhotoDetailFragment
import com.example.bitirmeprojesi.ui.login.LoginFragment.Companion.USER_EMAIL
import com.example.bitirmeprojesi.ui.login.LoginFragment.Companion.USER_ID
import com.example.bitirmeprojesi.ui.login.LoginFragment.Companion.USER_NAME
import com.example.bitirmeprojesi.ui.login.LoginFragment.Companion.USER_PASSWORD
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by activityViewModels()
    private var selectedCategory: String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

        viewModel.getCategories()
        listeners()
        observeDashboard()

        //getPhotos
        viewModel.getAllPhotos(selectedCategory)
        observePhotoListState()

        val userEmail= arguments?.getString(USER_EMAIL)
        val userName= arguments?.getString(USER_NAME)
        val userPassword= arguments?.getString(USER_PASSWORD)
        val userId = arguments?.getInt(USER_ID)

        userEmail?.let { userEmail ->
            binding.btnProfile.text = userEmail
        }

        val bundle = bundleOf(
            USER_EMAIL to userEmail,
            USER_NAME to userName,
            USER_PASSWORD to userPassword,
            USER_ID to userId,
        )

        binding.btnProfile.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment, bundle)
        }

    }

    private fun onLongClick(photo: Photo) {
        val photoDetailFragment = PhotoBottomFragment(photo)
        photoDetailFragment.show(childFragmentManager, null)
    }

    private fun onClick(photo: Photo) {
        val bundle = bundleOf("photoDetail" to photo)
        val photoDetail = PhotoDetailFragment()
        photoDetail.arguments = bundle
        findNavController().navigate(R.id.action_dashboardFragment_to_photoDetailFragment, bundle)
    }

    private fun observePhotoListState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.photoListState.collect{
                    when(it){
                        is PhotoListState.Idle->{}
                        is PhotoListState.Loading->{
                            binding.rvPhotos.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                        is PhotoListState.Empty->{
                            binding.rvPhotos.isVisible = false
                            binding.progressBar.isVisible = false
                        }
                        is PhotoListState.Result->{
                            binding.rvPhotos.isVisible = true
                            binding.progressBar.isVisible = false
                            binding.rvPhotos.adapter = PhotoAdapter(
                                requireContext(),
                                it.photos,
                                this@DashboardFragment::onClick,
                                this@DashboardFragment::onLongClick
                            )

                        }
                        is PhotoListState.Error->{
                            binding.rvPhotos.isVisible = false
                            binding.progressBar.isVisible = false
                            Snackbar.make(binding.rvPhotos,"Bir hata olustu", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun observeDashboard() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.dashboardState.collect { state ->
                    when (state) {
                        is DashboardState.Success -> {
                            val categories = state.categories

                            if (categories.isEmpty()) {
                                binding.spCategories.isVisible = false
                                binding.tvCategoryNotFound.text = getString(R.string.category_is_empty)
                            } else {
                                selectedCategory = categories.get(0)
                                binding.spCategories.isVisible = true
                                binding.tvCategoryNotFound.isVisible = false
                                setupSpinner(categories)
                            }

                            binding.spCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                    val newSelectedCategory = categories[position]
                                    println(newSelectedCategory)
                                    println(selectedCategory)

                                    if (newSelectedCategory != selectedCategory) {
                                        selectedCategory = newSelectedCategory
                                        viewModel.getAllPhotos(selectedCategory)
                                    }
                                }

                                override fun onNothingSelected(parent: AdapterView<*>?) {
                                    // ...
                                }
                            }
                        }
                        DashboardState.Empty->{
                            binding.spCategories.isVisible = false
                            binding.tvCategoryNotFound.text = getString(R.string.category_is_empty)
                        }
                        DashboardState.Idle -> {
                        }
                        is DashboardState.Error -> {
                        }
                    }
                }
            }
        }
    }

    private fun setupSpinner(categories: List<String>) {
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategories.adapter = adapter
    }

    private fun listeners() {
        binding.btnCategoryAdd.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_categoryAddFragment)
        }

        binding.fabCamera.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_cameraFragment)
        }

    }

}