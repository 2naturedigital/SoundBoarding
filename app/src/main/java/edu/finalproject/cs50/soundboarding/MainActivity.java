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

    // Class constants
    final Integer BUTTONCOUNT = 12;

    // Class variables
    private MediaPlayer mp;
    List<Integer> soundsIDList = new ArrayList<>();
    List<Button> buttonList = new ArrayList<>();
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
        int btnID = view.getId();
        menu.setHeaderTitle("Select Sound");
        Log.i("SOUNDBOARDING_SanityCheck", "Button ID: " + btnID);
        for (Map.Entry<Integer, String> sound : soundIDtoStringMap.entrySet()) {
            menu.add(btnID, sound.getKey(), 0, sound.getValue());
            Log.i("SOUNDBOARDING_SanityCheck", "Sound: " + sound.getValue());
        }
    }


    // Gets called when user selects context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int btnID = item.getGroupId();
        int sndID = item.getItemId();
        Log.i("SOUNDBOARDING_SanityCheck", "Button ID received: " + btnID);
        Log.i("SOUNDBOARDING_SanityCheck", "Sound ID received: " + sndID);
        // change the button sound based on which item was selected
        if (soundIDtoStringMap.containsKey(sndID)) {
            buttonIDtoSoundIDMap.replace(btnID, sndID);
            buttonList.get(buttonList.indexOf(findViewById(btnID))).setText(soundIDtoStringMap.get(sndID));
            Toast.makeText(this, "Changed to " + soundIDtoStringMap.get(sndID), Toast.LENGTH_LONG).show();
            return true;
        } else {
            Log.i("SOUNDBOARDING_Error", soundIDtoStringMap.get(sndID) + " sound not found");
            Toast.makeText(this, "No Change", Toast.LENGTH_LONG).show();
            return super.onContextItemSelected(item);
        }
    }


    // Detect which button was (single) pressed
    @Override
    public void onClick(View view) {
        int btnID = view.getId();
        if (buttonIDtoSoundIDMap.containsKey(btnID)) {
            mp = MediaPlayer.create(this, buttonIDtoSoundIDMap.get(btnID));
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
        for (int i = 0; i < BUTTONCOUNT; i++) {
            if (i == 0) {
                buttonList.add(i, (Button) findViewById(R.id.button1));
            } else if (i == 1) {
                buttonList.add(i, (Button) findViewById(R.id.button2));
            } else if (i == 2) {
                buttonList.add(i, (Button) findViewById(R.id.button3));
            } else if (i == 3) {
                buttonList.add(i, (Button) findViewById(R.id.button4));
            } else if (i == 4) {
                buttonList.add(i, (Button) findViewById(R.id.button5));
            } else if (i == 5) {
                buttonList.add(i, (Button) findViewById(R.id.button6));
            } else if (i == 6) {
                buttonList.add(i, (Button) findViewById(R.id.button7));
            } else if (i == 7) {
                buttonList.add(i, (Button) findViewById(R.id.button8));
            } else if (i == 8) {
                buttonList.add(i, (Button) findViewById(R.id.button9));
            } else if (i == 9) {
                buttonList.add(i, (Button) findViewById(R.id.button10));
            } else if (i == 10) {
                buttonList.add(i, (Button) findViewById(R.id.button11));
            } else if (i == 11) {
                buttonList.add(i, (Button) findViewById(R.id.button12));
            }
            buttonList.get(i).setOnClickListener(this);
            registerForContextMenu(buttonList.get(i));

            // Register random sounds to start
            int soundID = (int)(Math.random() * soundsIDList.size());
            buttonIDtoSoundIDMap.put(buttonList.get(i).getId(), soundsIDList.get(soundID));
            buttonList.get(i).setText(soundIDtoStringMap.get(soundsIDList.get(soundID)));
            Log.i("SOUNDBOARDING_SanityCheck", "Button Name: " + soundIDtoStringMap.get(soundsIDList.get(soundID)));
        }
    }
}