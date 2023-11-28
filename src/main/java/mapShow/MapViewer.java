package mapShow;
import common.Logging.Logger;
import entity.Continent;
import entity.Country;
import entity.RiskMap;
import entity.PlayerHandler;
import entity.Player;
import entity.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The main class representing the Map Application.
 */
public class MapViewer extends JFrame {

    private static MapViewer d_instance;

    private RiskMapPanel d_mapPanel;

    /**
     * The RiskMap instance used in the application.
     */
    private final RiskMap d_RISK_MAP;

    /**
     * Constructor for the MapViewer class.
     */

    public MapViewer(RiskMap p_map) {
        if(d_instance != null) {
            d_instance.dispose();
            d_instance = null;
        }
        d_RISK_MAP = p_map;
        initializeUI();
        d_instance = this;
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
     * Any active map viewer will be updated
     */
    public static void tryUpdateMapViewer() {
       if(d_instance != null)
           d_instance.repaint();
    }

    /**
     * Initializes the user interface for the application.
     */
    private void initializeUI() {
        setTitle("Risk Map Viewer");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 800);
        // Create a panel for the game map
        d_mapPanel = new RiskMapPanel(d_RISK_MAP); // Pass the RiskMap instance
        add(d_mapPanel);
        addMouseWheelListener(d_mapPanel);
        addMouseMotionListener(d_mapPanel);
        addKeyListener(d_mapPanel);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
               d_instance = null;
            }
        });
        setLocationRelativeTo(null);
        // Center the frame
    }

    @Override
    public boolean isFocusable() {
        return true;
    }

}