package MapShow;

import entity.Country;
import entity.RiskMap;
import mapEditer.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class representing the Risk Map Viewer application.
 */
public class MapViewer extends JFrame {

    /** The RiskMap instance used in the application. */
    private static RiskMap d_RISK_MAP = createRiskMap();

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
        RiskMap riskMap = mapLoader.getMap();
        return riskMap;
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
     * The panel class for rendering the RiskMap.
     */
    static class RiskMapPanel extends JPanel {

        /** The RiskMap instance to be rendered. */
        private RiskMap d_RISK_MAP;
        /** Map of continent names to colors. */
        private Map<String, Color> d_CONTINENT_COLORS;

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

            // Draw continents, countries, and connections
            for (Country country : d_RISK_MAP.getCountries()) {
                // Draw countries with the color of their continent
                String continentName = d_RISK_MAP.getContinentById(country.getContinentId()).getName();
                g.setColor(d_CONTINENT_COLORS.get(continentName));
                g.fillOval(country.getXCoordinates(), country.getYCoordinates(), 50, 50);

                // Draw country names
                g.setColor(Color.BLACK);
                g.drawString(country.getName(), country.getXCoordinates(), country.getYCoordinates() - 10);

                // Draw connections
                for (Country connectedCountry : country.getBorders().values()) {
                    g.setColor(Color.BLACK);
                    g.drawLine(
                            country.getXCoordinates() + 25, country.getYCoordinates() + 25,
                            connectedCountry.getXCoordinates() + 25, connectedCountry.getYCoordinates() + 25
                    );
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