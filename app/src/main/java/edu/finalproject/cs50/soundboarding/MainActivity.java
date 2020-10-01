package edu.finalproject.cs50.soundboarding;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Class constants
    final Integer buttonCount = 5;

    // Class variables
    private MediaPlayer mp;
    List<Integer> soundsIDList = new ArrayList<>();
    List<Integer> buttonIDList = new ArrayList<>();
    Map<Integer, String> soundIDtoStringMap = new HashMap<>();
    Map<Integer, Integer> buttonIDtoSoundIDMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Populate containers from installed sound files
        initFromRaw();
        // Initialize buttons
        buttonInit();
    }


    // Gets called when user long presses a registered button
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        // Sets the menu header and adds menu items based on strings created in initFromRaw()
        menu.setHeaderTitle("Select Sound");
        Log.i("SOUNDBOARDING_SanityCheck", "Button ID: " + view.getId());
        for (Map.Entry<Integer, String> entry : soundIDtoStringMap.entrySet()) {
            menu.add(view.getId(), entry.getKey(), 0, entry.getValue());
            Log.i("SOUNDBOARDING_SanityCheck", "Sound: " + entry.getValue());
        }
    }


    // Gets called when user selects context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.i("SOUNDBOARDING_SanityCheck", "Button ID received: " + item.getGroupId());
        Log.i("SOUNDBOARDING_SanityCheck", "Sound ID received: " + item.getItemId());
        // change the button sound based on which item was selected
        if (soundIDtoStringMap.containsKey(item.getItemId())) {
            buttonIDtoSoundIDMap.replace(item.getGroupId(), item.getItemId());
            Toast.makeText(this, "Changed to " + soundIDtoStringMap.get(item.getItemId()), Toast.LENGTH_LONG).show();
            return true;
        } else {
            Log.i("SOUNDBOARDING_Error", soundIDtoStringMap.get(item.getItemId()) + " sound not found");
            Toast.makeText(this, "No Change", Toast.LENGTH_LONG).show();
            return super.onContextItemSelected(item);
        }
    }


    // Detect which button was (single) pressed
    @Override
    public void onClick(View view) {
        if (buttonIDtoSoundIDMap.containsKey(view.getId())) {
            mp = MediaPlayer.create(this, buttonIDtoSoundIDMap.get(view.getId()));
            mp.start();
        } else {
            Log.i("SOUNDBOARDING_Error", "Button onClick creation error");
        }
    }


    // Method to populate ArrayList & HashMap with sound files
    private void initFromRaw() {
        try {
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                int resourceID = field.getInt(field);
                String fieldName = field.getName();
                String dataName = fieldName.substring(0,1)
                        .toUpperCase() + fieldName.substring(1)
                        .toLowerCase()
                        .replaceAll("_", " ");
                soundsIDList.add(resourceID);
                soundIDtoStringMap.put(resourceID, dataName);
                Log.i("SOUNDBOARDING_SanityCheck", "File Name: " + soundIDtoStringMap.get(resourceID));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    // Prepare the buttons with click listeners and register them and random starting sounds
    private void buttonInit() {
        // Iterate over declared buttons to initialize, add context, add to list
        for (int i = 0; i < buttonCount; i++) {
            if (i == 0) {
                buttonIDList.add(i, R.id.button1);
            } else if (i == 1) {
                buttonIDList.add(i, R.id.button2);
            } else if (i == 2) {
                buttonIDList.add(i, R.id.button3);
            } else if (i == 3) {
                buttonIDList.add(i, R.id.button4);
            } else if (i == 4) {
                buttonIDList.add(i, R.id.button5);
            }
            findViewById(buttonIDList.get(i)).setOnClickListener(this);
            registerForContextMenu(findViewById(buttonIDList.get(i)));

            // register random sounds to start
            int soundID = (int)(Math.random() * 6 + 1);
            buttonIDtoSoundIDMap.put(buttonIDList.get(i), soundsIDList.get(soundID));
        }
    }
}