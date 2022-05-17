package it.prova.gestionecompagnia.dao.impiegato;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.gestionecompagnia.dao.AbstractMySQLDAO;
import it.prova.gestionecompagnia.model.Compagnia;
import it.prova.gestionecompagnia.model.Impiegato;

public class ImpiegatoDAOImpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOImpl(Connection connection) {
		super(connection);
	}
	
	@Override
	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		try (Statement s = connection.createStatement(); ResultSet rs = s.executeQuery("select * from impiegato;")) {
			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("nome"));
				impiegatoTemp.setCognome(rs.getString("cognome"));
				impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
				impiegatoTemp.setDataNascita(rs.getDate("datanascita"));
				impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione"));
				result.add(impiegatoTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato impiegatoTemp = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?;")) {
			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("nome"));
					impiegatoTemp.setCognome(rs.getString("cognome"));
					impiegatoTemp.setCodiceFiscale(rs.getString("codicefiscale"));
					impiegatoTemp.setDataNascita(rs.getDate("datanascita"));
					impiegatoTemp.setDataAssunzione(rs.getDate("dataassunzione"));
					impiegatoTemp.setId(rs.getLong("id"));
				} else
					impiegatoTemp = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return impiegatoTemp;
	}

	@Override
	public int update(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE impiegato SET nome=?, cognome=?, codicefiscale=? datanascita=?, dataassunzione=? where id=?;")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(input.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDataAssunzione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Impiegato impiegatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"insert into impiegato(nome, cognome, codicefiscale, datanascita, dataassunzione) values(?, ?, ?, ?, ?);")) {
			ps.setString(1, impiegatoInput.getNome());
			ps.setString(2, impiegatoInput.getCognome());
			ps.setString(3,  impiegatoInput.getCodiceFiscale());
			ps.setDate(4, new java.sql.Date(impiegatoInput.getDataNascita().getTime()));
			ps.setDate(5, new java.sql.Date(impiegatoInput.getDataAssunzione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Impiegato impiegatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null || impiegatoInput.getId() == null || impiegatoInput.getId() < 1)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("delete from compagnia where id=?;")) {
			ps.setLong(1, impiegatoInput.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Impiegato> findByExample(Impiegato impiegatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (impiegatoInput == null)
			throw new Exception("Valore di input non ammesso.");
		
		List<Impiegato> listaImpiegati = new ArrayList<Impiegato>();
		Impiegato temp = null;
		
		String query = "select * from impiegato";
		
		if(impiegatoInput.getNome() != null || !impiegatoInput.getNome().isEmpty())
			query += " and nome like '" +impiegatoInput.getNome()+ "%'";
		if(impiegatoInput.getCognome() != null || !impiegatoInput.getCognome().isEmpty())
			query += " and nome like '" +impiegatoInput.getCognome()+ "%'";
		if(impiegatoInput.getCodiceFiscale() != null || !impiegatoInput.getCodiceFiscale().isEmpty())
			query += " and codicefiscale like '" + impiegatoInput.getCodiceFiscale()+ "%'";
		if(impiegatoInput.getDataNascita() != null)
			query += " and datanascita =" + new java.sql.Date(impiegatoInput.getDataNascita().getTime());
		if(impiegatoInput.getDataAssunzione() != null)
			query += " and dataassunzione =" + new java.sql.Date(impiegatoInput.getDataAssunzione().getTime());
		
		query += ";";
		
		try(Statement s = connection.createStatement()){
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()) {
				temp = new Impiegato();
				temp.setNome(rs.getString("nome"));
				temp.setCognome(rs.getString("cognome"));
				temp.setCodiceFiscale(rs.getString("codicefiscale"));
				temp.setDataNascita(rs.getDate("datanascita"));
				temp.setDataAssunzione(rs.getDate("dataassunzione"));
				temp.setId(rs.getLong("id"));
				listaImpiegati.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listaImpiegati;
	}

	@Override
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");
		
		List<Impiegato> listaImpiegati = new ArrayList<Impiegato>();
		Impiegato temp = null;

		try(PreparedStatement ps = connection.prepareStatement("select * from impiegato i inner join compagnia c ON c.id=i.compagnia_id where c.id=?;")){
			ps.setLong(1, compagniaInput.getId());
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					temp = new Impiegato();
					temp.setNome(rs.getString("nome"));
					temp.setCognome(rs.getString("cognome"));
					temp.setCodiceFiscale(rs.getString("codicefiscale"));
					temp.setDataNascita(rs.getDate("datanascita"));
					temp.setDataAssunzione(rs.getDate("dataassunzione"));
					temp.setId(rs.getLong("id"));
					listaImpiegati.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listaImpiegati;
	}
 
	@Override
	public int countByDataFondazioneCompagniaGreaterThan(Date fondazioneInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (fondazioneInput == null)
			throw new Exception("Valore di input non ammesso.");
		
		int contatoreImpiegati = 0;
		
		try(PreparedStatement ps = connection.prepareStatement("select count * from impiegato i inner join compagnia c on c.id=i.compagnia_id where c.datafondazione=?;")){
			ps.setDate(1, new java.sql.Date(fondazioneInput.getTime()));
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next())
					contatoreImpiegati++;
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return contatoreImpiegati;
	}

	@Override
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (fatturatoInput <= 0)
			throw new Exception("Valore di input non ammesso.");
		
		List<Impiegato> listaCompagniaAltoFatturato = new ArrayList<Impiegato>();
		Impiegato temp = null;
		
		try(PreparedStatement ps = connection.prepareStatement("select * from impiegato i inner join compagnia c ON c.id=i.compagnia_id where c.fatturatoannuo>?;")){
			ps.setInt(1, fatturatoInput);
			
			try(ResultSet rs = ps.executeQuery()){
				while(rs.next()) {
					temp = new Impiegato();
					temp.setNome(rs.getString("nome"));
					temp.setCognome(rs.getString("cognome"));
					temp.setCodiceFiscale(rs.getString("codicefiscale"));
					temp.setDataNascita(rs.getDate("datanascita"));
					temp.setDataAssunzione(rs.getDate("dataassunzione"));
					temp.setId(rs.getLong("id"));
					listaCompagniaAltoFatturato.add(temp);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return listaCompagniaAltoFatturato;
	}

	@Override
	public List<Impiegato> findAllErroriAssunzione() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");
		
		List<Impiegato> impiegatiErrore = new ArrayList<Impiegato>();
		Impiegato temp = null;
		
		try(PreparedStatement ps = connection.prepareStatement("select * from impiegato i inner join compagnia c on c.id=i.compagnia_id where i.dataassunzione < c.datafondazione;"); ResultSet rs = ps.executeQuery()){
			while(rs.next()) {
				temp = new Impiegato();
				temp.setNome(rs.getString("nome"));
				temp.setCognome(rs.getString("cognome"));
				temp.setCodiceFiscale(rs.getString("codicefiscale"));
				temp.setDataNascita(rs.getDate("datanascita"));
				temp.setDataAssunzione(rs.getDate("dataassunzione"));
				temp.setId(rs.getLong("id"));
				impiegatiErrore.add(temp);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return impiegatiErrore;
	}

}
