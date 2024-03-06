package com.example.buckshotroulette;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.webkit.WebView;

public class RuleActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule);

        String htmlName = "rule";

        WebView webView = findViewById(R.id.webViewRule);
        webView.setBackgroundColor(0);
        webView.loadUrl("file:///android_asset/html/" + htmlName + ".html");

        mediaPlayer = MediaPlayer.create(this, R.raw.main);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
        mediaPlayer.start();

    }
}