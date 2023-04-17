package com.example.wordgame

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import com.example.wordgame.R
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
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

        score_button.setOnClickListener{
            val intent=Intent(this,scorelist::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()

        }


    }

    fun change_activity(view: View){
        // Realtime Database referansı oluşturun
        var highscore=0;
        val intent= Intent(this,MainActivity::class.java)
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()

// Veritabanı referansını alın
        val myRef: DatabaseReference = database.reference
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Verileri okuyun
                for (postSnapshot in dataSnapshot.children) {
                    val value = postSnapshot.getValue()
                    val map = postSnapshot.getValue() as? Map<*, *>
                    map?.forEach { (key, value) ->
                        val innerMap = value as? Map<*, *>
                        val score = innerMap?.get("Score") as? String
                        if(score.toString().toInt()>highscore){
                            highscore=score.toString().toInt()
                        }
                    }
                }
                println(highscore)

                intent.putExtra("mosthighscore", highscore.toString())
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()

            }

            override fun onCancelled(error: DatabaseError) {
                // Hata durumunda ne yapacağınızı belirtin
            }
        })



    }




}