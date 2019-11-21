package aku.edu.dummyproject.ui

import aku.edu.dummyproject.R
import aku.edu.dummyproject.databinding.ActivityExampleBinding
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

class ExampleActivity : AppCompatActivity() {

    internal lateinit var bi: ActivityExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_example)
        bi.callback = this
    }


    fun saveDraft() {

    }

    fun fromValidation(): Boolean {

        return true
    }

    fun updateDB(): Boolean {

        return true
    }

    fun BtnContinue() {
        if (fromValidation()) {
            saveDraft();
            if (updateDB()) {
                startActivity(Intent(this, EndingActivity::class.java))
            }
        }
    }

    fun BtnEnd() {

        startActivity(Intent(this, MainActivity::class.java))
    }
}
