package it.prova.gestionecompagnia.test;

import it.prova.gestionecompagnia.dao.Constants;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

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
			/*
			testInsertCompagnia(compagniaDAOInstance);
			*/
			testInsertImpiegato(impiegatoDAOInstance);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testInsertCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("testInsertCompagnia inizializzato........");
		Compagnia compagniaProva = new Compagnia("Prova prova1", 12000000, new Date());
		int quantiElementiInseriti = compagniaDAOInstance.insert(compagniaProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertCompagnia FAILED");
		System.out.println("testInsertCompagnia concluso.....");
	}
	
	public static void testInsertImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception {
		System.out.println("testInsertImpiegato inizializzato......");
		Impiegato impiegatoProva = new Impiegato("Alessio", "Conti", "CNTLSS02E10H501D", new Date(), new Date());
		int quantiElementiInseriti = impiegatoDAOInstance.insert(impiegatoProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testInsertImpiegato FAILED");
		System.out.println("testInsertImpiegato concluso.....");
	}
	
	public static void testDeleteCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception {
		System.out.println("testDeleteCompagnia inizializzato......");
		Compagnia compagniaProva = new Compagnia("Prova prova2", 12000000, new Date());
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
		Impiegato impiegatoProva = new Impiegato("Alessio", "Conte", "CNTLSS02E10H501A", new Date(), new Date());
		int quantiElementiInseriti = impiegatoDAOInstance.insert(impiegatoProva);
		if(quantiElementiInseriti < 1)
			throw new RuntimeException("testDeleteImpiegato FAILED: impiegato da rimuovere non inserito");
		int testDelete = impiegatoDAOInstance.delete(impiegatoProva);
		if(testDelete <1)
			throw new RuntimeException("testDeleteImpiegato FAILED: elemento non cancellato");
		System.out.println("testDeleteImpiegato concluso.......");
	}
	
	public static void testFindByIdCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println("testFindByIdCompagnia inizializzato......");
		List<Compagnia> elementiTrovati = compagniaDAOInstance.list();
		
		Compagnia primaCompagnia = elementiTrovati.get(0);
		
		Compagnia elementoDaCercare = compagniaDAOInstance.get(primaCompagnia.getId());
		
		if(elementoDaCercare == null || !elementoDaCercare.getRagioneSociale().equals(primaCompagnia.getRagioneSociale()))
			throw new RuntimeException("testFindByIdCompagnia : FAILED, le ragioni sociali non corrispondono");
		
		System.out.println("testFindByIdCompagnia concluso.......");
	}
	
	public static void testFindByIdImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println("testFindByIdImpiegato inizializzato......");
		List<Impiegato> elementiTrovati = impiegatoDAOInstance.list();
		
		Impiegato primoImpiegato = elementiTrovati.get(0);
		
		Impiegato impiegatoDaCercare = impiegatoDAOInstance.get(primoImpiegato.getId());
		
		if(impiegatoDaCercare == null || !impiegatoDaCercare.getNome().equals(impiegatoDaCercare.getNome()))
			throw new RuntimeException("testFindByIdImpiegato : FAILED, i nomi non corrispondono");
		
		System.out.println("testFindByIdImpiegato concluso......");
	}
	
	public static void testFindByExampleCompagnia(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println("testFindByExampleCompagnia inizializzato.......");
		List<Compagnia> compagnie = compagniaDAOInstance.list();
		if(compagnie.size() < 1)
			throw new RuntimeException("testFindByExampleCompagnia FAILED: non ci sono voci sul db");
		Compagnia compagniaTemplate = new Compagnia("Prova prova2");
		List<Compagnia> compagnieByExample = compagniaDAOInstance.findByExample(compagniaTemplate);
		for(Compagnia compagniaInput : compagnieByExample)
			System.out.println(compagniaInput);
		System.out.println("testFindByExampleCompagnia concluso.......");
	}
	
	public static void testFindByExampleImpiegato(ImpiegatoDAO impiegatoDAOInstance) throws Exception{
		System.out.println("testFindByExampleCompagnia inizializzato.......");
		List<Impiegato> impiegati = impiegatoDAOInstance.list();
		if(impiegati.size() < 1)
			throw new RuntimeException("testFindByExampleCompagnia FAILED: non ci sono voci sul db");
		Impiegato impiegatoTemplate = new Impiegato("Alessio", "Conti");
		List<Impiegato> impiegatoByExample = impiegatoDAOInstance.findByExample(impiegatoTemplate);
		for(Impiegato impiegatoInput : impiegatoByExample)
			System.out.println(impiegatoInput);
		System.out.println("testFindByExampleCompagnia concluso.......");
	}
	
	public static void testFindByDataAssunzioneMaggiore(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println("testFindByDataAssunzioneMaggiore inizializzato......");
		Date dataControllo = new Date();
		List<Compagnia> compagnieTrovate = compagniaDAOInstance.findAllByDataAssunzioneMaggioreDi(dataControllo);
		for(Compagnia compagniaInput : compagnieTrovate)
			System.out.println(compagniaInput);
		System.out.println("testFindByDataAssunzioneMaggiore concluso.......");
	}
	
	public static void testFindAllRagioneSocialeContiene(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println("testFindAllRagioneSocialeContiene inizializzato........");
		String ragioneSocialeRicerca = "prov";
		List<Compagnia> compagniePerRagioneSocialeRicerca = compagniaDAOInstance.findAllByRagioneSocialeContiene(ragioneSocialeRicerca);
		for(Compagnia compagniaInput : compagniePerRagioneSocialeRicerca)
			System.out.println(compagniaInput);
		System.out.println("testFindAllRagioneSocialeContiene concluso......");
	}
	
	public static void testFindAllCodiceFiscaleImpiegatoContiene(CompagniaDAO compagniaDAOInstance) throws Exception{
		System.out.println("testFindAllCodiceFiscaleImpiegatoContiene inizializzato.....");
		String codiceFiscaleRicerca = "H501";
		List<Compagnia> compagniePerCodiceFiscaleImpiegato = compagniaDAOInstance.findAllByCodiceFiscaleImpiegatoContiene(codiceFiscaleRicerca);
		for(Compagnia compagniaInput : compagniePerCodiceFiscaleImpiegato)
			System.out.println(compagniaInput);
		System.out.println("testFindAllCodiceFiscaleImpiegatoContiene concluso.....");
	}
	


}
