
public class Commande {
	
	private int classe;
	private boolean Veux_une_Calculatrice;
	private Livre[] liste;
	public Commande(int classe,boolean Veux_une_calculatrice,Livre[] liste){
		this.classe=classe;
		this.Veux_une_Calculatrice=Veux_une_calculatrice;
		Livre[] tab=new Livre[liste.length]; 
		for(int i=0;i<liste.length;i++){
			tab[i]=liste[i].clone();
		}
		this.liste=tab;
	}
	public int getClasse(){
		return this.classe;
	}
	public void setClasse(int classe){
		this.classe=classe;
	}
	public void setVeux_une_calculatrice(boolean calculatrice){
		this.Veux_une_Calculatrice=calculatrice;
	}
	public boolean getVeux_une_calculatrice(){
		return this.Veux_une_Calculatrice;
	}
	public Commande clone(){
		return new Commande(this.classe,this.Veux_une_Calculatrice,this.liste);
	}
	public String toString(){
		StringBuffer s = new StringBuffer("Ceci est une commande pour un élève de classe de "+this.getClasse()+"et il désire la liste des livres suivants:");
		s.append("/n");
		for(int i=0;i<this.liste.length;i++){
			s.append(this.liste[i].toString());
			s.append("/n");
		}
		if(getVeux_une_calculatrice()){
		    s.append(" et il possède une calculatrice.");
		}
		else {
			s.append("il ne possède pas de calculatrice , et il en désire une.");
		}
		return new String(s);
		
	}
	
}
