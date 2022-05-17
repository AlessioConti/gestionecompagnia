package it.prova.gestionecompagnia.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.gestionecompagnia.dao.AbstractMySQLDAO;
import it.prova.gestionecompagnia.model.*;

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO{
	
	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}
	
	public List<Compagnia> list() throws Exception{
		
		if(isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		
		List<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;
		
		try(Statement s = connection.createStatement(); ResultSet rs = s.executeQuery("select * from compagnia;")){
			while(rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("id"));
				result.add(compagniaTemp);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	public Compagnia get(Long idInput) throws Exception{
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");
		
		Compagnia result = null;
		try(PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?;")){
			ps.setLong(1, idInput);
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(rs.getDate("datafondazione"));
					result.setId(rs.getLong("id"));
				}else
					result = null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	public int insert(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");
		
		int result = 0;
		try(PreparedStatement ps = connection.prepareStatement("insert into compagnia(ragione sociale, fatturatoannuo, datafondazione) values(?, ?, ?);")){
			ps.setString(1, compagniaInput.getRagioneSociale());
			ps.setInt(2, compagniaInput.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(compagniaInput.getDataFondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
}
