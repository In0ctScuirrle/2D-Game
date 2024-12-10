package lu.embellishedduck.engine.io.both;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import lombok.extern.slf4j.Slf4j;
import lu.embellishedduck.engine.io.input.Keybinding;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class KeybindingManager {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private static final String DEFAULT_CONFIG_FILE = "/config/keybindings.json";

    //Defining a readable and writable config file system where the file is saved
    private static final String USER_HOME = System.getProperty("user.home");
    private static final Path CONFIG_PATH = Paths.get(USER_HOME, "Saved Games", "2D Game", "config", "keybindings.json");


    //=====================================================
    // METHOD TO LOAD THE KEYBINDINGS FROM THE CONFIG FILE
    //=====================================================
    public static void loadKeybindings() {

        //This method cannot be called unless the initial run of saveKeybindings has been run.

        try {

            if (Files.exists(CONFIG_PATH)) {

                //Load the data into bytes from the readable location
                byte[] fileBytes = Files.readAllBytes(CONFIG_PATH);

                Any any = JsonIterator.deserialize(fileBytes);

                for (Keybinding keybinding : Keybinding.values()) {

                    int combined = any.get(keybinding.getIdentifier()).toInt();
                    keybinding.setCode(combined & 0xFFFF);//Extracting the key code, lower sixteen bits.
                    keybinding.setModifiers(combined & ~0xFFFF);//Extracting the modifier flags, upper bits.

                }//End of For-Each Loop

                log.info("Loaded keybindings successfully from {}", CONFIG_PATH);

            } else {

                saveKeybindings();//If the keybindings config cannot be loaded it has to be initialized, this prevents the crash from earlier.

            }//End of If-Else Statement

        } catch (IOException ex) {

            log.error("Failed to load keybindings.json", ex);

        }//End of Try-Catch Statement

    }//End of Method


    //===================================================
    // METHOD TO SAVE THE KEYBINDINGS TO THE CONFIG FILE
    //===================================================
    public static void saveKeybindings() {

        /*
        First we need to handel where the JSON file is going to be saved to, it cannot be inside the application's folder
        which is compiled into the fat JAR.
         */
        try {

            //Ensure the directory exists
            Files.createDirectories(CONFIG_PATH.getParent());

            //If it doesn't exist, copy it from the resources root (Carrying default values of course)
            if (Files.exists(CONFIG_PATH)) {

                try (InputStream inputStream = KeybindingManager.class.getResourceAsStream(DEFAULT_CONFIG_FILE)) {

                    if (inputStream != null) {

                        Files.copy(inputStream, CONFIG_PATH, StandardCopyOption.REPLACE_EXISTING);

                    } else {

                        throw new IOException("Resource file not found: " + DEFAULT_CONFIG_FILE);

                    }//End of If-Else Statement

                }//End of Try Statement

            }//End of If Statement

            //Putting the keybindings into a compatible format which Jsoniter can process.
            Map<String, Integer> data = new HashMap<>();

            for (Keybinding keybinding : Keybinding.values()) {

                data.put(keybinding.getIdentifier(), keybinding.getCode() | keybinding.getModifiers());

            }//End of For-Each Loop

            String json = JsonStream.serialize(data);

            //Write the JSON data into a writable file
            try (OutputStream outputStream = new FileOutputStream(CONFIG_PATH.toFile())) {

                outputStream.write(json.getBytes(StandardCharsets.UTF_8));
                log.info("Saved keybindings successfully to {}", CONFIG_PATH);

            }//End of Try Statement

        } catch (IOException ex) {

            log.error("Failed to save keybindings", ex);

        }//End of Try-Catch Statement

    }//End of Method

}//End of Class