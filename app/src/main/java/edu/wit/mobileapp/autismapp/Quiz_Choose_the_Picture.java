package edu.wit.mobileapp.autismapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Quiz_Choose_the_Picture extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 60000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private View view1, view2, view3, view4;

    private TextView textViewQuestion;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCountDown;
    private RadioGroup rbGroup;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Quiz_Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Quiz_Question currentQuestion;

    private int score;
    private boolean answered;

    private TextView resTv;

    private ImageView iv_option1;
    private ImageView iv_option2;
    private ImageView iv_option3;
    private ImageView iv_option4;

    Button listen;

    private TextToSpeech mTTS;

    int questionCheck = 0;
    private long backPressedTime;

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_choose_the_picture);

        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);

        iv_option1 = findViewById(R.id.iv_option1);
        iv_option2 = findViewById(R.id.iv_option2);
        iv_option3 = findViewById(R.id.iv_option3);
        iv_option4 = findViewById(R.id.iv_option4);

        listen = findViewById(R.id.b_listen);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.US);
                }
                else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        // Go back to starting screen!
        Button to_starting_screen = findViewById(R.id.b_quit);
        to_starting_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                to_starting_screen();
            }
        });
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);

        /**
        if (diagonalInches >= 7){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            ConstraintLayout.LayoutParams iv1 = (ConstraintLayout.LayoutParams) iv_option1.getLayoutParams();
            iv1.height = 400;
            iv1.width = 400;
            iv_option1.setLayoutParams(iv1);

            ConstraintLayout.LayoutParams iv2 = (ConstraintLayout.LayoutParams) iv_option2.getLayoutParams();
            iv2.height = 400;
            iv2.width = 400;
            iv_option2.setLayoutParams(iv2);

            ConstraintLayout.LayoutParams iv3 = (ConstraintLayout.LayoutParams) iv_option3.getLayoutParams();
            iv3.height = 400;
            iv3.width = 400;
            iv_option3.setLayoutParams(iv3);

            ConstraintLayout.LayoutParams iv4 = (ConstraintLayout.LayoutParams) iv_option4.getLayoutParams();
            iv4.height = 400;
            iv4.width = 400;
            iv_option4.setLayoutParams(iv4);

            ConstraintLayout.LayoutParams iv5 = (ConstraintLayout.LayoutParams) view1.getLayoutParams();
            iv5.height = 410;
            iv5.width = 410;
            view1.setLayoutParams(iv5);

            ConstraintLayout.LayoutParams iv6 = (ConstraintLayout.LayoutParams) view2.getLayoutParams();
            iv6.height = 410;
            iv6.width = 410;
            view2.setLayoutParams(iv6);

            ConstraintLayout.LayoutParams iv7 = (ConstraintLayout.LayoutParams) view3.getLayoutParams();
            iv7.height = 410;
            iv7.width = 410;
            view3.setLayoutParams(iv7);

            ConstraintLayout.LayoutParams iv8 = (ConstraintLayout.LayoutParams) view4.getLayoutParams();
            iv8.height = 410;
            iv8.width = 410;
            view4.setLayoutParams(iv8);

        }
         */

        if (diagonalInches >= 7) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            /*
            ConstraintLayout.LayoutParams iv1 = (ConstraintLayout.LayoutParams) iv_option1.getLayoutParams();
            iv1.height = 240;
            iv1.width = 240;
            iv_option1.setLayoutParams(iv1);

            ConstraintLayout.LayoutParams iv5 = (ConstraintLayout.LayoutParams) view1.getLayoutParams();
            iv5.height = 250;
            iv5.width = 250;
            view1.setLayoutParams(iv5);

            ConstraintLayout.LayoutParams iv2 = (ConstraintLayout.LayoutParams) iv_option2.getLayoutParams();
            iv2.height = 240;
            iv2.width = 240;
            iv_option2.setLayoutParams(iv2);

            ConstraintLayout.LayoutParams iv6 = (ConstraintLayout.LayoutParams) view2.getLayoutParams();
            iv5.height = 250;
            iv5.width = 250;
            view2.setLayoutParams(iv6);

            ConstraintLayout.LayoutParams iv3 = (ConstraintLayout.LayoutParams) iv_option3.getLayoutParams();
            iv3.height = 240;
            iv3.width = 240;
            iv_option3.setLayoutParams(iv3);

            ConstraintLayout.LayoutParams iv7 = (ConstraintLayout.LayoutParams) view3.getLayoutParams();
            iv5.height = 250;
            iv5.width = 250;
            view3.setLayoutParams(iv7);

            ConstraintLayout.LayoutParams iv4 = (ConstraintLayout.LayoutParams) iv_option4.getLayoutParams();
            iv4.height = 240;
            iv4.width = 240;
            iv_option4.setLayoutParams(iv4);

            ConstraintLayout.LayoutParams iv8 = (ConstraintLayout.LayoutParams) view4.getLayoutParams();
            iv5.height = 250;
            iv5.width = 250;
            view4.setLayoutParams(iv8);
            **/
        }

        else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        textViewQuestion = findViewById(R.id.tv_question);
        textViewScore = findViewById(R.id.tv_score);
        textViewQuestionCount = findViewById(R.id.tv_question_number);
        textViewCountDown = findViewById(R.id.tv_countdown);



        resTv = findViewById(R.id.resTv);

        resTv.setText("");

        iv_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCheck = 1;
                checkAnswer();
            }
        });
        iv_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCheck = 2;
                checkAnswer();
            }
        });
        iv_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCheck = 3;
                checkAnswer();
            }
        });

        iv_option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionCheck = 4;
                checkAnswer();
            }
        });



        buttonConfirmNext = findViewById(R.id.b_confirm);

        textColorDefaultCd = textViewCountDown.getTextColors();

        if (savedInstanceState == null) {
            Quiz_Database_Helper dbHelper = new Quiz_Database_Helper(this);
            questionList = dbHelper.getAllQuestions();
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        }
        else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();

            }
            else {
                updateCountDownText();
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (questionCheck != 0) {
                        checkAnswer();
                    }
                    else {
                        Toast.makeText(Quiz_Choose_the_Picture.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    showNextQuestion();
                    resTv.setText("");
                }
            }
        });

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = currentQuestion.getQuestion();
                speak();
            }
        });
        text = currentQuestion.getQuestion();
        float pitch;
        pitch = 0.8f;
        float speed = 0.5f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        // mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);


    }

    private void showNextQuestion() {
//        rb1.setTextColor(textColorDefaultRb);
//        rb2.setTextColor(textColorDefaultRb);
//        rb3.setTextColor(textColorDefaultRb);
//        rb4.setTextColor(textColorDefaultRb);

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            text = currentQuestion.getQuestion();
            speak();
//            rb1.setText(currentQuestion.getOption1());
//            rb2.setText(currentQuestion.getOption2());
//            rb3.setText(currentQuestion.getOption3());
//            rb4.setText(currentQuestion.getOption4());

            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Next");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        }
        else {
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        }
        else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        answered = true;

        countDownTimer.cancel();

        showSolution();
    }

    private void showSolution() {
//        rb1.setTextColor(Color.RED);
//        rb2.setTextColor(Color.RED);
//        rb3.setTextColor(Color.RED);
//        rb4.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
//                rb1.setTextColor(Color.GREEN);
                if(questionCheck == 1)
                {
                    resTv.setText("Correct");
                    resTv.setTextColor(getResources().getColor(R.color.green));
                    score++;
                    textViewScore.setText("Score:  " + score);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(getResources().getColor(R.color.green));
                    view2.setBackgroundColor(Color.RED);
                    view3.setBackgroundColor(Color.RED);
                    view4.setBackgroundColor(Color.RED);
                }
                else
                {
                    resTv.setText("Wrong");
                    resTv.setTextColor(Color.RED);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(getResources().getColor(R.color.green));
                    view2.setBackgroundColor(Color.RED);
                    view3.setBackgroundColor(Color.RED);
                    view4.setBackgroundColor(Color.RED);
                }
                questionCheck = 0;
                textViewQuestion.setText("Home");
                break;
            case 2:
//                rb2.setTextColor(Color.GREEN);
                if(questionCheck == 2)
                {
                    resTv.setText("Correct");
                    resTv.setTextColor(getResources().getColor(R.color.green));
                    score++;
                    textViewScore.setText("Score:  " + score);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(Color.RED);
                    view2.setBackgroundColor(getResources().getColor(R.color.green));
                    view3.setBackgroundColor(Color.RED);
                    view4.setBackgroundColor(Color.RED);
                }
                else
                {
                    resTv.setText("Wrong");
                    resTv.setTextColor(Color.RED);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(Color.RED);
                    view2.setBackgroundColor(getResources().getColor(R.color.green));
                    view3.setBackgroundColor(Color.RED);
                    view4.setBackgroundColor(Color.RED);

                }
                questionCheck = 0;
                textViewQuestion.setText("School");
                break;
            case 3:
//                rb3.setTextColor(Color.GREEN);
                if(questionCheck == 3)
                {
                    resTv.setText("Correct");
                    resTv.setTextColor(getResources().getColor(R.color.green));
                    score++;
                    textViewScore.setText("Score:  " + score);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(Color.RED);
                    view2.setBackgroundColor(Color.RED);
                    view3.setBackgroundColor(getResources().getColor(R.color.green));
                    view4.setBackgroundColor(Color.RED);
                }
                else
                {
                    resTv.setText("Wrong");
                    resTv.setTextColor(Color.RED);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(Color.RED);
                    view2.setBackgroundColor(Color.RED);
                    view3.setBackgroundColor(getResources().getColor(R.color.green));
                    view4.setBackgroundColor(Color.RED);
                }
                questionCheck = 0;
                textViewQuestion.setText("Park");
                break;
            case 4:
//                rb4.setTextColor(Color.GREEN);
                if(questionCheck == 4)
                {
                    resTv.setText("Correct");
                    resTv.setTextColor(getResources().getColor(R.color.green));
                    score++;
                    textViewScore.setText("Score:  " + score);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(Color.RED);
                    view2.setBackgroundColor(Color.RED);
                    view3.setBackgroundColor(Color.RED);
                    view4.setBackgroundColor(getResources().getColor(R.color.green));
                }
                else
                {
                    resTv.setText("Wrong");
                    resTv.setTextColor(Color.RED);
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view1.setBackgroundColor(Color.RED);
                    view2.setBackgroundColor(Color.RED);
                    view3.setBackgroundColor(Color.RED);
                    view4.setBackgroundColor(getResources().getColor(R.color.green));
                }
                questionCheck = 0;
                textViewQuestion.setText("Museum");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        }
        else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {
        int highscore;
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);

        if (score > highscore)
        {
            Toast.makeText(this, "High Score!", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(KEY_HIGHSCORE, score);
            editor.apply();
        }

        to_starting_screen();
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        }
        else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }

    // Go back to starting screen.
    private void to_starting_screen() {
        Intent intent = new Intent(Quiz_Choose_the_Picture.this, Quiz_Starting_Screen.class);
        startActivity(intent);
    }


    private void speak() {
        // String text = mSentence.getText().toString();
        float pitch;
        pitch = 0.8f;
        float speed = 0.5f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        // mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        mTTS.speak("Choose the matching word:  " + text, TextToSpeech.QUEUE_FLUSH, null);
    }
}

