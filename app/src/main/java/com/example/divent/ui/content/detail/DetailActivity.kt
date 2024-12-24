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
import com.example.divent.ui.content.favorite.FavoriteFragment.Companion.ID
import com.example.divent.ui.content.setting.SettingsViewModel
import com.example.divent.util.Mapper
import com.example.divent.util.SettingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    var id =0
    private val compositeDisposable = CompositeDisposable()
    private val checkBoxClicks = PublishSubject.create<Boolean>()

    private val settingViewModel: SettingsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()

        settingViewModel.theme.observe(this) { theme ->
            SettingUtil.setDarkMode(theme == "Dark")
        }

        if(viewModel.detailData!=null){
            showData(viewModel.detailData!!)
        }else{
            id = intent.getIntExtra(ID,0)
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
                            it.data?.let { it1 -> showData(it1) }
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

    private fun showData(event: DetailEvent) {
        Glide.with(this)
            .load(event.mediaCover)
            .placeholder(R.drawable.placeholder_banner)
            .error(R.drawable.placeholder_banner)
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
        lifecycleScope.launch {
            binding.cbFav.isChecked = viewModel.isEventExist(event.id)
        }

        binding.cbFav.setOnCheckedChangeListener { _, isChecked ->
            checkBoxClicks.onNext(isChecked)
        }

        compositeDisposable.add(
            checkBoxClicks
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe { isChecked ->
                    lifecycleScope.launch {
                        if (isChecked) {
                            viewModel.insertEvent(Mapper.detailDomainToEntity(event))
                        } else {
                            viewModel.deleteEventById(event.id)
                        }
                        setResult(RESULT_OK)
                    }
                }
        )
    }

    private fun showLoading(isShow:Boolean){
        binding.loading.loading.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.btRegis.isEnabled= !isShow
    }
    private fun showError(isShow:Boolean){
        binding.noData.noData.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.btRegis.isEnabled= !isShow
    }

    private fun showItem(isShow:Boolean){
        binding.allItems.visibility= if (isShow) View.VISIBLE else View.GONE
        binding.btRegis.isEnabled= isShow
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