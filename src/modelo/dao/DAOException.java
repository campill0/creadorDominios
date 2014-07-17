package modelo.dao;

/**
 *
 * @author Francisco Campillo Asensio
 */
public class DAOException extends Exception {

    /**
     * Crea una nueva instancia de <code>DAOException</code> sin un mensaje detallado.
     */
    public DAOException() {
    }


    /**
     * Construye una instancia de <code>DAOException</code> con un mensaje detallado.
     * @param msg el mensaje detallado.
     */
    public DAOException(String msg) {
        super(msg);
    }
}