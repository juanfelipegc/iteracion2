package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {
	
	@JsonProperty(value="fecha")
	private Date fecha;
	
	@JsonProperty(value="hora")
	private Long hora;
	
	@JsonProperty(value="cantidadPersonas")
	private Integer cantidadPersonas;
	
	@JsonProperty(value="id")
	private Long id;
	
	public Reserva(@JsonProperty(value="fecha")Date fecha, @JsonProperty(value="hora")Long hora, @JsonProperty(value="cantidadPersonas")Integer cantidadPersonas, @JsonProperty(value="id")Long id)
	{
		setFecha(fecha);
		setHora(hora);
		setCantidadPersonas(cantidadPersonas);
		setId(id);
	}
	
	/**
	 * @return La fecha.
	 */
	public Date getFecha() {
		return this.fecha;
	}

	/**
	 * @param fecha La fecha nueva.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	/**
	 * @return La hora.
	 */
	public Long getHora() {
		return this.hora;
	}

	/**
	 * @param hora La hora nueva.
	 */
	public void setHora(Long hora) {
		this.hora = hora;
	}

	/**
	 * @return La cantidad de personas.
	 */
	public Integer getCantidadPersonas() {
		return this.cantidadPersonas;
	}

	/**
	 * @param cantidadPersonas La cantidad de personas nueva.
	 */
	public void setCantidadPersonas(Integer cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}
	
	/**
	 * @return El id.
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param idZona El id nuevo.
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
