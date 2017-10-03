package vos;

public interface Producto {

	public Long getID();
	
	public void setID(Long id);
	
	public String getNombre();
	
	public void setNombre(String nombre);
	
	public String getDescricon();
	
	public void setDescripcion(String descripcion);
	
	public int getPrecio();
	
	public void setPrecio(int precio);
	
	public int getClasificacion();
	
	public void setClasificacion(int clasificacion);
	
	public boolean getAlcolica();
	
	public void setAlcoholica(boolean precio);
	
	public String getTraduccion();
	
	public void setTraduccion(String traduccion);
	
	public int getTiempoP();
	
	public void setTiempoP(int tiempo);
	
	public int getCostoP();
	
	public void setCostoP(int costo);
}
