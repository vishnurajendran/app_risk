package common.Serialisation;
import entity.Continent;
import entity.Country;
import entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vishnurajendran
 */
public class TestClass{
    private int a;
    private boolean b;
    private float c;

    private Map<Integer, Country> yo = new HashMap<>();
    private Map<Integer, Continent> yo2 = new HashMap<>();


    Player player = new Player();


    public TestClass(){
        yo2.put(1, new Continent(1, "_", 10));
        yo.put(1, new Country(1,"_1", 1, 100,15 ));
        yo.put(2, new Country(2,"_2", 1, 120,15 ));
        yo.put(3, new Country(3,"_3", 1, 140,15 ));
        yo.put(4, new Country(4,"_4", 1, 160,15 ));
    }

    @Override
    public String toString() {
        return a + " " + b + " " + c;
    }
}