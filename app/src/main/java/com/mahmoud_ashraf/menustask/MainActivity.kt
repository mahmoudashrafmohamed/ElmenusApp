package com.mahmoud_ashraf.menustask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mahmoud_ashraf.menustask.menu.viewmodel.MenuViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
  //  val vm : MenuViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}