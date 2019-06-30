package com.app.mmse.authentication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import es.dmoral.toasty.Toasty
import okhttp3.*
import java.io.IOException

class LoginUtils {

    //Handle the Login Process
    fun login(activity: Activity, ctx: Context, username: String, password: String) {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", username)
            .addFormDataPart("password", password)
            .build()

        //Url for Login
        val url = "http://api.mmse.bettermentit.com/service/v1/auth/login"
        val request = Request.Builder().url(url).post(requestBody).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                Log.d("OKHttp", body)

                try {
                    val gson = GsonBuilder().create()

                    val loginInfo = gson.fromJson(body, LoginInfo::class.java)

                    activity.runOnUiThread {
                        Log.d("az-login->status", loginInfo.status)
                        Log.d("az-login->message", loginInfo.message)
                        Log.d("az-login->username", loginInfo.data.username)
                        Log.d("az-login->authtoken", loginInfo.data.authToken)

                        val status = loginInfo.status
                        if (status == "200") { //Login Successful
                            Toasty.success(ctx,
                                "Login Successfully!", Toast.LENGTH_SHORT).show()
                        } else if (status == "400") { //Login Failed
                            Toasty.success(ctx,
                                "Login Failed!", Toast.LENGTH_SHORT).show()
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
    class LoginInfo(val status: String, val message: String, val data: Data)
    class Data(val username: String, val authToken: String)

}