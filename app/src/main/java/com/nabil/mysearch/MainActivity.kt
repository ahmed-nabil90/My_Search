package com.nabil.mysearch

import android.content.Intent
import android.net.Uri
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

    private lateinit var adapter: SearchAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewModel()
        setListeners()
        setAdapter()

    }

    private fun setAdapter() {
        adapter = SearchAdapter()
        rvSearch.adapter = adapter
    }

    private fun setListeners() {
        etSearch.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {

                var query = (v as EditText).text.toString()
                val newQuery = startWithProtocol(query)
                if (hasDomain(query)) {
                    if (newQuery.isNotEmpty())
                        search(newQuery, true)
                    else
                        search(query, true)
                } else {
                    search(query, false)
                    v.setText(newQuery)
                }
                true
            }
            false
        }
    }

    private fun startWithProtocol(query: String): String {
        var queryNew = ""
        val protocols =
            arrayOf(
                "http://",
                "https://",
                "http:/",
                "https:/",
                "http:",
                "https:",
                "https",
                "http",
                "www.",
                "www"
            )
        protocols.forEach { protocol ->
            if (query.startsWith(protocol, true)) {
                queryNew = query.removePrefix("www.")
            }
        }
        return queryNew
    }

    private fun hasDomain(query: String): Boolean {
        val domains = arrayOf(".com", ".org", ".net", ".edu", ".eg", ".int", ".gov", ".ae", "uk")
        domains.forEach { domain ->
            if (query.endsWith(domain, false)) return true
        }
        return false
    }

    private fun search(query: String, isUrl: Boolean) {
        if (isUrl.not())
            mainViewModel.searchQuery(query)
        else {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://$query")
            }
            if (intent.resolveActivity(packageManager) != null)
                startActivity(intent)
            else {
                intent.data = Uri.parse("http://$query")
                if (intent.resolveActivity(packageManager) != null)
                    startActivity(intent)
                else
                    mainViewModel.searchQuery(query)
            }
        }

    }

    private fun setViewModel() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.apply {

            errorLiveData.observe(
                this@MainActivity,
                Observer { t -> Toast.makeText(this@MainActivity, t, Toast.LENGTH_SHORT).show() })

            searchItemsLiveData.observe(this@MainActivity, Observer { searchList ->
                adapter.swapData(searchList)
            })
        }
    }
}
