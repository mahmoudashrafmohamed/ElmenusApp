package com.mahmoud_ashraf.menustask.menu.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud_ashraf.domain.menu.models.ItemOfTagModel
import com.mahmoud_ashraf.domain.menu.models.TagsModel
import com.mahmoud_ashraf.menustask.R
import com.mahmoud_ashraf.menustask.core.ITEM_DESC
import com.mahmoud_ashraf.menustask.core.PHOTO_URL
import com.mahmoud_ashraf.menustask.core.androidExtensions.observe
import com.mahmoud_ashraf.menustask.core.androidExtensions.showErrorSnackBar
import com.mahmoud_ashraf.menustask.core.exceptions.MenusException
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

    private val tagsAdapter by lazy {  TagsAdapter(onItemClicked = ::onTagClicked)}
    private val itemsOfTagAdapter by lazy { ItemsOfTagAdapter(onItemClicked = ::onItemOfTagClicked) }

    private fun onItemOfTagClicked(itemOfTagModel: ItemOfTagModel, ivItem: ImageView) {
        findNavController().navigate(
            R.id.action_menuScreenFragment_to_itemDetailsFragment,
            getBundleOfTagData(itemOfTagModel),
            null,
            getExtrasForSharedElementTransition(ivItem, itemOfTagModel)
        )
    }

    private fun getExtrasForSharedElementTransition(
        ivItem: ImageView,
        itemOfTagModel: ItemOfTagModel
    ) = FragmentNavigator.Extras.Builder().addSharedElements(
        mapOf(
            ivItem to itemOfTagModel.photoUrl
        )
    ).build()

    private fun getBundleOfTagData(itemOfTagModel: ItemOfTagModel) =
        Bundle().apply {
            putString(ITEM_DESC, itemOfTagModel.description)
            putString(PHOTO_URL, itemOfTagModel.photoUrl)
        }


    private fun onTagClicked(tagsModel: TagsModel) {
        binding.tvSelectedTag.text = getString(R.string.selected_tag,tagsModel.tagName)
        tagsAdapter.submitList(tagsAdapter.currentList.toMutableList().map {
            if (tagsModel.tagName == it.tagName) it.copy(isSelected = true)
            else it.copy(isSelected = false)
        })
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
    }

    private fun observeOnScreenState(state: MenuScreenStates) {
        when (state) {
            is MenuScreenStates.FirstLoading -> showShimmer()
            is MenuScreenStates.Loading -> showLoading()

            /*tags*/
            is MenuScreenStates.TagsLoadedSuccessfully -> handleTagsLoadedSuccessfullyState(state)
            is MenuScreenStates.TagsEmptyState -> handleTagsEmptyState()
            is MenuScreenStates.TagsInOfflineMode -> handleTagsOfflineModeState(state)
            is MenuScreenStates.TagsOfflineModeEmptyState -> handleTagsOfflineModeEmptyState(state)

            /*itemsOfTag */
            is MenuScreenStates.ItemsOfTagLoadedSuccessfully -> handleItemsOfTagLoadedSuccessfullyState(
                state
            )
            is MenuScreenStates.ItemsOfTagsOfflineMode -> handleItemsOfTagsOfflineModelState(state)
            is MenuScreenStates.ItemsOfTagsOfflineModelModeEmptyState -> handleItemsOfTagsOfflineModelEmptyState(
                state
            )
            is MenuScreenStates.ItemsOfTagsEmptyState -> handleItemsOfTagsEmptyState(state)

            is MenuScreenStates.Error -> state.error.handle()
        }
    }

    private fun handleTagsOfflineModeEmptyState(state: MenuScreenStates.TagsOfflineModeEmptyState) {
        hideLoading()
        hideShimmer()
        state.error.handle()
        tagsAdapter.submitList(emptyList())
        showEmptyView()
    }

    private fun handleTagsEmptyState() {
        hideLoading()
        hideShimmer()
        showEmptyView()
        tagsAdapter.submitList(emptyList())
    }

    private fun handleItemsOfTagsOfflineModelEmptyState(state: MenuScreenStates.ItemsOfTagsOfflineModelModeEmptyState) {
        hideLoading()
        state.error.handle()
        itemsOfTagAdapter.submitList(emptyList())
        showEmptyView()
    }

    private fun handleItemsOfTagsEmptyState(state: MenuScreenStates.ItemsOfTagsEmptyState) {
        hideLoading()
        itemsOfTagAdapter.submitList(emptyList())
        showEmptyView()
    }


    private fun showEmptyView() {
        binding.emptyView.isVisible = true
    }

    private fun hideEmptyView() {
        binding.emptyView.isVisible = false
    }

    private fun handleItemsOfTagsOfflineModelState(state: MenuScreenStates.ItemsOfTagsOfflineMode) {
        hideLoading()
        state.error.handle()
        itemsOfTagAdapter.submitList(state.list)
        hideEmptyView()
    }

    private fun handleItemsOfTagLoadedSuccessfullyState(state: MenuScreenStates.ItemsOfTagLoadedSuccessfully) {
        hideLoading()
        itemsOfTagAdapter.submitList(state.items)
        hideEmptyView()
    }

    private fun handleTagsOfflineModeState(state: MenuScreenStates.TagsInOfflineMode) {
        hideLoading()
        hideShimmer()
        state.error.handle()
        tagsAdapter.submitList(
            tagsAdapter.currentList.toMutableList().also { it.addAll(state.list) })
        hideEmptyView()
    }

    private fun handleTagsLoadedSuccessfullyState(state: MenuScreenStates.TagsLoadedSuccessfully) {
        hideLoading()
        hideShimmer()
        tagsAdapter.submitList(
            tagsAdapter.currentList.toMutableList().also { it.addAll(state.tags) })
        hideEmptyView()
    }

    private fun showLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showShimmer(){
        binding.nsContainer.isVisible = false
        binding.shimmerView.apply {
            isVisible = true
            startShimmer()
        }
    }

    private fun hideShimmer(){
        binding.nsContainer.isVisible = true
        binding.shimmerView.apply {
            isVisible = false
            stopShimmer()
        }
    }

    private fun hideLoading() {
        binding.progressBar.isVisible = false
    }

    private fun initViews() {
        with(binding) {
            rvTags.adapter = tagsAdapter
            rvTags.itemAnimator = null
            rvTags.addOnScrollListener(object : ScrollingPagination(
                rvTags.layoutManager as LinearLayoutManager
            ) {
                override fun loadMoreDate() {
                    viewModel.loadMoreTags()
                }
            })
            tagsAdapter.currentList.firstOrNull { it.isSelected }?.tagName?.let {
                tvSelectedTag.text = getString(R.string.selected_tag,it)
            }

            rvItems.adapter = itemsOfTagAdapter

            postponeEnterTransition()
            rvItems.viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun MenusException.handle() {
        when (this) {
            is MenusException.NoConnection -> showErrorSnackBar(
                binding.root,
                getString(R.string.lbl_no_internet_error_message)
            )
            else -> showErrorSnackBar(binding.root, getString(R.string.lbl_generic_error_message))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


