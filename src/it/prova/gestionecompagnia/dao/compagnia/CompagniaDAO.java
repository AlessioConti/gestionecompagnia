package it.prova.gestionecompagnia.dao.compagnia;

import it.prova.gestionecompagnia.model.Compagnia;

import java.util.Date;
import java.util.List;

import it.prova.gestionecompagnia.dao.IBaseDAO;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {
	
	public List<Compagnia> findAllByDataAssunzioneMaggioreDi(Date dataInput) throws Exception;
	public List<Compagnia> findAllByRagioneSocialeContiene (String lettereInput) throws Exception;
	public List<Compagnia> findAllByCodiceFiscaleImpiegatoContiene(String codiceInput) throws Exception;
	public boolean checkSeCiSonoLavoratori(Compagnia compagniaInput) throws Exception;
	
}
