package lu.embellishedduck.engine.core;

import lu.embellishedduck.engine.io.both.KeybindingManager;
import lu.embellishedduck.engine.io.input.Keybinding;

import java.util.Scanner;

public class Runner {

    //=============
    // MAIN METHOD
    //=============
    public static void main(String[] args) {

        System.out.println("Hello Player");

        KeybindingManager.loadKeybindings();

        for (Keybinding keybinding : Keybinding.values()) {

            System.out.println(keybinding.getCode());

        }//End of For-Each Loop

        Scanner scanner = new Scanner(System.in);
        System.out.println("You can set a new keybinding for moving forward here! You have to enter the actual code for it though.");
        Keybinding.up.setCode(scanner.nextInt());
        System.out.println(Keybinding.up.getCode());
        KeybindingManager.saveKeybindings();
        System.out.println("Check out the new keybinding in the file shown by the logger.");

    }//End of Main Method

}//End of Class