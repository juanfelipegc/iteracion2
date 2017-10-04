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
import vos.Producto;

public class ProductoService {

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
	 * M�todo que expone servicio REST usando GET que da todos los productos de la
	 * base de datos. 
	 * <b>URL: </b> http://"ip":8080/RotondAndes/rest/entidades/productos
	 * @return Json con todos los productos de la base de datos O json con el error
	 *         que se produjo
	 */
	@GET
	@Path("/productos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProductos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Producto> productos;
		try {
			productos = tm.darProducto();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(productos).build();
	}
	
	/**
	 * M�todo que expone servicio REST usando GET que el producto con la id que se indica. 
	 * Retorna informaci�n correspondiente al RFC2.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/id
	 * @return Json con el resultado o con el error
	 */
	@GET
	@Path("/productos/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getProducto(@javax.ws.rs.PathParam("id") String id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());			
		Producto producto;
		try {
			producto = tm.getProductos(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
	
    /**
     * M�todo que expone servicio REST usando POST que agrega el producto que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/id
     * @param exportador - producto a agregar
     * @return Json con el producto que agrego o Json con el error que se produjo
     */
	@POST
	@Path("/productos")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addMenu(Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addProducto(producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
	
	/**
     * M�todo que expone servicio REST usando PUT que agrega el producto que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/id
     * @param exportador - producto a agregar
     * @return Json con el producto que agrego o Json con el error que se produjo
     */
	@PUT
	@Path("/productos/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateProducto(Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());	
		try {
			tm.updateProducto(producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
	
	/**
     * M�todo que expone servicio REST usando DELETE que agrega el productos que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/productos/id
     * @param exportador - producto a agregar
     * @return Json con el producto que agrego o Json con el error que se produjo
     */
	@DELETE
	@Path("/productos/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteProducto(Producto producto) {
		RotondAndesTM tm = new RotondAndesTM(getPath());			
		try {
			tm.deleteProducto(producto);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(producto).build();
	}
}
