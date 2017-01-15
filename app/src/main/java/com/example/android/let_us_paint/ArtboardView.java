package com.example.android.let_us_paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by regan on 14/01/2017.
 */

public class ArtboardView extends View {

    // To draw something, you need 4 basic components:
    // 1.a Bitmap to hold the pixels
    private Bitmap bitmap;
    // 2.a Canvas to host the draw calls (writing into the bitmap)
    private Canvas canvas;
    // 3.a drawing primitive (e.g. Rect, Path, text, Bitmap)
    // The Path class encapsulates compound geometric paths consisting of straight line segments,
    // quadratic curves, and cubic curves.
    // It can be drawn with canvas.drawPath(path, paint)
    private Path path;
    // 4.a paint (to describe the colors and styles for the drawing)
    // the user paths drawn with drawPaint will be drawn onto the canvas, which is drawn with canvasPaint
    public Paint drawPaint;
    private Paint canvasPaint;
    public int paintColor = 0xFF1abc9c;
    // TODO: (4) After adding undo feature, eraser is not working fine
    // a flag for whether the user is currently erasing or not
    private boolean eraseMode = false;

    private ArrayList<Path> paths;
    private ArrayList<Path> undonePaths;

    private List<Pair<Path, Paint>> path_paint_list;
    private List<Pair<Path, Paint>> undoPath_paint_list;


    public ArtboardView(Context context) {
        super(context);
        setupDrawing();
    }

    public ArtboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public ArtboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupDrawing();
    }


    public void setErase(boolean isErase) {
        // set erase ture or false
        eraseMode = isErase;
        // alter the Paint object to erase or switch back to drawing
        if (eraseMode) {

            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else
            drawPaint.setXfermode(null);
    }

    // get drawing area setup for interaction
    private void setupDrawing() {

        drawPaint = new Paint();
        path = new Path();

        drawPaint.setColor(paintColor);

        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setAntiAlias(true);

        canvasPaint = new Paint(Paint.DITHER_FLAG);

        paths = new ArrayList<Path>();
        undonePaths = new ArrayList<Path>();

        path_paint_list = new ArrayList<Pair<Path, Paint>>();
        undoPath_paint_list = new ArrayList<Pair<Path, Paint>>();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public void undo() {
//        if (paths.size() > 0) {
//            //paths.remove(paths.size() - 1);
//            undonePaths.add(paths.remove(paths.size() - 1));
//            // To force a view to draw, call invalidate()
//            // every invalidate call will draw the entire path
//            invalidate();
//        }

        if (path_paint_list.size() > 0) {
            //paths.remove(paths.size() - 1);
            undoPath_paint_list.add((path_paint_list.remove(path_paint_list.size() - 1)));
            // To force a view to draw, call invalidate()
            // every invalidate call will draw the entire path
            invalidate();
        }

    }

    // Implement this to do drawing
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Treat the specified array of colors as a bitmap, and draw it.
        // This gives the same result as first creating a bitmap from the array,
        // and then drawing it, but this method avoids explicitly creating a bitmap object
        // which can be more efficient if the colors are changing often.
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);

//        for (Path p : paths) {
//            canvas.drawPath(p, drawPaint);
//        }

        for (Pair<Path, Paint> path_paint_pair : path_paint_list){
            //drawPaint = path_paint_pair.second;
            //drawPaint.setColor(path_clr.second);
            canvas.drawPath(path_paint_pair.first, path_paint_pair.second);
        }

        // Draw the specified path using the specified paint
        canvas.drawPath(path, drawPaint);



    }

    // Implement this method to handle touch screen motion events
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            // When the user touches the View, we move to that position to start drawing.
            case MotionEvent.ACTION_DOWN:
                // Set the beginning of the next contour to the point (x,y).
                path.moveTo(touchX, touchY);
                break;
            // When they move their finger on the View, we draw the path along with their touch
            case MotionEvent.ACTION_MOVE:
                // Add a line from the last point to the specified point (x,y)
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                //paths.add(path);
                // TODO: (3) When drawing with the new color, all previous drawings also changes colors
                path_paint_list.add(new Pair(path, drawPaint));
                // path_paint_list.add(new Pair(path, drawPaint.getColor())); 只存顏色不行！
                // When they lift their finger up off the View,
                // we draw the Path using the specified paint and reset it for the next drawing operation.
                //canvas.drawPath(path, drawPaint);
                // Clear any lines and curves from the path, making it empty.
                path = new Path();
                break;
            default:
                return false;
        }
        // To force a view to draw, call invalidate()
        // every invalidate call will draw the entire path
        invalidate();
        // 	return true if the event was handled, false otherwise.
        return true;
    }

}
