package com.example.app2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val str1 = "Seconds elapsed: "
    private lateinit var mPrefs: SharedPreferences

    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                (str1 + secondsElapsed++).also { textSecondsElapsed.text = it }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        val ed = mPrefs.edit()
        ed.putInt(str1, secondsElapsed)
        ed.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mPrefs = getSharedPreferences(localClassName, MODE_PRIVATE)
        secondsElapsed = mPrefs.getInt(str1, secondsElapsed)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }
}