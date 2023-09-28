package common;

/**
 * this interface will act as a callable
 * interface, to invoke methods passed by this.
 * @author vishnurajendran
 * Dated 26-09-2023
 */
@FunctionalInterface
public interface IMethod {
    public void invoke(Command p_cmd);
}
