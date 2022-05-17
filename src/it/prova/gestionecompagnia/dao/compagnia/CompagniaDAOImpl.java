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

public class CompagniaDAOImpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOImpl(Connection connection) {
		super(connection);
	}

	public List<Compagnia> list() throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery("select * from compagnia;")) {
			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagioneSociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDataFondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("id"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?;")) {
			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setRagioneSociale(rs.getString("ragionesociale"));
					result.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					result.setDataFondazione(rs.getDate("datafondazione"));
					result.setId(rs.getLong("id"));
				} else
					result = null;
			}
		} catch (Exception e) {
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
		try (PreparedStatement ps = connection.prepareStatement(
				"insert into compagnia(ragionesociale, fatturatoannuo, datafondazione) values(?, ?, ?);")) {
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

	public int update(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, compagniaInput.getRagioneSociale());
			ps.setInt(2, compagniaInput.getFatturatoAnnuo());
			ps.setDate(3, new java.sql.Date(compagniaInput.getDataFondazione().getTime()));
			ps.setLong(4, compagniaInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int delete(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null || compagniaInput.getId() == null || compagniaInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from compagnia where id=?;")) {
			ps.setLong(1, compagniaInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Compagnia> findByExample(Compagnia compagniaInput) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");

		List<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia temp = null;

		String query = "select * from compagnia where 1=1";

		if (compagniaInput.getRagioneSociale() != null && !compagniaInput.getRagioneSociale().isEmpty())
			query += " and ragione sociale like '" + compagniaInput.getRagioneSociale() + "%'";
		if (compagniaInput.getFatturatoAnnuo() < 0)
			query += " and fatturatoannuo = " + compagniaInput.getFatturatoAnnuo();
		if (compagniaInput.getDataFondazione() != null)
			query += " and datafondazione = '" + new java.sql.Date(compagniaInput.getDataFondazione().getTime());

		query += ";";

		try (Statement s = connection.createStatement()) {
			ResultSet rs = s.executeQuery(query);

			while (rs.next()) {
				temp = new Compagnia();
				temp.setRagioneSociale(rs.getString("ragionesociale"));
				temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
				temp.setDataFondazione(rs.getDate("datafondazione"));
				temp.setId(rs.getLong("id"));
				result.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception{
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		
		if(dataInput == null)
			throw new Exception("Errore! Dato non valido.");
		
		List<Compagnia> compagnieTrovate = new ArrayList<Compagnia>();
		Compagnia temp = null;
		
		try(PreparedStatement ps = connection.prepareStatement("select distinct * from compagnia c inner join impiegato i ON c.id = i.compagnia_id WHERE i.dataassunzione>?;")){
			ps.setDate(1, new java.sql.Date(dataInput.getTime()));
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					temp = new Compagnia();
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(rs.getDate("datafondazione"));
					temp.setId(rs.getLong("id"));
					compagnieTrovate.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return compagnieTrovate;
	}
	
	public List<Compagnia> findAllByRagioneSocialeContiene(String input) throws Exception{
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		
		if(input == null)
			throw new Exception("Errore! Dato non valido.");
		
		List<Compagnia> compagnieTrovateRagione = new ArrayList<Compagnia>();
		Compagnia temp = null;
		
		try(PreparedStatement ps = connection.prepareStatement("select * from compagnia where ragionesociale like ?;")){
			ps.setString(1, "%"+ input + "%");
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					temp = new Compagnia();
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(rs.getDate("datafondazione"));
					temp.setId(rs.getLong("id"));
					compagnieTrovateRagione.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return compagnieTrovateRagione;
	}
	
	public List<Compagnia> findAllByCodiceFiscaleImpiegatoContiene(String input) throws Exception{
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		
		if(input == null)
			throw new Exception("Errore! Dato non valido.");
		
		List<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia temp = null;
		
		try(PreparedStatement ps = connection.prepareStatement("select distinct * from compagnia c INNER JOIN impiegato i ON c.id=i.compagnia_id wherei.codicefiscale like ?;")){
			ps.setString(1, input);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					temp = new Compagnia();
					temp.setRagioneSociale(rs.getString("ragionesociale"));
					temp.setFatturatoAnnuo(rs.getInt("fatturatoannuo"));
					temp.setDataFondazione(rs.getDate("datafondazione"));
					temp.setId(rs.getLong("id"));
					result.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}

