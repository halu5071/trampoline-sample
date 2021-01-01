package io.moatwel.playground.trampoline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun test() {
        Thread.currentThread().name = "test_main"
        val viewModel = MainViewModel(MainUseCase(), Schedulers.trampoline())
        val observer = Observer<String> {
            println(it)
        }

        viewModel.threadNameData.observeForever(observer)

        viewModel.load()
    }
}
