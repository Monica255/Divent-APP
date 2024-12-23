package com.example.divent.ui


import android.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import com.example.divent.core.data.source.remote.model.Event
import io.reactivex.rxjava3.subjects.PublishSubject

fun SearchView.getQueryTextChangeObservable(): io.reactivex.rxjava3.core.Observable<String> {
    val subject = PublishSubject.create<String>()

    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onComplete()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            subject.onNext(newText ?: "")
            return true
        }
    })

    return subject
}

class MyDiffCallback(
    private val oldList: List<Event>,
    private val newList: List<Event>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
