package it.prova.gestionecompagnia.model;

import java.util.Date;

public class Impiegato {

	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataNascita;
	private Date dataAssunzione;
	private Compagnia compagnia;

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Impiegato(Long id, String nome, String cognome, String codiceFiscale, Date dataNascita, Date dataAssunzione,
			Compagnia compagnia) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
		this.compagnia = compagnia;
	}
	
	public Impiegato(String nome, String cognome, String codiceFiscale, Date dataNascita, Date dataAssunzione) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
	}
	
	public Impiegato(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
	}

	public Compagnia getCompagnia() {
		return compagnia;
	}

	public void setCompagnia(Compagnia compagnia) {
		this.compagnia = compagnia;
	}

	public Impiegato() {
	}

	public Impiegato(String nome, String cognome, Date dataNascita, Date dataAssunzione) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
	}

	public Impiegato(Long id, String nome, String cognome, Date dataNascita, Date dataAssunzione) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.dataAssunzione = dataAssunzione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public Date getDataAssunzione() {
		return dataAssunzione;
	}

	public void setDataAssunzione(Date dataAssunzione) {
		this.dataAssunzione = dataAssunzione;
	}
	
	public String toString() {
		return "Impiegato [ID = "+id+", Nome = "+nome+", Cognome = "+cognome+", Data Nascita = "+dataNascita+", Data Assunzione = "+dataAssunzione+"]";
	}

}
