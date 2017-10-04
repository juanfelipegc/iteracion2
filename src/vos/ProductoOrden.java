package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoOrden implements Producto {

	@JsonProperty(value="id_Producto")
	private Long id;
	
	@JsonProperty(value="alcoholica")
	private boolean alcoholica;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="precio")
	private double precio;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="tiempo")
	private int tiempo;
	
	@JsonProperty(value="clasificacion")
	private String clasificacion;
	
	@JsonProperty(value="traduccion")
	private String traduccion;
	
	@JsonProperty(value="costo")
	private double costo;
	
	@JsonProperty(value="cantidad")
	private int cantidad;
	
	
	public ProductoOrden(boolean alcoholica, int cantidad, String clasificacion,double costo,
			String descripcion,Long id, String nombre,double precio ,int tiempo, String traduccion){
		this.alcoholica = alcoholica;
		this.cantidad = cantidad;
		this.clasificacion = clasificacion;
		this.costo = costo;
		this.descripcion = descripcion;
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.tiempo = tiempo;
		this.traduccion = traduccion;
		
	}
	
	public int getCantidad(){
		return cantidad;
	}
	
	public void setCantidad(int cantidad){
		this.cantidad = cantidad;
	}
	
	@Override
	public Long getID() {
		return id;
	}

	@Override
	public void setID(Long id) {
		this.id = id;

	}

	@Override
	public String getNombre() {
		return nombre;
	}

	@Override
	public void setNombre(String nombre) {
		this.nombre = nombre;

	}

	@Override
	public String getDescricon() {
		return descripcion;
	}

	@Override
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public double getPrecio() {
		return precio;
	}

	@Override
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	@Override
	public String getClasificacion() {
		return clasificacion;
	}

	@Override
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	@Override
	public boolean getAlcolica() {
		return alcoholica;
	}

	@Override
	public void setAlcoholica(boolean alcoholica) {
		this.alcoholica = alcoholica;
	}

	@Override
	public String getTraduccion() {
		return traduccion;
	}

	@Override
	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}

	@Override
	public int getTiempoP() {
		return tiempo;
	}

	@Override
	public void setTiempoP(int tiempo) {
		this.tiempo = tiempo;
	}

	@Override
	public double getCostoP() {
		return costo;
	}

	@Override
	public void setCostoP(double costo) {
		this.costo = costo;
	}

}
