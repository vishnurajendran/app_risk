import mapEditer.MapLoader;

public class Main {
    public static void main(String[] args) {
        MapLoader l_loader=new MapLoader("ameroki.map");

        System.out.print(l_loader.getMap().toString());

    }
}