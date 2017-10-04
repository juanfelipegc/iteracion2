package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.RestauranteUs;

public class DAOTablaRestauranteUs {
	
	/**
	 * Arraylits de recursos que se usan para la ejecuci�n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexi�n a la base de datos
	 */
	private Connection conn;

	/**
	 * M�todo constructor que crea DAOTablaRestaurante
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestauranteUs() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * M�todo que cierra todos los recursos que estan enel arreglo de recursos
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
	 * M�todo que inicializa la connection del DAO a la base de datos con la conexi�n que entra como par�metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	/**
	 * M�todo que, usando la conexi�n a la base de datos, saca todos los restaurantes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESTAURANTEUS;
	 * @return Arraylist con los restaurantes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<RestauranteUs> darRestaurantesUs() throws SQLException, Exception {
		ArrayList<RestauranteUs> restaurantesUs = new ArrayList<RestauranteUs>();

		String sql = "SELECT * FROM RESTAURANTEUS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			restaurantesUs.add(new RestauranteUs(id, nombre, correo, rol));
		}
		return restaurantesUs;
	}

	/**
	 * M�todo que busca el/los restaurantes con el nombre que entra como par�metro.
	 * @param nombreBuscado - Nombre de el/los restaurantes a buscar
	 * @return ArrayList con los restaurantes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<RestauranteUs> buscarRestauranteUsPorName(String nombreBuscado) throws SQLException, Exception {
		ArrayList<RestauranteUs> restaurantesUs = new ArrayList<RestauranteUs>();

		String sql = "SELECT * FROM RESTAURANTEUS WHERE NOMBRE ='" + nombreBuscado + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			restaurantesUs.add(new RestauranteUs(id, nombre, correo, rol));
		}

		return restaurantesUs;
	}


	/**
	 * M�todo que busca el restaurante con el id que entra como par�metro.
	 * @param idBuscado - Id de el restaurante a buscar
	 * @return restaurante encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public RestauranteUs buscarRestauranteUsPorId(Long idBuscado) throws SQLException, Exception 
	{
		RestauranteUs restauranteUs = null;

		String sql = "SELECT * FROM RESTAURANTEUS WHERE ID =" + idBuscado;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			restauranteUs = new RestauranteUs(id, nombre, correo, rol);
		}

		return restauranteUs;
	}

	/**
	 * M�todo que agrega el restaurante que entra como par�metro a la base de datos.
	 * @param restaurante - el restaurante a agregar. restaurante !=  null
	 * <b> post: </b> se ha agregado el restaurante a la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que el restaurante baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el restaurante a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestauranteUs(RestauranteUs restauranteUs) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTEUS VALUES (";
		sql += restauranteUs.getId() + ",'";
		sql += restauranteUs.getNombre() + "','";
		sql += restauranteUs.getCorreo() + "','";
		sql += restauranteUs.getRol() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * M�todo que actualiza el restaurante que entra como par�metro en la base de datos.
	 * @param restaurante - el restaurante a actualizar. restaurante !=  null
	 * <b> post: </b> se ha actualizado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestauranteUs(RestauranteUs restauranteUs) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTEUS SET ";
		sql += "NOMBRE='" + restauranteUs.getNombre()+ "',";
		sql += "CORREO='" + restauranteUs.getCorreo()+ "',";
		sql += "ROL='" + restauranteUs.getRol() + "'";
		sql += " WHERE ID = " + restauranteUs.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * M�todo que elimina el restaurante que entra como par�metro en la base de datos.
	 * @param restaurante - el restaurante a borrar. restaurante !=  null
	 * <b> post: </b> se ha borrado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestauranteUs(RestauranteUs restauranteUs) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTEUS";
		sql += " WHERE ID = " + restauranteUs.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	public boolean esRestaurante(Long id){
		try {
			return (buscarRestauranteUsPorId(id) != null);
		} catch (Exception e) {
			return false;
		}
	}
	
	
	
	
	
}