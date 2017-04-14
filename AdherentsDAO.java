

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class AdherentsDAO extends DAO<Adherents> {
		public Adherents create(Adherents obj) {
			try{
				
				//Vu que nous sommes sous postgres, nous allons chercher manuellement
				//la prochaine valeur de la séquence correspondant à l'id de notre table
				ResultSet result = this	.connect
	                                    .createStatement(
	                                    		ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                    		ResultSet.CONCUR_UPDATABLE
	                                    ).executeQuery(
	                                    		"SELECT NEXTVAL('adherents_adh_id_seq') as id"
	                                    );
				if(result.first()){
					long id = result.getLong("id");
					PreparedStatement prepare = this    .connect
	                                                    .prepareStatement(
	                                            			"INSERT INTO adherents (adh_id, adh_idAdherents,adh_prenom,adh_nom,adh_numTelepone,adh_adresse,adh_adresseMail)"+
	                                            			"VALUES(?, ?, ?, ?, ?, ?, ?)"
	                                            		);
					
					prepare.setLong(1, id);
					prepare.setLong(2, obj.getIdadherent());;
					prepare.setString(3, obj.getPrenom());
					prepare.setString(4, obj.getNom());
					prepare.setString(5, obj.getNum_telephone());
					prepare.setString(6, obj.getadresse());
					prepare.setString(7, obj.getadresse_mail());
					prepare.executeUpdate();
					
					//Maintenant, nous devons créer les liens vers les commandes
					//Si la commande  n'existe pas en base, on le créé
					for(Commande com : obj.getListCommande()){
						if(com.getId() == 0){
							DAO<Commande> commandeDAO = AbstractDAOFactory    .getFactory(FactoryType.DAO_FACTORY)
	                                                                                             .getCommandeDAO();
							com = commandeDAO.create(com) ;
						}
						
						//On récupère la prochaine valeur de la séquence
						ResultSet result2 = this   .connect
	                                                .createStatement(
	                                            		ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            		ResultSet.CONCUR_UPDATABLE
	                                                ).executeQuery(
	                                                	"SELECT NEXTVAL('j_adh_com_jsd_id_seq') as id"
	                                                );
						if(result2.first()){
							
							long id2 = result2.getLong("id");
							PreparedStatement prepare2 = this .connect
	                                                            .prepareStatement(
	                                                    			"INSERT INTO j_adh_com (jsd_id, jsd_adh_k, jsd_com_k)"+
	                                                    			" VALUES(?, ?, ?)"//je ne sais pas ce que veut dire jsd par rapporrt au site que j'utilise
	                                                    		);
							prepare2.setLong(1, id2);
							prepare2.setLong(2, id);
							prepare2.setLong(3, com.getId());
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
		public Adherents find(long id) {
			Adherents adherents = new Adherents();                
	        
	        try {
	                ResultSet result = this .connect
	                                        .createStatement(
	                                             ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                             ResultSet.CONCUR_UPDATABLE
	                                        ).executeQuery(
	                                            "select * from adherents "+
	                                            " left join j_adh_com on jsd_cadh_k = adh_id AND adh_id = "+ id +
	                                            " inner join commande on jsd_com_k = com_id"
	                                        );

	                if(result.first()){
	                	CommandeDAO comDao = new CommandeDAO();
	                    ArrayList<Commande> listCommande = new ArrayList<Commande>();
	                    
	                    result.beforeFirst();
	                    while(result.next() && result.getLong("jsd_com_k") != 0)
	                    	listCommande.add(comDao.find(result.getLong("jsd_com_k")));
	                    
	                    result.first();
	                    adherents = new Adherents(id, result.getLong("com_idAdherents"),result.getString("com_prenom"),result.getString("com_nom"),result.getString("com_numTelephone"),result.getString("com_adresse"),result.getString("com_adresseMail"),listCommande);
	                }
	        } catch (SQLException e) {
	                e.printStackTrace();
	        }
	        return adherents;
		}
		
	public Adherents update(Adherents obj) {
			
			try{
				
				//On met à jours la liste des commandes au cas ou ( je ne sais pas si on peut faire plus 
				//simple que cette implémentation )
				
				PreparedStatement prepare = this .connect
												 .prepareStatement(
	                                            	"UPDATE adherents SET adh_idAdherents = '"+ obj.getIdadherent() +"'"+
	                                                "adh_prenom= '"+obj.getPrenom() + "',"+
	                                                "adh_nom= '"+obj.getNom() + "',"+
	                                                "adh_numTelephone= '"+obj.getNum_telephone() + "',"+
	                                                "adh_adresse= '"+obj.getadresse() + "',"+
	                                                "adh_adresseMail= '"+obj.getadresse_mail() + "',"+
	                                            	" WHERE adh_id = " + obj.getId()
	                                            );
				
				prepare.executeUpdate();
				
				//Maintenant, nous devons créer les liens vers les commandes
				//Si la commande n'existe pas en base, on la créé
				for(Commande com : obj.getListCommande()){
					
					DAO<Commande> commandeDAO = AbstractDAOFactory    .getFactory(FactoryType.DAO_FACTORY)
	                                                                                    .getCommandeDAO();

					
					//Si l'objet n'existe pas, on le créé avec sa jointure
					if(com.getId() == 0){
						
						com = commandeDAO.create(com);

						//On récupère la prochaine valeur de la séquence
						ResultSet result2 = this   .connect
	                                               .createStatement(
	                                            		ResultSet.TYPE_SCROLL_INSENSITIVE, 
	                                            		ResultSet.CONCUR_UPDATABLE
	                                                ).executeQuery(
	                                            		"SELECT NEXTVAL('j_adh_com_jsd_id_seq') as id"
	                                                );
						if(result2.first()){
						
							long id2 = result2.getLong("id");
							PreparedStatement prepare2 = this .connect
															  .prepareStatement(
	                                                            	"INSERT INTO j_adh_com (jsd_id, jsd_adh_k, jsd_com_k)"+
	                                                            	"VALUES(?, ?, ?)"
	                                                            );
							prepare2.setLong(1, id2);
							prepare2.setLong(2, obj.getId());
							prepare2.setLong(3, com.getId());
							prepare2.executeUpdate();
						}
						
					}
					else{
						commandeDAO.update(com);
					}
					
				}
				
				obj = this.find(obj.getId());
				
			}catch(SQLException e){
				e.printStackTrace();
			}
			
			return obj;
			
		}
		public void delete(Adherents obj) {
			
			try	{
				
				this.connect
		            .createStatement(
		            	ResultSet.TYPE_SCROLL_INSENSITIVE, 
		            	ResultSet.CONCUR_UPDATABLE
		             ).executeUpdate(
		            	"DELETE FROM j_adh_com WHERE jsd_adh_k = " + obj.getId()
		             );
				this.connect
		            .createStatement(
		                ResultSet.TYPE_SCROLL_INSENSITIVE, 
		                ResultSet.CONCUR_UPDATABLE
		            ).executeUpdate(
		                "DELETE FROM adherents WHERE adh_id = " + obj.getId()
		            );
		
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		}

}
