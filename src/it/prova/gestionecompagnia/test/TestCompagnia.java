package it.prova.gestionecompagnia.test;

import it.prova.gestionecompagnia.dao.Constants;

import java.sql.Connection;
import java.util.Date;

import it.prova.gestionecompagnia.connection.MyConnection;
import it.prova.gestionecompagnia.dao.compagnia.CompagniaDAO;
import it.prova.gestionecompagnia.dao.compagnia.CompagniaDAOImpl;
import it.prova.gestionecompagnia.dao.impiegato.ImpiegatoDAO;
import it.prova.gestionecompagnia.dao.impiegato.ImpiegatoDAOImpl;
import it.prova.gestionecompagnia.model.Compagnia;
import it.prova.gestionecompagnia.model.Impiegato;

public class TestCompagnia {

	public static void main(String[] args) {

		CompagniaDAO compagniaDAOInstance = null;
		ImpiegatoDAO impiegatoDAOInstance = null;

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			
			compagniaDAOInstance = new CompagniaDAOImpl(connection);
			impiegatoDAOInstance = new ImpiegatoDAOImpl(connection);
			
			testInsertCompagnia(compagniaDAOInstance);
			
			testInsertImpiegato(impiegatoDAOInstance);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("testInsertCompagnia inizializzato........");
		Compagnia compagniaProva = new Compagnia("Prova prova1", 12000000, new Date("2000-10-10"));
		int quantiElementiInseriti = compagniaDAOInstance.insert(compagniaProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia FAILED");
		System.out.println("testInsertCompagnia concluso.....");
	}
	
	public static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("testInsertImpiegato inizializzato......");
		Impiegato impiegatoProva = new Impiegato("Alessio", "Conti", "CNTLSS02E10H501D", new Date("2002-05-10"), new Date("2022-04-28"));
		int quantiElementiInseriti = impiegatoDAOInstance.insert(impiegatoProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertImpiegato FAILED");
		System.out.println("testInsertImpiegato concluso.....");
	}
	
	public static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("testDeleteCompagnia inizializzato......");
		Compagnia compagniaProva = new Compagnia("Prova prova2", 12000000, new Date("1999-10-10"));
		if(compagniaDAOInstance.checkSeCiSonoLavoratori(compagniaProva))
			throw new RuntimeException("ERRORE: ci sono ancora lavoratori nella compagnia, cancella loro prima");
		int quantiElementiInseriti = compagniaDAOInstance.insert(compagniaProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteCompagnia FAILED: impiegato da rimuovere non inserito");
		int testDelete = compagniaDAOInstance.delete(compagniaProva);
		if(testDelete < 1)
			throw new RuntimeException("testDeleteCompagnia FAILED: elemento non cancellato");
		System.out.println("testDeleteCompagnia concluso.......");
	}
	
	public static void testDeleteImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println("testDeleteImpiegato inizializzato.....");
		Impiegato impiegatoProva = new Impiegato("Alessio", "Conte", "CNTLSS02E10H501A", new Date("2002-04-10"), new Date("2022-04-28"));
		int quantiElementiInseriti = impiegatoDAOInstance.insert(impiegatoProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteImpiegato FAILED: impiegato da rimuovere non inserito");
		int testDelete = impiegatoDAOInstance.delete(impiegatoProva);
		if(testDelete <1)
			throw new RuntimeException("testDeleteImpiegato FAILED: elemento non cancellato");
		System.out.println("testDeleteImpiegato concluso.......");
	}
	

}
