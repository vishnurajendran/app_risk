package MapShow;

import entity.Country;
import entity.RiskMap;
import mapEditer.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MapViewer extends JFrame {

    private static RiskMap D_RISK_MAP = createRiskMap();

    public MapViewer() {
        initializeUI();
    }

    public static RiskMap createRiskMap() {
        MapLoader mapLoader = new MapLoader();
        mapLoader.loadMap("testMap.map");
        RiskMap riskMap = mapLoader.getMap();
        return riskMap;
    }

    private void initializeUI() {
        setTitle("Risk Map Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create a panel for the game map
        RiskMapPanel mapPanel = new RiskMapPanel(D_RISK_MAP); // Pass the RiskMap instance
        add(mapPanel);

        setLocationRelativeTo(null); // Center the frame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MapViewer mapViewer = new MapViewer();
            mapViewer.setVisible(true);
        });
    }

    static class RiskMapPanel extends JPanel {

        private RiskMap D_RISK_MAP;
        private Map<String, Color> D_CONTINENT_COLORS;

        public RiskMapPanel(RiskMap pRiskMap) {
            this.D_RISK_MAP = pRiskMap;
            this.D_CONTINENT_COLORS = initializeContinentColors();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw continents, countries, and connections
            for (Country country : D_RISK_MAP.getCountries()) {
                // Draw countries with the color of their continent
                String continentName = D_RISK_MAP.getContinentById(country.getContinentId()).getName();
                g.setColor(D_CONTINENT_COLORS.get(continentName));
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
