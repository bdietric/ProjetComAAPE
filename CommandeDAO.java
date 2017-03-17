
package com.developpez.dao.concret;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.developpez.bean.Developpeur;
import com.developpez.bean.Langage;
import com.developpez.dao.AbstractDAOFactory;
import com.developpez.dao.DAO;
import com.developpez.dao.FactoryType;


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
                                            			"INSERT INTO societe (com_id, com_classe,com_veuxCalculatrice)"+
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
						DAO<Commande> commandeDAO = AbstractDAOFactory    .getFactory(FactoryType.DAO_FACTORY)
                                                                                             .getCommandeDAO();
						book = commandeDAO.create(book);
					}
					
					//On récupère la prochaine valeur de la séquence
					ResultSet result2 = this   .connect
                                                .createStatement(
                                            		ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                            		ResultSet.CONCUR_UPDATABLE
                                                ).executeQuery(
                                                	"SELECT NEXTVAL('j_soc_dev_jsd_id_seq') as id"
                                                );
					if(result2.first()){
						
						long id2 = result2.getLong("id");
						PreparedStatement prepare2 = this .connect
                                                            .prepareStatement(
                                                    			"INSERT INTO j_soc_dev (jsd_id, jsd_soc_k, jsd_dev_k)"+
                                                    			" VALUES(?, ?, ?)"
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
