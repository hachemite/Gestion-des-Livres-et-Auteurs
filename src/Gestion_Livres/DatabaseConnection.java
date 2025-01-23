package Gestion_Livres;

import java.sql.*;


public class DatabaseConnection {
	
	
	private static final String URL = "jdbc:mysql://localhost:3306/bibliothequedatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "" ;


			

	public static Connection getConnection() throws SQLException {
		Connection myConnection = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}
		catch (ClassNotFoundException e ){
			e.printStackTrace();

		}
		try {
			myConnection = DriverManager.getConnection(URL,USER,PASSWORD);
			System.out.println("GOOD");
			return myConnection ;

		}
		catch(SQLException e) {
			e.printStackTrace();
        	throw new SQLException("MySQL JDBC Driver not found.", e);
        	}
 }
	
	
}
	
//public void afficher() {
//		
//		String requete = "Select * from livres ";
//		Statement st;
//		ResultSet r =null;
//		try {
//			st = (Statement) getConnection().createStatement();
//			r = st.executeQuery(requete);
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	
//	 try {
//		 while(r.next()) {
//			 System.out.println("id: " + r.getString(1) + ", titre: " + r.getString(2) + ", auteur: " + r.getString(3) + ", date_publication: " + r.getString(4)+",email:"  + r.getString(5));
//		 }
//	 } catch (SQLException e) {
//		 e.printStackTrace();
//	 }
//		 
//	 }
//
//public static void main(String[] args) {
//	DatabaseConnection  go = new DatabaseConnection();
//	go .afficher();
//// Replace 'your_table_name' with the name of your table
//}
//}
