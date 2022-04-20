package com.mahmoud_ashraf.menustask.menu.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.menustask.R
import com.mahmoud_ashraf.menustask.core.ITEM_DESC
import com.mahmoud_ashraf.menustask.core.PHOTO_URL
import com.mahmoud_ashraf.menustask.core.androidExtensions.observe
import com.mahmoud_ashraf.menustask.core.pagination.ScrollingPagination
import com.mahmoud_ashraf.menustask.databinding.FragmentMenuScreenBinding
import com.mahmoud_ashraf.menustask.menu.adapters.ItemsOfTagAdapter
import com.mahmoud_ashraf.menustask.menu.adapters.TagsAdapter
import com.mahmoud_ashraf.menustask.menu.viewmodel.MenuScreenStates
import com.mahmoud_ashraf.menustask.menu.viewmodel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MenuScreenFragment : Fragment() {

    val viewModel: MenuViewModel by viewModel()

    private var _binding: FragmentMenuScreenBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val tagsAdapter by lazy { TagsAdapter(onItemClicked = ::onTagClicked) }
    private val itemsOfTagAdapter by lazy { ItemsOfTagAdapter(onItemClicked = ::onItemOfTagClicked) }

    private fun onItemOfTagClicked(itemOfTagModel: ItemOfTagModel,ivItem : ImageView) {
        findNavController().navigate(
            R.id.action_menuScreenFragment_to_itemDetailsFragment, Bundle().apply {
                putString(ITEM_DESC, itemOfTagModel.description)
                putString(PHOTO_URL, itemOfTagModel.photoUrl)
            },
            null,
            FragmentNavigator.Extras.Builder().addSharedElements(
                mapOf(
                    ivItem to itemOfTagModel.photoUrl)
            ).build()
        )
    }

    private fun onTagClicked(tagsModel: TagsModel) {
        viewModel.getItemsOfTags(tagsModel.tagName)
    }

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
        viewModel.getTags()
    }

    private fun observeOnScreenState(state: MenuScreenStates) {
        when (state) {
            is MenuScreenStates.Loading -> showLoading()

            is MenuScreenStates.TagsLoadedSuccessfully -> {
                hideLoading()
                tagsAdapter.submitList(
                    tagsAdapter.currentList.toMutableList().also { it.addAll(state.tags) })
            }
            is MenuScreenStates.Error -> {
                hideLoading()
                // Todo -  state.error.handle()
            }
            is MenuScreenStates.ItemsOfTagLoadedSuccessfully -> {
                hideLoading()
                itemsOfTagAdapter.submitList(state.items)
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun initViews() {
        with(binding) {
            rvTags.adapter = tagsAdapter
            rvTags.addOnScrollListener(object : ScrollingPagination(
                rvTags.layoutManager as LinearLayoutManager
            ) {
                override fun loadMoreDate() {
                    viewModel.loadMoreTags()
                }
            })
            rvItems.adapter = itemsOfTagAdapter

            postponeEnterTransition()
           rvItems.viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}