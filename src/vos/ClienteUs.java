package vos;

public class ClienteUs extends Usuario{
	
	private Integer cedula;
	
	private boolean registrado;
	
	private String password;

	public ClienteUs(long id, String nombre, String correo, String rol, Integer cedula, boolean registrado, String password) {
		super(id, nombre, correo, rol);
		this.cedula = cedula;
		this.registrado = registrado;
		this.password = password;
	}
	
	public Integer getCedula()
	{
		return this.cedula;
	}
	
	public void setCedula(Integer cedula)
	{
		this.cedula = cedula;
	}
	
	public boolean isRegistrado()
	{
		return this.registrado;
	}
	
	public void setRegistrado(boolean registrado)
	{
		this.registrado = registrado;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}

}