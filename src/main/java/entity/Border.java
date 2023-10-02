package entity;

/**
 * The {@code Border} class represents a border between two countries in a Risk map.
 * It holds information about the IDs of the two countries involved in the border.
 *
 * <p>A border is defined by the IDs of two countries, country1 and country2, indicating
 * a connection between those two countries in the map.
 *
 * @author Shravani Kulkarni
 */
public class Border {
    /**
     * The ID of the first country in the border.
     */
    private int d_country1;

    /**
     * The ID of the second country in the border.
     */
    private int d_country2;

    /**
     * The constructor for the {@code Border} class.
     *
     * @param p_country1 The ID of the first country in the border.
     * @param p_country2 The ID of the second country in the border.
     */
    public Border(int p_country1, int p_country2) {
        this.d_country1 = p_country1;
        this.d_country2 = p_country2;
    }

    /**
     * Gets the ID of the first country in the border.
     *
     * @return The ID of the first country.
     */
    public int getCountry1() {
        return d_country1;
    }

    /**
     * Gets the ID of the second country in the border.
     *
     * @return The ID of the second country.
     */
    public int getCountry2() {
        return d_country2;
    }
}