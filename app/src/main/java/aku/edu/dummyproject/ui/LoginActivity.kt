package aku.edu.dummyproject.ui

import aku.edu.dummyproject.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import aku.edu.dummyproject.databinding.ActivityLoginBinding
import aku.edu.dummyproject.util.Util
import java.lang.Thread.sleep

class LoginActivity : AppCompatActivity() {


    internal lateinit var bi: ActivityLoginBinding;
    private var mAuthTask: UserLoginTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        bi = DataBindingUtil.setContentView(this, R.layout.activity_login)
        bi.callback = this

        val info = Util.getInfo(this)
        bi.txtinstalldate.text =
            "Ver. ${info.versionName} . ${info.versionCode} \r\n( Last Updated: " + SimpleDateFormat(
                "dd MMM. yyyy"
            ).format(
                Date(info.installedOn)
            ) + " )"


    }

    fun attemptLogin() {
        if (mAuthTask != null) {
            return
        }
        bi.userName.error = null
        bi.passowrd.error = null
        val email = bi.userName.text.toString()
        val password = bi.passowrd.text.toString()
        var cancel = false
        var focusView: View? = null

        if (!TextUtils.isEmpty(password) && !Util.isPasswordValid(password)) {
            bi.passowrd.error = getString(R.string.error_invalid_password)
            focusView = bi.passowrd
            cancel = true
        }
        if (TextUtils.isEmpty(email)) {
            bi.userName.error = getString(R.string.error_field_required)
            focusView = bi.userName
            cancel = true
        }

        if (cancel) {
            focusView!!.requestFocus()
        } else {
            showProgress(true)
            mAuthTask = UserLoginTask(email, password)
            mAuthTask!!.execute(null as Void?)
        }
    }


    private fun showProgress(b: Boolean) {
        if (b) {
            bi.loginBtn.visibility = View.GONE
            bi.progressBar.visibility = View.VISIBLE
        } else {
            bi.loginBtn.visibility = View.VISIBLE
            bi.progressBar.visibility = View.GONE
        }
    }

    inner class UserLoginTask internal constructor(
        private val mEmail: String,
        private val mPassword: String
    ) : AsyncTask<Void, Void, Boolean>() {


        override fun doInBackground(vararg params: Void): Boolean? {
            try {
                // Simulate network access.
                sleep(2000)
            } catch (e: InterruptedException) {
                return false
            }

            for (credential in DUMMY_CREDENTIALS) {
                val pieces =
                    credential.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (pieces[0] == mEmail) {
                    // Account exists, return true if the password matches.
                    return pieces[1] == mPassword
                }
            }
            return true
        }

        override fun onPostExecute(success: Boolean?) {
            mAuthTask = null
            val mlocManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (mEmail == "dmu@aku" && mPassword == "aku?dmu" || mEmail == "test1234" && mPassword == "test1234") {

                    val iLogin = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(iLogin)
                    showProgress(false)

                } else {
                    bi.passowrd.error = getString(R.string.error_incorrect_password)
                    bi.passowrd.requestFocus()
                    Toast.makeText(this@LoginActivity, "$mEmail $mPassword", Toast.LENGTH_SHORT)
                        .show()
                    showProgress(false)
                }
            } else {
                showProgress(false)
                val alertDialogBuilder = AlertDialog.Builder(
                    this@LoginActivity
                )
                alertDialogBuilder
                    .setMessage("GPS is disabled in your device. Enable it?")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Enable GPS"
                    ) { dialog, id ->
                        val callGPSSettingIntent = Intent(
                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
                        )
                        startActivity(callGPSSettingIntent)
                    }
                alertDialogBuilder.setNegativeButton(
                    "Cancel"
                ) { dialog, id -> dialog.cancel() }
                val alert = alertDialogBuilder.create()
                alert.show()
            }
        }


        override fun onCancelled() {
            mAuthTask = null
            showProgress(false)
        }
    }

    companion object {
        private val DUMMY_CREDENTIALS =
            arrayOf("test1234:test1234", "testS12345:testS12345", "bar@example.com:world")
    }
}
