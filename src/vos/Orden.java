package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Orden {
	
	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="idUsuario")
	private Long idUsuario;
	
	@JsonProperty(value="mesa")
	private Integer mesa;
	
	@JsonProperty(value="costo")
	private double costo;
	
	public Orden(@JsonProperty(value="id")Long id, @JsonProperty(value="idUsuario")Long idUsuario, @JsonProperty(value="mesa")Integer mesa, @JsonProperty(value="costo")double costo){
		setId(id);
		setIdUsuario(idUsuario);
		setMesa(mesa);
		setCosto(costo);
	}
	
	/**
	 * @return El id.
	 */
	public long getId() {
		return this.id;
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
		return this.idUsuario;
	}

	/**
	 * @param idUsuario El idUsuario a asociar.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return La mesa.
	 */
	public Integer getMesa() {
		return this.mesa;
	}

	/**
	 * @param costo La mesa nueva.
	 */
	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	/**
	 * @return El costo.
	 */
	public double getCosto() {
		return this.costo;
	}

	/**
	 * @param costo El costo nuevo.
	 */
	public void setCosto(double costo) {
		this.costo = costo;
	}
}
