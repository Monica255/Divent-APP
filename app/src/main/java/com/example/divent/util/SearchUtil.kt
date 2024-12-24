package com.example.divent.util


import android.widget.SearchView
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


