package com.example.android.ycet.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.ycet.R
import kotlinx.android.synthetic.main.activity_task_home.*

class TaskHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_home)
        fab_create_board.setOnClickListener {
            val intent = Intent(this,TaskActivity::class.java)
            startActivity(intent)
        }
    }
}
