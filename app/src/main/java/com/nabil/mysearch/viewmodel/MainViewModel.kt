package com.nabil.mysearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nabil.mysearch.network.RetrofitClient
import com.nabil.mysearch.network.model.SearchItem
import com.nabil.mysearch.network.model.SearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel() : ViewModel() {

    private var compositeDisposable = CompositeDisposable()

    val searchItemsLiveData = MutableLiveData<List<SearchItem>>()
    val errorLiveData = MutableLiveData<String>()

    fun searchQuery(query: String) {
        compositeDisposable.add(
            RetrofitClient.getRetrofitInstance().search(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res: SearchResponse? -> searchItemsLiveData.value = res?.items },
                    { t -> errorLiveData.value = t.message })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}