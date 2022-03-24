package org.generation.blogpessoal.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity // Trasformando em Tabela
@Table(name="tb_postagens")// Nomeia Tabela
public class Postagem {

	@Id // Informa que é um id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto_increment
	private Long id;
	
	@NotNull
	@Size(min=5,max=100)
	private String titulo;
	
	@NotNull
	@Size(min=5,max=100)// Definindo tamanho mínimo e máximo do campo 
	private String texto;
	
	private LocalDateTime data;

	
	// Métodos Getters and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
}