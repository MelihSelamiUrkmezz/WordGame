package com.example.wordgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import com.example.wordgame.domain.entities.Character
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //buttons
    var buttons = ArrayList<Button>();
    //characters
    var characters = ArrayList<Character>();
    //selectedCharacters
    var selectedCharacterIntex: Int?=null;
    //starter animation handler
    var starterAnimationRunnable: Runnable = Runnable{};
    var starterAnimationHandler = Handler(Looper.myLooper()!!);
    var flagStarAnimationHandler: Boolean = false;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //start add all buttons
        buttons.add(button_0_0);buttons.add(button_0_1);buttons.add(button_0_2);buttons.add(button_0_3);
        buttons.add(button_0_4);buttons.add(button_0_5);buttons.add(button_0_6);buttons.add(button_0_7);
        buttons.add(button_1_0);buttons.add(button_1_1);buttons.add(button_1_2);buttons.add(button_1_3);
        buttons.add(button_1_4);buttons.add(button_1_5);buttons.add(button_1_6);buttons.add(button_1_7);
        buttons.add(button_2_0);buttons.add(button_2_1);buttons.add(button_2_2);buttons.add(button_2_3);
        buttons.add(button_2_4);buttons.add(button_2_5);buttons.add(button_2_6);buttons.add(button_2_7);
        buttons.add(button_3_0);buttons.add(button_3_1);buttons.add(button_3_2);buttons.add(button_3_3);
        buttons.add(button_3_4);buttons.add(button_3_5);buttons.add(button_3_6);buttons.add(button_3_7);
        buttons.add(button_4_0);buttons.add(button_4_1);buttons.add(button_4_2);buttons.add(button_4_3);
        buttons.add(button_4_4);buttons.add(button_4_5);buttons.add(button_4_6);buttons.add(button_4_7);
        buttons.add(button_5_0);buttons.add(button_5_1);buttons.add(button_5_2);buttons.add(button_5_3);
        buttons.add(button_5_4);buttons.add(button_5_5);buttons.add(button_5_6);buttons.add(button_5_7);
        buttons.add(button_6_0);buttons.add(button_6_1);buttons.add(button_6_2);buttons.add(button_6_3);
        buttons.add(button_6_4);buttons.add(button_6_5);buttons.add(button_6_6);buttons.add(button_6_7);
        buttons.add(button_7_0);buttons.add(button_7_1);buttons.add(button_7_2);buttons.add(button_7_3);
        buttons.add(button_7_4);buttons.add(button_7_5);buttons.add(button_7_6);buttons.add(button_7_7);
        buttons.add(button_8_0);buttons.add(button_8_1);buttons.add(button_8_2);buttons.add(button_8_3);
        buttons.add(button_8_4);buttons.add(button_8_5);buttons.add(button_8_6);buttons.add(button_8_7);
        buttons.add(button_9_0);buttons.add(button_9_1);buttons.add(button_9_2);buttons.add(button_9_3);
        buttons.add(button_9_4);buttons.add(button_9_5);buttons.add(button_9_6);buttons.add(button_9_7);
        //end

        //start begin chaacter initilazed
        var starterCounter: Int = 0;
        while (starterCounter < 16){
            var value: String = createRandomCharacter();
            //we dont use freezing for now
            characters.add(Character(value));
            starterCounter = starterCounter + 1;
        }
        //end

        //start set begin value to buttons
        starterCounter = 0;
        while (starterCounter < 16){
            buttons[starterCounter].text = characters[starterCounter].value;
            starterCounter = starterCounter +1;
        }
        //end

        //start begin animation for 16 values
        var rowCounter:Int = 8;
        var a :Int = 1;
        starterAnimationRunnable = object :Runnable{
            override fun run() {
                if(rowCounter >= 64){
                    // 64 is starter index of ninth row
                    flagStarAnimationHandler = true;
                }
                starterAnimationHandler.postDelayed(starterAnimationRunnable, 300);
                setViewsStarterAnimation(rowCounter);

                rowCounter = rowCounter +8;

            }
        }
        starterAnimationHandler.post(starterAnimationRunnable);

        val starterAnimationCloseCD = object :CountDownTimer(5000, 200){
            override fun onTick(p0: Long) {
                if(flagStarAnimationHandler){
                    starterAnimationHandler.removeCallbacks(starterAnimationRunnable);
                    cancel()
                }
            }
            override fun onFinish() {

            }
        }
        starterAnimationCloseCD.start()
        //end

        //start add clickLister to all buttons
        buttons.forEachIndexed{index: Int, button: Button ->
            button.setOnClickListener {
                getSelectedButtonValue(index);
            }
        }
        //end

        //start delete response clicklistener
        deleteButton.setOnClickListener{
            wordResult.text = "";
        }
        //end

        //start apply response clicklistener
        applyButton.setOnClickListener{
            // have to add API function
            println("Butonla kapadim")
            starterAnimationHandler.removeCallbacks(starterAnimationRunnable);
        }
        //end

    }

    //start begin animationViews
    fun setViewsStarterAnimation(rowCounter: Int){

        var temp: Int = rowCounter;

        for(i in temp.. temp+7) {
            // get button and set text empty
            var button: Button = buttons[i]
            var buttonText = button.text.toString()

            // set text new location
            var targetButon: Button = buttons[i+8];
            targetButon.text = buttonText;
        }

        for(i in temp-8.. temp-1) {
            // get button and set text empty
            var button: Button = buttons[i]
            var buttonText = button.text.toString()
            button.text="";
            button.setBackgroundColor(Color.parseColor("#ffffff"))

            // set text new location
            var targetButon: Button = buttons[i+8];
            targetButon.text = buttonText;
        }

    }
    //end

    //start get stelected button and set Response text
    fun getSelectedButtonValue(index: Int){

        var selectedButton: Button = buttons[index];
        var characterValue: String? = selectedButton.getText().toString();

        //save wordResuly
        if(characterValue != null && characterValue.length != 0)
            wordResult.text = wordResult.text.toString() + characterValue;
    }
    //end

    //start creat random character between of A and Z
    fun createRandomCharacter(): String {
        val rand = ('A'..'Z').random()
        return rand.toString();
    }
    //end

}