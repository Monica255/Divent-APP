package com.example.divent.ui.content.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.divent.R
import com.example.divent.core.data.Resource
import com.example.divent.core.domain.model.DetailEvent
import com.example.divent.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    var id =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        if(viewModel.detailData!=null){
            showData(viewModel.detailData)
        }else{
            id = intent.getIntExtra("id",0)
            lifecycleScope.launch {
                viewModel.getDetailEvent(id).observe(this@DetailActivity){
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
                            viewModel.detailData=it.data
                            showData(it.data)
                        }
                        is Resource.Error->{
                            showLoading(false)
                            showError(true)
                            showItem(false)
                        }
                        else -> {}
                    }

                }
            }
        }
    }

    private fun showData(event: DetailEvent?) {
        event?.let{
            Glide.with(this)
                .load(event.mediaCover)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(binding.ivBanner)
            binding.tvCategory.text =event.category
            binding.tvOwner.text =event.ownerName
            binding.tvTitle.text =event.name
            binding.tvSummary.text = event.summary
            val left = event.quota - event.registrants
            val text = "$left\nquota left"
            binding.tvRegis.text = text
            val time = event.beginTime

            val datePart = time.substringBefore(" ")
            val clockPart = time.substringAfter(" ")

            binding.tvDate.text = datePart
            binding.tvClock.text = clockPart
            val str = HtmlCompat.fromHtml(event.description,HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.tvDesc.text = str

            binding.btRegis.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isShow:Boolean){
        binding.loading.loading.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.btRegis.isEnabled= if(isShow) false else true
    }
    private fun showError(isShow:Boolean){
        binding.noData.noData.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.btRegis.isEnabled= if(isShow) false else true
    }

    private fun showItem(isShow:Boolean){
        binding.allItems.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.btRegis.isEnabled= if(isShow) true else false
    }

    private fun setActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}