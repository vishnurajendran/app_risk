package mapShow;

import entity.Continent;
import entity.Country;
import entity.RiskMap;
import game.PlayerHandler;
import game.Player;
import mapEditer.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class representing the Map Application.
 */
public class MapViewer extends JFrame {

    /** The RiskMap instance used in the application. */
    private final RiskMap d_RISK_MAP = createRiskMap();

    /**
     * Constructor for the MapViewer class.
     */
    public MapViewer() {
        initializeUI();
    }

    /**
     * Creates and returns a RiskMap instance by loading a map from a file.
     *
     * @return The RiskMap instance.
     */
    public static RiskMap createRiskMap() {
        MapLoader mapLoader = new MapLoader();
        mapLoader.loadMap("testResources/ValidTestMap.map");
        return mapLoader.getMap();
    }

    /**
     * Initializes the user interface for the application.
     */
    private void initializeUI() {
        setTitle("Risk Map Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);

        // Create a panel for the game map
        RiskMapPanel mapPanel = new RiskMapPanel(d_RISK_MAP); // Pass the RiskMap instance
        add(mapPanel);

        setLocationRelativeTo(null); // Center the frame
    }

    /**
     * The main entry point for the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapViewer mapViewer = new MapViewer();
            mapViewer.setVisible(true);
        });
    }

    /**
     * The panel class for drawing the RiskMap.
     */
    static class RiskMapPanel extends JPanel {

        private final int d_DELTA = 75;
        private final int d_NODESIZE = 8;

        /** The RiskMap instance to be rendered. */
        private final RiskMap d_RISK_MAP;
        /** Map of continent names to colors. */
        private final Map<String, Color> d_CONTINENT_COLORS;

        /**
         * Constructor for the RiskMapPanel class.
         *
         * @param pRiskMap The RiskMap instance to be rendered.
         */
        public RiskMapPanel(RiskMap pRiskMap) {
            this.d_RISK_MAP = pRiskMap;
            this.d_CONTINENT_COLORS = initializeContinentColors();
        }

        /**
         * Paints the component, rendering continents, countries, and connections.
         *
         * @param g The graphics context.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.DARK_GRAY);
            ArrayList<Continent> continents = new ArrayList<>();

            Map<Country, Point> countryPoint = new HashMap<>();

            // Draw continents, countries, and connections
            for (Country country : d_RISK_MAP.getCountries()) {
                Continent currContinent = d_RISK_MAP.getContinentById(country.getContinentId());
                if(!continents.contains(currContinent)){
                    continents.add(currContinent);
                }

                String continentName = d_RISK_MAP.getContinentById(country.getContinentId()).getName();

                g.setColor(Color.GRAY);
                // Draw countries with the color of their continent
                Color continentColor = d_CONTINENT_COLORS.get(continentName);
                g.setColor(continentColor);

                g.fillOval(country.getXCoordinates(), country.getYCoordinates() + d_DELTA, d_NODESIZE, d_NODESIZE);

                countryPoint.put(country, new Point(country.getXCoordinates(), country.getYCoordinates() + d_DELTA));

                // Draw connections to neighboring countries
                g.setColor(Color.GRAY);
                for (Country connectedCountry : country.getBorders().values()) {
                    g.drawLine(
                            country.getXCoordinates() + d_NODESIZE/2, country.getYCoordinates() + d_DELTA + d_NODESIZE/2,
                            connectedCountry.getXCoordinates() + d_NODESIZE/2, connectedCountry.getYCoordinates() + d_DELTA + d_NODESIZE/2
                    );
                }
            }

            //Draw text over everything else
            for(Country country : d_RISK_MAP.getCountries()){

                // Draw country name
                g.setColor(Color.YELLOW);
                g.setFont(new Font("default", Font.BOLD, 12));
                g.drawString(country.getDId()+"", country.getXCoordinates(), country.getYCoordinates() - d_NODESIZE/2 + d_DELTA);

                // Display player info
                displayPlayerInfo(g, country);
            }

            // Draw continent labels
            g.setColor(Color.WHITE);

            //display continents
            for(Continent continent : continents){
                int minY = continent.getCountries().get(0).getYCoordinates();
                int midX = 0;
                for (Country country: continent.getCountries()) {
                    if(minY > country.getYCoordinates())
                        minY = country.getYCoordinates();

                    Point p = countryPoint.get(country);
                    midX += p.x;
                }
                midX /= continent.getCountries().size();
                String label = continent.getName();

                g.setFont(new Font("default", Font.BOLD, 12));
                g.drawString(label, midX, minY + d_DELTA - 40);
            }
        }

        /**
         * Displays player information on the map.
         *
         * @param g The graphics context.
         * @param country The country for which player information is displayed.
         */
        private void displayPlayerInfo(Graphics g, Country country) {
            for (Player player : PlayerHandler.getGamePlayers()) {
                if (player.getCountriesOwned().contains(country)) {
                    g.setColor(Color.RED);
                    g.drawString(player.getPlayerName(), country.getXCoordinates(), country.getYCoordinates() + 30);

                    // Display the total available reinforcements for the player
                    int availableReinforcements = player.getAvailableReinforcements();
                    g.drawString("Reinforcements: " + availableReinforcements, country.getXCoordinates(), country.getYCoordinates() + 45);
                }
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