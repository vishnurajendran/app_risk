package common.Serialisation;
import com.google.gson.Gson;
import java.lang.reflect.Type;

/**
 * @author vishnurajendran
 */
public class JsonSerialisation {
    private static Gson serialiser = new Gson();

    /**
     * private constructor to block
     * instancing of this class
     */
    private JsonSerialisation(){
    }

    /**
     * deserialise a json to an object instance
     * @param jsonText json to de-serialise
     * @param type type of object to return
     * @return an instance loaded with values from the json text
     * @param <T> target type to de-serialise to.
     */
    public static <T> T fromJson(String jsonText, Type type) {
        return serialiser.fromJson(jsonText, type);
    }

    /**
     * converts a java class instance to a json text
     * @param obj the object instance to serialise
     * @return a string containing the json formatted data for the object.
     */
    public static String toJson(Object obj) {
        return serialiser.toJson(obj);
    }

    public static void main(String[] args) {
        TestClass c = new TestClass();
        System.out.println(toJson(c));
    }
}
