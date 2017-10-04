package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.RestauranteUs;


@Path("restaurantes")
public class RestauranteUsService {


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
	 * Método que expone servicio REST usando GET que da todos los restaurantes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes
	 * @return Json con todos los restaurantes de la base de datos o json con 
     * el error que se produjo.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getRestaurantesUs() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<RestauranteUs> restaurantes;
		try {
			restaurantes = tm.darRestaurantesUs();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}

	 /**
     * Método que expone servicio REST usando GET que busca el restaurante con el id que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/<<id>>" para la búsqueda"
     * @param id - Id del restaurante a buscar que entra en la URL como parámetro 
     * @return Json con el/los restaurante/es encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getRestauranteUs( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			RestauranteUs v = tm.buscarRestauranteUsPorId(id);
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Método que expone servicio REST usando GET que busca el restaurante con el nombre que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/nombre/nombre?nombre=<<nombre>>" para la búsqueda"
     * @param nombre - Nombre del restaurante a buscar que entra en la URL como parámetro 
     * @return Json con el/los restaurantes encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getrestauranteUsName( @QueryParam("nombre") String nombre) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<RestauranteUs> restaurantes;
		try {
			if (nombre == null || nombre.length() == 0)
				throw new Exception("Nombre del restaurante no valido");
			restaurantes = tm.buscarRestauranteUsPorNombre(nombre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}


    /**
     * Método que expone servicio REST usando POST que agrega el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/restaurante
     * @param restaurante - restaurante a agregar
     * @return Json con el restaurante que agrego o Json con el error que se produjo.
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addrestauranteUs(RestauranteUs restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestauranteUs(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que agrega los restaurantes que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes/varios
     * @param restaurantes - restaurantes a agregar. 
     * @return Json con el restaurante que agrego o Json con el error que se produjo.
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addrestauranteUs(List<RestauranteUs> restaurantes) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addRestaurantesUs(restaurantes);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurantes).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que actualiza el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes
     * @param restaurante - restaurante a actualizar. 
     * @return Json con el restaurante que actualizo o Json con el error que se produjo.
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updaterestauranteUs(RestauranteUs restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateRestauranteUs(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que elimina el restaurante que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/restaurantes
     * @param restaurante - restaurante a aliminar. 
     * @return Json con el restaurante que elimino o Json con el error que se produjo.
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleterestauranteUs(RestauranteUs restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteRestauranteUs(restaurante);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(restaurante).build();
	}
}