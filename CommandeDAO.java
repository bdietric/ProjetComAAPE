


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;




public class CommandeDAO extends DAO<Commande>{
	public Commande create(Commande obj) {
		try{
			
			//Vu que nous sommes sous postgres, nous allons chercher manuellement
			//la prochaine valeur de la séquence correspondant à l'id de notre table
			ResultSet result = this	.connect
                                    .createStatement(
                                    		ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                    		ResultSet.CONCUR_UPDATABLE
                                    ).executeQuery(
                                    		"SELECT NEXTVAL('commande_com_id_seq') as id"
                                    );
			if(result.first()){
				long id = result.getLong("id");
				PreparedStatement prepare = this    .connect
                                                    .prepareStatement(
                                            			"INSERT INTO scommande (com_id, com_classe,com_veuxCalculatrice)"+
                                            			"VALUES(?, ?, ?)"
                                            		);
				
				prepare.setLong(1, id);
				prepare.setInt(2, obj.getClasse());;
				prepare.setBoolean(3, obj.getVeux_une_calculatrice());
				prepare.executeUpdate();
				
				//Maintenant, nous devons créer les liens vers les développeurs
				//Si le développeur n'existe pas en base, on le créé
				for(Livre book : obj.getListLivre()){
					if(book.getId() == 0){
						DAO<Livre> livreDAO = AbstractDAOFactory    .getFactory(FactoryType.DAO_FACTORY)
                                                                                             .getLivreDAO();
						book = livreDAO.create(book) ;
					}
					
					//On récupère la prochaine valeur de la séquence
					ResultSet result2 = this   .connect
                                                .createStatement(
                                            		ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                            		ResultSet.CONCUR_UPDATABLE
                                                ).executeQuery(
                                                	"SELECT NEXTVAL('j_com_liv_jsd_id_seq') as id"
                                                );
					if(result2.first()){
						
						long id2 = result2.getLong("id");
						PreparedStatement prepare2 = this .connect
                                                            .prepareStatement(
                                                    			"INSERT INTO j_com_liv (jsd_id, jsd_com_k, jsd_liv_k)"+
                                                    			" VALUES(?, ?, ?)"//je ne sais pas ce que veut dire jsd par rapporrt au site que j'utilise
                                                    		);
						prepare2.setLong(1, id2);
						prepare2.setLong(2, id);
						prepare2.setLong(3, book.getId());
						prepare2.executeUpdate();
					}
				}
				
				obj = this.find(id);	
				
			}
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
		
	}
	public Commande find(long id) {
		Commande commande = new Commande();                
        
        try {
                ResultSet result = this .connect
                                        .createStatement(
                                             ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                             ResultSet.CONCUR_UPDATABLE
                                        ).executeQuery(
                                            "select * from commande "+
                                            " left join j_com_liv on jsd_com_k = com_id AND com_id = "+ id +
                                            " inner join developpeur on jsd_dev_k = dev_id"
                                        );

                if(result.first()){
                	LivreDAO livDao = new LivreDAO();
                    ArrayList<Livre> listLivre = new ArrayList<Livre>();
                    
                    result.beforeFirst();
                    while(result.next() && result.getLong("jsd_liv_k") != 0)
                    	listLivre.add(livDao.find(result.getLong("jsd_liv_k")));
                    
                    result.first();
                    commande = new Commande(id, result.getInt("com_classe"),result.getBoolean("com_veuxCalculatrice"),listLivre);
                }
        } catch (SQLException e) {
                e.printStackTrace();
        }
        return commande;
	}
	
public Commande update(Commande obj) {
		
		try{
			
			//On met à jours la liste des développeurs au cas ou
			PreparedStatement prepare = this .connect
											 .prepareStatement(
                                            	"UPDATE commande SET com_classe = '"+ obj.getClasse() +"'"+
                                            	" WHERE com_id = " + obj.getId()
                                            );
			
			prepare.executeUpdate();
			
			//Maintenant, nous devons créer les liens vers les développeurs
			//Si le développeur n'existe pas en base, on le créé
			for(Livre liv : obj.getListLivre()){
				
				DAO<Livre> livreDAO = AbstractDAOFactory    .getFactory(FactoryType.DAO_FACTORY)
                                                                                    .getLivreDAO();

				
				//Si l'objet n'existe pas, on le créé avec sa jointure
				if(liv.getId() == 0){
					
					liv = livreDAO.create(liv);

					//On récupère la prochaine valeur de la séquence
					ResultSet result2 = this   .connect
                                               .createStatement(
                                            		ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                            		ResultSet.CONCUR_UPDATABLE
                                                ).executeQuery(
                                            		"SELECT NEXTVAL('j_com_liv_jsd_id_seq') as id"
                                                );
					if(result2.first()){
					
						long id2 = result2.getLong("id");
						PreparedStatement prepare2 = this .connect
														  .prepareStatement(
                                                            	"INSERT INTO j_com_liv (jsd_id, jsd_com_k, jsd_liv_k)"+
                                                            	"VALUES(?, ?, ?)"
                                                            );
						prepare2.setLong(1, id2);
						prepare2.setLong(2, obj.getId());
						prepare2.setLong(3, liv.getId());
						prepare2.executeUpdate();
					}
					
				}
				else{
					livreDAO.update(liv);
				}
				
			}
			
			obj = this.find(obj.getId());
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return obj;
		
	}
	public void delete(Commande obj) {
		
		try	{
			
			this.connect
	            .createStatement(
	            	ResultSet.TYPE_SCROLL_INSENSITIVE, 
	            	ResultSet.CONCUR_UPDATABLE
	             ).executeUpdate(
	            	"DELETE FROM j_com_liv WHERE jsd_com_k = " + obj.getId()
	             );
			this.connect
	            .createStatement(
	                ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                ResultSet.CONCUR_UPDATABLE
	            ).executeUpdate(
	                "DELETE FROM commande WHERE com_id = " + obj.getId()
	            );
	
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}
}
	

