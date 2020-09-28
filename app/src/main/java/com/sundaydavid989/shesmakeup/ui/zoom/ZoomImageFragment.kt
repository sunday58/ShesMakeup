package com.sundaydavid989.shesmakeup.ui.zoom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.data.db.entity.MakeupItem
import com.sundaydavid989.shesmakeup.databinding.FragmentZoomImageBinding
import com.sundaydavid989.shesmakeup.internal.glide.GlideApp


class ZoomImageFragment : Fragment() {

    private var _binding: FragmentZoomImageBinding? = null
    private val binding get() = _binding
    private lateinit var makeups: MakeupItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentZoomImageBinding.inflate(inflater, container, false)

        if (arguments != null && requireArguments().containsKey("makeupImage")) {
            makeups = requireArguments().getSerializable("makeupImage") as MakeupItem

            GlideApp.with(requireContext())
                .load(makeups.imageLink)
                .into(binding!!.largeImage)
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}