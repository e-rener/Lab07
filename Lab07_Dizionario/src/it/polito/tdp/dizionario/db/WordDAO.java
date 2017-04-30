package it.polito.tdp.dizionario.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WordDAO {

	/*
	 * Ritorna tutte le parole di una data lunghezza che differiscono per un solo carattere
	 */
	public List<String> getAllSimilarWords(String parola, int numeroLettere) {
		
		String addToSql = " '_"+parola.substring(1, parola.length())+"'";
		for(int i=1; i<numeroLettere-1; i++){
			addToSql += " OR nome LIKE '"+parola.substring(0, i)+"_"+parola.substring(i+1, parola.length())+"'";
		}
		addToSql += " OR nome LIKE '"+parola.substring(0, parola.length()-1)+"_'";
		
		Connection conn = DBConnect.getInstance().getConnection();
		
		String sql = "SELECT nome FROM parola WHERE nome LIKE "+addToSql;
		PreparedStatement st;

		try {

			st = conn.prepareStatement(sql);
			//st.setString(1, addToSql);
			ResultSet res = st.executeQuery();

			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));

			return new ArrayList<String>(parole);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		//System.out.println("WordDAO -- TODO");
	}

	/*
	 * Ritorna tutte le parole di una data lunghezza
	 */
	public List<String> getAllWordsFixedLength(int numeroLettere) {

		Connection conn = DBConnect.getInstance().getConnection();
		String sql = "SELECT nome FROM parola WHERE LENGTH(nome) = ?;";
		PreparedStatement st;

		try {

			st = conn.prepareStatement(sql);
			st.setInt(1, numeroLettere);
			ResultSet res = st.executeQuery();

			List<String> parole = new ArrayList<String>();

			while (res.next())
				parole.add(res.getString("nome"));

			return parole;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
