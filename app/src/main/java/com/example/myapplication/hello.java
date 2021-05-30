package com.example.myapplication;

import android.os.Bundle;
import android.text.Html;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class hello extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
getSupportActionBar ().setTitle (Html.fromHtml ("<font color=\"black\">"+"Vet"));
    }
}
