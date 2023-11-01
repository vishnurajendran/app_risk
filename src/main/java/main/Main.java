package main;

import application.Application;
import common.Command;
import common.ISubAppInstantiator;
import common.Logger.Logger;
import game.GameInstantiator;
import mapEditer.MapEditorInstantiator;

import java.util.Scanner;

/**
 * This class acts as the entry point to the application
 *
 * @author vishnurajendran
 */
public class Main {

    private static final String SYMB_DEBUGGING = "--debug";

    private static boolean isDebuggingMode() {
        return true;
    }

    /**
     * The main function initialises the application class
     * and runs a loop until the application instance has quit.
     * it is also responsible for getting the input from user,
     * parsing it to a Command and providing it to application to
     * process.
     *
     * @param args arguments for this program (eg --debug).
     */
    public static void main(String[] args) {

        boolean consolePrint = args.length > 0 && args[0].equals(SYMB_DEBUGGING);
        Logger.Initialise(consolePrint);
        Scanner l_sc = new Scanner(System.in);

        //create instance of game and map instantiators and application.
        ISubAppInstantiator l_gameInstantiator = new GameInstantiator();
        ISubAppInstantiator l_mapInstantiator = new MapEditorInstantiator();
        Application l_app = new Application(l_gameInstantiator, l_mapInstantiator);

        //init application.
        l_app.startup();

        //game loop
        while (!l_app.hasQuit()) {
            String l_input = l_sc.nextLine();
            Command l_cmd = Command.parseString(l_input);
            if (l_cmd != null) {
                String cmdMsg = "user input to parse '"+l_input+"'\n\t\t\t\tparsed to - "+l_cmd;
                Logger.log(cmdMsg);
                l_app.processCommand(l_cmd);
            }
        }

        //cleanup
        l_sc.close();
        l_app.cleanup();
    }
}

