package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Producto;
import vos.ProductoOrden;

public class DAOTablaProducto {


	private Connection co;
	
	private ArrayList<Object> recursos;
	
	public DAOTablaProducto(Connection co){
		super();
		this.co = co;
		recursos = new ArrayList<>();
	}
	
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
	
	public ArrayList<ProductoOrden> getProducto() throws SQLException, Exception {
		ArrayList<ProductoOrden> producto = new ArrayList<ProductoOrden>();

		String sql = "SELECT * FROM PRODUCTO";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			double precio = rs.getDouble("PRECIO");
			String traduccion = rs.getString("TRADUCCION");
			boolean alcoholica = rs.getBoolean("ALCOHOLICA");
			int cantidad = rs.getInt("CANTIDAD");
			double costo = rs.getDouble("COSTO");
			int tiempo = rs.getInt("TIEMPO");
			String descripcion = rs.getString("DESCRIPCION");
			String clasificacion = rs.getString("CLASIFICACION");
			producto.add(new ProductoOrden(alcoholica,cantidad,clasificacion,costo,descripcion,id2,nombre,precio,tiempo,traduccion));
		}
		return producto;
	}
	
	public ArrayList<Producto> buscarProductoPorId(String id) throws SQLException, Exception {
		ArrayList<Producto> producto = new ArrayList<Producto>();

		String sql = "SELECT * FROM PRODUCTO WHERE ID_PRODUCTO ='" + id + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			double precio = rs.getDouble("PRECIO");
			String traduccion = rs.getString("TRADUCCION");
			boolean alcoholica = rs.getBoolean("ALCOHOLICA");
			int cantidad = rs.getInt("CANTIDAD");
			double costo = rs.getDouble("COSTO");
			int tiempo = rs.getInt("TIEMPO");
			String descripcion = rs.getString("DESCRIPCION");
			String clasificacion = rs.getString("CLASIFICACION");
			producto.add(new ProductoOrden(alcoholica,cantidad,clasificacion,costo,descripcion,id2,nombre,precio,tiempo,traduccion));
		}
		return producto;
	}
	
	public void addProducto(ProductoOrden producto, long id) throws Exception
	{
		DAOTablaRestauranteUs r = new DAOTablaRestauranteUs();
		
		if(r.esRestaurante(id))
		{
			String sql = "INSERT INTO PRODUCTO (NOMBRE,PRECIO,DESCRIPCION,TRADUCCION,CLASIFICACION,ALCOHOLICO,TIEMPO,COSTO) VALUES "
					+ "('" 
					+ producto.getNombre() + "','" + producto.getPrecio() 
					+ "','" + producto.getDescricon() +"','" + producto.getTraduccion()
					+ "','" + producto.getClasificacion() + "','" + producto.getAlcolica()
					+ "','" + producto.getTiempoP() + "','" + producto.getCostoP() + "')";
			
			PreparedStatement prepStmt = co.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un producto");
	}
	
	public void updateProducto(Producto producto) throws SQLException, Exception {

		String sql = "UPDATE PRODUCTO SET ";
		sql += "NOMBRE=" + producto.getNombre();
		sql += ",PRECIO" + producto.getPrecio();
		sql += ",TRADUCCION" + producto.getTraduccion();
		sql += "ALCOHOLICA=" + producto.getNombre();
		sql += ",CANTIDAD" + producto.getPrecio();
		sql += ",COSTO" + producto.getTraduccion();
		sql += "DESCRIPCION=" + producto.getNombre();
		sql += ",CLASIFICACION" + producto.getPrecio();
		sql += ",TIEMPO" + producto.getTraduccion();
		sql += " WHERE ID_INGREDIENTE = '" + producto.getID() + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteProducto(Producto producto) throws SQLException, Exception {

		String sql = "DELETE FROM PRODUCTO";
		sql += " WHERE ID_PRODUCTO = " + producto.getID();

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
	public void addEquivalente(Long idP, Long idI,Long id)throws Exception{
		//DAOTablaUsuarios r = new DAOTablaUsuarios(conn);
		
		if(r.esRestaurante(id)){
			String sql = "INSERT INTO PREODUCOT_INGREDIENTE(ID_P,ID_IN) VALUES ('"
					+ idP + "','" + idI + "')";
			PreparedStatement prep = co.prepareStatement(sql);
			prep.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un ingrediente");
	}
	
}
