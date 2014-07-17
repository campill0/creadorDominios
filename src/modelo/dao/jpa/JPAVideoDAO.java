package modelo.dao.jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import modelo.Video;
import modelo.dao.DAOException;
import modelo.dao.VideoDAO;

public class JPAVideoDAO implements VideoDAO {

	@Override
	public List findAll() throws DAOException {
		// TODO Auto-generated method stub
		ArrayList<Video> lista= new ArrayList<Video>();
		EntityManager em=JPADAOFactory.getEntityManager();
		Query query = em.createQuery("SELECT v FROM Video v");
		List <Video> v = query.getResultList();
		em.close();
		 return v;
		
	}

	@Override
	public Video find(Long id) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		Video v = em.find(Video.class, id);
		em.close();
		return v;
		
	}

	@Override
	public void update(Video video) throws DAOException {
		// TODO Auto-generated method stub
		Util.merge(video);
	}

	@Override
	public void remove(Video video) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Video tmpV=em.find(Video.class,video.getId());
		em.remove(tmpV);
		tx.commit();
		em.close();
	}





	@Override
	public Video save(Video video) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em = JPADAOFactory.getEntityManager();
		em.getTransaction().begin();
		if(video.getId()==null){
			em.persist(video);	
		}
		else{
			em.merge(video);
		}
		
		em.getTransaction().commit();
		em.close();
		
		return video;
	}






	

}
