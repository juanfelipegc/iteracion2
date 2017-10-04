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
import vos.Ingrediente;

@Path("ingredientes")
public class IngredienteService {

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
		 * M�todo que expone servicio REST usando GET que da todos los exportadores de la
		 * base de datos. 
		 * <b>URL: </b> http://"ip":8080/RotondAndes/rest/entidades/ingredientes
		 * @return Json con todos los ingrediente de la base de datos O json con el error
		 *         que se produjo
		 */
		@GET
		@Path("/ingredientes")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getIngredientes() {
			RotondAndesTM tm = new RotondAndesTM(getPath());
			List<Ingrediente> ingredientes;
			try {
				ingredientes = tm.getIngredientes();
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(ingredientes).build();
		}
		
		/**
		 * M�todo que expone servicio REST usando GET que el ingrediente con la id que se indica. 
		 * Retorna informaci�n correspondiente al RFC2.
		 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingredientes/id
		 * @return Json con el resultado o con el error
		 */
		@GET
		@Path("/ingredientes/{id}")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response getIngrediente(@javax.ws.rs.PathParam("id") String id) {
			RotondAndesTM tm = new RotondAndesTM(getPath());			
			Ingrediente ingrediente;
			try {
				ingrediente = tm.getIngrediente(id);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(ingrediente).build();
		}
		
	    /**
	     * M�todo que expone servicio REST usando POST que agrega el ingrediente que recibe en Json
	     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingredientes/id
	     * @param exportador - ingrediente a agregar
	     * @return Json con el ingrediente que agrego o Json con el error que se produjo
	     */
		@POST
		@Path("/ingredientes")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response addIngrediente(Ingrediente ingrediente) {
			RotondAndesTM tm = new RotondAndesTM(getPath());			try {
				tm.addIngrediente(ingrediente);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(ingrediente).build();
		}
		
		/**
	     * M�todo que expone servicio REST usando PUT que agrega el ingrediente que recibe en Json
	     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingredientes/id
	     * @param exportador - ingrediente a agregar
	     * @return Json con el ingrediente que agrego o Json con el error que se produjo
	     */
		@PUT
		@Path("/ingredeinte/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response updateIngrediente(Ingrediente ingrediente) {
			RotondAndesTM tm = new RotondAndesTM(getPath());	
			try {
				tm.updateIngrediente(ingrediente);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(ingrediente).build();
		}
		
		/**
	     * M�todo que expone servicio REST usando DELETE que agrega el ingrediente que recibe en Json
	     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/ingredientes/id
	     * @param exportador - ingrediente a agregar
	     * @return Json con el ingrediente que agrego o Json con el error que se produjo
	     */
		@DELETE
		@Path("/ingredeitne/{id}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response deleteIngrediente(Ingrediente ingrediente) {
			RotondAndesTM tm = new RotondAndesTM(getPath());
			try {
				tm.deleteIngrediente(ingrediente);
			} catch (Exception e) {
				return Response.status(500).entity(doErrorMessage(e)).build();
			}
			return Response.status(200).entity(ingrediente).build();
		}

}
