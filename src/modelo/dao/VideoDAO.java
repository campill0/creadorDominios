package modelo.dao;

import java.io.Serializable;
import java.util.List;

import modelo.Video;



public interface VideoDAO extends Serializable {
	public List findAll()throws DAOException;
	public Video find(Long id)throws DAOException;
	public void update( Video video)throws DAOException;
	public void remove( Video video) throws DAOException;
	public Video save(Video video) throws DAOException;
	

}
