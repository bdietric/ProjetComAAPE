import java.util.ArrayList;
public class Adherents {
	private long Id;
	private long Id_adherent;
	private String Prenom;
	private String Nom;
	private String Num_telephone;
	private String adresse;
	private String adresse_mail ;
	private ArrayList<Commande> listCommande;
	
	/**Constructors**/
	public Adherents(){
		
	}
	public Adherents(long Id,long Id_adherent,String Prenom,String Nom,String Num_telephone,String adresse,String adresse_mail ,ArrayList<Commande> listCommande ){
		this.Id=Id;
		this.Id_adherent=Id_adherent;
		this.Prenom=Prenom;
		this.Nom=Nom;
		this.Num_telephone=Num_telephone;
		this.adresse_mail=adresse_mail;
		this.listCommande=listCommande;
		this.adresse=adresse;
	}
	/**Controllers**/
				/**Get**/
	public long getId(){
		return Id;
	}
	public void setId(long Id){
		this.Id=Id;
	}
	public long getIdadherent(){
		return this.Id_adherent;
	}
	public String getPrenom(){
		return this.Prenom;
	}
	public String getNom(){
		return this.Nom;
	}
	public String getNum_telephone(){
		return this.Num_telephone;
	}
	public String getadresse(){
		return this.adresse;
	}
	public String getadresse_mail(){
		return this.adresse_mail;
	}
	public ArrayList<Commande> getListCommande(){
		return this.listCommande;
	}
	
				/**Set**/
	public void setPrenom(String Prenom){
		this.Prenom=Prenom;
	}
	public void setNom(String Nom){
		this.Nom=Nom;
	}
	public void setNum_telephone(String Num_telephone){
		this.Num_telephone=Num_telephone;
	}
	public void setadresse(String adresse){
		this.adresse=adresse;
	}
	public void setadresse_mail(String adresse_mail){
		this.adresse_mail=adresse_mail;
	}
	public void setListCommande(ArrayList<Commande> listCommande){
		this.listCommande=listCommande;
	}
	
	
	public void addCommande(Commande com){
		this.listCommande.add(com);
	}
	public Commande getCommande(int indice){
		return this.listCommande.get(indice);
	}
	public String toString(){
		double somme =0;
		StringBuffer s = new StringBuffer("Récapitulatif des commandes de l'adherent");
		for(int i=0;i<listCommande.size();i++){
			s.append("Commande n°"+i);
			s.append(listCommande.get(i).toString());
			somme+=listCommande.get(i).facture();
		}
		s.append("La facture totale est de"+somme+" euros " );
		return new String(s);
	}
}
