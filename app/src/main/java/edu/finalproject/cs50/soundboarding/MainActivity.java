package edu.finalproject.cs50.soundboarding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private MediaPlayer mp;
    public String btn_1_sound_file;
    //public List<String> sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        /*sounds = new ArrayList<>();
        sounds.add("burn_out_the_computer");
        sounds.add("i_like_what_you_got");
        sounds.add("lick_lick_lick");
        sounds.add("ooh_yeah_can_do");
        sounds.add("portal_gun_louder");
        sounds.add("squeeze_them");
        sounds.add("what_did_you_do");
        btn_1_sound_file = sounds.get(1);*/

        btn_1_sound_file = "burn_out_the_computer";

        button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //int soundID = (int)(Math.random() * 7 + 1);
                //btn_1_sound_file = sounds.get(soundID);
                btn_1_sound_file = "what_did_you_do";
                Toast.makeText(view.getContext(), "Changed!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("*/*");
                startActivityForResult(intent, 1);
                return false;
            }
        });
    }

    public void sound1(View v) {
        int resID = getResources().getIdentifier(btn_1_sound_file,"raw", getPackageName());
        mp = MediaPlayer.create(this, resID);
        mp.start();
    }

    public void sound2(View v) {

    }

    public void sound3(View v) {

    }

    public void sound4(View v) {

    }

    public void sound5(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // handle result
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 1) {
                Uri uri = data.getData();
                // try getting new sound via Uri
                try {
                    Log.i("cs50", "Uri: " + uri);
                    mp.setDataSource(this, uri);
                } catch (IOException e) {
                    Log.i("cs50", "ERROR HIT", e);
                    e.printStackTrace();
                }
                // try getting new sound via FileDescriptor
                try {
                    ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    // makes the image
                    Log.i("cs50", "File Descriptor: " + fileDescriptor.toString());
                    mp.setDataSource(fileDescriptor);
                    parcelFileDescriptor.close();
                } catch (IOException e) {
                    Log.e("cs50", "Media not found", e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}