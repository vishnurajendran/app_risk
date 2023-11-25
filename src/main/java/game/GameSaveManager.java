package game;
import common.FileIO.FileIO;
import common.Logging.Logger;
import common.Serialisation.JsonSerialisation;
import game.Data.GameSaveData;
import java.util.UUID;

/**
 * @author vishnurajendran
 */
public class GameSaveManager {

    /**
     * this method saves the game state to
     * @param p_save Save data to write to file.
     * @return true if save was successful.
     */
    public static boolean saveGame(GameSaveData p_save, String p_path){
        p_path = p_path.isBlank()?"QuickSave_"+ UUID.randomUUID().toString().replace("-","")+".save":p_path;
        if(!p_path.endsWith(".save")){
            p_path += ".save";
        }
        return  FileIO.writeTextFile(p_path, JsonSerialisation.toJson(p_save));
    }

    /**
     * creates a SaveData instance from the serialised data in file
     * @return instance of save data.
     */
    public static GameSaveData loadSave(String p_path){
        if(p_path.isBlank())
        {
            System.out.println("Save file path is empty!!");
            return null;
        }

        if(!p_path.endsWith(".save"))
            p_path += ".save";

        if(!FileIO.fileExists(p_path)) {
            System.out.println("File at path " + p_path + " does not exist!!");
            return null;
        }

        return JsonSerialisation.fromJson(FileIO.readTextFile(p_path), GameSaveData.class);
    }
}
