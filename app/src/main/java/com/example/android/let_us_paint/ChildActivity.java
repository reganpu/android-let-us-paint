package com.example.android.let_us_paint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class ChildActivity extends AppCompatActivity {

    ImageButton ibtnRed;
    ImageButton ibtnOrange;
    ImageButton ibtnYellow;
    ImageButton ibtnGreen;
    ImageButton ibtnBlue;
    ImageButton ibtnPurple;
    ImageButton ibtnGray;
    ImageButton ibtnBlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        ibtnRed = (ImageButton) findViewById(R.id.ibtn_red);
        ibtnOrange = (ImageButton) findViewById(R.id.ibtn_orange);
        ibtnYellow = (ImageButton) findViewById(R.id.ibtn_yellow);
        ibtnGreen = (ImageButton) findViewById(R.id.ibtn_green);
        ibtnBlue = (ImageButton) findViewById(R.id.ibtn_blue);
        ibtnPurple = (ImageButton) findViewById(R.id.ibtn_purple);
        ibtnGray = (ImageButton) findViewById(R.id.ibtn_gray);
        ibtnBlack = (ImageButton) findViewById(R.id.ibtn_black);

        ibtnRed.setOnClickListener(mColorListener);
        ibtnOrange.setOnClickListener(mColorListener);
        ibtnYellow.setOnClickListener(mColorListener);
        ibtnGreen.setOnClickListener(mColorListener);
        ibtnBlue.setOnClickListener(mColorListener);
        ibtnPurple.setOnClickListener(mColorListener);
        ibtnGray.setOnClickListener(mColorListener);
        ibtnBlack.setOnClickListener(mColorListener);
    }

    // Create an anonymous implementation of OnClickListener
    private View.OnClickListener mColorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // do something when the button is clicked
            //創建intent對象，設置顏色為附加參數
            Intent intent = new Intent();
            //new一個Bundle物件，並將要傳遞的資料傳入
            Bundle bundle = new Bundle();
            //傳遞String

            switch(v.getId()){
                case R.id.ibtn_red:
                    bundle.putString("color", "red");
                    break;
                case R.id.ibtn_orange:
                    bundle.putString("color", "orange");
                    break;
                case R.id.ibtn_yellow:
                    bundle.putString("color", "yellow");
                    break;
                case R.id.ibtn_green:
                    bundle.putString("color", "green");
                    break;
                case R.id.ibtn_blue:
                    bundle.putString("color", "blue");
                    break;
                case R.id.ibtn_purple:
                    bundle.putString("color", "purple");
                    break;
                case R.id.ibtn_gray:
                    bundle.putString("color", "gray");
                    break;
                case R.id.ibtn_black:
                    bundle.putString("color", "black");
                    break;
                default:
                    bundle.putString("color", "green");
                    break;
            }

            //將Bundle物件傳給intent
            intent.putExtras(bundle);
            //設置返回值，將intent對象作為資料返回到來源Activity
            setResult(RESULT_OK, intent);
            ChildActivity.this.finish();
        }
    };
}
