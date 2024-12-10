package lu.embellishedduck.engine.core;

import lombok.extern.slf4j.Slf4j;
import lu.embellishedduck.engine.io.both.KeybindingManager;

@Slf4j
public class Runner {

    //=============
    // MAIN METHOD
    //=============
    public static void main(String[] args) {

        loadResources();
        new Game().run();

    }//End of Main Method


    //====================================
    // METHOD TO LOAD RESOURCES ON LAUNCH
    //====================================
    private static void loadResources() {

        log.info("Loading resources...");

        KeybindingManager.loadKeybindings();

    }//End of Method

}//End of Class