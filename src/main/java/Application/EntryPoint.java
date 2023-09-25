package Application;

import java.util.Scanner;

/**
 * This class acts as the entry point to the application
 * @author vishnurajendran
 * Dated 23-09-2023
 */
public class EntryPoint {
    public static void main(String[] args){
        Scanner l_sc = new Scanner(System.in);
        Application l_app = new Application();
        l_app.startup();
        while(!l_app.hasQuit()){
            ApplicationCommand l_cmd = ApplicationCommand.parseString(l_sc.nextLine());
            if(l_cmd != null)
                l_app.processCommand(l_cmd);
        }

        l_app.cleanup();
        l_sc.close();
    }
}

