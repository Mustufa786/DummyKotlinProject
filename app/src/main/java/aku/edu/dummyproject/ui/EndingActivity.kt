package aku.edu.dummyproject.ui

import aku.edu.dummyproject.R
import aku.edu.dummyproject.databinding.ActivityEndingBinding
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil


class EndingActivity : AppCompatActivity() {

    internal lateinit var binding: ActivityEndingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_ending
        )
        binding.setCallback(this)


    }

    fun BtnEnd() {

        if (formValidation()) {
            SaveDraft()
            if (UpdateDB()) {
                val endSec = Intent(this, MainActivity::class.java)
            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun SaveDraft() {


        //
    }

    private fun UpdateDB(): Boolean {


        return true
    }

    private fun formValidation(): Boolean {
        return true
    }


    override fun onBackPressed() {

        Toast.makeText(this, "Can not go back", Toast.LENGTH_SHORT).show()
    }

    companion object {

        private val TAG = EndingActivity::class.java.simpleName
    }

}
