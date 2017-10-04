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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Orden;


@Path("ordenes")
public class OrdenService {


	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	/**
	 * Método que expone servicio REST usando GET que da todas las ordenes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ordenes
	 * @return Json con todas las ordenes de la base de datos o json con 
     * el error que se produjo.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getOrdens() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Orden> Ordenes;
		try {
			Ordenes = tm.darOrdenes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(Ordenes).build();
	}

    /**
     * Método que expone servicio REST usando GET que busca la orden con el id que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ordenes/id/id?id=<<id>>" para la búsqueda"
     * @param id - Id de la Orden a buscar que entra en la URL como parámetro 
     * @return Json con la orden encontrada con el id que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{id}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getOrdenId( @QueryParam("id") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Orden orden;
		try {
			if (id == null)
				throw new Exception("id de la Orden no valido");
			orden = tm.buscarOrdenPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(orden).build();
	}


    /**
     * Método que expone servicio REST usando POST que agrega la Orden que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ordenes/orden
     * @param Orden - Orden a agregar
     * @return Json con la Orden que agregó o Json con el error que se produjo.
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOrden(Orden orden) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addOrden(orden);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(orden).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que agrega las ordenes que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ordenes/varios
     * @param ordenes - Ordenes a agregar. 
     * @return Json con las Ordens que agregó o Json con el error que se produjo.
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addOrden(List<Orden> ordenes) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addOrdenes(ordenes);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ordenes).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que actualiza la orden que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ordenes
     * @param orden - Orden a actualizar. 
     * @return Json con la Orden que actualizó o Json con el error que se produjo.
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateOrden(Orden orden) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateOrden(orden);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(orden).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que elimina la orden que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ordenes
     * @param Orden - Orden a eliminar. 
     * @return Json con la orden que elimino o Json con el error que se produjo.
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOrden(Orden orden) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteOrden(orden);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(orden).build();
	}
}