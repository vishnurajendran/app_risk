package common;

/**
 * this interface is meant to be used as an
 * implementation for any factory or instantiater class
 * to provide ISubApplication instances
 * @author vishnurajendran
 */
public interface ISubAppInstantiator {
    /**
     * this method will be return a new instance of
     * the class that implements ISubApplication.
     * @return ISubApplication
     */
    public ISubApplication createInstane();
}
