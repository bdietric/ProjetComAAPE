public class DAOFactory extends AbstractDAOFactory{

	public DAO<Livre> getLivreDAO(){
		return new LivreDAO();
	}	
	public DAO<Commande> getCommandeDAO(){
		return new CommandeDAO();
	}	
	public DAO<Adherents> getAdherentsDAO(){
		return new AdherentsDAO();
	}
}