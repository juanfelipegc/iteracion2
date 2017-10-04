package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import vos.Ingrediente;

public class DAOTablaIngrediente {

	private Connection co;
	
	public DAOTablaIngrediente(Connection co){
		super();
		this.co = co;
	}
	
	public void addIngrediente(Ingrediente ingrediente, long id) throws Exception
	{
		//DAOTablaUsuarios r = new DAOTablaUsuarios(conn);
		
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
	
	public void addEquivalente(Long idIn1, Long idIn2,Long idRes )throws Exception{
		//DAOTablaUsuarios r = new DAOTablaUsuarios(conn);
		
		if(r.esRestaurante(idRes)){
			String sql = "INSERT INTO EQUIVALENTE(ID_IN1,ID_IN2,ID_REST) VALUES ('"
					+ idIn1 + "','" + idIn2 + "','" + idRes + "')";
			PreparedStatement prep = co.prepareStatement(sql);
			prep.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un ingrediente");
	}
	
}
