package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val strSecondsElapsed: String = "Seconds elapsed: "
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

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(strSecondsElapsed, secondsElapsed)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.run {
            secondsElapsed = getInt(strSecondsElapsed)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
