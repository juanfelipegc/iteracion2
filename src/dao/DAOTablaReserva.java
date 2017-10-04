package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Reserva;
import vos.Zona;

public class DAOTablaReserva {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOTablaReserva
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaReserva() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	/**
	 * Método que, usando la conexión a la base de datos, saca todas las Reservas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESERVA;
	 * @return Arraylist con las Reservas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Reserva> darReservas() throws SQLException, Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		String sql = "SELECT * FROM RESERVA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Date fecha = rs.getDate("FECHA");
			Long hora = rs.getLong("HORA");
			Integer cantidadPersonas = rs.getInt("CANTIDADPERSONAS");
			Long id = rs.getLong("ID");
			reservas.add(new Reserva(fecha, hora, cantidadPersonas, id));
		}
		return reservas;
	}

	/**
	 * Método que busca la Reserva con el id que entra como parámetro.
	 * @param idBuscado - Id de la Reserva a buscar
	 * @return Reserva encontrada
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Reserva buscarReservaPorId(Long idBuscado) throws SQLException, Exception 
	{
		Reserva reserva = null;

		String sql = "SELECT * FROM RESERVA WHERE ID =" + idBuscado;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Date fecha = rs.getDate("FECHA");
			Long hora = rs.getLong("HORA");
			Integer cantidadPersonas = rs.getInt("CANTIDADPERSONAS");
			Long id = rs.getLong("ID");
			reserva = new Reserva(fecha, hora, cantidadPersonas, id);
		}

		return reserva;
	}

	/**
	 * Método que agrega el Reserva que entra como parámetro a la base de datos.
	 * @param Reserva - el Reserva a agregar. Reserva !=  null
	 * <b> post: </b> se ha agregado el Reserva a la base de datos en la transaction actual. pendiente que el Reserva master
	 * haga commit para que el Reserva baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el Reserva a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addReserva(Reserva Reserva) throws SQLException, Exception {

		String sql = "INSERT INTO RESERVA VALUES (";
		sql += Reserva.getFecha() + ",";
		sql += Reserva.getHora() + ",";
		sql += Reserva.getCantidadPersonas() + ",";
		sql += Reserva.getId() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Método que actualiza el Reserva que entra como parámetro en la base de datos.
	 * @param Reserva - el Reserva a actualizar. Reserva !=  null
	 * <b> post: </b> se ha actualizado el Reserva en la base de datos en la transaction actual. pendiente que el Reserva master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Reserva.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateReserva(Reserva Reserva) throws SQLException, Exception {

		String sql = "UPDATE Reserva SET ";
		sql += "FECHA=" + Reserva.getFecha() + ",";
		sql += "HORA=" + Reserva.getHora() + ",";
		sql += "CANTIDADPERSONAS=" + Reserva.getCantidadPersonas();
		sql += " WHERE ID = " + Reserva.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el Reserva que entra como parámetro en la base de datos.
	 * @param Reserva - el Reserva a borrar. Reserva !=  null
	 * <b> post: </b> se ha borrado el Reserva en la base de datos en la transaction actual. pendiente que el Reserva master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el Reserva.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteReserva(Reserva Reserva) throws SQLException, Exception {

		String sql = "DELETE FROM RESERVA";
		sql += " WHERE ID = " + Reserva.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	
	
	
	
}