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
	private int precio;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="tiempo")
	private int tiempo;
	
	@JsonProperty(value="clasificacion")
	private int clasificacion;
	
	@JsonProperty(value="traduccion")
	private String traduccion;
	
	@JsonProperty(value="costo")
	private int costo;
	
	
	
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
	public int getPrecio() {
		return precio;
	}

	@Override
	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public int getClasificacion() {
		return clasificacion;
	}

	@Override
	public void setClasificacion(int clasificacion) {
		this.clasificacion = clasificacion;
	}

	@Override
	public boolean getAlcolica() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClasificacion(boolean precio) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTraduccion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTraduccion(String traduccion) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getTiempoP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTiempoP(int tiempo) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCostoP() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCostoP(int costo) {
		// TODO Auto-generated method stub

	}

}
