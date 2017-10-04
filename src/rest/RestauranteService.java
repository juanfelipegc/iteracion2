package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Restaurante;

public class RestauranteService {

	/**
	 * Servlet Context de la conexi�n actual
	 */
	@Context
	private ServletContext context;

	/**
	 * Retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	private String doErrorMessage(Exception e) {
		return "{ \"ERROR\": \"" + e.getMessage() + "\"}";
	}


	/**
	 * M�todo que expone servicio REST usando GET que da todos los restaurantes de la
	 * base de datos. 
	 * <b>URL: </b> http://"ip":8080/RotondAndes/rest/entidades/restaurantes
	 * @return Json con todos los restaurantes de la base de datos O json con el error
	 *         que se produjo
	 */
	@GET
	@Path("/restaurantes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Restaurante> restaurantes;
		try {
			restaurantes = tm.darRestaurantes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}
	
	/**
	 * M�todo que expone servicio REST usando GET que el restaurantes con la id que se indica. 
	 * Retorna informaci�n correspondiente al RFC2.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/id
	 * @return Json con el resultado o con el error
	 */
	@GET
	@Path("/restaurantes/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurante(@javax.ws.rs.PathParam("id") String id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());			
		Restaurante restaurante;
		try {
			restaurante = tm.getRestaurante(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
    /**
     * M�todo que expone servicio REST usando POST que agrega el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/id
     * @param exportador - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@POST
	@Path("/restaurantes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRestaurantes(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
	/**
     * M�todo que expone servicio REST usando PUT que agrega el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/id
     * @param exportador - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@PUT
	@Path("/restaurantes/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRestaurante(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}
	
	/**
     * M�todo que expone servicio REST usando DELETE que agrega el restaurantes que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/id
     * @param exportador - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@DELETE
	@Path("/restaurantes/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRestaurante(Restaurante restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());			try {
			tm.deleteRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}
}
