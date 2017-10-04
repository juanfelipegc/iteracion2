package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Orden {
	
	@JsonProperty(value="id")
	private long id;
	
	@JsonProperty(value="idUsuario")
	private long idUsuario;
	
	@JsonProperty(value="costo")
	private double costo;
	
	public Orden(@JsonProperty(value="id")long id, @JsonProperty(value="idUsuario")long idUsuario, @JsonProperty(value="costo")double costo){
		setId(id);
		setIdUsuario(idUsuario);
		setCosto(costo);
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
	 * @return El idUsuario asociado.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario El idUsuario a asociar.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}


	/**
	 * @return El costo.
	 */
	public double getCosto() {
		return costo;
	}

	/**
	 * @param costo El costo nuevo.
	 */
	public void setCosto(double costo) {
		this.costo = costo;
	}
}

}
