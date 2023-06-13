package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map <Integer, Country> countryIdMap) {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Country c = new Country (rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				countryIdMap.put(c.getcCode(), c) ;
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno,Map <Integer, Country> countryIdMap ) {
		List<Border> res = new ArrayList<> ();
		String sql = " select c.year ,c.state1no, c.state1ab, c.state2no, c.state2ab "
				+ "from contiguity as c "
				+ "where c.year <= ? AND c.conttype = 1 "
				+ "order by year ";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c1 = countryIdMap.get(rs.getInt("state1no")) ;
				Country c2 = countryIdMap.get(rs.getInt("state2no")) ;
				int year = rs.getInt("year") ;
				Border b = new Border (c1, c2, year);
				res.add(b);
			}
			
			conn.close();
			return res;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
