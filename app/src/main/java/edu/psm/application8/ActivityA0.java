package edu.psm.application8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ActivityA0 extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_a0);
        textView = findViewById(R.id.textView_a0);
        textView.setText(Html.fromHtml(getString(R.string.opis_cwiczenia1)));
    }
}
