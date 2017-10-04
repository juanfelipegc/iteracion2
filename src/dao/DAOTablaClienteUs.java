package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ClienteUs;

public class DAOTablaClienteUs {
	
	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOTablaClienteUs
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaClienteUs() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan en el arreglo de recursos
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
	 * Método que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTEUS;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteUs> darClientes() throws SQLException, Exception {
		ArrayList<ClienteUs> clientes = new ArrayList<ClienteUs>();

		String sql = "SELECT * FROM CLIENTEUS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			Integer cedula = rs.getInt("CEDULA");
			boolean registrado = rs.getBoolean("REGISTRADO");
			String password = rs.getString("PASSWORD");
			clientes.add(new ClienteUs(id, nombre, correo, rol, cedula, registrado, password));
		}
		return clientes;
	}

	/**
	 * Método que busca el/los clientes con el nombre que entra como parámetro.
	 * @param nombre - Nombre de el/los clientes a buscar
	 * @return ArrayList con los clientes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteUs> buscarClientePorNombre(String nombreBuscado) throws SQLException, Exception {
		ArrayList<ClienteUs> clientes = new ArrayList<ClienteUs>();

		String sql = "SELECT * FROM CLIENTEUS WHERE NOMBRE ='" + nombreBuscado + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			Integer cedula = rs.getInt("CEDULA");
			boolean registrado = rs.getBoolean("REGISTRADO");
			String password = rs.getString("PASSWORD");
			clientes.add(new ClienteUs(id, nombre, correo, rol, cedula, registrado, password));
		}

		return clientes;
	}

	/**
	 * Método que busca el cliente con el id que entra como parámetro.
	 * @param id - Id de el cliente a buscar
	 * @return cliente encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteUs buscarClientePorId(Long idBuscado) throws SQLException, Exception 
	{
		ClienteUs cliente = null;

		String sql = "SELECT * FROM CLIENTEUS WHERE ID =" + idBuscado;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			Long id = rs.getLong("ID");
			String nombre = rs.getString("NOMBRE");
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			Integer cedula = rs.getInt("CEDULA");
			boolean registrado = rs.getBoolean("REGISTRADO");
			String password = rs.getString("PASSWORD");
			cliente = new ClienteUs(id, nombre, correo, rol, cedula, registrado, password);
		}

		return cliente;
	}
	
	/**
	 * Método que agrega el cliente que entra como parámetro a la base de datos.
	 * @param cliente - el cliente a agregar. cliente !=  null
	 * <b> post: </b> se ha agregado el cliente a la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que el cliente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cliente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCliente(ClienteUs cliente) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTEUS VALUES (";
		sql += cliente.getId() + ",'";
		sql += cliente.getNombre() + "','";
		sql += cliente.getCorreo() + "','";
		sql += cliente.getRol() + "','";
		sql += cliente.getCedula() + "','";
		sql += cliente.isRegistrado() + "','";
		sql += cliente.getPassword() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}


	/**
	 * Método que actualiza el cliente que entra como parámetro en la base de datos.
	 * @param cliente - el cliente a actualizar. cliente !=  null
	 * <b> post: </b> se ha actualizado el cliente en la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCliente(ClienteUs cliente) throws SQLException, Exception {

		String sql = "UPDATE CLIENTEUS SET ";
		sql += "NOMBRE='" + cliente.getNombre() + "',";
		sql += "CORREO='" + cliente.getCorreo() + "',";
		sql += "ROL='" + cliente.getRol() + "',";
		sql += "CEDULA='" + cliente.getCedula() + "'";
		sql += "REGISTRADO='" + cliente.isRegistrado() + "'";
		sql += "PASSWORD='" + cliente.getPassword() + "'";
		sql += " WHERE ID = " + cliente.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el cliente que entra como parámetro en la base de datos.
	 * @param cliente - el cliente a borrar. cliente !=  null
	 * <b> post: </b> se ha borrado el cliente en la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCliente(ClienteUs cliente) throws SQLException, Exception {

		String sql = "DELETE FROM CLIENTEUS";
		sql += " WHERE ID = " + cliente.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	
	
	
	
	
}