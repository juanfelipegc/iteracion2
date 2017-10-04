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
import vos.Menu;

public class MenuService {

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
	 * M�todo que expone servicio REST usando GET que da todos los menus de la
	 * base de datos. 
	 * <b>URL: </b> http://"ip":8080/RotondAndes/rest/entidades/menus
	 * @return Json con todos los menus de la base de datos O json con el error
	 *         que se produjo
	 */
	@GET
	@Path("/menus")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenus() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Menu> menus;
		try {
			menus = tm.darMenus();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menus).build();
	}
	
	/**
	 * M�todo que expone servicio REST usando GET que el menu con la id que se indica. 
	 * Retorna informaci�n correspondiente al RFC2.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/id
	 * @return Json con el resultado o con el error
	 */
	@GET
	@Path("/menus/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getMenu(@javax.ws.rs.PathParam("id") String id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());			
		Menu menu;
		try {
			menu = tm.getMenu(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
    /**
     * M�todo que expone servicio REST usando POST que agrega el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/id
     * @param exportador - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@POST
	@Path("/menus")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
	/**
     * M�todo que expone servicio REST usando PUT que agrega el menu que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/id
     * @param exportador - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@PUT
	@Path("/menus/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateMenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
	
	/**
     * M�todo que expone servicio REST usando DELETE que agrega el ingrediente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/menus/id
     * @param exportador - menu a agregar
     * @return Json con el menu que agrego o Json con el error que se produjo
     */
	@DELETE
	@Path("/menus/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMenu(Menu menu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());			try {
			tm.deleteMenu(menu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(menu).build();
	}
}
