package mapShow;

import entity.Country;
import game.PlayerHandler;
import entity.RiskMap;
import mapEditer.MapLoader;
import game.Player;

import javax.swing.*;
import java.awt.*;
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
        mapLoader.loadMap("testMap.map");
        return mapLoader.getMap();
    }

    /**
     * Initializes the user interface for the application.
     */
    private void initializeUI() {
        setTitle("Risk Map Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

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

            Map<String, Point> continentLabels = new HashMap<>(); // Store continent labels' positions

            // Draw continents, countries, and connections
            for (Country country : d_RISK_MAP.getCountries()) {
                String continentName = d_RISK_MAP.getContinentById(country.getContinentId()).getName();

                // Draw countries with the color of their continent
                Color continentColor = d_CONTINENT_COLORS.get(continentName);
                g.setColor(continentColor);
                g.fillOval(country.getXCoordinates(), country.getYCoordinates(), 50, 50);

                // Draw connections to neighboring countries
                g.setColor(Color.BLACK);
                for (Country connectedCountry : country.getBorders().values()) {
                    g.drawLine(
                            country.getXCoordinates() + 25, country.getYCoordinates() + 25,
                            connectedCountry.getXCoordinates() + 25, connectedCountry.getYCoordinates() + 25
                    );
                }

                // Draw country name
                g.setColor(Color.GRAY);
                g.drawString(country.getName(), country.getXCoordinates(), country.getYCoordinates() - 10);

                // Store the continent label position if not already stored
                continentLabels.computeIfAbsent(continentName, k -> new Point(country.getXCoordinates(), country.getYCoordinates() - 30));

                // Display player info
                displayPlayerInfo(g, country);
            }

            // Draw continent labels
            g.setColor(Color.BLACK);
            for (Map.Entry<String, Point> entry : continentLabels.entrySet()) {
                String label = "Continent: " + entry.getKey();
                g.drawString(label, entry.getValue().x, entry.getValue().y);
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
                if (player.getCountriesOwned().containsKey(country)) {
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