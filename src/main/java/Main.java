import application.Application;
import common.Command;
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
    public static void main(String[] args){
        Scanner l_sc = new Scanner(System.in);
        Application l_app = new Application();
        l_app.startup();
        Logger.SetConsolePrinting(false);
        while(!l_app.hasQuit()){
            Command l_cmd = Command.parseString(l_sc.nextLine());
            if(l_cmd != null)
                l_app.processCommand(l_cmd);
        }
        l_app.cleanup();
        l_sc.close();
    }
}

