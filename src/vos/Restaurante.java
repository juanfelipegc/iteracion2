package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Restaurante {

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="nombre")
	private Long id;
	
	@JsonProperty(value="nombre")
	private String tipo;
	
	public Restaurante(String nombre, Long id, String tipo){
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public Long getID(){
		return id;
	}
	
	public void setID(Long id){
		this.id = id;
	}
	
	public String getTipo(){
		return tipo;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
}
