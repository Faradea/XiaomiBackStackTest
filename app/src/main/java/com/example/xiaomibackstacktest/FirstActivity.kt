package com.example.xiaomibackstacktest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FirstActivity : AppCompatActivity() {

    companion object {
        const val ACTIVITY_EXTRA = "activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        intent.getStringExtra(ACTIVITY_EXTRA)?.let {
            startActivity(Intent(intent).setClassName(this, it));
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
