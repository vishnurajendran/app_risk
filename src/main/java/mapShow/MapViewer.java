package mapShow;

import entity.Continent;
import entity.Country;
import entity.RiskMap;
import entity.PlayerHandler;
import entity.Player;
import entity.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class representing the Map Application.
 */
public class MapViewer extends JFrame {

    /**
     * The RiskMap instance used in the application.
     */
    private final RiskMap d_RISK_MAP;

    /**
     * Constructor for the MapViewer class.
     */

    public MapViewer(RiskMap p_map) {
        d_RISK_MAP = p_map;
        initializeUI();
    }

    /**
     * Creates and returns a RiskMap instance by loading a map from a file.
     *
     * @return The RiskMap instance.
     */
    public static RiskMap createRiskMap() {
        MapLoader l_mapLoader = new MapLoader();
        l_mapLoader.loadMap("testResources/ValidTestMap.map");
        return l_mapLoader.getMap();
    }

    /**
     * The main entry point for the application.
     *
     * @param p_map map to show
     */
    public static void showMap(RiskMap p_map) {
        SwingUtilities.invokeLater(() -> {
            MapViewer l_mapViewer = new MapViewer(p_map);
            l_mapViewer.setVisible(true);
        });
    }

    /**
     * Initializes the user interface for the application.
     */
    private void initializeUI() {
        setTitle("Risk Map Viewer");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(800, 800);

        // Create a panel for the game map
        RiskMapPanel l_mapPanel = new RiskMapPanel(d_RISK_MAP); // Pass the RiskMap instance
        add(l_mapPanel);

        setLocationRelativeTo(null); // Center the frame
    }

    /**
     * The panel class for drawing the RiskMap.
     */
    static class RiskMapPanel extends JPanel {

        private final int d_DELTA = 75;
        private final int d_NODESIZE = 8;

        /**
         * The RiskMap instance to be rendered.
         */
        private final RiskMap d_RISK_MAP;
        /**
         * Map of continent names to colors.
         */
        private final Map<String, Color> d_CONTINENT_COLORS;

        /**
         * Constructor for the RiskMapPanel class.
         *
         * @param p_RiskMap The RiskMap instance to be rendered.
         */
        public RiskMapPanel(RiskMap p_RiskMap) {
            this.d_RISK_MAP = p_RiskMap;
            this.d_CONTINENT_COLORS = initializeContinentColors();
            // Display player info
            displayPlayerInfo();
        }

        /**
         * Paints the component, rendering continents, countries, and connections.
         *
         * @param p_grpahics The graphics context.
         */
        @Override
        protected void paintComponent(Graphics p_grpahics) {
            super.paintComponent(p_grpahics);
            setBackground(Color.DARK_GRAY);
            ArrayList<Continent> l_continents = new ArrayList<>();
            Map<Country, Point> l_countryPoint = new HashMap<>();

            if(d_RISK_MAP.getContinents() != null) {
                // Draw continents, countries, and connections
                for (Country l_country : d_RISK_MAP.getCountries()) {
                    Continent currContinent = d_RISK_MAP.getContinentById(l_country.getContinentId());
                    if (!l_continents.contains(currContinent)) {
                        l_continents.add(currContinent);
                    }

                    String l_continentName = d_RISK_MAP.getContinentById(l_country.getContinentId()).getName();

                    p_grpahics.setColor(Color.GRAY);
                    // Draw countries with the color of their continent
                    Color l_continentColor = d_CONTINENT_COLORS.get(l_continentName);
                    p_grpahics.setColor(l_continentColor);

                    p_grpahics.fillOval(l_country.getXCoordinates(), l_country.getYCoordinates() + d_DELTA, d_NODESIZE, d_NODESIZE);

                    l_countryPoint.put(l_country, new Point(l_country.getXCoordinates(), l_country.getYCoordinates() + d_DELTA));

                    // Draw connections to neighboring countries
                    p_grpahics.setColor(Color.GRAY);
                    for (Country l_connectedCountry : l_country.getBorders().values()) {
                        p_grpahics.drawLine(
                                l_country.getXCoordinates() + d_NODESIZE / 2, l_country.getYCoordinates() + d_DELTA + d_NODESIZE / 2,
                                l_connectedCountry.getXCoordinates() + d_NODESIZE / 2, l_connectedCountry.getYCoordinates() + d_DELTA + d_NODESIZE / 2
                        );
                    }
                }
            }

            if(d_RISK_MAP.getCountries() != null) {
                //Draw text over everything else
                for (Country l_country : d_RISK_MAP.getCountries()) {

                    // Draw country name
                    p_grpahics.setColor(Color.YELLOW);
                    p_grpahics.setFont(new Font("default", Font.BOLD, 12));
                    p_grpahics.drawString(l_country.getDId() + "", l_country.getXCoordinates(), l_country.getYCoordinates() - d_NODESIZE / 2 + d_DELTA);
                }
            }

            // Draw continent labels
            p_grpahics.setColor(Color.WHITE);

            if(l_continents != null) {
                //display continents
                for (Continent l_continent : l_continents) {
                    int l_minY = l_continent.getCountries().get(0).getYCoordinates();
                    int l_midX = 0;
                    for (Country country : l_continent.getCountries()) {
                        if (l_minY > country.getYCoordinates())
                            l_minY = country.getYCoordinates();

                        Point p = l_countryPoint.get(country);
                        l_midX += p.x;
                    }
                    l_midX /= l_continent.getCountries().size();
                    String l_label = l_continent.getName();

                    p_grpahics.setFont(new Font("default", Font.BOLD, 12));
                    p_grpahics.drawString(l_label, l_midX, l_minY + d_DELTA - 40);
                }
            }
        }

        /**
         * Displays player information on the map.
         */
        private void displayPlayerInfo() {
            //printing to console for now, cannot think of a cleaner way to display.
            System.out.println();
            for (Player l_player : PlayerHandler.getGamePlayers()) {
                System.out.println(MessageFormat.format("[Player `\u001B[33m{0}\u001B[0m` Map Stats]", l_player.getPlayerName()));
                for (Country l_country : l_player.getCountriesOwned()) {
                    System.out.println(MessageFormat.format("\tId: \u001B[31m{0}\u001B[0m Name: {1} Army: \u001B[31m{2}\u001B[0m", l_country.getDId(), l_country.getName(), l_country.getArmy()));
                }
                System.out.println();
            }
        }

        /**
         * Initializes the map of continent names to colors.
         *
         * @return The map of continent names to colors.
         */
        private Map<String, Color> initializeContinentColors() {
            Map<String, Color> colors = new HashMap<>();
            colors.put("ameroki", Color.YELLOW);
            colors.put("utropa", Color.PINK); // Hex color for #a980ff
            colors.put("amerpoll", Color.BLUE);
            colors.put("ulstrailia", Color.GREEN);
            return colors;
        }
    }
}