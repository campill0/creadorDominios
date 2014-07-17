package modelo.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Modelo;
import modelo.dao.DAOException;



public interface ModeloDAO extends Serializable {
	public List findAll()throws DAOException;
	public Modelo find(Long id)throws DAOException;
	public void update( Modelo modelo)throws DAOException;
	public void remove( Modelo modelo) throws DAOException;
	public Modelo create(MModelo mmodelo) throws DAOException;
	public Modelo save(Modelo modelo) throws DAOException;
	

}
