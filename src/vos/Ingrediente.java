package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingrediente {

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="id_Ingrediente")
	private Long id;
	
	@JsonProperty(value="traduccion")
	private String traduccion;
	
	public Ingrediente(String nombre, int precio, Long id, String traduccion){
		this.nombre = nombre;
		this.precio = precio;
		this.id = id;
		this.traduccion = traduccion;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public double getPrecio(){
		return precio;
	}
	
	public void setPrecio(double precio){
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
