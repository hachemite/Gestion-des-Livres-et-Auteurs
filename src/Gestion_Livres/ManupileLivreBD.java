package Gestion_Livres;

import java.sql.*;
import java.util.ArrayList;
 

public class ManupileLivreBD {
	
	
	Connection conn = null;
	PreparedStatement pstmt;
	String sql =null;

	public ManupileLivreBD() {
		// TODO Auto-generated constructor stub
	}

	
	public void ajouter(Livre livre) throws SQLException {
		
		sql = "INSERT INTO livres (titre,auteur,annee_publication,existe,genre,image) VALUES (?,?,?,?,?,?) ";
		
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, livre.getTitre());
			pstmt.setString(2, livre.getAuteur());
			pstmt.setInt(3, livre.getAnneePublication());
			pstmt.setBoolean(4, livre.isExiste());
			pstmt.setString(5, livre.getGenre());
			pstmt.setString(6, livre.getImage());
			
			
			pstmt.executeUpdate();
			
		}
		 
		
	}
	
    public ArrayList<Livre> getTous() throws SQLException{
    	
    	sql = "SELECT * FROM livres";
    	ArrayList<Livre> livres = new ArrayList<Livre>();
    	
    	try(Connection conn = DatabaseConnection.getConnection();
    			PreparedStatement pstmt = conn.prepareStatement(sql);
    			ResultSet rs =  pstmt.executeQuery()){
    		
    	while(rs.next()) {
    		Livre livre = new Livre();
    		livre.setId(rs.getInt("id"));
    		livre.setTitre(rs.getString("titre"));
    		livre.setAuteur(rs.getString("auteur"));
            livre.setAnneePublication(rs.getInt("annee_publication"));
            livre.setExiste(rs.getBoolean("existe")); 
            livre.setGenre(rs.getString("genre"));
            livre.setImage(rs.getString("image"));
            
    		livres.add(livre);
    	}
    	
    	return livres;
    	
    }

    }
    
    public void modifier(Livre livre) throws SQLException {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, annee_publication = ?, existe = ?, genre = ?, image = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

  
            pstmt.setString(1, livre.getTitre());
            pstmt.setString(2, livre.getAuteur());
            pstmt.setInt(3, livre.getAnneePublication());
            pstmt.setBoolean(4, livre.isExiste());
            pstmt.setString(5, livre.getGenre());
            pstmt.setString(6, livre.getImage());
            pstmt.setInt(7, livre.getId());

            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new SQLException("La modification a échoué, aucun livre trouvé avec l'ID: " + livre.getId());
            }
        }
    }
    
	
	
	public void supprimer(int id) throws SQLException {
		sql = "DELETE FROM livres where id=?";
		
		try(Connection conn  = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1,id);
			pstmt.executeUpdate();
			
		}
		
	}
	
	
	
	
	
	    public ArrayList<Livre> rechercherGeneral(String critere) throws SQLException {
	        String sql = "SELECT * FROM livres WHERE titre LIKE ? OR auteur LIKE ? OR annee_publication LIKE ? OR genre LIKE ?";
	        ArrayList<Livre> livres = new ArrayList<>();
	        
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            String aRechercher = "%" + critere + "%";
	            pstmt.setString(1, aRechercher);
	            pstmt.setString(2, aRechercher);
	            pstmt.setString(3, aRechercher);
	            pstmt.setString(4, aRechercher);

	            
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    livres.add(extraireLivre(rs));
	                }
	            }
	        }
	        return livres;
	    }
	    
	    
	    
	    public ArrayList<Livre> rechercherParTitre(String critere) throws SQLException {
	        String sql = "SELECT * FROM livres WHERE titre LIKE ?";
	        return rechercherParCritere(sql, critere);
	    }
	    
	    public ArrayList<Livre> rechercherParAuteur(String critere) throws SQLException {
	        String sql = "SELECT * FROM livres WHERE auteur LIKE ?";
	        return rechercherParCritere(sql, critere);
	    }
	    
	    public ArrayList<Livre> rechercherParAnnee(int annee) throws SQLException {
	        String sql = "SELECT * FROM livres WHERE annee_publication LIKE ?";
	        return rechercherParCritere(sql, String.valueOf(annee));
	    }
	    
	    public ArrayList<Livre> rechercherParGenre(String critere) throws SQLException {
	        String sql = "SELECT * FROM livres WHERE genre LIKE ?";
	        return rechercherParCritere(sql, critere);
	    }
	    
	    
	    private ArrayList<Livre> rechercherParCritere(String sql, String critere) throws SQLException {
	        ArrayList<Livre> livres = new ArrayList<>();
	        
	        try (Connection conn = DatabaseConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            String searchTerm =  "%" + critere + "%";
	            
	            pstmt.setString(1, searchTerm);

	            
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    livres.add(extraireLivre(rs));
	                }
	            }
	        }
	        return livres;
	    }
	    
	    private Livre extraireLivre(ResultSet rs) throws SQLException {
	        Livre livre = new Livre();
	        livre.setId(rs.getInt("id"));
	        livre.setTitre(rs.getString("titre"));
	        livre.setAuteur(rs.getString("auteur"));
	        livre.setAnneePublication(rs.getInt("annee_publication"));
	        livre.setExiste(rs.getBoolean("existe"));
	        livre.setGenre(rs.getString("genre"));
	        livre.setImage(rs.getString("image"));
	        return livre;
	    }
	}

