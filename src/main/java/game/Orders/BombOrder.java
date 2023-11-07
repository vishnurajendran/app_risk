package game.Orders;
import entity.CardType;
import entity.Country;
import entity.Player;
import entity.RiskMap;


/**
 * Bomb order class used to execute the bomb order.
 */
public class BombOrder extends Order {
    String d_countryName;
    Country d_country;
    Player d_player;
    private Country d_country;
    /**
     * Constructor which create order and initialize i
     * @param p_player Player that want to execute this order
     * @param p_countryName The targetCountry of the order
     */
    public Bomb(Player p_player, String p_countryName) {
        this.d_countryName = p_countryName;
        this.d_player = p_player;
        d_country = new Country();
        d_country = d_country.getDId(p_countryName);

    }
}
