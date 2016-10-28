package com.ribicnejc.horoscopeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton mImageButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageButton = (ImageButton) findViewById(R.id.scorpio_sign);
        mImageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.scorpio_sign:
                startActivity(new Intent(getBaseContext(), SignInfo.class));
                break;
            default:
                Toast.makeText(MainActivity.this, "Other button pressed", Toast.LENGTH_SHORT).show();
        }
    }
}
