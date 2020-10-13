package com.sundaydavid989.shesmakeup.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sundaydavid989.shesmakeup.databinding.FavoriteFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.FavoriteAdapter
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


@ExperimentalCoroutinesApi
@FlowPreview
class FavoriteFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: FavoriteViewModelFactory by instance()

    private lateinit var viewModel: FavoriteViewModel
    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch {
        binding!!.favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val favorite = viewModel.favorite.await()
        favorite.observe(viewLifecycleOwner, { favoriteList ->
            if (favoriteList == null) binding!!.emptyFavorite.visibility = View.VISIBLE
            adapter = FavoriteAdapter(favoriteList)
            binding!!.favoriteRecyclerView.adapter = adapter
            binding!!.emptyFavorite.visibility = View.GONE
            adapter.notifyDataSetChanged()
        })

    }

}