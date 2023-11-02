package main;

import application.Application;
import common.Command;
import common.ISubAppInstantiator;
import common.Logging.ConsoleLogWriter;
import common.Logging.FileLogWriter;
import common.Logging.ILogWriter;
import common.Logging.Logger;
import game.GameInstantiator;
import mapEditer.MapEditorInstantiator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class acts as the entry point to the application
 *
 * @author vishnurajendran
 */
public class Main {
    private static final String SYMB_DEBUGGING = "--debug";

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

        //Set up logger.
        boolean L_consolePrint = args.length > 0 && args[0].equals(SYMB_DEBUGGING);
        List<ILogWriter> l_logWriters = new ArrayList<>();
        l_logWriters.add(new FileLogWriter());
        if(L_consolePrint)
            l_logWriters.add(new ConsoleLogWriter());

        Logger.initialise(l_logWriters ,L_consolePrint);

        //Setup application
        Scanner l_sc = new Scanner(System.in);
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

