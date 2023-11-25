package common.FileIO;

import common.Logging.Logger;

import java.io.*;
import java.util.Scanner;

/**
 * This class aids in reading and writing to file.
 * @author vishnurajendran
 */
public class FileIO {

    /**
     * private constructor to make this class
     * non-instantiable
     */
    private FileIO(){

    }

    /**
     * checks if a file exists.
     * @param p_path path to file
     * @return true if file exists, else false.
     */
    public static boolean fileExists(String p_path){
        File l_file = new File(p_path);
        return l_file.exists();
    }

    /**
     * removes the file.
     * @param p_path path to file
     * @return true if file was removed
     */
    public static boolean removeFile(String p_path){
        File l_file = new File(p_path);
        if(l_file.exists())
            return l_file.delete();

        return false;
    }

    /**
     * writes to a file (overwrites, it already exists)
     * @param p_path path to file
     * @param p_data data to write
     * @return true if file is written else false.
     */
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

    /**
     * reads a file.
     * @param p_path path to file
     * @return string data in the file.
     */
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
