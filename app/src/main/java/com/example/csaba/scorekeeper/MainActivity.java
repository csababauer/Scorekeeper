package com.example.csaba.scorekeeper;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    int scoreA = 0;
    int scoreB = 0;
    private Button goalA;
    private Button goalB;
    private Button reset;
    private TableLayout tableLayout;
    private int rowCounter;

    private String team;

    //timer
    private Chronometer chronometer;
    private long elapsedMillis;
    private long minutes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** button definitions */
        goalA = (Button) findViewById(R.id.goalTeamA);
        goalB = (Button) findViewById(R.id.goalTeamB);
        reset = (Button) findViewById(R.id.reset);


        /** timer */
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        elapsedMillis = 0;

        /** button onclick listeners */
        goalA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shootA(scoreA);
            }
        });
        goalB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shootB(scoreB);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            scoreA = 0;
            scoreB = 0;
            displayA(scoreA);
            displayB(scoreB);
            cleanTable();
            timerRestart();
            }
        });


        rowCounter = 1;

    }




    /** score for team A
     * @param view*/
    public void shootA (int view){
        scoreA = scoreA + 1;
        displayA(scoreA);

        team = "A";
        addTableRow(team);
    }
    /** score for team B
     * @param view*/
    public void shootB (int view){
        scoreB = scoreB + 1;
        displayB(scoreB);

        team = "B";
        addTableRow(team);
    }




    /** display scores */
    private void displayA(int number) {
        TextView scoreTeamA = (TextView) findViewById(R.id.teamA);
        scoreTeamA.setText("" + number);
    }
    private void displayB(int number) {
        TextView scoreTeamB = (TextView) findViewById(R.id.teamB);
        scoreTeamB.setText("" + number);
    }


    /** creating a new table row */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void addTableRow (String team){

        /** calculate elapsed time*/
        elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        long minutes = (elapsedMillis / 1000) / 60 + 1;

        TableLayout tl = (TableLayout) findViewById(R.id.myTable);
        TableRow row = new TableRow(this);

        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        TextView tv = new TextView(this);
        tv.setWidth(400);

        /** "String team" defines where the football unicode character (\u25BD) appears*/
        if (team == "A") {
            tv.setText("\u26BD" + "\u0020\u0020\u0020" + scoreA + " - " + scoreB + "           " + minutes + "'");
        } else {
            tv.setText("\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020" + scoreA + " - " + scoreB + "\u0020\u0020\u0020" + "\u26BD" + "   " + minutes + "'" );
        }

        tv.setGravity(Gravity.CENTER);
        row.setGravity(Gravity.CENTER);

        if (rowCounter % 2 == 0) {
            // Background Color for even rows
            tv.setBackgroundColor(Color.rgb(150, 150, 150));
        } else {
            // Background Color for odd rows
            tv.setBackgroundColor(Color.rgb(50, 50, 50));
        }

        tl.addView(row);
        row.addView(tv);


        rowCounter ++;
    }



    /** clean table row on reset */
    private void cleanTable() {
        TableLayout table = (TableLayout) findViewById(R.id.myTable);
        table.removeAllViews();
    }



    /** timer restart */
    public void timerRestart () {
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        chronometer.start();
    }

}





