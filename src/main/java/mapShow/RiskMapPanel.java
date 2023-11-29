package mapShow;

import entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The panel class for drawing the RiskMap.
 * @author vishnu rajendran
 */
public class RiskMapPanel extends JPanel implements MouseWheelListener, MouseMotionListener, KeyListener {

    /**
     * this enum denotes the view mode of the map text
     */
    private enum ViewMode{
        CountryID,
        CountryName,
        PlayerOwnerShip,
        ArmyValue
    }

    private final int d_DELTA = 75;
    private final int d_NODESIZE = 8;

    private final float ZOOM_LIMIT_MIN = 1;
    private final float ZOOM_LIMIT_MAX = 10;

    //------------------------------
    // All gui related vars
    //------------------------------
    private ViewMode d_currViewMode = ViewMode.CountryID;
    private Point d_dragStartPos = new Point(0,0);
    private Point d_dragCurrPos = new Point(0,0);
    private Point d_cachedDragDelta = new Point(0,0);

    private float d_zoomScale = 1;
    private float d_zoomMultiplier = 1;


    /**
     * The RiskMap instance to be rendered.
     */
    private final RiskMap d_RISK_MAP;

    /**
     * Constructor for the RiskMapPanel class.
     *
     * @param p_RiskMap The RiskMap instance to be rendered.
     */
    public RiskMapPanel(RiskMap p_RiskMap) {
        this.d_RISK_MAP = p_RiskMap;
    }

    /**
     * Paints the component, rendering continents, countries, and connections.
     *
     * @param p_graphics The graphics context.
     */
    @Override
    protected void paintComponent(Graphics p_graphics) {
        super.paintComponent(p_graphics);
        Color l_ogColor = p_graphics.getColor();
        setBackground(new Color(35,35,35));
        drawMap(p_graphics);
        drawTitle(p_graphics);
        drawLegend(p_graphics);
        p_graphics.setColor(l_ogColor);
    }

    /**
     * this method draws the legend on the screen
     * @param p_graphics graphics instance
     */
    private void drawTitle(Graphics p_graphics){
        int l_padding = 10;
        int l_legendWidth = 200;
        int l_legendHeight = 30;
        int l_xPos = l_padding;
        int l_yPos = l_padding;

        Point l_rectOrigin = new Point(l_xPos, l_yPos);
        Point l_rectSize = new Point(l_legendWidth, l_legendHeight);

        Color l_ogColor = p_graphics.getColor();
        p_graphics.setColor(Color.BLACK);
        p_graphics.fillRoundRect(l_xPos,l_yPos, l_legendWidth, l_legendHeight,2,2);
        p_graphics.setColor(l_ogColor);
        p_graphics.setColor(Color.white);
        p_graphics.drawString("RiskMap Viewer v2", (l_rectOrigin.x + l_rectSize.x/2) - 50, l_rectOrigin.y + l_rectSize.y/2 + l_padding/2);
    }

    /**
     * this method draws the legend on the screen
     * @param p_graphics graphics instance
     */
    private void drawLegend(Graphics p_graphics){
        int l_padding = 10;
        int l_legendWidth = 200;
        int l_legendHeight = 245;
        int l_xPos = getWidth() - (l_legendWidth + l_padding);
        int l_yPos = l_padding;

        Point l_rectOrigin = new Point(l_xPos, l_yPos);
        Point l_rectSize = new Point(l_legendWidth, l_legendHeight);

        Color l_ogColor = p_graphics.getColor();
        p_graphics.setColor(Color.BLACK);
        p_graphics.fillRoundRect(l_xPos,l_yPos, l_legendWidth, l_legendHeight,2,2);
        p_graphics.setColor(l_ogColor);
        p_graphics.setColor(Color.white);
        p_graphics.drawString("Controls", (l_rectOrigin.x + l_rectSize.x/2) - 25, l_rectOrigin.y + 15);
        p_graphics.drawString("Use LMB or RMB to drag", l_rectOrigin.x + 10, l_rectOrigin.y + 45);
        p_graphics.drawString("Scroll to zoom", l_rectOrigin.x + 10, l_rectOrigin.y + 70);
        p_graphics.drawString("Key 1 - Country Id", l_rectOrigin.x + 10, l_rectOrigin.y + 95);
        p_graphics.drawString("Key 2 - Country Names", l_rectOrigin.x + 10, l_rectOrigin.y + 120);
        p_graphics.drawString("Key 3 - Player Ownership", l_rectOrigin.x + 10, l_rectOrigin.y + 145);
        p_graphics.drawString("Key 4 - Army Values", l_rectOrigin.x + 10, l_rectOrigin.y + 170);
        p_graphics.drawLine(l_rectOrigin.x + 10, l_rectOrigin.y + 195, l_rectOrigin.x + l_rectSize.x - 10, l_rectOrigin.y + 195);
        p_graphics.setColor(Color.yellow);

        int l_viewX = 0;
        switch (d_currViewMode){
            case CountryID -> l_viewX = (l_rectOrigin.x + l_rectSize.x/2) - 50;
            case CountryName -> l_viewX = (l_rectOrigin.x + l_rectSize.x/2) - 60;
            case PlayerOwnerShip -> l_viewX = (l_rectOrigin.x + l_rectSize.x/2) - 73;
            case ArmyValue -> l_viewX = (l_rectOrigin.x + l_rectSize.x/2) - 55;
        }
        p_graphics.drawString("viewing "+ d_currViewMode, l_viewX, l_rectOrigin.y + 220);
    }

