package com.spring.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

public class Logging {
    
    private Long id;

    public Logging() {
    	
    }
    
    public Logging(Long id, String nomUsuario, String apPaterno, String apMaterno) {
		super();
		this.id = id;
		this.nomUsuario = nomUsuario;
		this.apPaterno = apPaterno;
		this.apMaterno = apMaterno;
	}
    
    public static String[] fields() {
		return new String[] { "id", "nomUsuario", "apPaterno", "apMaterno" };
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomUsuario() {
		return nomUsuario;
	}

	public void setNomUsuario(String nomUsuario) {
		this.nomUsuario = nomUsuario;
	}

	public String getApPaterno() {
		return apPaterno;
	}

	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	public String getApMaterno() {
		return apMaterno;
	}

	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	private String nomUsuario;
    
    private String apPaterno;
    
    private String apMaterno;
}