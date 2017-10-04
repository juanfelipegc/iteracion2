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
import vos.ClienteUs;


@Path("clientes")
public class ClienteUsService {


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
	 * Método que expone servicio REST usando GET que da todos los clienteUs de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
	 * @return Json con todos los clienteUs de la base de datos o json con 
     * el error que se produjo.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getClientesUs() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ClienteUs> clientes;
		try {
			clientes = tm.darClientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}

	 /**
     * Método que expone servicio REST usando GET que busca el clienteUs con el id que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes/<<id>>" para la búsqueda"
     * @param id - Id del clienteUs a buscar que entra en la URL como parámetro 
     * @return Json con el/los clienteUs encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{id: \\d+}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteUs( @PathParam( "id" ) Long id )
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		try
		{
			ClienteUs v = tm.buscarClienteUsPorId(id);
			return Response.status( 200 ).entity( v ).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

    /**
     * Método que expone servicio REST usando GET que busca el clienteUs con el nombre que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes/nombre/nombre?nombre=<<nombre>>" para la búsqueda"
     * @param nombre - Nombre del clienteUs a buscar que entra en la URL como parámetro 
     * @return Json con el/los clienteus encontrados con el nombre que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{nombre}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getClienteUsNombre( @QueryParam("nombre") String nombre) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<ClienteUs> clientes;
		try {
			if (nombre == null || nombre.length() == 0)
				throw new Exception("Nombre del clienteUs no valido");
			clientes = tm.buscarClientesUsPorNombre(nombre);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}


    /**
     * Método que expone servicio REST usando POST que agrega el clienteUs que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes/cliente
     * @param clienteUs - clienteUs a agregar
     * @return Json con el clienteUs que agrego o Json con el error que se produjo.
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClienteUs(ClienteUs cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addClienteUs(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que agrega los clienteus que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes/varios
     * @param clientes - clienteus a agregar. 
     * @return Json con el clienteUs que agrego o Json con el error que se produjo.
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addClienteUs(List<ClienteUs> clientes) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addClientesUs(clientes);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(clientes).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que actualiza el clienteUs que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param clienteUs - clienteUs a actualizar. 
     * @return Json con el clienteUs que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateClienteUs(ClienteUs cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateClienteUs(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que elimina el clienteUs que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/clientes
     * @param clienteUs - clienteUs a eliminar. 
     * @return Json con el clienteUs que elimino o Json con el error que se produjo.
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteClienteUs(ClienteUs cliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteClienteUs(cliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(cliente).build();
	}
}