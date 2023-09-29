package entity;

/**
 * This class hold information about a single continent.
 *
 * @author TaranjeetKaur
 *
 */
public class Continent {

    int d_id;
    String d_name;
    int d_controlValue;
    String d_color;

    /**
     * Parameterised Constructor for the Continent
     *
     * @param p_id                  the number id for the continent, should be unique.
     * @param p_name                the name of the continent
     * @param p_controlValue        the control value depicts the number of armies per turn
     *                              that is given to the player that control all of it
     * @param p_color               the color of the continent (useful for UI)
     */
    public Continent(int p_id, String p_name, int p_controlValue, String p_color) {
        this.d_id = p_id;
        this.d_name = p_name;
        this.d_controlValue = p_controlValue;
        this.d_color = p_color;
    }

    /**
     * Setter for id
     *
     * @param p_id      the unique id for the continent
     */
    public void setId(int p_id) { this.d_id = p_id; }

    /**
     * Setter for name
     *
     * @param p_name    the name of the continent
     */
    public void setName(String p_name) { this.d_name = p_name; }

    /**
     * Setter for Control Value
     *
     * @param p_controlValue        the control value depicts the number of armies per turn
     *                              that is given to the player that control all of it
     */
    public void setControlValue(int p_controlValue) { this.d_controlValue = p_controlValue; }

    /**
     * Setter for color
     *
     * @param p_color   the color of the continent (useful for UI)
     */
    public void setColor(String p_color) { this.d_color = p_color; }

    /**
     * Getter for id
     *
     * @return      the unique id for the continent as an Integer
     */
    public int getId() { return this.d_id; }

    /**
     * Getter for Name
     *
     * @return      the name of the continent as a String
     */
    public String getName() { return this.d_name; }

    /**
     * Getter for ControlValue
     *
     * @return      the number of armies per turn that is given to the player
     *              that control all of it as an Integer
     */
    public int getControlValue() { return this.d_controlValue; }

    /**
     * Getter for color
     *
     * @return  the color of the continent as a String
     */
    public String getColor() { return this.d_color; }

    /**
     * Override the toString to show detailed info of the class
     *
     * @return A string contain ID, name and Control value.
     */
    public String toString() {
        return "Id: " + d_id + " Name: " + d_name + " ControlValue: " + d_controlValue;
    }

}