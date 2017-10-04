package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaRestaurante;
import vos.Restaurante;
import vos.AdministradorUs;
import vos.ClienteUs;
import vos.Ingrediente;
import vos.RestauranteUs;
import vos.Zona;
import dao.DAOTablaAdministradorUs;
import dao.DAOTablaClienteUs;
import dao.DAOTablaIngrediente;
import dao.DAOTablaRestauranteUs;
import dao.DAOTablaZona;



/**
 * Transaction Manager de la aplicacion (TM)
 * @author jf.garcia, c.martinezc1
 *
 */

public class RotondAndesTM {
	
	
	/**
	 * Atributo est�tico que contiene el path relativo del archivo que tiene los datos de la conexi�n
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";
	
	/**
	 * Atributo est�tico que contiene el path absoluto del archivo que tiene los datos de la conexi�n
	 */
	private  String connectionDataPath;
	
	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;
	
	
	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;
	
	
	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;
	
	
	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * Conexi�n a la base de datos
	 */
	private Connection conn;
	
	
	
	/**
	 * M�todo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacci�nes y la l�gica de negocios que �stas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexi�n a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	
	
	/**
	 * M�todo que  inicializa los atributos que se usan para la conexi�n a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexi�n a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * M�todo que  retorna la conexi�n a la base de datos
	 * @return Connection - la conexi�n a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexi�n a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}
	
	/////////////////////////////////////////////
	/////////Transacciones//////////////////////
	///////////////////////////////////////////
	
	//Zona
	
			/**
			 * M�todo que modela la transacci�n que retorna todas las zonas de la base de datos.
			 * @return zonas - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la busqueda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<Zona> darZonas() throws Exception {
				List<Zona> zonas;
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacci�n
					this.conn = darConexion();
					daoZonas.setConn(conn);
					zonas = daoZonas.darZonas();

				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					try {
						daoZonas.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return zonas;
			}

			/**
			 * M�todo que modela la transacci�n que busca la/s zona/s en la base de datos con el id entra como par�metro.
			 * @param name - Nombre de la zona a buscar. name != null
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la busqueda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<Zona> buscarZonasPorId(Long id) throws Exception {
				List<Zona> zonas;
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacci�n
					this.conn = darConexion();
					daoZonas.setConn(conn);
					zonas = daoZonas.buscarZonasPorId(id);

				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					try {
						daoZonas.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return zonas;
			}
			
			/**
			 * M�todo que modela la transacci�n que agrega una sola zona a la base de datos.
			 * <b> post: </b> se ha agregado la zona que entra como par�metro
			 * @param zona - La zona a agregar. zona != null
			 * @throws Exception - cualquier error que se genere agregando la zona
			 */
			public void addZona(Zona zona) throws Exception {
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacci�n
					this.conn = darConexion();
					daoZonas.setConn(conn);
					daoZonas.addZona(zona);
					conn.commit();

				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					try {
						daoZonas.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			/**
			 * M�todo que modela la transacci�n que agrega las zonas que entran como par�metro a la base de datos.
			 * <b> post: </b> se han agregado las zonas que entran como par�metro
			 * @param zonas - objeto que modela una lista de zonas y que estas se pretenden agregar. zonas != null
			 * @throws Exception - cualquier error que se genera agregando las zonas
			 */
			public void addZonas(List<Zona> zonas) throws Exception {
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacci�n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoZonas.setConn(conn);
					Iterator<Zona> it = zonas.iterator();
					while(it.hasNext())
					{
						daoZonas.addZona(it.next());
					}
					
					conn.commit();
				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					conn.rollback();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					conn.rollback();
					throw e;
				} finally {
					try {
						daoZonas.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			/**
			 * M�todo que modela la transacci�n que actualiza la zona que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha actualizado la zona que entra como par�metro
			 * @param zona - Zona a actualizar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void updateZona(Zona zona) throws Exception {
				DAOTablaZona daoZona = new DAOTablaZona();
				try 
				{
					//////transacci�n
					this.conn = darConexion();
					daoZona.setConn(conn);
					daoZona.updateZona(zona);

				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					try {
						daoZona.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}

			/**
			 * M�todo que modela la transacci�n que elimina la zona que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha eliminado la zona que entra como par�metro
			 * @param video - Zona a eliminar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void deleteZona(Zona zona) throws Exception {
				DAOTablaZona daoZona = new DAOTablaZona();
				try 
				{
					//////transacci�n
					this.conn = darConexion();
					daoZona.setConn(conn);
					daoZona.deleteZona(zona);

				} catch (SQLException e) {
					System.err.println("SQLException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					System.err.println("GeneralException:" + e.getMessage());
					e.printStackTrace();
					throw e;
				} finally {
					try {
						daoZona.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
	
}