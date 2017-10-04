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
import vos.AdministradorUs;
import vos.ClienteUs;


@Path("administradores")
public class AdministradorUsService {


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
	 * Método que expone servicio REST usando GET que da todos los administradores de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores
	 * @return Json con todos los administradores de la base de datos o json con 
     * el error que se produjo.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAdministradoresUs() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<AdministradorUs> administradores;
		try {
			administradores = tm.darAdministradoresUs();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(administradores).build();
	}

	 /**
     * Método que expone servicio REST usando GET que busca el administrador con el id que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores/<<id>>" para la búsqueda"
     * @param id - Id del administrador a buscar que entra en la URL como parámetro 
     * @return Json con el/los administrador/es encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getAdministradorUs( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			AdministradorUs v = tm.buscarAdministradorUsPorId(id);
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Método que expone servicio REST usando GET que busca el administrador con el nombre que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores/nombre/nombre?nombre=<<nombre>>" para la búsqueda"
     * @param nombre - Nombre del administrador a buscar que entra en la URL como parámetro 
     * @return Json con el/los administradores encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getAdministradorUsName( @QueryParam("nombre") String nombre) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<AdministradorUs> administradores;
		try {
			if (nombre == null || nombre.length() == 0)
				throw new Exception("Nombre del administrador no valido");
			administradores = tm.buscarAdministradoresUsPorNombre(nombre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(administradores).build();
	}


    /**
     * Método que expone servicio REST usando POST que agrega el administrador que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores/administrador
     * @param administrador - administrador a agregar
     * @return Json con el administrador que agrego o Json con el error que se produjo.
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAdministradorUs(AdministradorUs administrador) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addAdministradorUs(administrador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(administrador).build();
	}
	
	@POST
	@Path( "{id: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClienteAdministradorUs(@PathParam( "id" ) Long id, ClienteUs cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addClienteAdministradorUs(id,cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que agrega los administradores que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores/varios
     * @param administradores - administradores a agregar. 
     * @return Json con el administrador que agrego o Json con el error que se produjo.
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAdministradorUs(List<AdministradorUs> administradores) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addAdministradoresUs(administradores);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(administradores).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que actualiza el administrador que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores
     * @param administrador - administrador a actualizar. 
     * @return Json con el administrador que actualizo o Json con el error que se produjo.
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAdministradorUs(AdministradorUs administrador) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateAdministradorUs(administrador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(administrador).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que elimina el administrador que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/administradores
     * @param administrador - administrador a aliminar. 
     * @return Json con el administrador que elimino o Json con el error que se produjo.
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAdministradorUs(AdministradorUs administrador) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteAdministradorUs(administrador);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(administrador).build();
	}
}