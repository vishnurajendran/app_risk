package entity;

import common.Logging.Logger;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Objects.isNull;

/**
 * Reader for conquest map
 */
public class ConquestMapReader implements MapReader{

    /**
     * Read conquest map
     * @param p_mapName file path of the map
     * @return Loaded map or null
     */
    @Override
    public RiskMap readMap(String p_mapName) {
        RiskMap l_riskMap=new RiskMap();
        l_riskMap.setName(p_mapName);
        Scanner l_scanner=null;
        try{
            File l_mapFile = new File(p_mapName);
            l_scanner = new Scanner(l_mapFile);
            //for temp store of data
            Map<String,Continent> l_continents=new HashMap<>();
            Map<String,Country> l_countries=new HashMap<>();

            Map<Country,String[]> l_connections=new HashMap<>();
            while (l_scanner.hasNextLine()){
                String l_line = l_scanner.nextLine().trim();
                // skip empty lines
                if (l_line.isEmpty()) {
                    continue;
                }
                // load continent
                if (l_line.equals("[Continents]")) {
                    int l_continentID = 1;
                    l_line = l_scanner.nextLine();
                    while (!l_line.isEmpty()) {
                        String[] l_linePieces = l_line.split("=");
                        Continent l_continent=new Continent(l_continentID,l_linePieces[0],Integer.parseInt(l_linePieces[1]));
                        l_riskMap.addContinent(l_continent);
                        //put continent here for look up by name
                        l_continents.put(l_linePieces[0],l_continent);

                        l_continentID++;
                        if (l_scanner.hasNextLine()) {
                            l_line = l_scanner.nextLine();
                        } else {
                            break;
                        }
                    }
                }

                // load countries
                if (l_line.equals("[Territories]")) {
                    l_line = l_scanner.nextLine();
                    int l_countryID = 1;
                    while (!l_line.isEmpty()) {
                        String[] l_linePieces = l_line.split(",");
                        Country l_country=new Country(l_countryID,l_linePieces[0],l_continents.get(l_linePieces[3]).getId(),Integer.parseInt(l_linePieces[1]),Integer.parseInt(l_linePieces[2]));


                        //throw error if duplicate found
                        if (!isNull(l_riskMap.getCountryById(l_country.getDId()))) {
                            throw new Exception("Duplicated found");
                        }
                        //add for lookup and adding border later
                        l_countries.put(l_linePieces[0],l_country);
                        l_connections.put(l_country, Arrays.copyOfRange(l_linePieces,4,l_linePieces.length));

                        l_riskMap.addCountry(l_country);

                        l_countryID++;
                        if (l_scanner.hasNextLine()) {
                            l_line = l_scanner.nextLine();
                        } else {
                            break;
                        }
                    }
                    //adding borders
                    for( Country l_country: l_connections.keySet()){
                        String[] connectionNames=l_connections.get(l_country);
                        String[] connectionIds=new String[connectionNames.length];
                        //converting names to ids
                        for(int i=0;i<connectionNames.length;i++){
                            connectionIds[i]=l_countries.get(connectionNames[i]).getDId()+"";
                        }
                        l_riskMap.addBorders(l_country.getDId(),connectionIds);
                    }
                }

            }
            return l_riskMap;
        }catch (Exception e){
            Logger.logError(e.getMessage());
            if(!isNull(l_scanner)){
                l_scanner.close();
            }
            return null;
        }
    }
}
