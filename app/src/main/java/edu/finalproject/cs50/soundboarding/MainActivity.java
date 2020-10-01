package edu.finalproject.cs50.soundboarding;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mp;
    List<Integer> btn_strings = new ArrayList<>();
    public int current_button = 0;
    List<Integer> sounds = new ArrayList<>();
    Map<String, Integer> dicky = new HashMap<>();
    Button buttonList[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iterate over declared buttons to initialize, add context, add to list
        buttonList = new Button[4];
        for (int i = 0; i < buttonList.length; i++) {
            if (i == 0) {
                buttonList[i] = findViewById(R.id.button1);
            }
            if (i == 1) {
                buttonList[i] = findViewById(R.id.button2);
            }
            if (i == 2) {
                buttonList[i] = findViewById(R.id.button3);
            }
            if (i == 3) {
                buttonList[i] = findViewById(R.id.button4);
            }
            if (i == 4) {
                buttonList[i] = findViewById(R.id.button5);
            }
            buttonList[i].setOnClickListener(this);
            registerForContextMenu(buttonList[i]);
        }


        //initialize buttons with views
        /*button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);*/

        // adding list view to select items from
        /*registerForContextMenu(button1);
        registerForContextMenu(button2);
        registerForContextMenu(button3);
        registerForContextMenu(button4);
        registerForContextMenu(button5);*/

        //set context for buttons
        /*button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);*/




        //populate ArrayList "sounds" and Hashmap "dicky" with sound files
        try {
            listRaw();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // set up random starting sounds -- TODO
        /*int soundID = (int)(Math.random() * 6 + 1);
        btn_strings.add(0, sounds.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        btn_strings.add(1, sounds.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        btn_strings.add(2, sounds.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        btn_strings.add(3, sounds.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        btn_strings.add(4, sounds.get(soundID));*/


    }



    //Method to populate arraylist & hashmap with sound files
    public void listRaw() throws IllegalAccessException {
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            int resourceID = field.getInt(field);
            String s = field.getName();
            String t = s.substring(0,1)
                    .toUpperCase() + s.substring(1)
                    .toLowerCase()
                    .replaceAll("_", " ");
            sounds.add(resourceID);
            dicky.put(t, resourceID);
            Log.i("file name ", t);
        }
    }

    // creates a list of items to select from on long press
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        // save which button was long pressed
        switch (view.getId()) {
            case R.id.button1:
                current_button = 0;
                break;
            case R.id.button2:
                current_button = 1;
                break;
            case R.id.button3:
                current_button = 2;
                break;
            case R.id.button4:
                current_button = 3;
                break;
            case R.id.button5:
                current_button = 4;
                break;
            default:
                current_button = 0;
        }

        menu.setHeaderTitle("Select sound");
        getMenuInflater().inflate(R.menu.sound_options_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // change the button sound based on which item was selected
        switch(item.getItemId()) {
            case R.id.action_options_1:
                btn_strings.set(current_button, sounds.get(0));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_options_2:
                btn_strings.set(current_button, sounds.get(1));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_options_3:
                btn_strings.set(current_button, sounds.get(2));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_options_4:
                btn_strings.set(current_button, sounds.get(3));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_options_5:
                btn_strings.set(current_button, sounds.get(4));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_options_6:
                btn_strings.set(current_button, sounds.get(5));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_options_7:
                btn_strings.set(current_button, sounds.get(6));
                Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
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

    //detect which button was (single) pressed -- consider making it more dynamic - TODO
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                mp = MediaPlayer.create(this, btn_strings.get(0));
                mp.start();
                break;
            case R.id.button2:
                mp = MediaPlayer.create(this, btn_strings.get(1));
                mp.start();
                break;
            case R.id.button3:
                mp = MediaPlayer.create(this, btn_strings.get(2));
                mp.start();
                break;
            case R.id.button4:
                mp = MediaPlayer.create(this, btn_strings.get(3));
                mp.start();
                break;
            case R.id.button5:
                mp = MediaPlayer.create(this, btn_strings.get(4));
                mp.start();
                break;
        }
    }

    /*public void sound1(View v) {
        //int resID = getResources().getIdentifier(btn_strings.get(0),"raw", getPackageName());
        mp = MediaPlayer.create(this, btn_strings.get(0));
        mp.start();
    }

    public void sound2(View v) {
        //int resID = getResources().getIdentifier(btn_strings.get(1),"raw", getPackageName());
        mp = MediaPlayer.create(this, btn_strings.get(1));
        mp.start();
    }

    public void sound3(View v) {
        //int resID = getResources().getIdentifier(btn_strings.get(2),"raw", getPackageName());
        mp = MediaPlayer.create(this, btn_strings.get(2));
        mp.start();
    }

    public void sound4(View v) {
        //int resID = getResources().getIdentifier(btn_strings.get(3),"raw", getPackageName());
        mp = MediaPlayer.create(this, btn_strings.get(3));
        mp.start();
    }

    public void sound5(View v) {
        //int resID = getResources().getIdentifier(btn_strings.get(4),"raw", getPackageName());
        mp = MediaPlayer.create(this, btn_strings.get(4));
        mp.start();
    }*/
}