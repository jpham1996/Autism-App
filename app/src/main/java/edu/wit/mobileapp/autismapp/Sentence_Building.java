package edu.wit.mobileapp.autismapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Sentence_Building extends AppCompatActivity implements View.OnClickListener {

    // Declaration for my Text-to-Speech
    private TextToSpeech mTTS;

    // Sentence
    private TextView mSentence;

    // Speaker Variables
    private SeekBar mSBPitch;
    private SeekBar mSBSpeed;
    private Button mSpeak;

    // Buttons and Image Buttons
    private Button mReset;

    /** Declaration is not needed as declaration is already made in getUiObject().
     private ImageButton mI, mWant, mTo, mThe, mEat, mDrink, mPlay, mGo, mYes, mNo;
     private Button mSandwich, mSalad, mRice, mNoodle, mWater, mJuice, mMilk, mSuperhero, mBarbie, mToyTrain,
     mPlayDoh, mRun, mSwim, mTennis, mBasketball, mSoccer, mHome, mSchool, mMuseum, mPark, mZoom, Sleep; **/
    private String display_sentence = "";

    // Create dialogs for Pop Ups, there are six categories.
    Dialog Dialog_Food, Dialog_Drink, Dialog_Sport, Dialog_Toy, Dialog_Location, Dialog_Other, Dialog_People;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_building);

        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=7){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        Window window = Sentence_Building.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(ContextCompat.getColor(Sentence_Building.this,R.color.statusBar));
        }


        Button go_back = findViewById(R.id.b_go_back);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_back();
            }
        });

        mSpeak = findViewById(R.id.b_speak);

        // Text to speech, it fails, catlog shows the warning message.
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                    else {
                        mSpeak.setEnabled(true);
                    }
                }
                else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        // Locate for the ID of seek bars.
        mSBPitch = findViewById(R.id.sb_pitch);
        mSBSpeed = findViewById(R.id.sb_speed);

        mSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

        // Declaration of six categories.
        Dialog_Food = new Dialog(this);
        Dialog_Drink = new Dialog(this);
        Dialog_Sport = new Dialog(this);
        Dialog_Toy = new Dialog(this);
        Dialog_Location = new Dialog(this);
        Dialog_Other = new Dialog(this);
        Dialog_People = new Dialog(this);
        getUiObject();

    }

    // Acquire UI objects from linked layout file.
    public void getUiObject() {

        mReset = findViewById(R.id.b_reset);

        ImageButton mI = findViewById(R.id.ib_i);
        ImageButton mDont = findViewById(R.id.ib_dont);
        ImageButton mWant = findViewById(R.id.ib_want);
        ImageButton mTo = findViewById(R.id.ib_to);
        // ImageButton mThe = findViewById(R.id.ib_the);
        ImageButton mEat = findViewById(R.id.ib_eat);
        ImageButton mDrink = findViewById(R.id.ib_drink);
        ImageButton mPlay = findViewById(R.id.ib_play);
        ImageButton mGo = findViewById(R.id.ib_go);
        ImageButton mYes = findViewById(R.id.ib_yes);
        ImageButton mNo = findViewById(R.id.ib_no);
        ImageButton mWith = findViewById(R.id.ib_with);

        mSentence = findViewById(R.id.tv_sentence);

        mI.setOnClickListener(this);
        mDont.setOnClickListener(this);
        mWant.setOnClickListener(this);
        mTo.setOnClickListener(this);
        // mThe.setOnClickListener(this);
        mEat.setOnClickListener(this);
        mDrink.setOnClickListener(this);
        mPlay.setOnClickListener(this);
        mGo.setOnClickListener(this);
        mYes.setOnClickListener(this);
        mNo.setOnClickListener(this);
        mWith.setOnClickListener(this);

        mReset.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            /** Method below is only works with buttons not image buttons.
             *
             case R.id.ib_sleep:
             display_sentence += mSleep.getText().toString() + " ";
             mSentence.setText(display_sentence);
             break;
             */

            // Use Boolean to ensure display sentence is making sense.
            case R.id.ib_i:
                mTTS.speak("I", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence == "") {
                    display_sentence += "I ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_dont:
                mTTS.speak("don't", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I ")) {
                    display_sentence += "don't ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_want:
                mTTS.speak("want", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I ") ||
                        display_sentence.equalsIgnoreCase("I don't ")) {
                    display_sentence += "want ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_to:
                mTTS.speak("to", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I want ") ||
                        display_sentence.equalsIgnoreCase("I want to go ") ||
                        display_sentence.equalsIgnoreCase("I don't want ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go ")) {
                    display_sentence += "to ";
                    mSentence.setText(display_sentence);
                }
                break;
            /** Comment out in case if we still use "the" later.
             case R.id.ib_the:
             mTTS.speak("the", TextToSpeech.QUEUE_FLUSH, null);
             if(display_sentence.equalsIgnoreCase("I want to go to ")) {
             display_sentence += "the ";
             mSentence.setText(display_sentence);
             }
             break;
             */
            case R.id.ib_eat:
                mTTS.speak("eat", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "eat ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_drink:
                mTTS.speak("drink", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "drink ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_play:
                mTTS.speak("play", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "play ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_go:
                mTTS.speak("go", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "go ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_yes:
                mTTS.speak("yes", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("")) {
                    display_sentence += "Yes ";
                    mSentence.setText(display_sentence);
                }
                break;
            case R.id.ib_no:
                mTTS.speak("no", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("")) {
                    display_sentence += "No ";
                    mSentence.setText(display_sentence);
                }
                break;

            case R.id.ib_with:
                mTTS.speak("with", TextToSpeech.QUEUE_FLUSH, null);
                if(display_sentence.equalsIgnoreCase("I want to eat ") ||
                        display_sentence.equalsIgnoreCase("I want to eat sandwich ") ||
                        display_sentence.equalsIgnoreCase("I want to eat salad ") ||
                        display_sentence.equalsIgnoreCase("I want to eat rice ") ||
                        display_sentence.equalsIgnoreCase("I want to juice ") ||
                        display_sentence.equalsIgnoreCase("I want to drink ") ||
                        display_sentence.equalsIgnoreCase("I want to drink water ") ||
                        display_sentence.equalsIgnoreCase("I want to drink juice ") ||
                        display_sentence.equalsIgnoreCase("I want to drink milk ") ||
                        display_sentence.equalsIgnoreCase("I want to play ") ||
                        display_sentence.equalsIgnoreCase("I want to play superhero ") ||
                        display_sentence.equalsIgnoreCase("I want to play barbie ") ||
                        display_sentence.equalsIgnoreCase("I want to play toy train ") ||
                        display_sentence.equalsIgnoreCase("I want to play play-doh ") ||
                        display_sentence.equalsIgnoreCase("I want to run ") ||
                        display_sentence.equalsIgnoreCase("I want to swim ") ||
                        display_sentence.equalsIgnoreCase("I want to play tennis ") ||
                        display_sentence.equalsIgnoreCase("I want to play basketball ") ||
                        display_sentence.equalsIgnoreCase("I want to play soccer ") ||
                        display_sentence.equalsIgnoreCase("I want to play baseball ") ||
                        display_sentence.equalsIgnoreCase("I want to go ") ||
                        display_sentence.equalsIgnoreCase("I want to go home ") ||
                        display_sentence.equalsIgnoreCase("I want to go to school ") ||
                        display_sentence.equalsIgnoreCase("I want to go to museum ") ||
                        display_sentence.equalsIgnoreCase("I want to go to park ") ||
                        display_sentence.equalsIgnoreCase("I want to go to zoo ") ||

                        display_sentence.equalsIgnoreCase("I don't want to eat ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat sandwich ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat salad ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat rice ") ||
                        display_sentence.equalsIgnoreCase("I don't want to juice ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink water ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink juice ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink milk ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play superhero ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play barbie ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play toy train ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play play-doh ") ||
                        display_sentence.equalsIgnoreCase("I don't want to run ") ||
                        display_sentence.equalsIgnoreCase("I don't want to swim ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play tennis ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play basketball ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play soccer ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play baseball ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go home ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to school ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to museum ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to park ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to zoo ")) {
                    display_sentence += "with ";
                    mSentence.setText(display_sentence);
                }
                break;

            case R.id.b_reset:
                display_sentence = "";
                mSentence.setText(display_sentence);
                break;
            // Reset to empty sentence.
        }
    }

    private void speak() {
        // String text = mSentence.getText().toString();
        float pitch = (float) mSBPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSBSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        // mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        mTTS.speak(display_sentence, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    /** CATEGORY:  FOOD
     * PopUpFood is the "OnClick" reference of the button ID "pop_up_food".
     */
    public void PopUpFood (View v) {
        Dialog_Food.setContentView(R.layout.pop_up_food);
        Dialog_Food.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_Food.show();

        ImageButton mSandwich = Dialog_Food.findViewById(R.id.ib_sandwich);
        ImageButton mSalad = Dialog_Food.findViewById(R.id.ib_salad);
        ImageButton mRice = Dialog_Food.findViewById(R.id.ib_rice);
        ImageButton mNoodle = Dialog_Food.findViewById(R.id.ib_noodle);
        mSandwich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to eat ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat ")) {
                    display_sentence += "sandwich ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("sandwich", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mSalad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to eat ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat ")) {
                    display_sentence += "salad ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("salad", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mRice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to eat ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat ")) {
                    display_sentence += "rice ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("rice", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mNoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to eat ") ||
                        display_sentence.equalsIgnoreCase("I don't want to eat ")) {
                    display_sentence += "noodle ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("noodle", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    /** CATEGORY:  DRINK
     * PopUpDrink is the "OnClick" reference of the button ID:  pop_up_drink.
     */
    public void PopUpDrink (View v) {
        Dialog_Drink.setContentView(R.layout.pop_up_drink);
        Dialog_Drink.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_Drink.show();

        ImageButton mWater = Dialog_Drink.findViewById(R.id.ib_water);
        ImageButton mJuice = Dialog_Drink.findViewById(R.id.ib_juice);
        ImageButton mMilk = Dialog_Drink.findViewById(R.id.ib_milk);
        mWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to drink ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink ")) {
                    display_sentence += "water ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("water", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mJuice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to drink ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink ")) {
                    display_sentence += "juice ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("juice", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to drink ") ||
                        display_sentence.equalsIgnoreCase("I don't want to drink ")) {
                    display_sentence += "milk ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("milk", TextToSpeech.QUEUE_FLUSH, null);
            }
        });


    }

    /** CATEGORY:  SPORT
     * PopUpSport is the "OnClick" reference of the button ID:  pop_up_sport.
     */
    public void PopUpSport (View v) {
        Dialog_Sport.setContentView(R.layout.pop_up_sport);
        Dialog_Sport.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_Sport.show();

        ImageButton mRun = Dialog_Sport.findViewById(R.id.ib_run);
        ImageButton mSwim = Dialog_Sport.findViewById(R.id.ib_swim);
        ImageButton mTennis = Dialog_Sport.findViewById(R.id.ib_tennis);
        ImageButton mBasketball = Dialog_Sport.findViewById(R.id.ib_basketball);
        ImageButton mSoccer = Dialog_Sport.findViewById(R.id.ib_soccer);
        ImageButton mBaseball = Dialog_Sport.findViewById(R.id.ib_baseball);

        mRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "run ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("run", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mSwim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "swim ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("swim", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play ")) {
                    display_sentence += "tennis ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("tennis", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mBasketball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play ")) {
                    display_sentence += "basketball ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("basketball", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mSoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play ")) {
                    display_sentence += "soccer ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("soccer", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mSoccer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play ")) {
                    display_sentence += "baseball ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("baseball", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    /** CATEGORY:  TOY
     * PopUpToy is the "OnClick" reference of the button ID:  pop_up_toy.
     */
    public void PopUpToy (View v) {
        Dialog_Toy.setContentView(R.layout.pop_up_toy);
        Dialog_Toy.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_Toy.show();

        ImageButton mSuperhero = Dialog_Toy.findViewById(R.id.ib_superhero);
        ImageButton mBarbie = Dialog_Toy.findViewById(R.id.ib_barbie);
        ImageButton mToyTrain = Dialog_Toy.findViewById(R.id.ib_toy_train);
        ImageButton mPlayDoh = Dialog_Toy.findViewById(R.id.ib_play_doh);

        mSuperhero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play with ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play with ")) {
                    display_sentence += "superhero ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("superhero", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mBarbie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play with ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play with ")) {
                    display_sentence += "barbie ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("barbie", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mToyTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play with ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play with ")) {
                    display_sentence += "toy train ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("toy train", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mPlayDoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to play ") ||
                        display_sentence.equalsIgnoreCase("I don't want to play with ")) {
                    display_sentence += "play-doh ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("play-doh", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    /** CATEGORY:  LOCATION
     * PopUpLocation is the "OnClick" reference of the button ID:  pop_up_location.
     */
    public void PopUpLocation (View v) {
        Dialog_Location.setContentView(R.layout.pop_up_location);
        Dialog_Location.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_Location.show();

        ImageButton mHome = Dialog_Location.findViewById(R.id.ib_home);
        ImageButton mSchool = Dialog_Location.findViewById(R.id.ib_school);
        ImageButton mMuseum = Dialog_Location.findViewById(R.id.ib_museum);
        ImageButton mPark = Dialog_Location.findViewById(R.id.ib_park);
        ImageButton mZoo = Dialog_Location.findViewById(R.id.ib_zoo);

        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to go ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go ")) {
                    display_sentence += "home ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("home", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to go to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to ")) {
                    display_sentence += "school ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("school", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mMuseum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to go to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to ")) {
                    display_sentence += "museum ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("museum", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to go to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to ")) {
                    display_sentence += "park ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("park", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mZoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to go to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to go to ")) {
                    display_sentence += "zoo ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("zoo", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    /** CATEGORY:  OTHER
     * PopUpOther is the "OnClick" reference of the button ID:  pop_up_other.
     */
    public void PopUpOther (View v) {
        Dialog_Other.setContentView(R.layout.pop_up_other);
        Dialog_Other.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_Other.show();

        ImageButton mSleep = Dialog_Other.findViewById(R.id.ib_sleep);
        ImageButton mKnow = Dialog_Other.findViewById(R.id.ib_know);

        mSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ")) {
                    display_sentence += "sleep ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("sleep", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        mKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(display_sentence.equalsIgnoreCase("I want to ") ||
                        display_sentence.equalsIgnoreCase("I don't want to ") ||
                        display_sentence.equalsIgnoreCase("I ") ||
                        display_sentence.equalsIgnoreCase("I don't ")) {
                    display_sentence += "know ";
                }
                mSentence.setText(display_sentence);
                mTTS.speak("know", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    /** NEW CATEGORY:  PEOPLE
     * PopUpPeople is the "OnClick" reference of the button ID:  pop_up_other.
     */
    public void PopUpPeople (View v) {
        Dialog_People.setContentView(R.layout.pop_up_people);
        Dialog_People.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Dialog_People.show();

        // Get the last word.

        ImageButton mYou = Dialog_People.findViewById(R.id.ib_you);
        ImageButton mMom = Dialog_People.findViewById(R.id.ib_mom);
        ImageButton mDad = Dialog_People.findViewById(R.id.ib_dad);
        ImageButton mBrother = Dialog_People.findViewById(R.id.ib_brother);
        ImageButton mSister = Dialog_People.findViewById(R.id.ib_sister);
        ImageButton mFriend = Dialog_People.findViewById(R.id.ib_friend);

        mYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = display_sentence.split(" ");
                String LastWord = parts[parts.length - 1];
                if(LastWord.equalsIgnoreCase("with")) {
                    display_sentence += "you ";
                    mSentence.setText(display_sentence);
                }
                mSentence.setText(display_sentence);
                mTTS.speak("you", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        mMom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = display_sentence.split(" ");
                String LastWord = parts[parts.length - 1];
                if(LastWord.equalsIgnoreCase("with")) {
                    display_sentence += "mom ";
                    mSentence.setText(display_sentence);
                }
                mSentence.setText(display_sentence);
                mTTS.speak("mom", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        mDad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = display_sentence.split(" ");
                String LastWord = parts[parts.length - 1];
                if(LastWord.equalsIgnoreCase("with")) {
                    display_sentence += "dad ";
                    mSentence.setText(display_sentence);
                }
                mSentence.setText(display_sentence);
                mTTS.speak("dad", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        mBrother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = display_sentence.split(" ");
                String LastWord = parts[parts.length - 1];
                if(LastWord.equalsIgnoreCase("with")) {
                    display_sentence += "brother ";
                    mSentence.setText(display_sentence);
                }
                mSentence.setText(display_sentence);
                mTTS.speak("brother", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        mSister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = display_sentence.split(" ");
                String LastWord = parts[parts.length - 1];
                if(LastWord.equalsIgnoreCase("with")) {
                    display_sentence += "sister ";
                    mSentence.setText(display_sentence);
                }
                mSentence.setText(display_sentence);
                mTTS.speak("sister", TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        mFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parts = display_sentence.split(" ");
                String LastWord = parts[parts.length - 1];
                if(LastWord.equalsIgnoreCase("with")) {
                    display_sentence += "friend ";
                    mSentence.setText(display_sentence);
                }
                mSentence.setText(display_sentence);
                mTTS.speak("friend", TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    private void go_back() {
        Intent intent = new Intent(Sentence_Building.this, Main_Screen.class);
        startActivity(intent);
    }


}