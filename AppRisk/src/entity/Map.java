package entity;

import java.util.HashMap;

public class Map {
    String d_name;
    //used for store data for demo only, can change to some other data structure
    java.util.Map<Integer,Country> countries;

    public Map() {
        countries=new HashMap<Integer,Country>();
    }

    public Map(String p_name){
        countries=new HashMap<Integer,Country>();
        d_name=p_name;
    }

    public String getName() {
        return d_name;
    }

    public void setName(String d_name) {
        this.d_name = d_name;
    }

    public void addCountry(Country p_Country){
        countries.put(p_Country.getDId(),p_Country);
    }

    public void addBorders(int p_countryId,String[] p_otherCountriesId){
        Country l_country= countries.get(p_countryId);
        for(String l_oneCountry:p_otherCountriesId){
            l_country.addBorder(Integer.parseInt(l_oneCountry));
        }
    }
    public String toString(){
        String l_countriesString="";
        for(Integer l_key:countries.keySet()){
            l_countriesString+=(countries.get(l_key)+"\n");
        }
        return "Map "+d_name+"\n"+l_countriesString;
    }
}
