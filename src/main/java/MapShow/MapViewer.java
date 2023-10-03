package MapShow;

import entity.Country;
import entity.RiskMap;
import mapEditer.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapViewer extends JFrame {

    private RiskMap riskMap;

    public MapViewer() {
        this.riskMap = createRiskMap(); // Create or load the RiskMap instance
        initializeUI();
    }

    public static RiskMap createRiskMap() {
        MapLoader mp = new MapLoader();
        mp.loadMap("testMap.map");
        RiskMap map1 = mp.getMap();
        return map1;
    }

    private void initializeUI() {
        setTitle("Risk Map Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create a panel for the game map
        RiskMapPanel mapPanel = new RiskMapPanel(riskMap); // Pass the RiskMap instance
        add(mapPanel);

        setLocationRelativeTo(null); // Center the frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapViewer mapViewer = new MapViewer();
            mapViewer.setVisible(true);
        });
    }

    class RiskMapPanel extends JPanel {

        private RiskMap riskMap;
        private Map<String, Color> continentColors;

        public RiskMapPanel(RiskMap riskMap) {
            this.riskMap = riskMap;
            this.continentColors = initializeContinentColors();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw continents, countries, and connections
            for (Country country : riskMap.getCountries()) {
                // Draw countries with the color of their continent
                String continentName = riskMap.getContinentById(country.getContinentId()).getName();
                g.setColor(continentColors.get(continentName));
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