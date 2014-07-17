package modelo.dao;

import java.io.Serializable;

import modelo.dao.jpa.JPADAOFactory;


public abstract class DAOFactory implements Serializable {
	private static final long serialVersionUID = -7857443584350290761L;

	// M�todos
	public enum Type {
		JPA //,HIBERNATE, JDBC,CASSANDRA,...
	}

	// constantes de tipo de factor�a
	public static DAOFactory getDAOFactory(Type type) throws DAOException {
		switch (type) {
		case JPA: {
			try {
				return new JPADAOFactory();
			} catch (Exception e) {
				throw new DAOException(e.getMessage());
			}
		}
		default:
			return null;
		}
	}
	
	//m�todos DAO
	
		public abstract MModeloDAO getMModeloDAO();

		public abstract ModeloDAO getModeloDAO();
	
		public abstract VideoDAO getVideoDAO();

}
