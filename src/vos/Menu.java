package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Menu {

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="id_menu")
	private Long id;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="producto1")
	private Long idP1;
	
	@JsonProperty(value="producto2")
	private Long idP2;
	
	@JsonProperty(value="producto3")
	private Long idP3;
	
	@JsonProperty(value="producto4")
	private Long idP4;
	
	@JsonProperty(value="producto5")
	private Long idP5;
	
	public Menu(String nombre, Long id, int precio){
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
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
	
	public void setId(Long id){
		this.id = id;
	}
	
	public double getPrecio(){
		return precio;
	}
	
	public void setPrecio(int precio){
		this.precio = precio;
	}
}
