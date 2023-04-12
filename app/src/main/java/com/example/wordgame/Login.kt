package com.example.wordgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.widget.Button
import com.example.wordgame.domain.entities.Character
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.util.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

class Login : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        signinbutton.setOnClickListener{

            var giris_kullaniciadi=username.text.toString()
            var giris_sifre=password.text.toString()


            if(TextUtils.isEmpty(giris_kullaniciadi)){

               error.text = "Lütfen kullanıcı adınızı giriniz!"

                return@setOnClickListener
            }

            else if(TextUtils.isEmpty(giris_sifre)){

                error.text = "Lütfen şifrenizi giriniz."

                return@setOnClickListener
            }
            login_control(giris_kullaniciadi,giris_sifre)


        }

    }
    fun login_control(username:String,password:String){


        val URL:String = "http://127.0.0.1:5000/api/login"
        val jsonObject = JSONObject()
        jsonObject.put("username",username)
        jsonObject.put("password",password)
        val requestBody = RequestBody.create("application/json".toMediaTypeOrNull(), jsonObject.toString())
        if(URL.isNotEmpty()){

            val client = OkHttpClient()

            val request = Request.Builder()
                .post(requestBody)
                .url(URL)
                .build()

            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    println("Debug")

                    response.use {

                        if(!response.isSuccessful){

                            println("Error")

                        }
                        else{

                            val body = response?.body?.string()

                            val response: String?=body.toString()
                            println(response)
                            if(response=="OK"){

                                //intent = Intent(applicationContext,Preferences::class.java)
                                //startActivity(intent)

                            }
                            else{

                              //  Handler(Looper.getMainLooper()).post {
                               //     Toast.makeText(this@MainActivity, "Kullanıcı adı veya şifre hatalı!", Toast.LENGTH_LONG).show()
                               // }

                            }


                        }
                    }
                }
            })

        }
        else{

            println("Tehlike")

        }
    }



}