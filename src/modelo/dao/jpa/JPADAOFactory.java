package modelo.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import modelo.dao.DAOFactory;
import modelo.dao.MModeloDAO;
import modelo.dao.ModeloDAO;
import modelo.dao.VideoDAO;

import org.apache.log4j.BasicConfigurator;





public class JPADAOFactory extends DAOFactory {

	public JPADAOFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static EntityManager getEntityManager(){
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("creadorDominios");
		
		EntityManager em = emf.createEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
		
		return em;
	}
	
	
	//DAO methods
	
	
	@Override
	public MModeloDAO getMModeloDAO() {
		return (MModeloDAO) new JPAMModeloDAO();
	} 
	@Override
	public ModeloDAO getModeloDAO() {
		return (ModeloDAO) new JPAModeloDAO();
	}
	
	@Override
	public VideoDAO getVideoDAO() {
		// TODO Auto-generated method stub
		return (VideoDAO) new JPAVideoDAO();
	} 


}
