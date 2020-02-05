package edu.psm.application8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ShowExercise extends AppCompatActivity {

    ImageView imageView;
    ImageButton buttonSE;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_exercise);


        imageView = findViewById(R.id.imageView_SE);
        buttonSE = findViewById(R.id.buttonSE);

        showProperExercise();


       buttonSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(ShowExercise.this, R.anim.rotate);
                buttonSE.startAnimation(animation);

                startActivity(i);

            }
        });
    }


    public void showProperExercise() {
        String id = getIntent().getStringExtra("test");
        System.out.println(id);

       if (id.equals("A0")) {
            imageView.setImageResource(R.drawable.wyciskanie2);
            i = new Intent(this, ActivityA0.class);
        } else {
           imageView.setImageResource(R.drawable.wyciskanie1);
           i = new Intent(this, ActivityA5.class);
        }
    }
}
