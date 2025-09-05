package com.example.appventure;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;


import com.example.appventure.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



    }

}