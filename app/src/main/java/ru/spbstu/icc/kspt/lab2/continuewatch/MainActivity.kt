package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private lateinit var textSecondsElapsed: TextView
    private val str1: String = "Seconds elapsed: "

    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                (str1 + secondsElapsed++).also { textSecondsElapsed.text = it }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        outState.run {
            putInt(str1, secondsElapsed)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.run {
            secondsElapsed = getInt(str1)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }
}
