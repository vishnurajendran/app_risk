package entity;

import entity.Country;
import entity.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    /**
     * this test aims at verifying if the calculation
     * of re-inforcements is accurate
     */
    @Test
    void assignReinforcementsToPlayer() {
        Player player = new Player(PlayerHandler.generatePlayerId(),"test-player", null);

        //no countries, should return 3 since max(0,floor(0/3)) = 3
        player.assignReinforcementsToPlayer();
        assertEquals(player.getAvailableReinforcements(), 3);

        //10 countries, should return 3 since max(0,floor(15/3)) = 5
        for (int i = 0; i < 15; i++) {
            player.assignCountry(new Country(i, "test-country" + i, 0), 0);
        }
        player.assignReinforcementsToPlayer();
        assertEquals(player.getAvailableReinforcements(), 5);
    }
}