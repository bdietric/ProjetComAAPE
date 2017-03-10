
public class Livre {
	private int etat;
	private long reference;
	private boolean enStock;
	
	public Livre ( int etat,long reference , boolean enStock){
		this.etat=etat;
		this.reference=reference;
		this.enStock=enStock;
	}
	public int getEtat(){
		return this.etat;
	}
	public void setEtat(int etat){
		this.etat=etat;
	}public long getReference(){
		return this.reference;
	}
	public void setReference(long reference){
		this.reference=reference;
	}
	public boolean getEnStock(){
		return enStock;
	}
	public void setEnStock(boolean enStock){
		this.enStock=enStock;
	}
	public Livre clone(){
		return new Livre(this.etat,this.reference,this.enStock);
	}
	public String toString(){
		return "Le livre de reference " + this.getReference() + " dans l'etat "+this.getEtat() +" et il "+this.getEnStock() +" en Stock";
	}
}
