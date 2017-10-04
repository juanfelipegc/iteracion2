package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingrediente;

public class DAOTablaIngrediente {

	private Connection co;
	
	private ArrayList<Object> recursos;
	
	public DAOTablaIngrediente(){
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
	
	public ArrayList<Ingrediente> darIngredientes() throws SQLException, Exception {
		ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

		String sql = "SELECT * FROM INGREDIENTE";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			Double precio = rs.getDouble("PRECIO");
			String traduccion = rs.getString("TRADUCCION");
			ingredientes.add(new Ingrediente(nombre, precio,id2,traduccion));
		}
		return ingredientes;
	}
	
	public Ingrediente buscarIngredientePorId(Long id) throws SQLException, Exception {
		Ingrediente ingrediente = null;

		String sql = "SELECT * FROM INGREDEINTE WHERE ID_INGREDIENTE ='" + id + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			Long id2 = rs.getLong("ID_INGREDIENTE");
			String nombre = rs.getString("NOMBRE");
			Double precio = rs.getDouble("PRECIO");
			String traduccion = rs.getString("TRADUCCION");
			ingrediente = (new Ingrediente(nombre, precio,id2,traduccion));
		}
		return ingrediente;
	}
	
	public void addIngrediente(Ingrediente ingrediente, long id) throws Exception
	{
		DAOTablaRestauranteUs r = new DAOTablaRestauranteUs();
		
		if(r.esRestaurante(id))
		{
			String sql = "INSERT INTO INGREDIENTE (NOMBRE,PRECIO,TRADUCION) VALUES ('" 
					+ ingrediente.getNombre() +"','" + ingrediente.getPrecio() + "','"
					+ ingrediente.getTraduccion() + "')";
			
			PreparedStatement prepStmt = co.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un ingrediente");
	}
	
	public void updateIngrediente(Ingrediente ingrediente) throws SQLException, Exception {

		String sql = "UPDATE INGREDIENTE SET ";
		sql += "NOMBRE=" + ingrediente.getNombre();
		sql += ",PRECIO" + ingrediente.getPrecio();
		sql += ",TRADUCCION" + ingrediente.getTraduccion();
		sql += " WHERE ID_INGREDIENTE = '" + ingrediente.getID() + "'";

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void deleteIngrediente(Ingrediente ingrediente) throws SQLException, Exception {

		String sql = "DELETE FROM INGREDIENTE";
		sql += " WHERE ID_INGREDIENTE = " + ingrediente.getID();

		PreparedStatement prepStmt = co.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void addEquivalente(Long idIn1, Long idIn2,Long idRes )throws Exception{
		DAOTablaRestauranteUs r = new DAOTablaRestauranteUs();
		
		if(r.esRestaurante(idRes)){
			String sql = "INSERT INTO EQUIVALENTE(ID_IN1,ID_IN2,ID_REST) VALUES ('"
					+ idIn1 + "','" + idIn2 + "','" + idRes + "')";
			PreparedStatement prep = co.prepareStatement(sql);
			prep.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un ingrediente");
	}
	
}
