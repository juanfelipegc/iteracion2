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
import vos.Reserva;


@Path("reservas")
public class ReservaService {


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
	 * Método que expone servicio REST usando GET que da todas las reservas de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas
	 * @return Json con todas las reservas de la base de datos o json con 
     * el error que se produjo.
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getreservas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Reserva> reservas;
		try {
			reservas = tm.darReservas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservas).build();
	}

    /**
     * Método que expone servicio REST usando GET que busca la reserva con el id que entra como parámetro
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas/id/id?id=<<id>>" para la búsqueda"
     * @param id - Id de la reserva a buscar que entra en la URL como parámetro 
     * @return Json con la reserva encontrada con el id que entra como parámetro o json con 
     * el error que se produjo.
     */
	@GET
	@Path( "{id}" )
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getreservaId( @QueryParam("id") Long id) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		Reserva reserva;
		try {
			if (id == null)
				throw new Exception("id de la reserva no valido");
			reserva = tm.buscarReservaPorId(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}


    /**
     * Método que expone servicio REST usando POST que agrega la reserva que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas/reserva
     * @param reserva - reserva a agregar
     * @return Json con la reserva que agregó o Json con el error que se produjo.
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addreserva(Reserva reserva) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	
    /**
     * Método que expone servicio REST usando POST que agrega las reservas que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas/varios
     * @param reservas - reservas a agregar. 
     * @return Json con las reservas que agregó o Json con el error que se produjo.
     */
	@POST
	@Path("/varios")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addreserva(List<Reserva> reservas) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addReservas(reservas);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservas).build();
	}
	
    /**
     * Método que expone servicio REST usando PUT que actualiza la reserva que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas
     * @param reserva - reserva a actualizar. 
     * @return Json con la reserva que actualizó o Json con el error que se produjo.
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatereserva(Reserva reserva) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.updateReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	
    /**
     * Método que expone servicio REST usando DELETE que elimina la reserva que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/RotondAndes/rest/reservas
     * @param reserva - reserva a eliminar. 
     * @return Json con la reserva que elimino o Json con el error que se produjo.
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletereserva(Reserva reserva) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.deleteReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
}