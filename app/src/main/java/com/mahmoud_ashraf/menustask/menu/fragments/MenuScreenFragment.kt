package com.mahmoud_ashraf.menustask.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mahmoud_ashraf.menustask.core.androidExtensions.observe
import com.mahmoud_ashraf.menustask.databinding.FragmentMenuScreenBinding
import com.mahmoud_ashraf.menustask.menu.adapters.TagsAdapter
import com.mahmoud_ashraf.menustask.menu.viewmodel.MenuScreenStates
import com.mahmoud_ashraf.menustask.menu.viewmodel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuScreenFragment : Fragment() {

    val viewModel : MenuViewModel by viewModel()

    private var _binding: FragmentMenuScreenBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val tagsAdapter by lazy { TagsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMenuScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.screenState, ::observeOnScreenState)
        initViews()
        viewModel.getTags("1")
    }

    private fun observeOnScreenState(state: MenuScreenStates) {
        when (state) {
            is MenuScreenStates.Loading -> {
                binding.progressBar.isVisible = true
            }
            is MenuScreenStates.TagsLoadedSuccessfully -> {
                binding.progressBar.isVisible = false
                tagsAdapter.submitList(state.tags)
            }
            is MenuScreenStates.Error -> {
                binding.progressBar.isVisible = false
               // Todo -  state.error.handle()
            }
         }
    }

    private fun initViews() {
        with(binding){
            rvTags.adapter = tagsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}