package com.example.urbandictionaryn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.urbandictionaryn.viewmodel.UrbanDictionaryViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: UrbanDictionaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, WordFragment.newInstance()).commit()
        }
    }
}