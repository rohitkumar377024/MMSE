package com.app.mmse.username_screen.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.app.mmse.R
import com.app.mmse.numbers.serial_threes.SerialThreesActivity
import com.app.mmse.orientation.temporal.main.TemporalOrientationActivity
import com.app.mmse.username_screen.utils.UsernameIntentUtils
import kotlinx.android.synthetic.main.activity_username_main.*

class UsernameMainActivity : AppCompatActivity() {

    //Audio Permission Request Code String
    private val REQUEST_AUDIO_PERMISSION = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username_main)

        //First request permission!
        requestPermissions()

        //todo -> remove both below later once orientation & numerical questions done
        goToNumericalQuestionsDummyBtn.setOnClickListener {
            startActivity(Intent(this, SerialThreesActivity::class.java))
        }
        goToOrientationQuestionsDummyBtn.setOnClickListener {
            startActivity(Intent(this, TemporalOrientationActivity::class.java))
        }

        //Set up clicks for both buttons -> Existing username and New username
        existing_username_btn.setOnClickListener { UsernameIntentUtils().goToUsernameChooseActivity(this) }
        new_username_btn.setOnClickListener { UsernameIntentUtils().goToNewUsernameActivity(this) }
    }

    //Code for requesting permissions
    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                //Code for Versions >= Marshmallow
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    Toast.makeText(this, "App required access to audio!", Toast.LENGTH_SHORT).show()
                }
                requestPermissions(Array(1) { Manifest.permission.RECORD_AUDIO }, REQUEST_AUDIO_PERMISSION)
            }
        } else {
            //Code for Versions < Marshmallow
        }
    }

    //Handles requesting of permission
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_AUDIO_PERMISSION) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                //If Permission not granted
                Toast.makeText(
                    applicationContext,
                    "Application will not be use your audio!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}
