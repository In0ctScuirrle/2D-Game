package lu.embellishedduck.engine.io.input;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;

/**
 * This enumerated class serves as a data holder for programmable keybindings.
 * Though this class does store a variable of type Runnable, it is only ever called
 * in the KeybindingManager class.
 */
@Getter
public enum Keybinding {

    //====================
    // INSTANCE VARIABLES
    //====================
    up("MoveForward", GLFW.GLFW_KEY_W, GLFW.GLFW_KEY_W, () -> System.out.println("Forwards!")),
    left("MoveLeft", GLFW.GLFW_KEY_A, GLFW.GLFW_KEY_A, () -> System.out.println("Left!")),
    down("MoveBackward", GLFW.GLFW_KEY_S, GLFW.GLFW_KEY_S, () -> System.out.println("Backwards!")),
    right("MoveRight", GLFW.GLFW_KEY_D, GLFW.GLFW_KEY_D, () -> System.out.println("Right!"));


    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private final String identifier;

    @Setter
    private int code;
    private final int defaultCode;

    private final Runnable action;


    //=============
    // CONSTRUCTOR
    //=============
    Keybinding(String identifier, int code, int defaultCode, Runnable action) {

        this.identifier = identifier;
        this.code = code;
        this.defaultCode = defaultCode;
        this.action = action;

    }//End of Constructor


    //==============================================
    // METHOD TO SET THE CODE TO IT'S DEFAULT VALUE
    //==============================================
    public void setDefaultKeybinding() {

        code = defaultCode;

    }//End of Method

}//End of Enumerated Class