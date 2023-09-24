package entity;

import java.util.LinkedList;

public class Country {
    int d_id;
    String d_name;

    int d_continentId;
    //offered in the sample file and not sure if we need to use
    int d_xCoordinates;
    int d_yCoordinates;
    LinkedList<Integer> d_borders;

    public Country(int p_id, String p_name, int p_continentId, int p_xCoordinates, int p_yCoordinates) {
        this.d_id = p_id;
        this.d_name=p_name;
        this.d_continentId = p_continentId;
        this.d_xCoordinates = p_xCoordinates;
        this.d_yCoordinates = p_yCoordinates;
        d_borders=new LinkedList<Integer>();
    }

    public void setId(int p_id) {
        this.d_id = p_id;
    }

    public void setContinentId(int p_continentId) {
        this.d_continentId = p_continentId;
    }

    public void setXCoordinates(int p_xCoordinates) {
        this.d_xCoordinates = p_xCoordinates;
    }

    public void setYCoordinates(int p_yCoordinates) {
        this.d_yCoordinates = p_yCoordinates;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }
    public String getD_name() {
        return d_name;
    }



    public int getDId() {
        return d_id;
    }


    public int getContinentId() {
        return d_continentId;
    }

    public int getXCoordinates() {
        return d_xCoordinates;
    }

    public int getYCoordinates() {
        return d_yCoordinates;
    }

    public LinkedList<Integer> getBorders() {
        return d_borders;
    }

    public boolean addBorder(int p_border){
        return d_borders.add(p_border);
    }
    public String toString(){
        return "Id: "+d_id+" Name: "+d_name+" continentId: "+d_continentId+d_borders.toString();
    }
}
