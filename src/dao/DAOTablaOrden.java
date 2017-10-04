package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Orden;

public class DAOTablaOrden {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOTablaOrden
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaOrden() {
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
	 * Método que, usando la conexión a la base de datos, saca todas las ordenes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ORDEN;
	 * @return Arraylist con las ordenes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Orden> darOrdenes() throws SQLException, Exception {
		ArrayList<Orden> ordenes = new ArrayList<Orden>();

		String sql = "SELECT * FROM ORDEN";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Integer mesa = rs.getInt("MESA");
			Double costo = rs.getDouble("COSTO");
			ordenes.add(new Orden(id, idUsuario, mesa, costo));
		}
		return ordenes;
	}

	/**
	 * Método que busca la orden con el id que entra como parámetro.
	 * @param name - Id de la orden a buscar
	 * @return orden encontrada
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Orden buscarOrdenPorId(Long idBuscado) throws SQLException, Exception 
	{
		Orden orden = null;

		String sql = "SELECT * FROM ORDEN WHERE ID =" + idBuscado;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id = rs.getLong("ID");
			Long idUsuario = rs.getLong("IDUSUARIO");
			Integer mesa = rs.getInt("MESA");
			Double costo = rs.getDouble("COSTO");
			orden = new Orden(id, idUsuario, mesa, costo);
		}

		return orden;
	}

	/**
	 * Método que agrega el orden que entra como parámetro a la base de datos.
	 * @param orden - el orden a agregar. orden !=  null
	 * <b> post: </b> se ha agregado el orden a la base de datos en la transaction actual. pendiente que el orden master
	 * haga commit para que el orden baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el orden a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addOrden(Orden orden) throws SQLException, Exception {

		String sql = "INSERT INTO ORDEN VALUES (";
		sql += orden.getId() + ",";
		sql += orden.getIdUsuario() + ",";
		sql += orden.getMesa() + ",";
		sql += orden.getCosto() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Método que actualiza el orden que entra como parámetro en la base de datos.
	 * @param orden - el orden a actualizar. orden !=  null
	 * <b> post: </b> se ha actualizado el orden en la base de datos en la transaction actual. pendiente que el orden master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el orden.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateOrden(Orden orden) throws SQLException, Exception {

		String sql = "UPDATE ORDEN SET ";
		sql += "IDUSUARIO=" + orden.getIdUsuario() + ",";
		sql += "MESA=" + orden.getMesa() + ",";
		sql += "COSTO=" + orden.getCosto();
		sql += " WHERE ID = " + orden.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el orden que entra como parámetro en la base de datos.
	 * @param orden - el orden a borrar. orden !=  null
	 * <b> post: </b> se ha borrado el orden en la base de datos en la transaction actual. pendiente que el orden master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el orden.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteOrden(Orden orden) throws SQLException, Exception {

		String sql = "DELETE FROM ORDEN";
		sql += " WHERE ID = " + orden.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	
	
	
	
}