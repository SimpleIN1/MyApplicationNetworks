package com.example.myapplicationnetworks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonGet = findViewById<Button>(R.id.buttonGet)
        val editTextNumber = findViewById<EditText>(R.id.editTextNumber)

        val textView = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)

        val apiService = APIService()

        buttonGet.setOnClickListener {
            thread {
                Log.d("check", editTextNumber.text.toString())
                val post = apiService.getPostById(editTextNumber.text.toString().toInt())

                runOnUiThread {
                    if (post != null) {
                        textView.text =post.id.toString()
                        textView2.text =post.userId.toString()
                        textView3.text =post.title
                        textView4.text =post.body
                    }
                }
            }
        }
    }
}