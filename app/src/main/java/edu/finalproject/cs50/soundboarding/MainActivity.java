package edu.finalproject.cs50.soundboarding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    //private MaterialButton button1;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private MediaPlayer mp;
    public String btn_1_sound_file;
    private Uri filepathUri;
    private String filepathString;
    private String filename;
    //public List<String> sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        //button1 = findViewById(R.id.mButton1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        filename = "lick_lick_lick.mp3";
        File file = new File(this.getFilesDir(), filename);
        filepathUri = Uri.fromFile(file);


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
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
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