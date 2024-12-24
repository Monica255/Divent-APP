package com.example.divent.ui.content.favorite

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.divent.R
import com.example.divent.core.data.Resource
import com.example.divent.databinding.FragmentFavoriteBinding
import com.example.divent.ui.content.EventAdapter
import com.example.divent.ui.content.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private val onClick: (Int)->Unit = {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(ID, it)
        launcherForResult.launch(intent)
    }

    private val launcherForResult= registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            getData()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isAdded){
            val layoutManager2 = LinearLayoutManager(requireActivity())
            binding.rvEvent.layoutManager=layoutManager2
            eventAdapter= EventAdapter(onClick)
            binding.rvEvent.adapter=eventAdapter
            getData()
        }
    }

    private fun getData(){
        viewModel.getEvents().observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading->{
                    showLoading(true)
                    showError(false)
                    showItem(false)
                }
                is Resource.Success->{
                    showLoading(false)
                    showItem(true)
                    it.data?.let { it1 -> eventAdapter.updateData(it1) }
                    if(!it.data.isNullOrEmpty()){
                        showError(false)
                    }else{
                        showError(true,"No Data Found")
                    }
                }
                is Resource.Error->{
                    showLoading(false)
                    showError(true)
                    showItem(false)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=FragmentFavoriteBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    private fun showLoading(isShow:Boolean){
        binding.loading.loading.visibility= if (isShow) View.VISIBLE else View.GONE
    }

    private fun showError(isShow:Boolean,text:String= resources.getString(R.string.something_went_wrong)){
        binding.noData.noData.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.noData.tvDesc.text = text
    }

    private fun showItem(isShow:Boolean){
        binding.rvEvent.visibility= if (isShow) View.VISIBLE else View.GONE
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val ID = "id"
    }
}