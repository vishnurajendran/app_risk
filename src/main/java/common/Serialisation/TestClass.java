package common.Serialisation;

import entity.Player;

/**
 * @author vishnurajendran
 */
public class TestClass{
    public int a;
    public boolean b;
    public float c;
    Player player = new Player();

    @Override
    public String toString() {
        return a + " " + b + " " + c;
    }
}