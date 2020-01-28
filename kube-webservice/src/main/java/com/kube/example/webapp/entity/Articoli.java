package com.kube.example.webapp.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

// Lombok autogenerate
@Data
public class Articoli implements Serializable{
	
	private static final long serialVersionUID = 7361753083273455478L;
	
	private String codArt;
	private String descrizione;	
	private String um;
	private String codStat;
	private Integer pzCart;
	private double pesoNetto;
	private String idStatoArt;
	private Date dataCreaz;
	private double prezzo;

	public Articoli(String codart, String descrizione, String um, Integer pezzi, double pesoNetto, double prezzo) {
		this.codArt = codart;
		this.descrizione = descrizione;
		this.um = um;
		this.pzCart = pezzi;
		this.pesoNetto = pesoNetto;
		this.prezzo = prezzo; 
	}
	
} //end class
