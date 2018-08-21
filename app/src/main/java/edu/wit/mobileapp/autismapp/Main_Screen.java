// Display Resolution 1920 x 1200 (Nexus 7 Tablet).

package edu.wit.mobileapp.autismapp;

//If emulator doesn't run, delete and add again.

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Main_Screen extends AppCompatActivity {

    Button exit_app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

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

        Window window = Main_Screen.this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(ContextCompat.getColor(Main_Screen.this,R.color.statusBar));
        }

        Button sentence_building = findViewById(R.id.b_sentence_building);
        sentence_building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentence_building();
            }
        });

        Button quiz = findViewById(R.id.b_quiz);
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz();
            }
        });

        // Exit the app!
        /**
        exit_app = (Button)findViewById(R.id.b_quit);
        exit_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Finish method is used to close all open activities.
                finish();
            }
        });
        */

    }

    private void sentence_building() {
        Intent intent = new Intent(Main_Screen.this, Sentence_Building.class);
        startActivity(intent);
    }

    private void quiz() {
        Intent intent = new Intent(Main_Screen.this, Quiz_Starting_Screen.class);
        startActivity(intent);
    }

    public void exit_app(View view) {
        finish();
        System.exit(0);
        // moveTaskToBack(true);
    }

    // Alert message, not working.
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you wish to exit out of this application?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Main_Screen.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}