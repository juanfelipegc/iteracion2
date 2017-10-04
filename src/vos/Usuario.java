package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public abstract class Usuario {

	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="correo")
	private String correo;
	
	@JsonProperty(value="rol")
	private String rol;
	
	public Usuario(@JsonProperty(value="id")long id, 
			@JsonProperty(value="nombre")String nombre, @JsonProperty(value="correo")String correo,
			@JsonProperty(value="rol")String rol){
		setId(id);
		setNombre(nombre);
		setCorreo(correo);
		setRol(rol);
	}

	/**
	 * @return El id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id El id nuevo.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return El nombre.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre El nombre nuevo.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return El correo.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo El correo nuevo.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * @return El rol.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol El rol nuevo.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
}