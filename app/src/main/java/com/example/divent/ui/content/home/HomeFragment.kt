package com.example.divent.ui.content.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.divent.core.data.Resource
import com.example.divent.core.data.source.remote.network.EVENT
import com.example.divent.databinding.FragmentHomeBinding
import com.example.divent.ui.content.EventAdapter
import com.example.divent.ui.content.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingAdapter : UpcomingAdapter
    private lateinit var finishedAdapter : EventAdapter
    private val onClick: (Int)->Unit = {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("id", it)
        startActivity(intent)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isAdded){
            val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            val layoutManager2 = LinearLayoutManager(requireActivity())

            binding.rvUpcomingEvent.layoutManager=layoutManager
            binding.rvEvent.layoutManager=layoutManager2

            upcomingAdapter = UpcomingAdapter(onClick)
            finishedAdapter= EventAdapter(onClick)

            binding.rvUpcomingEvent.adapter=upcomingAdapter
            binding.rvEvent.adapter=finishedAdapter

            lifecycleScope.launch {
                if(viewModel.upcomingEvent!=null){
                    setAdapterUpcomingData()
                }else{
                    viewModel.getEvent(type = EVENT.UPCOMING, limit = 5).observe(viewLifecycleOwner){
                        when(it){
                            is Resource.Loading->{
                                showLoading(true)
                                showError(false)
                                showItem(false)
                            }
                            is Resource.Success->{
                                showLoading(false)
                                showError(false)
                                showItem(true)
                                viewModel.upcomingEvent=it.data
                                setAdapterUpcomingData()
                            }
                            is Resource.Error->{
                                showLoading(false)
                                showError(true)
                                showItem(false)
                            }
                        }
                    }
                }

                if(viewModel.finishedEvent!=null){
                    setAdapterFinishedData()
                }else{
                    viewModel.getEvent(type = EVENT.FINISHED, limit = 5).observe(viewLifecycleOwner){
                        when(it){
                            is Resource.Loading->{
                                showLoading(true)
                                showError(false)
                                showItem(false)
                            }
                            is Resource.Success->{
                                showLoading(false)
                                showError(false)
                                showItem(true)
                                viewModel.finishedEvent=it.data
                                setAdapterFinishedData()
                            }
                            is Resource.Error->{
                                showLoading(false)
                                showError(true)
                                showItem(false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setAdapterUpcomingData(){
        viewModel.upcomingEvent?.let { upcomingAdapter.updateData(it) }
    }

    private fun setAdapterFinishedData(){
        viewModel.finishedEvent?.let { finishedAdapter.updateData(it) }
    }

    private fun showLoading(isShow:Boolean){
        binding.loading.loading.visibility= if (isShow) View.VISIBLE else View.GONE
    }

    private fun showError(isShow:Boolean){
        binding.noData.noData.visibility= if (isShow) View.VISIBLE else View.GONE
    }

    private fun showItem(isShow:Boolean){
        binding.allItems.visibility= if (isShow) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding!!.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}