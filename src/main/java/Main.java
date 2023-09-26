import application.Application;
import common.Command;
import common.ISubAppInstantiator;
import common.Logger;
import game.GameInstantiator;
import mapeditor.MapEditorInstantiator;
import java.util.Scanner;
/**
 * This class acts as the entry point to the application
 * @author vishnurajendran
 * Dated 23-09-2023
 */
public class Main {

    private static boolean isDebuggingMode(){
        return true;
    }

    public static void main(String[] args){
        Scanner l_sc = new Scanner(System.in);

        //create instance of game and map instantiators and application.
        ISubAppInstantiator l_gameInstantiator = new GameInstantiator();
        ISubAppInstantiator l_mapInstantiator = new MapEditorInstantiator();
        Application l_app = new Application(l_gameInstantiator, l_mapInstantiator);

        //init application.
        l_app.startup();

        //managing logging.
        if(isDebuggingMode()){
            Logger.SetConsolePrinting(true);
        }
        else {
            Logger.SetConsolePrinting(false);
        }

        //game loop
        while(!l_app.hasQuit()){
            Command l_cmd = Command.parseString(l_sc.nextLine());
            if(l_cmd != null)
                l_app.processCommand(l_cmd);
        }

        //cleanup
        l_sc.close();
        l_app.cleanup();
    }
}

