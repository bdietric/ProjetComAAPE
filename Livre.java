
public class Livre {
	private long Id;
	private double prix;
	private int etat;
	private long reference;
	private boolean enStock;
	
	/** Constructor **/
	public Livre(){
		
	}
	public Livre (long Id,double prix, int etat,long reference , boolean enStock){
		this.Id=Id;
		this.prix=prix;
		this.etat=etat;
		this.reference=reference;
		this.enStock=enStock;
	}

	/** Controllers **/
	public long getId(){
		return Id;
	}
	public int getEtat(){
		return this.etat;
	}
	public double getPrix(){
		return this.prix;
	}
	public boolean getEnStock(){
		return enStock;
	}
	public long getReference(){
		return this.reference;
	}
	public void setId(long Id){
		this.Id=Id;
	}
	public void setPrix(double prix){
		this.prix=prix;
	}	
	public void setEtat(int etat){
		this.etat=etat;
	}
	public void setReference(long reference){
		this.reference=reference;
	}
	public void setEnStock(boolean enStock){
		this.enStock=enStock;
	}
	
	public Livre clone(){
		return new Livre(this.Id,this.prix,this.etat,this.reference,this.enStock);
	}
	
	public boolean equals(Livre bis){
		return this.getPrix()==bis.getPrix()&&this.getEtat()==bis.getEtat()&&this.getReference()==this.getReference()&&this.getEnStock()==bis.getEnStock();
	}
	
	public String toString(){
		return "Le livre de reference " + this.getReference() + " dans l'etat "+this.getEtat() +" et il "+this.getEnStock() +" en Stock";
	}
}
