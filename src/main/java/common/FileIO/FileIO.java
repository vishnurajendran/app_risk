package common.FileIO;

import common.Logging.Logger;

import java.io.*;
import java.util.Scanner;

/**
 * This class aids in reading and writing to file.
 * @author vishnurajendran
 */
public class FileIO {
    private FileIO(){

    }

    public static boolean fileExists(String p_path){
        File l_file = new File(p_path);
        return l_file.exists();
    }

    public static boolean removeFile(String p_path){
        File l_file = new File(p_path);
        if(l_file.exists())
            return l_file.delete();

        return false;
    }

    public static boolean writeTextFile(String p_path, String p_data){
        try {
            if (p_path.isBlank()) {
                Logger.logError("Attempting to write to empty file-path");
                return false;
            }

            FileWriter l_fileWriter = new FileWriter(p_path, false);
            l_fileWriter.write(p_data);
            l_fileWriter.close();
            return true;
        } catch (IOException l_ex) {
            Logger.logError(l_ex.getMessage());
            return false;
        }
    }

    public static String readTextFile(String p_path){
        try {
            File l_file = new File(p_path);
            Scanner l_scanner = new Scanner(l_file);
            String l_data = "";
            while(l_scanner.hasNextLine()){
                l_data += l_scanner.nextLine();
            }
            l_scanner.close();
            return l_data;
        }
        catch (FileNotFoundException l_ex){
            Logger.logError(l_ex.getMessage());
        }
        return "";
    }

}
