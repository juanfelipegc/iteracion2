package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import vos.Ingrediente;
import vos.ProductoOrden;

public class DAOTablaProducto {

private Connection co;
	
	public DAOTablaProducto(Connection co){
		super();
		this.co = co;
	}
	
	public void addProducto(ProductoOrden producto, long id) throws Exception
	{
		//DAOTablaUsuarios r = new DAOTablaUsuarios(co);
		
		if(r.esRestaurante(id))
		{
			String sql = "INSERT INTO PRODUCTO (NOMBRE,PRECIO,DESCRIPCION,TRADUCCION,CLASIFICACION,ALCOHOLICO,TIEMPO,COSTO) VALUES ('" 
					+ producto.getNombre() + "','" + producto.getPrecio() + "','" 
					+"','" + producto.getDescricon() + "','" +"','" + producto.getTraduccion() + "','"
					+"','" + producto.getClasificacion() + "','" + "','" + producto.getAlcolica() + "','"
					+ producto.getTiempoP() + "')" +"','" + producto.getCostoP() + "','";
			
			PreparedStatement prepStmt = co.prepareStatement(sql);
			prepStmt.executeQuery();
		}
		else throw new Exception("Debe ser Restaurante para poder agregar un producto");
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
