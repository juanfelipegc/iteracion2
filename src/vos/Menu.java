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
	
	public Menu(String nombre, Long id, double precio,Long idP1,Long idP2,Long idP3,Long idP4,Long idP5){
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.idP1 = idP1;
		this.idP2 = idP2;
		this.idP3 = idP3;
		this.idP4 = idP4;
		this.idP5 = idP5;
		
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
	
	public Long getProducto1(){
		return idP1;
	}
	
	public void setProducto1(Long idP1){
		this.idP1 = idP1;
	}
	
	public Long getProducto2(){
		return idP2;
	}
	
	public void setProducto2(Long idP2){
		this.idP2 = idP2;
	}
	
	public Long getProducto3(){
		return idP3;
	}
	
	public void setProducto3(Long idP3){
		this.idP3 = idP3;	
	}
	
	public Long getProducto4(){
		return idP4;
	}
	
	public void setProducto4(Long idP4){
		this.idP4 = idP4;	
	}
	
	public Long getProducto5(){
		return idP5;
	}
	
	public void setProducto5(Long idP5){
		this.idP5 = idP5;	
	}
}
