package com.example.android.let_us_paint;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton ibtnPen, ibtnEraser, ibtnUndo;
    private ArtboardView artboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instantiate this variable by retrieving a reference to it from the layout
        // We now have the View that is displayed in the Activity on which we can call
        // the methods in the ArtboardView class.
        artboardView = (ArtboardView) findViewById(R.id.artboard);

        /*
         * Using findViewById, we get a reference to our Button from xml. This allows us to
         * do things like set the onClickListener which determines what happens when the button
         * is clicked.
         */
        ibtnPen = (ImageButton) findViewById(R.id.ibtn_pen);
        ibtnEraser = (ImageButton) findViewById(R.id.ibtn_eraser);
        ibtnUndo = (ImageButton) findViewById(R.id.ibtn_undo);
        /* Setting an OnClickListener allows us to do something when this button is clicked. */
        ibtnPen.setOnClickListener(onClickListenerPen);
        ibtnEraser.setOnClickListener(onClickListenerEraser);
        ibtnUndo.setOnClickListener(onClickListenerUndo);

    }

    private View.OnClickListener onClickListenerUndo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            artboardView.undo();
        }
    };

    private View.OnClickListener onClickListenerEraser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // switch to erase mode
            //artboardView.setErase(true);
            artboardView.drawPaint = createDrawPaint(0xFFFFFFFF);
            //artboardView.canvasBitmap.eraseColor(Color.TRANSPARENT);
        }
    };

    private View.OnClickListener onClickListenerPen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // switch to non-erase mode
            //artboardView.setErase(false);
            /*
                 * Storing the Context in a variable in this case is redundant since we could have
                 * just used "this" or "MainActivity.this" in the method call below. However, we
                 * wanted to demonstrate what parameter we were using "MainActivity.this" for as
                 * clear as possible.
                 */
            Context context = MainActivity.this;

                /* This is the class that we want to start (and open) when the button is clicked. */
            Class destinationActivity = ChildActivity.class;

                /*
                 * Here, we create the Intent that will start the Activity we specified above in
                 * the destinationActivity variable. The constructor for an Intent also requires a
                 * context, which we stored in the variable named "context".
                 */
            Intent startChildActivityIntent = new Intent(context, destinationActivity);

            startActivityForResult(startChildActivityIntent, 0);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch(resultCode){
            //resultCode是A切換到B時設的resultCode
            case RESULT_OK:
                //當B傳回來的Intent的requestCode 等於當初A傳出去的話
                String result = data.getExtras().getString("color");
                switch (result) {
                    case "red":
                        ibtnPen.setImageResource(R.drawable.red_e74c3c);
                        artboardView.drawPaint = createDrawPaint(0xFFe74c3c);
                        break;
                    case "orange":
                        ibtnPen.setImageResource(R.drawable.orange_e67e22);
                        artboardView.drawPaint = createDrawPaint(0xFFe67e22);
                        break;
                    case "yellow":
                        ibtnPen.setImageResource(R.drawable.yellow_f1c40f);
                        artboardView.drawPaint = createDrawPaint(0xFFf1c40f);
                        break;
                    case "green":
                        ibtnPen.setImageResource(R.drawable.green_1abc9c);
                        artboardView.drawPaint = createDrawPaint(0xFF1abc9c);
                        break;
                    case "blue":
                        ibtnPen.setImageResource(R.drawable.blue_3498db);
                        artboardView.drawPaint = createDrawPaint(0xFF3498db);
                        break;
                    case "purple":
                        ibtnPen.setImageResource(R.drawable.purple_9b59b6);
                        artboardView.drawPaint = createDrawPaint(0xFF9b59b6);
                        break;
                    case "gray":
                        ibtnPen.setImageResource(R.drawable.gray_7f8c8d);
                        artboardView.drawPaint = createDrawPaint(0xFF7f8c8d);
                        break;
                    case "black":
                        ibtnPen.setImageResource(R.drawable.black_34495e);
                        artboardView.drawPaint.setColor(0xFF34495e);
                        break;
                }
                break;

        }
    }

    // TODO: (3) 成功解掉Change path color without changing previous paths問題
    private Paint createDrawPaint(Integer color) {
        Paint paint = new Paint();
        paint.setColor(color);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

        return paint;
    }
}
