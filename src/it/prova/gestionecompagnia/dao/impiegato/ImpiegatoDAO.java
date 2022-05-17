package it.prova.gestionecompagnia.dao.impiegato;
import it.prova.gestionecompagnia.model.Compagnia;
import it.prova.gestionecompagnia.model.Impiegato;

import java.util.Date;
import java.util.List;

import it.prova.gestionecompagnia.dao.IBaseDAO;

public interface ImpiegatoDAO extends IBaseDAO<Impiegato>{
	
	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception;
	public int countByDataFondazioneCompagniaGreaterThan(Date dataInput) throws Exception;
	public List<Impiegato> findAllByCompagniaConFatturatoMaggioreDi(int fatturatoInput) throws Exception;
	public List<Impiegato> findAllErroriAssunzione()throws Exception;
	
}
