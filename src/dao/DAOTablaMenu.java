package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Menu;


public class DAOTablaMenu {


	private ArrayList<Object> recursos;
	
	private Connection co;
	
	public DAOTablaMenu(Connection co){
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
	
	public ArrayList<Menu> getMenu() throws SQLException, Exception {
		ArrayList<Menu> menu = new ArrayList<Menu>();

		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_MENU");
			String nombre = rs.getString("NOMBRE");
			double precio = rs.getDouble("PRECIO");
			Long producto1 = rs.getLong("PRODUCTO1");
			Long producto2 = rs.getLong("PRODUCTO2");
			Long producto3 = rs.getLong("PRODUCTO3");
			Long producto4 = rs.getLong("PRODUCTO4");
			Long producto5 = rs.getLong("PRODUCTO5");
			menu.add(new Menu(nombre,id2,precio,producto1,producto2,producto3,producto4,producto5));
		}
		return menu;
	}
	
	public Menu buscarMenuPorId(String id) throws SQLException, Exception {
		Menu menu = null;

		String sql = "SELECT * FROM MENU WHERE ID_MENU ='" + id + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_MENU");
			String nombre = rs.getString("NOMBRE");
			double precio = rs.getDouble("PRECIO");
			Long producto1 = rs.getLong("PRODUCTO1");
			Long producto2 = rs.getLong("PRODUCTO2");
			Long producto3 = rs.getLong("PRODUCTO3");
			Long producto4 = rs.getLong("PRODUCTO4");
			Long producto5 = rs.getLong("PRODUCTO5");
			menu=(new Menu(nombre,id2,precio,producto1,producto2,producto3,producto4,producto5));
		}
		return menu;
	}
	
	public void addMenu(Menu menu, long id) throws Exception
	{
		DAOTablaRestauranteUs r = new DAOTablaRestauranteUs();
		
		if(r.esRestaurante(id))
		{
			if(clasificacionRepetida(menu))
			{
				throw new Exception("el menu debe tener solamente 1 producto por tipo");
			}
			
			String p1 = "null";
			String p2 = "null";
			String p3 = "null";
			String p4 = "null";
			String p5 = "null";
			if(menu.getProducto1() != 0) p1 = ""+ menu.getProducto1();
			if(menu.getProducto2() != 0) p2 = ""+ menu.getProducto2();
			if(menu.getProducto3() != 0) p3 = ""+ menu.getProducto3();
			if(menu.getProducto4() != 0) p4 = ""+ menu.getProducto4();
			if(menu.getProducto5() != 0) p5 = ""+ menu.getProducto5();

			
			String sql = "INSERT INTO PRODUCTO (NOMBRE,PRECIO,ID_RES,PRODUCTO1,PRODUCTO2,PRODUCTO3,PRODUCTO4,PRODUCTO5) VALUES ('" 
					+ menu.getNombre() + "','" + menu.getPrecio() + "','" 
					+"','" + menu.getID() + "','" + "','" + p1 + "','"
					+"','" + p2 + "','" + "','" + p3 + "','"
					+"','" + p4 + "','" + p5 + "','";
			
			PreparedStatement prepStmt = co.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un producto");
	}
	
	public void updateMenu(Menu menu) throws SQLException, Exception {

		String sql = "UPDATE MENU SET ";
		sql += "NOMBRE=" + menu.getNombre();
		sql += ",PRECIO" + menu.getPrecio();
		sql += ",PRODUCTO1" + menu.getProducto1();
		sql += ",PRODUCTO2" + menu.getProducto2();
		sql += ",PRODUCTO3" + menu.getProducto3();
		sql += ",PRODUCTO4" + menu.getProducto4();
		sql += ",PRODUCTO5" + menu.getProducto5();
		sql += " WHERE ID_INGREDIENTE = '" + menu.getID() + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteMenu(Menu menu) throws SQLException, Exception {

		String sql = "DELETE FROM MENU";
		sql += " WHERE ID_MENU = " + menu.getID();

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	private boolean clasificacionRepetida(Menu menu) throws Exception {
		ArrayList <Long> array = new ArrayList<Long>();
		
		if (menu.getProducto1() != 0) array.add(menu.getProducto1());
		if (menu.getProducto2() != 0) array.add(menu.getProducto2());
		if (menu.getProducto3() != 0) array.add(menu.getProducto3());
		if (menu.getProducto4() != 0) array.add(menu.getProducto4());
		if (menu.getProducto5() != 0) array.add(menu.getProducto5());
		
		ArrayList <Long> temp = new ArrayList<Long>();
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i)!= null)
			{
				String sql = "SELECT CLASIFICACION FROM PRODUCTO WHERE ID = " + array.get(i);
				
				 PreparedStatement prepStmt = co.prepareStatement(sql);
				 ResultSet result = prepStmt.executeQuery();
				
				 if(result.next())
				 {
					 temp.add(result.getLong("CLASIFICACION"));
				 }
				 else
				 {
					 throw new Exception("El producto no existe");
				 }
				 
				 
			}
		}
		for (int j = 0; j < temp.size(); j++) {
			for (int j2 = j+1; j2 < temp.size(); j2++) {
				if(temp.get(j2) == temp.get(j)) return true;
			}
		}
		
		return false;
		
	}	
}
