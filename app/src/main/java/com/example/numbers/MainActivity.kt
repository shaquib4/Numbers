package com.example.numbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.numbers.Adapters.AdapterNumbers
import com.example.numbers.Modals.ModalNumbers

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapterN: AdapterNumbers
    private lateinit var manager: LinearLayoutManager
    private lateinit var list: ArrayList<ModalNumbers>
    private lateinit var progress: ProgressBar
    var isScrolling: Boolean = false
    var currentNumbers: Int = 0
    var scrolledNumbers: Int = 0
    var totalNumbers: Int = 0
    var vl: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.recycler)
        progress = findViewById(R.id.progress)
        manager = LinearLayoutManager(this)
        recycler.layoutManager = manager
        list = ArrayList<ModalNumbers>()
        val sp = getSharedPreferences("Numbers", MODE_PRIVATE)

        vl = sp.getInt("savedNumber", 0)
        for (i in vl + 1..vl + 5) {
            val obj = ModalNumbers(i)
            list.add(obj)
        }
        adapterN = AdapterNumbers(this, list)
        recycler.adapter = adapterN
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalNumbers = manager.itemCount
                scrolledNumbers = manager.findFirstVisibleItemPosition()
                currentNumbers = manager.childCount
                if (isScrolling && (scrolledNumbers + currentNumbers == totalNumbers)) {
                    isScrolling = false

                    getNewNumbers()

                }
            }
        })


    }

    private fun getNewNumbers() {
        progress.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            val newNum = list[list.size - 1].number
            for (i in newNum + 1..newNum + 5) {
                val obj = ModalNumbers(i)
                list.add(obj)
                adapterN.notifyDataSetChanged()
                progress.visibility = View.INVISIBLE
            }

        }, 1500)
        val sp = getSharedPreferences("Numbers", MODE_PRIVATE)
        vl = list[list.size - 1].number
        val editor = sp.edit();
        editor.putInt("savedNumber", vl)
        editor.apply()
    }
}