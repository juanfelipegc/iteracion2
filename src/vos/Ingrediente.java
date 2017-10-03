package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="precio")
	private int precio;
	
	@JsonProperty(value="id_Ingrediente")
	private Long id;
	
	@JsonProperty(value="traduccion")
	private String traduccion;
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public int getPrecio(){
		return precio;
	}
	
	public void setPrecio(int precio){
		this.precio = precio;
	}
	
	public Long getID(){
		return id;
	}
	
	public void setID(Long id){
		this.id = id;
	}
	
	public String getTraduccion(){
		return traduccion;
	}
	
	public void setTraducion(String traduccion){
		this.traduccion = traduccion;
	}
	
	
}
