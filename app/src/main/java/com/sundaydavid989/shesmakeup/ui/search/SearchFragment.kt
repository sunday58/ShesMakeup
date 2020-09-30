package com.sundaydavid989.shesmakeup.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sundaydavid989.shesmakeup.R
import com.sundaydavid989.shesmakeup.databinding.SearchFragmentBinding
import com.sundaydavid989.shesmakeup.ui.adapters.MakeupLoadStateAdapter
import com.sundaydavid989.shesmakeup.ui.adapters.SearchAdapter
import com.sundaydavid989.shesmakeup.ui.base.ScopedFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SearchViewModelFactory by instance()
    private lateinit var viewModel: SearchViewModel
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding
    private val adapter = SearchAdapter()

    private fun search(query: String) = launch {
        viewModel.searchMakeup(query).collect {
            adapter.submitData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = SearchFragmentBinding.inflate(inflater, container, false)


        initAdapter()
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)
        binding!!.retryButton.setOnClickListener { adapter.retry() }
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY,
        binding!!.searchMakeup.text.trim().toString())
    }

    private fun initAdapter(){
        binding!!.searchRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MakeupLoadStateAdapter { adapter.retry() },
            footer = MakeupLoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            binding!!.searchRecyclerView.isVisible = loadState.source.refresh is
                    LoadState.NotLoading
            binding!!.spinKit.isVisible = loadState.source.refresh is
                    LoadState.Loading
            binding!!.retryButton.isVisible = loadState.source.refresh is
                    LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.append as? LoadState.Error
            errorState?.let {
                showToast("Check Network Connection")
            }
        }
    }

    @Suppress("SameParameterValue")
    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun initSearch(query: String) {
        binding!!.searchMakeup.setText(query)

        binding!!.searchMakeup.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateSearchListFromInput()
                true
            }else {
                false
            }
        }
        binding!!.searchMakeup.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode ==
                    KeyEvent.KEYCODE_ENTER) {
                updateSearchListFromInput()
                true
            }else {
                false
            }
        }
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding!!.searchRecyclerView.scrollToPosition(0) }
        }
    }

    private fun updateSearchListFromInput() {
        binding!!.searchMakeup.text.trim().let {
            if (it.isNotEmpty()) {
                binding!!.searchRecyclerView.scrollToPosition(0)

            }
        }
    }

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "lipstick"
    }

}