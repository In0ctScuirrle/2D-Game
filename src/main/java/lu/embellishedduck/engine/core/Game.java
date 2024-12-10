package lu.embellishedduck.engine.core;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
@Getter
@Setter
public class Game {

    //=======================
    // INSTANTIATE VARIABLES
    //=======================
    private long window;

    private GameState gameState;
    private enum GameState {

        PLAY,
        PAUSE

    }//End of Enumerated Type


    //=============
    // CONSTRUCTOR
    //=============
    public Game() {

        this.gameState = GameState.PLAY;

    }//End of Constructor


    //========================
    // METHOD TO RUN THE GAME
    //========================
    public void run() {

        log.info("Game launched");
        log.info("Hello LWJGL {}!", Version.getVersion());

        init();
        loop();

        //Free the window callbacks and destroy it
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        //Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }//End of Method


    //=================================
    // METHOD TO INITIALIZE THE WINDOW
    //=================================
    public void init() {

        //Setting up an error callback
        GLFWErrorCallback.createPrint(System.err).set();
        glfwSetErrorCallback(new SLF4JErrorCallback());

        //Initialize GLFW, most of the functions won't work without this
        if (!glfwInit()) {

            throw new IllegalStateException("Unable to initialize GLFW");

        }//End of If Statement

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //Create the window
        window = glfwCreateWindow(800, 600, "2D Game", NULL, NULL);

        if (window == NULL) {

            throw new RuntimeException("Failed to create the GLFW window");

        }//End of If Statement

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {});

        //Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {

            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            //Get the window size passed to the glfwCreateWindow()
            glfwGetWindowSize(window, pWidth, pHeight);

            //Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            //Center the window
            assert vidMode != null;
            glfwSetWindowPos(window, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2);

        }//The stack is popped automatically

        //Make the OpenGL context current
        glfwMakeContextCurrent(window);
        //Enable V-Sync
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(window);

    }//End of Method


    //===============================
    // AHH YES, THE GAME LOOP METHOD
    //===============================
    public void loop() {

        /*
        This line is critical for LWJGL's interoperation with GLFW's
        OpenGL context, or any context that is managed externally.
        LWJGL detects the context that is current in the current thread,
        creates the GLCapabilities instance and makes the OpenGL
        bindings available for use.
        */
        GL.createCapabilities();

        //Run the rendering loop until the user has closed the window
        while (!glfwWindowShouldClose(window)) {

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            glfwSwapBuffers(window);
            glfwPollEvents();

            //This is where all things that require updating will be updating
            if (gameState == GameState.PLAY) {



            }//End of If Statement

        }//End of While Loop

    }//End of Method


    //======================================================
    // EMBEDDED CLASS FOR ADDING SLF4J AS AN ERROR CALLBACK
    //======================================================
    private static class SLF4JErrorCallback extends GLFWErrorCallback {

        //===============================
        // METHOD TO INVOKE THE CALLBACK
        //===============================
        @Override
        public void invoke(int error, long description) {

            log.error("GLFW error [{}]: {}", error, description);

        }//End of Method

    }//End of Embedded Class

}//End of Class