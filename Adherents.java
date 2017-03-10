

public class Adherents {
	private long Id_adherent;
	private String Prenom;
	private String Nom;
	private String Num_telephone;
	private String adresse;
	private String adresse_mail ;
	private Commande[] commande;
	public Adherents(long Id_adherent,String Prenom,String Nom,String Num_telephone,String adresse_mail ,Commande[] commande ){
		this.Id_adherent=Id_adherent;
		this.Nom=Nom;
		this.Prenom=Prenom;
		this.Num_telephone=Num_telephone;
		this.adresse=adresse;
		this.adresse_mail=adresse_mail;
		Commande[] tab= new Commande[commande.length];
		for(int i=0;i<commande.length;i++){
			tab[i]=commande[i].clone();
		}
		this.commande=tab;
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
}