    private void drawMap(Graphics p_graphics){
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

                p_graphics.setColor(Color.GRAY);
                int xCountryPos = (int)(l_country.getXCoordinates() * d_zoomScale) + d_cachedDragDelta.x;
                int yCountryPos = (int)((l_country.getYCoordinates() + d_DELTA) * d_zoomScale) + d_cachedDragDelta.y;

                p_graphics.fillOval(xCountryPos, yCountryPos, d_NODESIZE, d_NODESIZE);
                l_countryPoint.put(l_country, new Point(xCountryPos, yCountryPos));

                // Draw connections to neighboring countries
                p_graphics.setColor(Color.GRAY);
                for (Country l_connectedCountry : l_country.getBorders().values()) {
                    int xCountryPos2 = (int)(l_connectedCountry.getXCoordinates() * d_zoomScale) + d_cachedDragDelta.x;
                    int yCountryPos2 = (int)((l_connectedCountry.getYCoordinates() + d_DELTA) * d_zoomScale) + d_cachedDragDelta.y;
                    p_graphics.drawLine(
                            xCountryPos + d_NODESIZE / 2, yCountryPos + d_NODESIZE / 2,
                            xCountryPos2 + d_NODESIZE / 2, yCountryPos2 + d_NODESIZE / 2
                    );
                }
            }
        }

        if(d_RISK_MAP.getCountries() != null) {
            //Draw text over everything else
            for (Country l_country : d_RISK_MAP.getCountries()) {

                int l_xCountryPos = (int)(l_country.getXCoordinates() * d_zoomScale) + d_cachedDragDelta.x;
                int l_yCountryPos = (int)((l_country.getYCoordinates() + d_DELTA) * d_zoomScale) + d_cachedDragDelta.y;
                // Draw country name
                p_graphics.setColor(getCountryLabelColor());
                p_graphics.setFont(new Font("default", Font.BOLD, 12));
                p_graphics.drawString(getLabel(l_country) + "", l_xCountryPos, l_yCountryPos - d_NODESIZE / 2);
            }
        }

        // Draw continent labels
        p_graphics.setColor(Color.WHITE);

        if(l_continents != null) {
            //display continents
            for (Continent l_continent : l_continents) {
                int l_minY = l_continent.getCountries().get(0).getYCoordinates();
                int l_midX = 0;
                for (Country l_country : l_continent.getCountries()) {
                    if (l_minY > l_country.getYCoordinates())
                        l_minY = l_country.getYCoordinates();

                    l_midX += l_country.getXCoordinates();
                }
                l_midX /= l_continent.getCountries().size();
                String l_label =l_continent.getName();

                int l_xLablePos = (int)(l_midX * d_zoomScale) + d_cachedDragDelta.x;
                int l_yLabelPos = (int)(((l_minY) + d_DELTA - 40) * d_zoomScale) + d_cachedDragDelta.y;

                p_graphics.setFont(new Font("default", Font.BOLD, 12));
                p_graphics.drawString(l_label, l_xLablePos, l_yLabelPos);
            }
        }
    }

    /**
     * gets a color for country labels using the current view
     * enum
     * @return a Color representing view mode.
     */
    private Color getCountryLabelColor(){
        switch (d_currViewMode){
            case CountryID: return Color.white;
            case CountryName: return Color.CYAN;
            case PlayerOwnerShip: return Color.yellow;
            case ArmyValue: return Color.red;
        }
        return Color.yellow;
    }

    /**
     * produces a label according to the view type
     * @return a string representing the label.
     */
    private String getLabel(Country p_country){
        switch (d_currViewMode){
            case CountryID: return p_country.getDId()+"";
            case CountryName: return p_country.getName();
            case PlayerOwnerShip: return getPlayerNameUsingCountry(p_country);
            case ArmyValue: return p_country.getArmy()+"";
        }
        return "";
    }

    /**
     * resolves a country to its owner.
     * @param p_country the country to get ownership off.
     * @return a string with player-name if available, else 'N/A'
     */
    private String getPlayerNameUsingCountry(Country p_country){
        if(PlayerHandler.getGamePlayers().isEmpty())
            return "N/A";

        for(Player l_player : PlayerHandler.getGamePlayers()){
            if(l_player.getCountriesOwned().contains(p_country))
                return l_player.getPlayerName();
        }
        return "N/A";
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        float l_prevZoom = d_zoomScale;
        d_zoomScale += (-1 * e.getWheelRotation() * 0.1f) * d_zoomMultiplier;
        if(d_zoomScale > ZOOM_LIMIT_MAX)
            d_zoomScale = ZOOM_LIMIT_MAX;
        else if(d_zoomScale < ZOOM_LIMIT_MIN)
            d_zoomScale = ZOOM_LIMIT_MIN;

        // only repaint, if this value changes, no-need to repaint again,
        // if the zoom-scale got clamped by new-value.
        if(d_zoomScale != l_prevZoom)
            repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        d_dragCurrPos.setLocation(e.getX(), e.getY());
        d_cachedDragDelta.translate(d_dragCurrPos.x - d_dragStartPos.x, d_dragCurrPos.y - d_dragStartPos.y);
        repaint();
        d_dragStartPos.setLocation(d_dragCurrPos);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        d_dragStartPos.setLocation(e.getX(), e.getY());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        char l_keyChar = e.getKeyChar();
        if(Character.isDigit(l_keyChar)){
            int digit = Integer.parseInt(Character.toString(l_keyChar));
            if(digit > 0 && digit <= ViewMode.values().length){
                d_currViewMode = ViewMode.values()[digit-1];
                repaint();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //nothing here
    }
}