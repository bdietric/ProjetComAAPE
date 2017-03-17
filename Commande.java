import java.util.ArrayList;
public class Commande {
	private int Id=0;
	private int classe=0;
	private boolean Veux_une_Calculatrice=false;
	private ArrayList<Livre> listeLivre=new ArrayList<Livre>();
	/**Constructor*/
	public Commande(){
		
	}
	public Commande(int Id,int classe,boolean Veux_une_calculatrice,ArrayList<Livre> liste){
		this.Id=Id;
		this.classe=classe;
		this.Veux_une_Calculatrice=Veux_une_calculatrice;
		this.listeLivre=liste;
	}
	
	/**Controllers**/
	public int getId(){
		return Id;
	}
	public int getClasse(){
		return this.classe;
	}
	public boolean getVeux_une_calculatrice(){
		return this.Veux_une_Calculatrice;
	}
	public ArrayList<Livre> getListLivre(){
		return this.listeLivre;
	}
	public void setId(int Id){
		this.Id=Id;
	}
	public void setClasse(int classe){
		this.classe=classe;
	}
	public void setVeux_une_calculatrice(boolean calculatrice){
		this.Veux_une_Calculatrice=calculatrice;
	}
	public void setListLivre (ArrayList<Livre> listLivre){
		this.listeLivre=listLivre;
	}
	public void addLivre (Livre book){
		this.listeLivre.add(book);
	}
	
	
	public Commande clone(){
		return new Commande(this.Id,this.classe,this.Veux_une_Calculatrice,this.listeLivre);
	}
	public Livre getLivre(int indice){
		return this.listeLivre.get(indice);
	}

	public double facture(){
		double somme=0;
		for(int i=0;i<listeLivre.size();i++){
			somme+=listeLivre.get(i).getPrix();
		}
		return somme;
	}
	

	public String toString(){
		StringBuffer s = new StringBuffer("Ceci est une commande pour un élève de classe de "+this.getClasse()+"et il désire la liste des livres suivants:");
		s.append("/n");
		for(int i=0;i<this.listeLivre.size();i++){
			s.append(this.listeLivre.get(i).toString());
			s.append("/n");
		}
		if(getVeux_une_calculatrice()){
		    s.append(" et il possède une calculatrice.");
		}
		else {
			s.append("il ne possède pas de calculatrice , et il en désire une.");
		}
		s.append("Le pix total de cette commande est de ");
		s.append(this.facture());
		return new String(s);
		
	}
	
}
