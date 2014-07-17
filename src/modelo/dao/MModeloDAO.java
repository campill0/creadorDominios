package modelo.dao;

import java.io.Serializable;
import java.util.List;


import modelo.MConcepto;
import modelo.MModelo;

import modelo.MReferencia;
import modelo.dao.DAOException;



public interface MModeloDAO extends Serializable{
	public  MModelo create( String nombre, List<MReferencia> referencias,	List<MConcepto> conceptos) throws DAOException;
	public  MModelo create( String nombre) throws DAOException;
	public  MModelo save( MModelo mmodelo) throws DAOException;
	public List findAll()throws DAOException;
	public List getModelos(Long id)throws DAOException;
	public  MModelo find(Long id)throws DAOException;
	public void update( MModelo mc)throws DAOException;
	public void remove( MModelo mc) throws DAOException;
	

}
