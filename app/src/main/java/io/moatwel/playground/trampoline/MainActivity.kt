package io.moatwel.playground.trampoline

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.moatwel.playground.trampoline.databinding.ActivityMainBinding
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainViewModel(MainUseCase(), Schedulers.io()) as T
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.load.setOnClickListener {
            viewModel.load()
        }

        with(viewModel) {
            val owner = this@MainActivity
            threadNameData.observe(owner) {
                binding.log.text = "${binding.log.text}\n$it"
            }
        }
    }
}