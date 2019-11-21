package aku.edu.dummyproject.util

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import aku.edu.dummyproject.R
import aku.edu.dummyproject.model.Info

import android.content.Context.MODE_PRIVATE

object Util {

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 7
    }

    fun getInfo(context: Context): Info {

        val packageName = context.packageName
        val installedOn = context.packageManager.getPackageInfo(packageName, 0).lastUpdateTime
        val versionName = context.packageManager.getPackageInfo(packageName, 0).versionName
        val versionCode = context.packageManager.getPackageInfo(packageName, 0).versionCode
        return Info(installedOn, versionCode, versionName)
    }


    fun setGPS(context: Context) {
        val GPSPref = context.getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE)

        //        String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

        try {
            val lat = GPSPref.getString("Latitude", "0")
            val lang = GPSPref.getString("Longitude", "0")
            val acc = GPSPref.getString("Accuracy", "0")
            val dt = GPSPref.getString("Time", "0")

            if (lat === "0" && lang === "0") {
                Toast.makeText(context, "Could not obtained GPS points", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "GPS set", Toast.LENGTH_SHORT).show()
            }

            val date = DateFormat.format(
                "dd-MM-yyyy HH:mm",
                java.lang.Long.parseLong(GPSPref.getString("Time", "0")!!)
            ).toString()

            //            MainApp.fc.setGpsLat(GPSPref.getString("Latitude", "0"));
            //            MainApp.fc.setGpsLng(GPSPref.getString("Longitude", "0"));
            //            MainApp.fc.setGpsAcc(GPSPref.getString("Accuracy", "0"));
            ////            AppMain.fc.setGpsTime(GPSPref.getString(date, "0")); // Timestamp is converted to date above
            //            MainApp.fc.setGpsTime(date); // Timestamp is converted to date above
            //            MainApp.fc.setGpsDT(date); // Timestamp is converted to date above

            // Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (e: Exception) {

        }

    }


}
