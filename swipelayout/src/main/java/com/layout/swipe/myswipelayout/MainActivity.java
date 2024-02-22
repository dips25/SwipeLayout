package com.layout.swipe.myswipelayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout swipeRoot;
    SwipeLayout swipemain;

    RecyclerView swipeRecycler;
    String chars = "bfjsdbjfdsjbfdsbfhjdsbfjhsdbfhsdbfhdbsfhbsdfhbsdhfbdsjf";
    char[]ch = chars.toCharArray();


   public static boolean isScrolling = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRecycler = (RecyclerView) findViewById(R.id.swipeRecycler);
        swipeRecycler.setLayoutManager(new CustomLinearLayoutManager(this));

        SampleRecyclerAdapter sampleRecyclerAdapter = new SampleRecyclerAdapter(ch);


        swipeRecycler.setAdapter(sampleRecyclerAdapter);

//        swipeRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//
////                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
////
////                    isScrolling = true;
////
////                }
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//
//                super.onScrolled(recyclerView, dx, dy);
//
//                Log.d(MainActivity.class.getName(), "onScrolled: " + dx + " " + dy);
//
//
//
//
//
//            }
//        });

        sampleRecyclerAdapter.notifyDataSetChanged();




    }

    public class SampleRecyclerAdapter extends RecyclerView.Adapter<SampleRecyclerAdapter.ViewHolder> {

        char[] ch;

        public SampleRecyclerAdapter(char[] ch) {

            this.ch = ch;

        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_recycler , parent , false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.swipemain.setSwipeLayout(holder.swipeRoot);
            holder.swipemain.setRecyclerView(swipeRecycler);



        }

        @Override
        public int getItemCount() {
            return ch.length;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            SwipeLayout swipemain;
            RelativeLayout swipeRoot;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                swipemain = (SwipeLayout) itemView.findViewById(R.id.swipemain);
                swipeRoot = (RelativeLayout) itemView.findViewById(R.id.swiperoot);
            }
        }
    }


}