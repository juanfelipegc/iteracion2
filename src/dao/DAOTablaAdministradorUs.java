package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.AdministradorUs;

public class DAOTablaAdministradorUs {
	
	/**
	 * Arraylits de recursos que se usan para la ejecuciï¿½n de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexiï¿½n a la base de datos
	 */
	private Connection conn;

	/**
	 * Mï¿½todo constructor que crea DAOTablaAdministradorUs
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaAdministradorUs() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Mï¿½todo que cierra todos los recursos que estan enel arreglo de recursos
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
	 * Mï¿½todo que inicializa la connection del DAO a la base de datos con la conexiï¿½n que entra como parï¿½metro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}

	/**
	 * Mï¿½todo que, usando la conexiï¿½n a la base de datos, saca todos los administradores de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ADMINISTRADORUS;
	 * @return Arraylist con los administradores de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<AdministradorUs> darAdministradores() throws SQLException, Exception {
		ArrayList<AdministradorUs> administradores = new ArrayList<AdministradorUs>();

		String sql = "SELECT * FROM ADMINISTRADORUS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			administradores.add(new AdministradorUs(id, nombre, correo, rol));
		}
		return administradores;
	}

	/**
<<<<<<< HEAD
	 * Método que busca el/los administrador/es con el nombre que entra como parámetro.
	 * @param nombreBuscado - Nombre de el/los administrador/es a buscar
=======
	 * Mï¿½todo que busca el/los administrador/es con el nombre que entra como parï¿½metro.
	 * @param name - Nombre de el/los administrador/es a buscar
>>>>>>> 76f180f71017c4c9c0c2a2f3424463b5f11bb0f7
	 * @return ArrayList con los administradores encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<AdministradorUs> buscarAdministradorPorNombre(String nombreBuscado) throws SQLException, Exception {
		ArrayList<AdministradorUs> administradores = new ArrayList<AdministradorUs>();

		String sql = "SELECT * FROM ADMINISTRADORUS WHERE NOMBRE ='" + nombreBuscado + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			administradores.add(new AdministradorUs(id, nombre, correo, rol));
		}
		return administradores;
	}
	
	/**
	 * Mï¿½todo que busca el administrador con el id que entra como parï¿½metro.
	 * @param name - Id de el administrador a buscar
	 * @return administrador encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public AdministradorUs buscarAdministradorPorId(Long idBuscado) throws SQLException, Exception 
	{
		AdministradorUs administrador = null;

		String sql = "SELECT * FROM ADMINISTRADORUS WHERE ID =" + idBuscado;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Long id = rs.getLong("ID");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			administrador = new AdministradorUs(id, nombre, correo, rol);
		}

		return administrador;
	}

	/**
	 * Mï¿½todo que agrega el administrador que entra como parï¿½metro a la base de datos.
	 * @param administrador - el administrador a agregar. administrador !=  null
	 * <b> post: </b> se ha agregado el administrador a la base de datos en la transaction actual. pendiente que el administrador master
	 * haga commit para que el administrador baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el administrador a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addAdministrador(AdministradorUs administradorUs) throws SQLException, Exception {

		String sql = "INSERT INTO ADMINISTRADORUS VALUES (";
		sql += administradorUs.getId() + ",'";
		sql += administradorUs.getNombre() + "','";
		sql += administradorUs.getCorreo() + "','";
		sql += administradorUs.getRol() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Mï¿½todo que actualiza el administrador que entra como parï¿½metro en la base de datos.
	 * @param administrador - el administrador a actualizar. administrador !=  null
	 * <b> post: </b> se ha actualizado el administrador en la base de datos en la transaction actual. pendiente que el administrador master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el administrador.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateAdministrador(AdministradorUs administrador) throws SQLException, Exception {

		String sql = "UPDATE CLIENTEUS SET ";
		sql += "NOMBRE='" + administrador.getNombre() + "',";
		sql += "CORREO='" + administrador.getCorreo() + "',";
		sql += "ROL='" + administrador.getRol() + "'";
		sql += " WHERE ID = " + administrador.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Mï¿½todo que elimina el administrador que entra como parï¿½metro en la base de datos.
	 * @param administrador - el administrador a borrar. administrador !=  null
	 * <b> post: </b> se ha borrado el administrador en la base de datos en la transaction actual. pendiente que el administrador master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el administrador.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteAdministrador(AdministradorUs administrador) throws SQLException, Exception {

		String sql = "DELETE FROM ADMINISTRADORUS";
		sql += " WHERE ID = " + administrador.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public boolean esAdmin(long id) {
		try {
			return buscarAdministradorPorId(id) != null;
		} catch (SQLException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}


	
	
}