package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Restaurante;


public class DAOTablaRestaurante {

private Connection co;
	
	private ArrayList<Object> recursos;
	
	public DAOTablaRestaurante(){
		super();
		recursos = new ArrayList<>();
	}
	
	public void setConnection(Connection co){
		this.co = co;
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
	
	public ArrayList<Restaurante> getRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> restaurante = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTE";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			restaurante.add(new Restaurante(nombre, id2,tipo));
		}
		return restaurante;
	}
	
	public Restaurante buscarRestaurantePorId(Long id) throws SQLException, Exception {
		Restaurante restaurante = null;

		String sql = "SELECT * FROM RESTAURANTE WHERE ID_RESTAURNATE ='" + id + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			restaurante=(new Restaurante(nombre, id2,tipo));
		}
		return restaurante;
	}
	
	public void addRestaurante(Restaurante restaurante, long id) throws Exception
	{
		DAOTablaAdministradorUs r = new DAOTablaAdministradorUs();
		
		if(r.esAdmin(id))
		{
			String sql = "INSERT INTO RESTAURANTE (NOMBRE,TIPO) VALUES ('" 
					+ restaurante.getNombre() +"','" + restaurante.getTipo() + "')";
			
			PreparedStatement prepStmt = co.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		else throw new Exception("Debe ser Admin para poder agregar un restaurante");
	}
	
	public void updateRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTE SET ";
		sql += "NOMBRE=" + restaurante.getNombre();
		sql += ",PRECIO" + restaurante.getTipo();
		sql += " WHERE ID_INGREDIENTE = '" + restaurante.getID() + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "DELETE FROM RESTAURANTE";
		sql += " WHERE ID_RESTAURNATE = " + restaurante.getID();

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	
}
