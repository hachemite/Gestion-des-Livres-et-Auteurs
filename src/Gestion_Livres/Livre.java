package Gestion_Livres;



public class Livre {
	  	private int id;
	    private String titre;
	    private String auteur;
	    private int anneePublication;
	    private boolean existe;
	    private String genre;
	    private String image;
	
	//methode pour crétion livre  vide puis ajouter les donné avec set et get
	public Livre() {
	}


	//methode
	public Livre( String titre, String auteur, int anneePublication, boolean existe, String genre,String image) {
		super();
		this.titre = titre;
		this.auteur = auteur;
		this.anneePublication = anneePublication;
		this.existe = existe;
		this.genre = genre;
		this.image = image;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getAuteur() {
		return auteur;
	}


	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}


	public int getAnneePublication() {
		return anneePublication;
	}


	public void setAnneePublication(int anneePublication) {
		this.anneePublication = anneePublication;
	}


	public boolean isExiste() {
		return existe;
	}


	public void setExiste(boolean existe) {
		this.existe = existe;
	}


	public String getGenre() {
		return genre;
	}


	public void setGenre(String genre) {
		this.genre = genre;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}
	
	

}
