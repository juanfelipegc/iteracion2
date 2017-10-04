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
import vos.Zona;


@Path("zonas")
public class ZonaService {


	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexión actual.
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
	 * Método que expone servicio REST usando GET que da todas las zonas de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas
	 * @return Json con todas las zonas de la base de datos o json con 
     * el error que se produjo.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getZonas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zonas;
		try {
			zonas = tm.darZonas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}

    /**
     * Método que expone servicio REST usando GET que busca la zona con el id que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas/id/id?id=<<id>>" para la búsqueda"
     * @param id - Id de la zona a buscar que entra en la URL como parámetro 
     * @return Json con la/s zona/s encontrada/s con el id que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{id}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZonaId( @QueryParam("id") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zonas;
		try {
			if (id == null)
				throw new Exception("id de la zona no valido");
			zonas = tm.buscarZonasPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}


    /**
     * Método que expone servicio REST usando POST que agrega la zona que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas/zona
     * @param zona - zona a agregar
     * @return Json con la zona que agregó o Json con el error que se produjo.
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que agrega las zonas que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas/varios
     * @param zonas - zonas a agregar. 
     * @return Json con las zonas que agregó o Json con el error que se produjo.
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(List<Zona> zonas) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZonas(zonas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que actualiza la zona que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas
     * @param zona - zona a actualizar. 
     * @return Json con la zona que actualizó o Json con el error que se produjo.
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que elimina la zona que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/zonas
     * @param zona - zona a aliminar. 
     * @return Json con la zona que elimino o Json con el error que se produjo.
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
}