package modelo.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import modelo.MModelo;
import modelo.Modelo;
import modelo.dao.DAOException;
import modelo.dao.ModeloDAO;




public class JPAModeloDAO implements ModeloDAO {




	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		ArrayList<Modelo> lista= new ArrayList<Modelo>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT mm FROM Modelo mm");
		List <Modelo> mm = query.getResultList();
		em.close();
		 return mm;
		
	}

	@Override
	public Modelo find(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Modelo mm = em.find(Modelo.class, id);
		em.close();
		return mm;
		
	}

	@Override
	public void update(Modelo modelo) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(modelo);
	}

	@Override
	public void remove(Modelo modelo) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Modelo tmpM=em.find(Modelo.class,modelo.getId());
		em.remove(tmpM);
		tx.commit();
		em.close();
	}


	@Override
	public Modelo create(MModelo mmodelo) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		Modelo m = new Modelo();
		m.setMmodelo(mmodelo);
		em.persist(m);
		em.getTransaction().commit();
		em.close();
		
		return m;
	}


	@Override
	public Modelo save(Modelo modelo) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		if(modelo.getId()==null){
			em.persist(modelo);	
		}
		else{
			em.merge(modelo);
		}
		
		em.getTransaction().commit();
		em.close();
		
		return modelo;
	}






	

}
