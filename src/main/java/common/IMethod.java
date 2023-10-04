package common;

/**
 * this interface will act as a callable
 * interface, to invoke methods passed by this.
 *
 * @author vishnurajendran
 */
@FunctionalInterface
public interface IMethod {

    /**
     * This method will be called to invoke the
     * method registered by this functional interface.
     *
     * @param p_cmd the command object, for processing further.
     */
    public void invoke(Command p_cmd);
}
