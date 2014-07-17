package modelo.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import modelo.MConcepto;
import modelo.MModelo;
import modelo.MPropiedad;
import modelo.MReferencia;
import modelo.Modelo;
import modelo.dao.DAOException;

import modelo.dao.MModeloDAO;




public class JPAMModeloDAO implements MModeloDAO{

	@Override
	public MModelo create(String nombre, List<MReferencia> referencias,
			List<MConcepto> conceptos) throws DAOException {
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		MModelo mm = new MModelo();
		mm.setNombre(nombre);
		
		mm.getmConceptos().addAll(conceptos);
		mm.getmReferencias().addAll(referencias);
		em.persist(mm);
		em.getTransaction().commit();
		em.close();
		
		return mm;
	}


	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		ArrayList<MModelo> lista= new ArrayList<MModelo>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT mm FROM MModelo mm");
		List <MModelo> mm = query.getResultList();
		em.close();
		 return mm;
		
	}

	@Override
	public MModelo find(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		MModelo mm = em.find(MModelo.class, id);
		em.close();
		return mm;
		
	}

	@Override
	public void update(MModelo mc) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(mc);
	}

	@Override
	public void remove(MModelo mm) throws DAOException {
		// TODO Auto-generated method stub
		
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		MModelo metamodelo=em.find(MModelo.class,mm.getId());
		Query query = em.createQuery("SELECT m FROM Modelo m where m.mmodelo = :metamodelo");
		 query.setParameter("metamodelo", metamodelo);
		List <Modelo> modelos = query.getResultList();
		for (Modelo modelo : modelos) {
			em.remove(modelo);
		}
		em.remove(metamodelo);
		tx.commit();
		em.close();
		
		
	}


	@Override
	public MModelo create(String nombre) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		MModelo mm = new MModelo();
		mm.setNombre(nombre);
		em.persist(mm);
		em.getTransaction().commit();
		em.close();
		
		return mm;
	}


	@Override
	public MModelo save(MModelo mmodelo) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		if(mmodelo.getId()==null){
			em.persist(mmodelo);	
		}
		else{
			em.merge(mmodelo);
		}
		
		em.getTransaction().commit();
		em.close();
		
		return mmodelo;
	}


	@Override
	public List getModelos(Long id) throws DAOException {
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		MModelo metamodelo=em.find(MModelo.class,id);
		Query query = em.createQuery("SELECT m FROM Modelo m where m.mmodelo = :metamodelo");
		 query.setParameter("metamodelo", metamodelo);
		List <Modelo> modelos = query.getResultList();
		
		tx.commit();
		em.close();
		return modelos;
	}









	

}
