package common;

/**
 * this interface will act as a callable
 * interface, to invoke methods passed by this.
 * @author vishnurajendran
 */
@FunctionalInterface
public interface IMethod {
    public void invoke(Command p_cmd);
}
