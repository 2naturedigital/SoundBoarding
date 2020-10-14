package edu.finalproject.cs50.soundboarding;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Class constants
    final Integer BUTTON_COUNT = 12;
    final Integer INITIAL_SOUND_ID = 0;
    public static final String SHARED_PREFS = "sharedPrefs";

    // Class variables
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
        // Load/update any saved data
        loadData();
    }


    // Gets called when user long presses a registered button
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        // Sets the menu header and adds menu items based on strings created in initFromRaw()
        int btnID = view.getId();
        SpannableString headerTitle = new SpannableString(getResources().getString(R.string.context_menu_header_title));
        headerTitle.setSpan(new ForegroundColorSpan(Color.BLACK), 0, headerTitle.length(), 0);
        menu.setHeaderTitle(headerTitle);
        Log.i("SOUNDBOARDING_Check", "Button ID: " + btnID);

        for (Map.Entry<Integer, String> sound : soundIDtoStringMap.entrySet()) {
            menu.add(btnID, sound.getKey(), 0, sound.getValue());
            Log.i("SOUNDBOARDING_Check", "Sound: " + sound.getValue());
        }
    }


    // Gets called when user selects context menu item
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Integer btnID = item.getGroupId();
        Integer sndID = item.getItemId();

        // Change the button sound based on which item was selected
        if (soundIDtoStringMap.containsKey(sndID)) {
            // Only change if necessary
            Integer soundID = buttonIDtoSoundIDMap.get(btnID);
            if (soundID != null && !soundID.equals(sndID)) {
                buttonIDtoSoundIDMap.put(btnID, sndID); //changed ".replace" method to .put
                Button b = findViewById(btnID);
                buttonList.get(buttonList.indexOf(b)).setText(soundIDtoStringMap.get(sndID));
                Toast.makeText(this, "Changed to " + soundIDtoStringMap.get(sndID), Toast.LENGTH_SHORT).show();
                Log.i("SOUNDBOARDING_Check", "Sound changed to: " + soundIDtoStringMap.get(sndID));
                saveData();
                return true;
            } else {
                Toast.makeText(this, "Same sound chosen", Toast.LENGTH_SHORT).show();
                Log.i("SOUNDBOARDING_Check", "Same sound chosen, no change made");
                return super.onContextItemSelected(item);
            }
        } else {
            Toast.makeText(this, "No Change", Toast.LENGTH_SHORT).show();
            Log.i("SOUNDBOARDING_Error", soundIDtoStringMap.get(sndID) + " sound not found");
            return super.onContextItemSelected(item);
        }
    }


    // Detect which button was (single) pressed
    @Override
    public void onClick(View view) {
        Integer btnID = view.getId();
        Integer sndID = buttonIDtoSoundIDMap.get(btnID);
        if (buttonIDtoSoundIDMap.containsKey(btnID)) {
            if (sndID != null && !sndID.equals(INITIAL_SOUND_ID)) {
                MediaPlayer mediaPlayer = MediaPlayer.create(this, sndID);
                mediaPlayer.start();
            }
        } else {
            Log.i("SOUNDBOARDING_Error", "Button onClick creation error");
        }
    }


    // Method to populate ArrayList & HashMap with sound files
    private void initFromRaw() {
        try {
            Field[] fields = R.raw.class.getFields();
            for (Field field : fields) {
                Integer resourceID = field.getInt(field);
                String fieldName = field.getName();
                String dataName = fieldName.substring(0,1)
                        .toUpperCase() + fieldName.substring(1)
                        .toLowerCase()
                        .replaceAll("_", " ");
                soundsIDList.add(resourceID);
                soundIDtoStringMap.put(resourceID, dataName);
                Log.i("SOUNDBOARDING_Check", "Loaded file: " + soundIDtoStringMap.get(resourceID));
            }
        } catch (IllegalAccessException error) {
            error.printStackTrace();
            Log.i("SOUNDBOARDING_Error", Objects.requireNonNull(error.getMessage()));
        }
    }


    // Prepare the buttons with click listeners and register them and random starting sounds
    private void buttonInit() {
        // Iterate over declared buttons to initialize, add context, add to list
        for (int i = 0; i < BUTTON_COUNT; i++) {
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

            // Register INITIAL_SOUND_ID for sounds to start (which will cause them to show default starting string message
            buttonIDtoSoundIDMap.put(buttonList.get(i).getId(), INITIAL_SOUND_ID);
        }
    }


    // Save button sounds on exit/reload
    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Store data with view as a string and sound id as data saved for that button
        for (Button button : buttonList) {
            Integer soundID = buttonIDtoSoundIDMap.get(button.getId());
            if (soundID != null) {
                editor.putInt(String.valueOf(button.getId()), soundID);
            }
        }

        editor.apply();

        Toast.makeText(this, "Sounds saved", Toast.LENGTH_SHORT).show();
        Log.i("SOUNDBOARDING_Check", "Sounds saved");
    }


    // Load the saved preferences data and update sounds and button text appropriately
    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Restore the button label and sound id if it was saved, default to random sound if not found
        for (Button button : buttonList) {
            Integer sharedPrefsSoundID = sharedPreferences.getInt(String.valueOf(button.getId()), 0);
            if (!sharedPrefsSoundID.equals(INITIAL_SOUND_ID)) {
                buttonIDtoSoundIDMap.put(button.getId(), sharedPrefsSoundID); //changed ".replace" method to .put
                button.setText(soundIDtoStringMap.get(sharedPrefsSoundID));
            }
        }
    }
}