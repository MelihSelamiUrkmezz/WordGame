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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
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

        val db = Firebase.database.reference
        var highscore=0
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (data in dataSnapshot.children) {
                        // Do something with each data
                        val key = data.key
                        val value = data.value
                        if(key=="Score" && value.toString().toInt()>highscore){
                            highscore=value.toString().toInt()
                        }
                        Log.d(TAG, "Key: $key, Value: $value")
                    }
                } else {
                    Log.d(TAG, "No data found")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "Error getting data", databaseError.toException())
            }
        })


        val intent= Intent(this,MainActivity::class.java)

        val message = highscore.toString()
        println(message)
        intent.putExtra("mosthighscore", message)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()



    }




}