package com.example.local_music.ui.base

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.local_music.R
import com.example.local_music.player.PlayManager
import org.litepal.LitePal
import org.litepal.LitePalApplication

/**
 * Created by Emily on 11/1/21
 */
abstract class BaseActivity : AppCompatActivity() {
    abstract val layoutId: Int

    open val openBind = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!openBind) {
            setContentView(layoutId)
        }
        LitePal.initialize(this)
        initPlay()
        initView()
        initTitle()
        initData()
    }

    private fun initPlay() {
        PlayManager.getInstance().init()
    }

    abstract fun initView()

    abstract fun initData()

    fun setTitleName(title: String) {
        findViewById<TextView>(R.id.lm_common_title_tv)?.text = title
    }

    fun showMsg(msg: String) {
        Toast.makeText(
            LitePalApplication.getContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initTitle() {
        findViewById<ImageButton>(R.id.lm_common_title_back_bt)?.setOnClickListener {
            finish()
        }
    }
}
