package mapShow;

import entity.RiskMap;
import java.awt.image.BufferedImage;
import java.awt.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Unit test for MapViewer
 */
class MapViewerTest {

    /**
     * Unit test to ensure that the RiskMap instance is created successfully.
     * Test cases:
     * 1. Check if RiskMap instance is not null.
     */
    @Test
    void testCreateRiskMap() {
        RiskMap riskMap = MapViewer.createRiskMap();
        assertNotNull(riskMap);
    }

    /**
     * Unit test to ensure that the MapViewer class is initialized successfully.
     * Test cases:
     * 1. Check if MapViewer instance is not null.
     * 2. Check if RiskMapPanel instance is created successfully.
     */
    @Test
    void testInitializeUI() {
        MapViewer mapViewer = new MapViewer();
        assertNotNull(mapViewer);

        MapViewer.RiskMapPanel riskMapPanel = new MapViewer.RiskMapPanel(new RiskMap());
        assertNotNull(riskMapPanel);
    }

    /**
     * Unit test to ensure that the RiskMapPanel class paints the component correctly.
     * Test cases:
     * 1. Check if the paintComponent method works without errors.
     */
    @Test
    void testRiskMapPanelPaintComponent() {
        RiskMap riskMap = new RiskMap();
        MapViewer.RiskMapPanel riskMapPanel = new MapViewer.RiskMapPanel(riskMap);

        // Create a dummy Graphics2D object for testing
        Graphics2D dummyGraphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();

        riskMapPanel.paintComponent(dummyGraphics);

        // Dispose the dummyGraphics after use
        dummyGraphics.dispose();
    }
}

