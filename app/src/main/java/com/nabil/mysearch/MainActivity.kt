package com.nabil.mysearch

import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nabil.mysearch.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewModel()
        setListeners()

    }

    private fun setListeners() {
        etSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_SEARCH) {
                mainViewModel.searchQuery((v as EditText).text.toString())
                true
            }
            false
        }
    }

    private fun setViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.apply {

            errorLiveData.observe(
                this@MainActivity,
                Observer { t -> Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show() })

            searchItemsLiveData.observe(this@MainActivity, Observer { searchList ->

            })
        }
    }
}
