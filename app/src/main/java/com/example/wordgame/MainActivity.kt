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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //buttons
    var buttons = ArrayList<Button>();
    //characters
    var characters = ArrayList<Character>();
    //falling Time
    var fallingTime:Long = 5000;
    //selectedButtons
    var selectedButtonsIndex = ArrayList<Int>();


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
        while (starterCounter < 80){
            if(starterCounter < 24){
                var value: String = createRandomCharacter();
                //we dont use freezing and score for now
                characters.add(Character(value));
            }else{
                characters.add(Character());
            }
            starterCounter = starterCounter + 1;
        }
        //end

        //start set begin value to buttons
        starterCounter = 0;
        while (starterCounter < 80){
            buttons[starterCounter].text = characters[starterCounter].value;
            starterCounter = starterCounter +1;
        }
        //end

        //start begin animation for 16 values
        startStarterAnimation();
        //end

        //start fallAnimation by timer
        var isFirstFallAnimation = true;
        object :CountDownTimer(500000000, fallingTime){
            override fun onTick(p0: Long) {
                if(isFirstFallAnimation){
                    // we have just wasted first 4 second time here
                    isFirstFallAnimation = false;
                }else{
                    startRandomFallAnimation()
                }
            }
            override fun onFinish() {}
        }.start()
        //end

        //start add clickLister to all buttons
        buttons.forEachIndexed{index: Int, button: Button ->
            button.setOnClickListener {
                if(isClicked(index)){
                    // if clicked and last vlue have to delete
                    deleteIfLastClicking(index)
                }else{
                    //value
                    getSelectedButtonValue(index);
                    //disable
                    setDisableToSelectedButton(index);
                }
            }
        }
        //end

        //start delete response clicklistener
        deleteButton.setOnClickListener{
            setEnableToSelectedAllButtons()
            wordResult.text = "";
        }
        //end

        //start apply response clicklistener
        applyButton.setOnClickListener{
            // have to add API function

        }
        //end

    }

    //start begin startAnimation time
    fun startStarterAnimation(){
        var rowCounter:Int = 16;
        object :CountDownTimer(10000,200){
            override fun onTick(p0: Long) {
                if(rowCounter > 56){
                    // 56 is starter index of eighth row
                    cancel()
                }
                setViewsStarterAnimation(rowCounter);
                rowCounter = rowCounter +8;
            }
            override fun onFinish() {}
        }.start()
    }
    //end

    //start begin animationViews
    fun setViewsStarterAnimation(rowCounter: Int){

        var temp: Int = rowCounter;

        for(i in temp.. temp+7) {
            // get button and set text empty
            var button: Button = buttons[i]
            var buttonText = button.text.toString()
            // change old button and character
            button.text="";
            characters[i].value="";

            // set text new location
            var targetButon: Button = buttons[i+8];
            // chage button text and characte text
            targetButon.text = buttonText;
            characters[i+8].value = buttonText;

            targetButon.setBackgroundColor(Color.parseColor("#E8A0BF"))
        }

        for(i in temp-8.. temp-1) {
            // get button and set text empty
            var button: Button = buttons[i]
            var buttonText = button.text.toString()
            // change old button and character
            button.text="";
            characters[i].value="";


            // set text new location
            var targetButon: Button = buttons[i+8];
            // chage button text and characte text
            targetButon.text = buttonText;
            characters[i+8].value = buttonText;
            targetButon.setBackgroundColor(Color.parseColor("#E8A0BF"))
        }

        for(i in temp-16.. temp-9) {
            // get button and set text empty
            var button: Button = buttons[i]
            var buttonText = button.text.toString()
            // change old button and character
            button.text="";
            characters[i].value="";
            button.setBackgroundColor(Color.parseColor("#ffffff"))

            // set text new location
            var targetButon: Button = buttons[i+8];
            // chage button text and characte text
            targetButon.text = buttonText;
            characters[i+8].value = buttonText;
            targetButon.setBackgroundColor(Color.parseColor("#E8A0BF"))
        }

    }
    //end

    //start begin random FallAnimation timer
    fun startRandomFallAnimation(specificColumn: Int = -1){
        var fallingColumn:Int;
        //random
        if(specificColumn == -1)
             fallingColumn = createRandomColumn();
        //not random we can use for all row so...
        else
             fallingColumn = specificColumn;

        var fallIndexCounter = fallingColumn;
        var createdCharacter = createRandomCharacter();

        var fallingAnimation = object :CountDownTimer(100000, 300){
            override fun onTick(p0: Long) {
                if(
                    fallIndexCounter > 72||
                    fallIndexCounter > 73||
                    fallIndexCounter > 74||
                    fallIndexCounter > 75||
                    fallIndexCounter > 76||
                    fallIndexCounter > 77||
                    fallIndexCounter > 78||
                    fallIndexCounter > 79
                ){
                    // we reach some last row
                    cancel()
                }
                var isDone = setViewRandomFallAnimation(fallIndexCounter, createdCharacter)
                if(isDone){
                    // have under value
                    cancel()
                }
                fallIndexCounter = fallIndexCounter + 8;
            }
            override fun onFinish() {}
        }
        fallingAnimation.start()
    }
    //end

    //start set view randomFallAnimation
    fun setViewRandomFallAnimation(fallIndex:Int, character:String): Boolean{
        var index = fallIndex;
        //first Row condition
        if(index <= 7){
            //first row
            //speacial situatin not deleteing before
            var button : Button = buttons[index];
            // check is game over ?
            if(button.text.toString().length !=0){
                // game should be over
                // for now game doesn't end
                return true;
            }else{
                button.text = character;
                characters[index].value = character;
                button.setBackgroundColor(Color.parseColor("#E8A0BF"))
                return  false;
            }
        }
        //another Row condition
        else{
            var oldButton:Button = buttons[index - 8];
            var nextButton:Button = buttons[index];

            // if nextButton has value, you wont go it
            if(nextButton.text.toString().length != 0){
                return true;
            }else{
                oldButton.text="";
                characters[index-8].value="";
                oldButton.setBackgroundColor(Color.parseColor("#ffffff"))

                nextButton.text=character;
                characters[index].value=character;
                nextButton.setBackgroundColor(Color.parseColor("#E8A0BF"))

                return false;
            }
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

    //start disable to selected Button
    fun setDisableToSelectedButton(index: Int){
        // add disabled array to find later
        selectedButtonsIndex.add(index);
        // diasbled characters
        characters[index].isActive = false;
        // disabled buttons
        buttons[index].setBackgroundColor(Color.parseColor("#77037B"))
        buttons[index].setTextColor(Color.parseColor("#FFFFFF"))
    }
    //end

    //start enable to all selected buttons
    fun setEnableToSelectedAllButtons(){
        // add enabled all selected buttons
        for(index in selectedButtonsIndex){

            // enable to characters
            characters[index].isActive = true;
            // enable buttons
            buttons[index].setBackgroundColor(Color.parseColor("#E8A0BF"))
            buttons[index].setTextColor(Color.parseColor("#000000"))
        }

        // clear selected array
        selectedButtonsIndex.clear()
    }
    //end

    //Start enable to specific button
    fun setEnableToSelectedButton(index: Int){

        // enable to characters
        characters[index].isActive = true;
        // enable buttons
        buttons[index].setBackgroundColor(Color.parseColor("#E8A0BF"))
        buttons[index].setTextColor(Color.parseColor("#000000"))

        selectedButtonsIndex.remove(index);

    }
    //end

    //start if lastcliked buttons delete else do nothing
    fun deleteIfLastClicking(index: Int){

        var result:String = wordResult.text.toString();

        if(result.last().toString().equals(buttons[index].text.toString())){

            //clear output
            result = result.dropLast(1);
            wordResult.text = result;
            //set enable To button
            setEnableToSelectedButton(index);
        }
    }
    //end

    //start checkIsClick
    fun isClicked(index: Int): Boolean{
        if(selectedButtonsIndex.indexOf(index) != -1){
            return true
        }

        return false
    }
    //end

    //start creat random character between of A and Z
    fun createRandomCharacter(): String {
        val rand = ('A'..'Z').random();
        return rand.toString();
    }
    //end

    //start create random column between of 0 and 7
    fun createRandomColumn(): Int{
        val rand = (0..7).random();

        return rand;
    }
    //end

}