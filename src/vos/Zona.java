package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Zona {
	
	//------------------------------------
	//Atributos
	//------------------------------------

	@JsonProperty(value = "id")
	private Long id;
	
	@JsonProperty(value = "abierto")
	private boolean abierto;
	
	@JsonProperty(value = "discapacitados")
	private boolean discapacitados;
	
	@JsonProperty(value = "condiciones")
	private String condiciones;
	
	@JsonProperty(value = "capacidad")
	private Integer capacidad;



	
	
	//------------------------------------
	//Constructor
	//------------------------------------
	
	public Zona(@JsonProperty(value = "id")Long id
				, @JsonProperty(value = "abierto") boolean abierto
				, @JsonProperty(value = "discapacitados")boolean discapacitados
				, @JsonProperty(value = "condiciones") String condiciones
				,@JsonProperty(value = "capacidad")int capacidad) {
		super();
		this.id = id;
		this.abierto = abierto;
		this.discapacitados = discapacitados;
		this.condiciones = condiciones;
		this.capacidad = capacidad;
	}


	//------------------------------------
	//Metodos
	//------------------------------------
	


	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public boolean isAbierto() {
		return abierto;
	}





	public void setAbierto(boolean abierto) {
		this.abierto = abierto;
	}





	public boolean isDiscapacitados() {
		return discapacitados;
	}





	public void setDiscapacitados(boolean discapacitados) {
		this.discapacitados = discapacitados;
	}





	public String getCondiciones() {
		return condiciones;
	}





	public void setCondicones(String condiciones) {
		this.condiciones = condiciones;
	}





	public Integer getCapacidad() {
		return capacidad;
	}





	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}
	

	
	

}