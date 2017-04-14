

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class LivreDAO extends DAO<Livre> {
	public Livre create(Livre obj) {
		try {
			 
			//Vu que nous sommes sous postgres, nous allons chercher manuellement
			//la prochaine valeur de la séquence correspondant à l'id de notre table
			ResultSet result = this	.connect
                                    .createStatement(
                                    		ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                    		ResultSet.CONCUR_UPDATABLE
                                    ).executeQuery(
                                    		"SELECT NEXTVAL('livre_liv_id_seq') as id"
                                    );
			if(result.first()){
				long id = result.getLong("id");
    			PreparedStatement prepare = this	.connect
                                                    .prepareStatement(
                                                    	"INSERT INTO langage (liv_id, liv_prix,liv_etat,liv_reference,liv_enStock) VALUES(?, ?, ?, ? ,?)"
                                                    );
				prepare.setLong(1, id);
				prepare.setDouble(2, obj.getPrix());
				prepare.setInt(3,obj.getEtat());
				prepare.setLong(4,obj.getReference());
				prepare.setBoolean(5, obj.getEnStock());
				
				prepare.executeUpdate();
				obj = this.find(id);	
				
			}
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	    return obj;
	}
	public Livre find(long id) {
		Livre book = new Livre();
		try {
            ResultSet result = this .connect
                                    .createStatement(
                                            	ResultSet.TYPE_SCROLL_INSENSITIVE, 
                                                ResultSet.CONCUR_UPDATABLE
                                             ).executeQuery(
                                                "SELECT * FROM livre WHERE liv_id = " + id
                                             );
            if(result.first())
            		book = new Livre(
                                        id, 
                                        result.getDouble("liv_prix"),
                                        result.getInt("liv_etat"),
                                        result.getLong("liv_reference"),
                                        result.getBoolean("liv_enStock")
                                    );
            
		    } catch (SQLException e) {
		            e.printStackTrace();
		    }
		   return book;

	}
	public Livre update(Livre obj) {
		try {
			
            this .connect	
                 .createStatement(
                	ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
                 ).executeUpdate(
                	"UPDATE langage SET liv_prix = '" + obj.getPrix() + "',"+
                	"liv_etat = '" +obj.getEtat() + "',"+
                	"liv_reference = '" + obj.getReference() + "',"+
                	"liv_enStock = '" +obj.getEnStock()+ "',"+
                	" WHERE liv_id = " + obj.getId()
                 );
		
		obj = this.find(obj.getId());
    } catch (SQLException e) {
            e.printStackTrace();
    }
    
    return obj;
	}
	public void delete(Livre obj) {
		try {
			
                this    .connect
                    	.createStatement(
                             ResultSet.TYPE_SCROLL_INSENSITIVE, 
                             ResultSet.CONCUR_UPDATABLE
                        ).executeUpdate(
                             "DELETE FROM langage WHERE liv_id = " + obj.getId()
                        );
			
	    } catch (SQLException e) {
	            e.printStackTrace();
	    }
	}

}
