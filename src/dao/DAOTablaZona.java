package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Zona;

public class DAOTablaZona {
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOZona
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaZona() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan en el arreglo de recursos
	 * <b>post: </b> Todos los recursos del arreglo de recursos han sido cerrados
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
	 * Método que, usando la conexión a la base de datos, saca todas las zonas de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ZONA;
	 * @return Arraylist con las zonas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> darZonas() throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			boolean abierto = rs.getBoolean("ABIERTO");
			boolean discapacitados = rs.getBoolean("DISCAPACITADOS");
			String condiciones = rs.getString("CONDICIONES");
			Integer capacidad = rs.getInt("CAPACIDAD");
			zonas.add(new Zona(id, abierto, discapacitados, condiciones, capacidad));
		}
		return zonas;
	}
	
	/**
	 * Metodo que busca la/las zonas con el id que entra como parametro.
	 * @param id - Nombre de la/las zonas a buscar
	 * @return ArrayList con las zonas encontradas
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> buscarZonasPorId(Long idBuscado) throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONA WHERE ID =" + idBuscado;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			boolean abierto = rs.getBoolean("ABIERTO");
			boolean discapacitados = rs.getBoolean("DISCAPACITADOS");
			String condiciones = rs.getString("CONDICIONES");
			Integer capacidad = rs.getInt("CAPACIDAD");
			zonas.add(new Zona(id, abierto, discapacitados, condiciones, capacidad));
		}

		return zonas;
	}
	
	/**
	 * Método que agrega la zona que entra como parámetro a la base de datos.
	 * @param zona - la zona a agregar. zona !=  null
	 * <b> post: </b> se ha agregado la zona a la base de datos en la transaction actual. Pendiente que la zona master
	 * haga commit para que la zona baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addZona(Zona zona) throws SQLException, Exception {

		String sql = "INSERT INTO ZONA VALUES ('";
		sql += zona.getId() + "','";
		sql += zona.isAbierto() + "','";
		sql += zona.isDiscapacitados() + "',";
		sql += zona.getCondiciones() + ",";
		sql += zona.getCapacidad() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Método que actualiza la zona que entra como parámetro en la base de datos.
	 * @param zona - la zona a actualizar. zona !=  null
	 * <b> post: </b> se ha actualizado la zona en la base de datos en la transaction actual. Pendiente que la zona master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateZona(Zona zona) throws SQLException, Exception {

		String sql = "UPDATE ZONA SET ";
		sql += "ABIERTO='" + zona.isAbierto() + "',";
		sql += "DISCAPACITADOS='" + zona.isDiscapacitados() + "',";
		sql += "CONDICIONES=" + zona.getCondiciones() + ",";
		sql += "CAPACIDAD=" + zona.getCapacidad();
		sql += " WHERE ID = '" + zona.getId() + "'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina la zona que entra como parametro en la base de datos.
	 * @param zona - la zona a borrar. zona !=  null
	 * <b> post: </b> se ha borrado la zona en la base de datos en la transaction actual. pendiente que la zona master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteVideo(Zona zona) throws SQLException, Exception {

		String sql = "DELETE FROM ZONA";
		sql += " WHERE ID = " + zona.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	
	
	
	
	
}