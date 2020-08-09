package com.example.android.ycet.activity

import android.os.Bundle
import com.example.android.ycet.R
import kotlinx.android.synthetic.main.activty_task.*

// TODO (Step 2: Create a CreateBoardActivity.)
// START
class TaskActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_task)

        // TODO (Step 8: Call the setup action bar function)
        setupActionBar()
    }

    // TODO (Step 7: Create a function to setup action bar.)
    // START
    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_create_board_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_create_board_activity.setNavigationOnClickListener { onBackPressed() }
    }
    // END
}
// END