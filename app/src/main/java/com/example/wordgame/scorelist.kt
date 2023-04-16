package com.example.wordgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class scorelist : AppCompatActivity() {

    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var scoreArrayList: ArrayList<ScoreModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scorelist)

        userRecyclerView=findViewById<RecyclerView>(R.id.recy)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        scoreArrayList = arrayListOf<ScoreModel>()
        getScoreData()


        val returnbutton=findViewById<Button>(R.id.returnbutton)

        returnbutton.setOnClickListener {

            val intent= Intent(this,firstpage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()



        }

    }

    private fun getScoreData() {
        dbref=FirebaseDatabase.getInstance().getReference("Scores")
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val score = userSnapshot.getValue(ScoreModel::class.java)
                        scoreArrayList.add(score!!)
                    }
                    userRecyclerView.adapter=CustomAdapter(scoreArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }
}