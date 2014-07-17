package modelo;

/**
 *
 * @author Francisco Campillo Asensio
 */
public class ModeloException extends Exception {

    /**
     * Creates a new instance of <code>DAOException</code> without detail message.
     */
    public ModeloException() {
    }


    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ModeloException(String msg) {
        super(msg);
    }
}