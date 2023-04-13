package com.example.wordgame

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.wordgame.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class firstpage : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firstpage)

        val game_button=findViewById<Button>(R.id.start)
        val score_button=findViewById<Button>(R.id.score)
        game_button.setOnClickListener{

            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        score_button.setOnClickListener{
            val intent=Intent(this,scorelist::class.java)
            startActivity(intent)
            finish()

        }


    }
}