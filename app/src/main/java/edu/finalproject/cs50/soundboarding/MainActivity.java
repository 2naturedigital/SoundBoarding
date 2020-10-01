package edu.finalproject.cs50.soundboarding;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MediaPlayer mp;
    List<Integer> soundsIDList = new ArrayList<>();
    List<Integer> buttonSoundIDList = new ArrayList<>();
    Map<Integer, String> soundIDMap = new HashMap<>();
    Button[] buttonList;
    private int current_button = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        buttonInit();

        // Populate ArrayList "soundsIDList" and HashMap "soundIDMap" with sound files
        try {
            initFromRaw();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Set up random starting sounds
        soundInit();
    }


    // Gets called when user long presses a registered button
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        // Save which button was long pressed
        //TODO find a better way to do this maybe
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

        // Sets the menu header and adds menu items based on strings created in initFromRaw()
        menu.setHeaderTitle("Select Sound");
        for (int id : soundsIDList) {
            menu.add(0, id, 0, soundIDMap.get(id));
        }
        //TODO remove inflater when sure it is not needed
        //getMenuInflater().inflate(R.menu.sound_options_menu, menu);
    }

    // Gets called when user selects context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // change the button sound based on which item was selected
        if (soundsIDList.contains(item.getItemId())) {
            buttonSoundIDList.set(current_button, item.getItemId());
            Toast.makeText(this, "Changed to " + soundIDMap.get(item.getItemId()), Toast.LENGTH_LONG).show();
            return true;
        } else {
            Log.i("SOUNDBOARDING_Error", soundIDMap.get(item.getItemId()) + " sound not found");
            Toast.makeText(this, "No Change", Toast.LENGTH_LONG).show();
            return super.onContextItemSelected(item);
        }
    }


    // Detect which button was (single) pressed
    //TODO consider making it more dynamic
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                mp = MediaPlayer.create(this, buttonSoundIDList.get(0));
                mp.start();
                break;
            case R.id.button2:
                mp = MediaPlayer.create(this, buttonSoundIDList.get(1));
                mp.start();
                break;
            case R.id.button3:
                mp = MediaPlayer.create(this, buttonSoundIDList.get(2));
                mp.start();
                break;
            case R.id.button4:
                mp = MediaPlayer.create(this, buttonSoundIDList.get(3));
                mp.start();
                break;
            case R.id.button5:
                mp = MediaPlayer.create(this, buttonSoundIDList.get(4));
                mp.start();
                break;
            default:
                Log.i("SOUNDBOARDING_Error", "Button onClick creation error");
        }
    }


    // Prepare initial sounds with random sounds
    //TODO make this function reset sounds properly on reload activity
    private void soundInit() {
        int soundID = (int)(Math.random() * 6 + 1);
        buttonSoundIDList.add(0, soundsIDList.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        buttonSoundIDList.add(1, soundsIDList.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        buttonSoundIDList.add(2, soundsIDList.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        buttonSoundIDList.add(3, soundsIDList.get(soundID));
        soundID = (int)(Math.random() * 6 + 1);
        buttonSoundIDList.add(4, soundsIDList.get(soundID));
    }


    // Prepare the buttons with click listeners and register them
    private void buttonInit() {
        // Iterate over declared buttons to initialize, add context, add to list
        buttonList = new Button[5];
        for (int i = 0; i < buttonList.length; i++) {
            if (i == 0) {
                buttonList[i] = findViewById(R.id.button1);
            } else if (i == 1) {
                buttonList[i] = findViewById(R.id.button2);
            } else if (i == 2) {
                buttonList[i] = findViewById(R.id.button3);
            } else if (i == 3) {
                buttonList[i] = findViewById(R.id.button4);
            } else if (i == 4) {
                buttonList[i] = findViewById(R.id.button5);
            }
            buttonList[i].setOnClickListener(this);
            registerForContextMenu(buttonList[i]);
        }
    }


    // Method to populate ArrayList & HashMap with sound files
    private void initFromRaw() throws IllegalAccessException {
        Field[] fields = R.raw.class.getFields();
        for (Field field : fields) {
            int resourceID = field.getInt(field);
            String fieldName = field.getName();
            String dataName = fieldName.substring(0,1)
                    .toUpperCase() + fieldName.substring(1)
                    .toLowerCase()
                    .replaceAll("_", " ");
            soundsIDList.add(resourceID);
            soundIDMap.put(resourceID, dataName);
            Log.i("SOUNDBOARDING_SanityCheck", "File Name: " + soundIDMap.get(resourceID));
        }
    }
}