package com.example.divent.ui.content.upcoming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.divent.databinding.FragmentUpcomingBinding
import com.example.divent.ui.content.EventAdapter
import com.example.divent.ui.content.detail.DetailActivity
import com.example.divent.ui.getQueryTextChangeObservable
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class UpcomingFragment : Fragment() {
    private val viewModel: UpcomingViewModel by viewModels()
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private val compositeDisposable = CompositeDisposable()

    private val onClick: (Int) -> Unit = {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", it)
        startActivity(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isAdded) {
            val layoutManager2 = LinearLayoutManager(requireActivity())
            binding.rvEvent.layoutManager = layoutManager2
            eventAdapter = EventAdapter(onClick)
            binding.rvEvent.adapter = eventAdapter

            viewModel.searchEvents()

            val searchObservable = binding.searchView.getQueryTextChangeObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            compositeDisposable.add(
                searchObservable.subscribe({ query ->
                    viewModel.searchEvents(query)
                }, { throwable ->
                    Toast.makeText(requireActivity(), "Error: ${throwable.message}", Toast.LENGTH_SHORT).show()
                })
            )

            viewModel.moreUpcomingEventLiveData.observe(viewLifecycleOwner) { data ->
                if (!data.isNullOrEmpty()) {
                    eventAdapter.updateData(data)
                    showError(true)
                    showItem(false)
                    showLoading(false)
                }
            }

            viewModel.showErrorLiveData.observe(viewLifecycleOwner) { showError ->
                if (showError) {
                    showError(true)
                    showItem(false)
                }else{
                    showError(false)
                    showItem(true)
                }
            }

            viewModel.showLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) {
                    showLoading(true)
                    showItem(false)
                }else{
                    showLoading(false)
                    showItem(true)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return _binding!!.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isShow: Boolean) {
        binding.loading.loading.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showError(isShow: Boolean) {
        binding.noData.noData.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    private fun showItem(isShow: Boolean) {
        binding.rvEvent.visibility = if (isShow) View.VISIBLE else View.GONE
    }

}