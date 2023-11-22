package entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * this class will hold map data
 * @author vishnurajendran
 */
public class MapSaveData {
    private String d_path;
    private Map<Integer, Integer> d_countryArmyData;

    private MapSaveData(){

    }

    public static MapSaveData createSaveData(RiskMap p_map, String p_filePath){
        MapSaveData l_data = new MapSaveData();
        l_data.d_countryArmyData = new HashMap<>();
        l_data.d_path = p_filePath;
        for(Country l_country : p_map.getCountries()){
            l_data.d_countryArmyData.put(l_country.getDId(), l_country.getArmy());
        }
        return l_data;
    }

    public static RiskMap parseSaveData(MapSaveData p_saveData){
       if(p_saveData == null)
           return null;

       RiskMap l_map = MapLoader.getMap(p_saveData.d_path);
       for(Integer l_countryId : p_saveData.d_countryArmyData.keySet()){
           l_map.setCountryArmyById(l_countryId, p_saveData.d_countryArmyData.get(l_countryId));
       }
       return l_map;
    }

    public String getMapFilepath() {
        return d_path;
    }
}
