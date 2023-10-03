package game;

import application.Application;
import application.GameInstantiatorMock;
import application.MapEditorInstantiatorMock;
import entity.Country;
import org.junit.jupiter.api.BeforeEach;

class PlayerHandlerTest {

    private Player d_player;
    private Country d_country;

    private Application d_app;

    /**
     * This method initializes players to test the functions
     */
    @BeforeEach
    void setUp(){
        d_player = new Player("Soham");
        d_country = new Country(4, "India" , 4);
        d_country.setArmy(4);
        d_app = new Application(new GameInstantiatorMock(), new MapEditorInstantiatorMock());
        d_app.startup();
    }



}