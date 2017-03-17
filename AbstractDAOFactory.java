
public abstract class AbstractDAOFactory {

	public abstract DAO getAdherentsDAO();
	public abstract DAO getLivreDAO();
	public abstract DAO getCommandeDAO();

	/**
	 * Méthode nous permettant de récupérer une factory de DAO
	 * @param type
	 * @return AbstractDAOFactory
	 */
	public static AbstractDAOFactory getFactory(FactoryType type){
		
		if(type.equals(type.DAO_FACTORY)) 
			return new DAOFactory();
		
		if(type.equals(type.XML_DAO_Factory))
			return new XMLDAOFactory();
		
		return null;
	}
	
}