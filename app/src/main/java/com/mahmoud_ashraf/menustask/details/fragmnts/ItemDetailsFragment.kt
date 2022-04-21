package com.mahmoud_ashraf.menustask.details.fragmnts

import android.os.Bundle
import androidx.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mahmoud_ashraf.menustask.R
import com.mahmoud_ashraf.menustask.core.ITEM_DESC
import com.mahmoud_ashraf.menustask.core.PHOTO_URL
import com.mahmoud_ashraf.menustask.databinding.FragmentItemDetailsBinding

class ItemDetailsFragment : Fragment() {

    private var _binding: FragmentItemDetailsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSharedElementTransitionsOnEnter()
        initView()
    }

    private fun setSharedElementTransitionsOnEnter() {
        sharedElementEnterTransition = TransitionInflater.from(context?:return)
            .inflateTransition(R.transition.shared_element_transition)
    }

    private fun initView() {
        arguments?.let {
            binding.ivItemOfTag.transitionName = it.getString(PHOTO_URL)
            Glide.with(this)
                .load(it.getString(PHOTO_URL))
                .dontAnimate()
                .into(binding.ivItemOfTag)
            binding.tvDescription.text = it.getString(ITEM_DESC)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}