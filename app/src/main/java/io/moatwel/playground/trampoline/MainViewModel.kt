package io.moatwel.playground.trampoline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class MainViewModel(
    private val useCase: MainUseCase,
    private val scheduler: Scheduler
) : ViewModel() {

    private val _threadNameData: MutableLiveData<String> = MutableLiveData()
    val threadNameData: LiveData<String> = _threadNameData

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun load() {
        useCase.getCurrentThreadName()
            .subscribeOn(scheduler)
            .subscribeBy {
                _threadNameData.postValue(it)
            }
            .addTo(compositeDisposable)

        // Scheduler.io() と Scheduler.trampoline() とで、ここの実行タイミングは...？
        _threadNameData.postValue("ViewModel: ${Thread.currentThread().name}")
    }
}