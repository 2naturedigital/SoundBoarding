package edu.finalproject.cs50.soundboarding;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
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
    public String btn_2_sound_file;
    public String btn_3_sound_file;
    public String btn_4_sound_file;
    public String btn_5_sound_file;
    List<String> sounds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);

        // adding list view to select items from
        registerForContextMenu(button1);
        registerForContextMenu(button2);
        registerForContextMenu(button3);
        registerForContextMenu(button4);
        registerForContextMenu(button5);

        sounds.add("burn_out_the_computer");
        sounds.add("i_like_what_you_got");
        sounds.add("lick_lick_lick");
        sounds.add("ooh_yeah_can_do");
        sounds.add("portal_gun_louder");
        sounds.add("squeeze_them");
        sounds.add("what_did_you_do");


        int soundID = (int)(Math.random() * 6 + 1);
        btn_1_sound_file = sounds.get(soundID);
        soundID = (int)(Math.random() * 6 + 1);
        btn_2_sound_file = sounds.get(soundID);
        soundID = (int)(Math.random() * 6 + 1);
        btn_3_sound_file = sounds.get(soundID);
        soundID = (int)(Math.random() * 6 + 1);
        btn_4_sound_file = sounds.get(soundID);
        soundID = (int)(Math.random() * 6 + 1);
        btn_5_sound_file = sounds.get(soundID);
    }

    // creates a list of items to select from on long press
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderTitle("Choose your sound");
        getMenuInflater().inflate(R.menu.sound_options_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_options_1:
                btn_1_sound_file = sounds.get(0);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_options_2:
                btn_1_sound_file = sounds.get(1);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_options_3:
                btn_1_sound_file = sounds.get(2);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_options_4:
                btn_1_sound_file = sounds.get(3);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_options_5:
                btn_1_sound_file = sounds.get(4);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_options_6:
                btn_1_sound_file = sounds.get(5);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_options_7:
                btn_1_sound_file = sounds.get(6);
                Toast.makeText(this, "Changed!", Toast.LENGTH_LONG).show();
                return true;
            case 100:
                //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //intent.setType("");
                //startActivityForResult(intent, 1);
            default:
                Toast.makeText(this, "No Change", Toast.LENGTH_LONG).show();
                return super.onContextItemSelected(item);
        }
    }


    public void sound1(View v) {
        int resID = getResources().getIdentifier(btn_1_sound_file,"raw", getPackageName());
        mp = MediaPlayer.create(this, resID);
        mp.start();
    }

    public void sound2(View v) {
        int resID = getResources().getIdentifier(btn_2_sound_file,"raw", getPackageName());
        mp = MediaPlayer.create(this, resID);
        mp.start();
    }

    public void sound3(View v) {
        int resID = getResources().getIdentifier(btn_3_sound_file,"raw", getPackageName());
        mp = MediaPlayer.create(this, resID);
        mp.start();
    }

    public void sound4(View v) {
        int resID = getResources().getIdentifier(btn_4_sound_file,"raw", getPackageName());
        mp = MediaPlayer.create(this, resID);
        mp.start();
    }

    public void sound5(View v) {
        int resID = getResources().getIdentifier(btn_5_sound_file,"raw", getPackageName());
        mp = MediaPlayer.create(this, resID);
        mp.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // handle result
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            Uri uri;
            if (data != null) {
                uri = data.getData();
                // try getting new sound via Uri
                try {
                    Log.i("cs50", "Uri: " + uri);
                    if (uri != null) {
                        mp.setDataSource(this, uri);
                    }
                } catch (IOException e) {
                    Log.i("cs50", "ERROR HIT", e);
                    e.printStackTrace();
                }
                // try getting new sound via FileDescriptor
                try {
                    ParcelFileDescriptor parcelFileDescriptor = null;
                    if (uri != null) {
                        parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
                    }
                    FileDescriptor fileDescriptor = null;
                    if (parcelFileDescriptor != null) {
                        fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    }

                    if (fileDescriptor != null) {
                        Log.i("cs50", "File Descriptor: " + fileDescriptor.toString());
                        mp.setDataSource(fileDescriptor);
                    }

                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
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