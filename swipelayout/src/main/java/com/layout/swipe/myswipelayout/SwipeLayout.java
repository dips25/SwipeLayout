package com.layout.swipe.myswipelayout;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


public class SwipeLayout extends RelativeLayout {

    private static final String TAG = SwipeLayout.class.getName();

    Context context;

    int width , height;

    boolean isRunning = false;

    int eX;
    int SCREEN_WIDTH;

    ViewGroup swipeRoot;
    TextView textView;

    boolean isTouch;

    RecyclerView recyclerView;

    boolean isScrolling;


    public SwipeLayout(Context context) {
        super(context);
        this.context = context;
        SCREEN_WIDTH = Utils.getScreenWidth(context);

    }

    public SwipeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        SCREEN_WIDTH = Utils.getScreenWidth(context);

    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.width = w;
        this.height = h;

    }

    @Override
    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);

    }

    public void setSwipeLayout(ViewGroup viewGroup) {

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                viewGroup.setTranslationX(0);
            }
        });

        int width = MeasureSpec.makeMeasureSpec(SCREEN_WIDTH , MeasureSpec.EXACTLY);
        int height = MeasureSpec.makeMeasureSpec(this.getLayoutParams().height , MeasureSpec.AT_MOST);

        this.measure(width , height);

        int measuredWidth = this.getMeasuredWidth();
        int measuredHeight = this.getMeasuredHeight();

        this.swipeRoot = viewGroup;

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(measuredWidth
                , measuredHeight);

        textView.setLayoutParams(rlp);
        textView.setWidth(this.getLayoutParams().width);
        textView.setWidth(this.getLayoutParams().height);
        textView.setBackgroundColor(Color.RED);
        textView.setGravity(Gravity.CENTER);


        swipeRoot.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        isTouch = true;


                        return false;

                    case MotionEvent.ACTION_UP:

                        if (isTouch) {

                            Toast.makeText(context, "Touched" + event.getX() + " " + event.getY(), Toast.LENGTH_SHORT).show();

                        } else {

                            moveView(event.getX() , swipeRoot , true);

                        }

                        Log.d(TAG, "onTouchEvent: " + event.getX());

                        return false;


                    case MotionEvent.ACTION_CANCEL:

                        Log.d(TAG, "onTouch: " + "Cancel");

                        if (setRecyclerView(recyclerView)) {

                            return false;
                        } else {

                            Log.d(TAG, "onTouch: " + MainActivity.isScrolling);

                            moveView(event.getX() , swipeRoot , true);
                        }

                        return false;

                    case MotionEvent.ACTION_MOVE:

                        Log.d(TAG, "onTouchEvent: " + event.getX() + " " + event.getY());

                        ((CustomLinearLayoutManager)recyclerView.getLayoutManager()).setScrollEnabled(false);



                            isRunning = true;
                            isTouch = false;

                            moveView(event.getX() , swipeRoot , true);


                        Log.d(TAG, "onTouch: " + event.getX() + " " + event.getY());

                        return false;



                }
                return false;
            }
        });

        swipeRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });





    }





    private void moveView(float x, ViewGroup mainLayout , boolean isMove) {

        eX = (int)x;

        if (eX<SCREEN_WIDTH/2) {

            mainLayout.setTranslationX(eX);
            mainLayout.setClickable(false);

            if (isMove) {

                while (eX>20) {

                    eX = eX-20;

                    if (eX>0) {

                        mainLayout.setTranslationX(eX);
                        int alphaa = eX/SCREEN_WIDTH;
                        textView.setAlpha(alphaa);


                    } else {

                        eX = 0;
                        mainLayout.setTranslationX(eX);
                        textView.setAlpha(0);
                    }


                }




            }



        } else if (eX > SCREEN_WIDTH/2) {

            mainLayout.setTranslationX(eX);

            while (eX>SCREEN_WIDTH/2 && isMove) {

                eX = eX + 20;

                if (eX>SCREEN_WIDTH) {

                    break;
                }

                mainLayout.setTranslationX(eX);
                int alpha = eX/SCREEN_WIDTH;
                textView.setAlpha(alpha);

            }


            textView.setAlpha(1);
            isRunning = false;
        }

        eX = 0;
        isTouch = false;
        ((CustomLinearLayoutManager)recyclerView.getLayoutManager()).setScrollEnabled(true);

        swipeRoot.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



    public boolean setRecyclerView(RecyclerView recyclerView) {

        this.recyclerView = recyclerView;

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (Math.abs(dy) > 0 && Math.abs(dy) < 50) {

                    isScrolling = false;


                } else if (dx==0 && dy == 0){

                    isScrolling = false;

                } else {

                    isScrolling = true;
                }
            }
        });

        return isScrolling;


    }
}
