package Exception;

/**
 * This class defines user defined exception.
 *
 * @author Rachit
 */
public class InvalidMapException extends Exception {

    /**
     * This method throws user define exception if map is not valid.
     *
     * @param p_message Message for the exception
     */
    public InvalidMapException(String p_message) {
        super(p_message);

    }

}