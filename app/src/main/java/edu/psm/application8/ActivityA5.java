package edu.psm.application8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ActivityA5 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_a5);
        textView = findViewById(R.id.textView_a5);
        textView.setText(Html.fromHtml(getString(R.string.opis_cwiczenia2)));
    }
}
