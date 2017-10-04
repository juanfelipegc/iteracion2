package vos;

public interface Producto {

	public Long getID();
	
	public void setID(Long id);
	
	public String getNombre();
	
	public void setNombre(String nombre);
	
	public String getDescricon();
	
	public void setDescripcion(String descripcion);
	
	public double getPrecio();
	
	public void setPrecio(double precio);
	
	public String getClasificacion();
	
	public void setClasificacion(String clasificacion);
	
	public boolean getAlcolica();
	
	public void setAlcoholica(boolean alcoholica);
	
	public String getTraduccion();
	
	public void setTraduccion(String traduccion);
	
	public int getTiempoP();
	
	public void setTiempoP(int tiempo);
	
	public double getCostoP();
	
	public void setCostoP(double costo);
}
