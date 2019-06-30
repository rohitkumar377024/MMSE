package com.app.mmse.authentication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import es.dmoral.toasty.Toasty
import okhttp3.*
import java.io.IOException

class RegisterUtils {

    //Handle the Registration Process
    fun register(activity: Activity, ctx: Context, username: String, password: String) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()

        //Url for Registration
        val url = "http://api.mmse.bettermentit.com/service/v1/user/create"
        val request = Request.Builder().url(url).post(requestBody).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                Log.d("OKHttp", body)

                try {
                    val gson = GsonBuilder().create()

                    val registerInfo = gson.fromJson(body, RegisterInfo::class.java)

                    activity.runOnUiThread {
                        Log.d("az-register->status", registerInfo.status)
                        Log.d("az-register->username", registerInfo.data.username)
                        Log.d("az-register->authtoken", registerInfo.data.authToken)

                        val status = registerInfo.status
                        if (status == "200") { //Registration Successful
                            Toasty.success(ctx,
                                "Registered Successfully!", Toast.LENGTH_SHORT).show()
                        } else if (status == "400") { //Registration Failed
                            Toasty.success(ctx,
                                "Registration Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("exceptional", e.printStackTrace().toString())
                    activity.runOnUiThread {
                        Toasty.error(ctx,
                            "Enter correct username and password!", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("OKHttp", "Failed to execute request ${e.localizedMessage}")
            }
        })
    }

    //Classes used for getting data related to Logged In Partner!
    class RegisterInfo(val status: String, val data: Data)
    class Data(val username: String, val authToken: String)

}