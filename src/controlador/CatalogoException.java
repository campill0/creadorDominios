package controlador;

/**
 *
 * @author Francisco Campillo Asensio
 */
public class CatalogoException extends Exception {

    /**
     * Creates a new instance of <code>DAOException</code> without detail message.
     */
    public CatalogoException() {
    }


    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public CatalogoException(String msg) {
        super(msg);
    }
}