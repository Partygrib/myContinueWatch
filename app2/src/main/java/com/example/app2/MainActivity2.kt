package com.example.app2

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity2 : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val strSecondsElapsed = "Seconds elapsed: "
    private lateinit var mPrefs: SharedPreferences
    private var state: Int = 0

    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (state == 0) {
                textSecondsElapsed.post {
                    (strSecondsElapsed + secondsElapsed++).also { textSecondsElapsed.text = it }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        val ed = mPrefs.edit()
        ed.putInt(strSecondsElapsed, secondsElapsed)
        ed.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        mPrefs = getSharedPreferences(localClassName, MODE_PRIVATE)
        secondsElapsed = mPrefs.getInt(strSecondsElapsed, secondsElapsed)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onPause() {
        super.onPause()

        state = 1
    }

    override fun onResume() {
        super.onResume()

        state = 0
    }
}