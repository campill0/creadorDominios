package modelo.dao.jpa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import modelo.dao.DAOException;






public class Util {
	private enum Tipo { 
		PERSIST, MERGE
	}
	
	public static Object persist(Object ob) throws DAOException {
		return persistGeneric(ob, Tipo.PERSIST);
	}

	public static Object merge(Object ob) throws DAOException {
		return persistGeneric(ob, Tipo.MERGE);
	}
	public static void remove(Object ob){
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		em.remove(ob);
		tx.commit();
		em.close();
	
	}
	private static Object persistGeneric(Object ob, Tipo tipo)
			throws DAOException {
		EntityManager em = JPADAOFactory.getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			switch (tipo) {
			case MERGE:
				em.merge(ob); // Se guarda en la base de datos pero si ya existe se actualiza.
				break;

			case PERSIST:
				em.persist(ob); // Se guarda en la base de datos si ya existe se crea otro.
				break;
			default:
				break;
			}

			tx.commit();
		} catch (Exception e) {
			
			
			tx.rollback();
			em.close();
			
		//	throw new DAOException( 		"Hubo un problema en el update de " + ob.getClass().getCanonicalName());
		}

		em.close();
		return ob;
	}
	public  static void syncronize(){
		EntityManager em=JPADAOFactory.getEntityManager();
		em.getEntityManagerFactory().getCache().evictAll();
	}
	public static void updateQuery(String queryStr) throws DAOException {
		// TODO Auto-generated method stub
		EntityManager em=JPADAOFactory.getEntityManager();
		
		Query query = em.createQuery(queryStr);
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		query.executeUpdate();
		
		try {
			
						tx.commit();
		} catch (Exception e) {
			
			
			tx.rollback();
			em.close();
			
			throw new DAOException( 		"Hubo un problema en el delete/update" );
		}
		em.close();
		
	}
	public static void resetTable(String table) throws DAOException {
		// TODO Auto-generated method stub
		
		updateQuery("DELETE FROM " + table + " table");
		
	}
	public static void resetDb() throws DAOException {
}
	public static void resetDb2() throws DAOException {
		// TODO Auto-generated method stub
//		 resetTable("Usuario");
		
	
		// tu codigo aqui
		
		
		
	}
	
	public static Object find(Class c,Long id){
		
	 
	
	EntityManager em = JPADAOFactory.getEntityManager();
	
	Object ob=em.find(c, id);
	em.close();
	return ob;
	}
	public static Object refresh(Object ob){
		 
			Long id=0L;
			
	//			id = ob.getClass().getDeclaredField("id").getLong(ob);
				try {
					Method method = ob.getClass().getMethod("getId");
					id=(Long) method.invoke(ob);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
			
			EntityManager em = JPADAOFactory.getEntityManager();
			
			ob=em.find(ob.getClass(), id);
			em.close();
			return ob;
	}
	public static Object refresh2(Object ob)
			throws DAOException {
		EntityManager em = JPADAOFactory.getEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			
				em.refresh(ob); // Se guarda en la base de datos pero si ya existe se actualiza.

			tx.commit();
		} catch (Exception e) {
			
			
			tx.rollback();
			if(em.isOpen()){		em.close();}
			
		//	throw new DAOException( 		"Hubo un problema en el update de " + ob.getClass().getCanonicalName());
		}
		if(em.isOpen()){		em.close();}
		return ob;
	}
	
	
}
