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
import vos.Menu;
import vos.Orden;
import vos.Producto;
import vos.ProductoOrden;
import vos.Reserva;
import vos.RestauranteUs;
import vos.Zona;
import dao.DAOTablaAdministradorUs;
import dao.DAOTablaClienteUs;
import dao.DAOTablaIngrediente;
import dao.DAOTablaMenu;
import dao.DAOTablaOrden;
import dao.DAOTablaProducto;
import dao.DAOTablaReserva;
import dao.DAOTablaRestauranteUs;
import dao.DAOTablaZona;



/**
 * Transaction Manager de la aplicacion (TM)
 * @author jf.garcia, c.martinezc1
 *
 */

public class RotondAndesTM {
	
	
	/**
	 * Atributo estï¿½tico que contiene el path relativo del archivo que tiene los datos de la conexiï¿½n
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";
	
	/**
	 * Atributo estï¿½tico que contiene el path absoluto del archivo que tiene los datos de la conexiï¿½n
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
	 * Conexiï¿½n a la base de datos
	 */
	private Connection conn;
	
	
	
	/**
	 * Mï¿½todo constructor de la clase RotondAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciï¿½nes y la lï¿½gica de negocios que ï¿½stas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexiï¿½n a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}

	
	
	/**
	 * Mï¿½todo que  inicializa los atributos que se usan para la conexiï¿½n a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexiï¿½n a la base de datos.
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
	 * Mï¿½todo que  retorna la conexiï¿½n a la base de datos
	 * @return Connection - la conexiï¿½n a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexiï¿½n a la base de datos
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
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las zonas de la base de datos.
			 * @return zonas - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Zona> darZonas() throws Exception {
				List<Zona> zonas;
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacciï¿½n
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
			 * Mï¿½todo que modela la transacciï¿½n que busca la/s zona/s en la base de datos con el id entra como parï¿½metro.
			 * @param name - Nombre de la zona a buscar. name != null
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Zona> buscarZonasPorId(Long id) throws Exception {
				List<Zona> zonas;
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacciï¿½n
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola zona a la base de datos.
			 * <b> post: </b> se ha agregado la zona que entra como parï¿½metro
			 * @param zona - La zona a agregar. zona != null
			 * @throws Exception - cualquier error que se genere agregando la zona
			 */
			public void addZona(Zona zona) throws Exception {
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacciï¿½n
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las zonas que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las zonas que entran como parï¿½metro
			 * @param zonas - objeto que modela una lista de zonas y que estas se pretenden agregar. zonas != null
			 * @throws Exception - cualquier error que se genera agregando las zonas
			 */
			public void addZonas(List<Zona> zonas) throws Exception {
				DAOTablaZona daoZonas = new DAOTablaZona();
				try 
				{
					//////transacciï¿½n - ACID Example
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la zona que entra como parï¿½metro
			 * @param zona - Zona a actualizar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void updateZona(Zona zona) throws Exception {
				DAOTablaZona daoZona = new DAOTablaZona();
				try 
				{
					//////transacciï¿½n
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la zona que entra como parï¿½metro
			 * @param video - Zona a eliminar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void deleteZona(Zona zona) throws Exception {
				DAOTablaZona daoZona = new DAOTablaZona();
				try 
				{
					//////transacciï¿½n
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
			
			//AdministradorUs
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todos administradoes de la base de datos.
			 * @return administradores - objeto que modela un arreglo de administradores. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<AdministradorUs> darAdministradoresUs() throws Exception {
				List<AdministradorUs> administradores;
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradorUs.setConn(conn);
					administradores = daoAdministradorUs.darAdministradores();

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
						daoAdministradorUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return administradores;
			}

			/**
<<<<<<< HEAD
			 * Método que modela la transacción que busca el/los administrador/es en la base de datos con el nombre entra como parámetro.
			 * @param nombre - Nombre de los administradores a buscar. name != null
			 * @return administradores - objeto que modela  un arreglo de administradores. Este arreglo contiene el resultado de la búsqueda
			 * @throws Exception -  cualquier error que se genere durante la transacción
=======
			 * Mï¿½todo que modela la transacciï¿½n que busca el/los administrador/es en la base de datos con el nombre entra como parï¿½metro.
			 * @param name - Nombre de los administradores a buscar. name != null
			 * @return administradores - objeto que modela  un arreglo de administradores. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
>>>>>>> 76f180f71017c4c9c0c2a2f3424463b5f11bb0f7
			 */
			public List<AdministradorUs> buscarAdministradoresUsPorNombre(String nombre) throws Exception {
				List<AdministradorUs> administradores;
				DAOTablaAdministradorUs daoAdministradoresUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradoresUs.setConn(conn);
					administradores = daoAdministradoresUs.buscarAdministradorPorNombre(nombre);

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
						daoAdministradoresUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return administradores;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca el administrador en la base de datos con el id que entra como parï¿½metro.
			 * @param id - Id del administrador a buscar. id != null
			 * @return transacciï¿½n - Resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public AdministradorUs buscarAdministradorUsPorId(Long id) throws Exception {
				AdministradorUs administrador;
				DAOTablaAdministradorUs daoAdministradoresUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradoresUs.setConn(conn);
					administrador = daoAdministradoresUs.buscarAdministradorPorId(id);

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
						daoAdministradoresUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return administrador;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega un solo administrador a la base de datos.
			 * <b> post: </b> se ha agregado el administrador que entra como parï¿½metro
			 * @param administrador - el administrador a agregar. administrador != null
			 * @throws Exception - cualquier error que se genere agregando el administrador
			 */
			public void addAdministradorUs(AdministradorUs administrador) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradorUs.setConn(conn);
					daoAdministradorUs.addAdministrador(administrador);
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
						daoAdministradorUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//Revisar que este el cliente
			public void addClienteAdministradorUs(Long id, ClienteUs cliente) throws Exception {
				DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradorUs.setConn(conn);
					daoClienteUs.setConn(conn);
					daoAdministradorUs.buscarAdministradorPorId(id);
					daoClienteUs.addCliente(cliente);
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
						daoClienteUs.cerrarRecursos();
						daoAdministradorUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega los administradores que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado los administradores que entran como parï¿½metro
			 * @param administradores - objeto que modela una lista de administradores y que estos se pretenden agregar. administradores != null
			 * @throws Exception - cualquier error que se genera agregando los administradores
			 */
			public void addAdministradoresUs(List<AdministradorUs> administradores) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoAdministradorUs.setConn(conn);
					Iterator<AdministradorUs> it = administradores.iterator();
					while(it.hasNext())
					{
						daoAdministradorUs.addAdministrador(it.next());
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
						daoAdministradorUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza el administrador que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado el administrador que entra como parï¿½metro
			 * @param administrador - administrador a actualizar. administrador != null
			 * @throws Exception - cualquier error que se genera actualizando los administradores
			 */
			public void updateAdministradorUs(AdministradorUs administradorUs) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradorUs.setConn(conn);
					daoAdministradorUs.updateAdministrador(administradorUs);

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
						daoAdministradorUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina el administrador que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado el administrador que entra como parï¿½metro
			 * @param administrador - administrador a eliminar. administrador != null
			 * @throws Exception - cualquier error que se genera actualizando los administradores
			 */
			public void deleteAdministradorUs(AdministradorUs administrador) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoAdministradorUs.setConn(conn);
					daoAdministradorUs.deleteAdministrador(administrador);

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
						daoAdministradorUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//ClienteUs
			
			/**
			 * Mï¿½todo que modela la transaccion que retorna todos los clientes de la base de datos.
			 * @return clientes - objeto que modela  un arreglo de clientes. este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transaccion
			 */
			public List<ClienteUs> darClientes() throws Exception {
				List<ClienteUs> clientes;
				DAOTablaClienteUs daoclientesUs = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoclientesUs.setConn(conn);
					clientes = daoclientesUs.darClientes();

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
						daoclientesUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return clientes;
			}

			/**
			 * Mï¿½todo que modela la transaccion que busca el/los cliente/s en la base de datos con el nombre entra como parï¿½metro.
			 * @param nombre - Nombre del cliente a buscar. nombre != null
			 * @return Listaclientes - objeto que modela  un arreglo de clientes. este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transaccion
			 */
			public List<ClienteUs> buscarClientesUsPorNombre(String nombre) throws Exception {
				List<ClienteUs> clientes;
				DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoClienteUs.setConn(conn);
					clientes = daoClienteUs.buscarClientePorNombre(nombre);

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
						daoClienteUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return clientes;
			}
			
			/**
			 * Mï¿½todo que modela la transaccion que busca el cliente en la base de datos con el id que entra como parï¿½metro.
			 * @param id - Id del cliente a buscar. id != null
			 * @return cliente - Resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transaccion
			 */
			public ClienteUs buscarClienteUsPorId(Long id) throws Exception {
				ClienteUs cliente;
				DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoClienteUs.setConn(conn);
					cliente = daoClienteUs.buscarClientePorId(id);

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
						daoClienteUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return cliente;
			}
			
			/**
			 * Mï¿½todo que modela la transaccion que agrega un sï¿½lo cliente a la base de datos.
			 * <b> post: </b> se ha agregado el cliente que entra como parï¿½metro
			 * @param cliente - el cliente a agregar. cliente != null
			 * @throws Exception - cualquier error que se genere agregando el cliente
			 */
			public void addClienteUs(ClienteUs cliente) throws Exception {
				DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoClienteUs.setConn(conn);
					daoClienteUs.addCliente(cliente);
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
						daoClienteUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transaccion que agrega los clientes que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado los clientes que entran como parï¿½metro
			 * @param clientes - objeto que modela una lista de clientes y se estos se pretenden agregar. clientes != null
			 * @throws Exception - cualquier error que se genera agregando los clientes
			 */
			public void addClientesUs(List<ClienteUs> clientes) throws Exception {
				DAOTablaClienteUs daoClientesUs = new DAOTablaClienteUs();
				try 
				{
					//////transaccion - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoClientesUs.setConn(conn);
					Iterator<ClienteUs> it = clientes.iterator();
					while(it.hasNext())
					{
						daoClientesUs.addCliente(it.next());
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
						daoClientesUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transaccion que actualiza el cliente que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado el cliente que entra como parï¿½metro
			 * @param cliente - cliente a actualizar. cliente != null
			 * @throws Exception - cualquier error que se genera actualizando los clientes
			 */
			public void updateClienteUs(ClienteUs cliente) throws Exception {
				DAOTablaClienteUs daoClientes = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoClientes.setConn(conn);
					daoClientes.updateCliente(cliente);

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
						daoClientes.cerrarRecursos();
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
			 * Mï¿½todo que modela la transaccion que elimina el cliente que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado el cliente que entra como parï¿½metro
			 * @param cliente - cliente a eliminar. cliente != null
			 * @throws Exception - cualquier error que se genera actualizando los clientes
			 */
			public void deleteClienteUs(ClienteUs cliente) throws Exception {
				DAOTablaClienteUs daoClientes = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoClientes.setConn(conn);
					daoClientes.deleteCliente(cliente);

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
						daoClientes.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//RestauranteUs
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todos los restauranteus de la base de datos.
			 * @return restaurantesUs - objeto que modela  un arreglo de restauranteus. este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<RestauranteUs> darRestaurantesUs() throws Exception {
				List<RestauranteUs> restaurantesUs;
				DAOTablaRestauranteUs daoRestaurantes = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurantes.setConn(conn);
					restaurantesUs = daoRestaurantes.darRestaurantesUs();

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
						daoRestaurantes.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return restaurantesUs;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca el/los restauranteus en la base de datos con el nombre entra como parï¿½metro.
			 * @param nombre - Nombre del restauranteus a buscar. nombre != null
			 * @return Listarestauranteuss - objeto que modela  un arreglo de restauranteuss. este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<RestauranteUs> buscarRestauranteUsPorNombre(String nombre) throws Exception {
				List<RestauranteUs> restaurantesUs;
				DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurantesUs.setConn(conn);
					restaurantesUs = daoRestaurantesUs.buscarRestauranteUsPorNombre(nombre);

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
						daoRestaurantesUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return restaurantesUs;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca el restauranteus en la base de datos con el id que entra como parï¿½metro.
			 * @param id - Id del restauranteus a buscar. id != null
			 * @return restauranteUs - Resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public RestauranteUs buscarRestauranteUsPorId(Long id) throws Exception {
				RestauranteUs restauranteUs;
				DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestauranteUs.setConn(conn);
					restauranteUs = daoRestauranteUs.buscarRestauranteUsPorId(id);

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
						daoRestauranteUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return restauranteUs;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega un solo restauranteus a la base de datos.
			 * <b> post: </b> se ha agregado el restauranteus que entra como parï¿½metro
			 * @param restauranteUs - el restauranteus a agregar. restauranteUs != null
			 * @throws Exception - cualquier error que se genere agregando el restauranteUs
			 */
			public void addRestauranteUs(RestauranteUs restauranteUs) throws Exception {
				DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestauranteUs.setConn(conn);
					daoRestauranteUs.addRestauranteUs(restauranteUs);
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
						daoRestauranteUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega los restauranteus que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado los restauranteus que entran como parï¿½metro
			 * @param restaurantes - objeto que modela una lista de restauranteuss y se estos se pretenden agregar. restaurantes != null
			 * @throws Exception - cualquier error que se genera agregando los restaurantes
			 */
			public void addRestaurantesUs(List<RestauranteUs> restaurantes) throws Exception {
				DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoRestaurantesUs.setConn(conn);
					Iterator<RestauranteUs> it = restaurantes.iterator();
					while(it.hasNext())
					{
						daoRestaurantesUs.addRestauranteUs(it.next());
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
						daoRestaurantesUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza el restauranteus que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado el restauranteus que entra como parï¿½metro
			 * @param restauranteUs - restauranteus a actualizar. restauranteUs != null
			 * @throws Exception - cualquier error que se genera actualizando los restauranteus
			 */
			public void updateRestauranteUs(RestauranteUs restauranteUs) throws Exception {
				DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurantesUs.setConn(conn);
					daoRestaurantesUs.updateRestauranteUs(restauranteUs);

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
						daoRestaurantesUs.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina el restauranteus que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado el restauranteus que entra como parï¿½metro
			 * @param restauranteUs - restauranteus a eliminar. restauranteUs != null
			 * @throws Exception - cualquier error que se genera actualizando los restauranteus
			 */
			public void deleteRestauranteUs(RestauranteUs restauranteUs) throws Exception {
				DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestauranteUs.setConn(conn);
					daoRestauranteUs.deleteRestauranteUs(restauranteUs);

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
						daoRestauranteUs.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//Orden
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las ordenes de la base de datos.
			 * @return ordenes - objeto que modela  un arreglo de ordenes. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Orden> darOrdenes() throws Exception {
				List<Orden> ordenes;
				DAOTablaOrden daoOrdenes = new DAOTablaOrden();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoOrdenes.setConn(conn);
					ordenes = daoOrdenes.darOrdenes();

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
						daoOrdenes.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return ordenes;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca la orden en la base de datos con el id que entra como parï¿½metro.
			 * @param id - Id de la orden a buscar. id != null
			 * @return orden - Resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public Orden buscarOrdenPorId(Long id) throws Exception {
				Orden orden;
				DAOTablaOrden daoOrden = new DAOTablaOrden();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoOrden.setConn(conn);
					orden = daoOrden.buscarOrdenPorId(id);

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
						daoOrden.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return orden;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola orden a la base de datos.
			 * <b> post: </b> se ha agregado la orden que entra como parï¿½metro
			 * @param orden - la orden a agregar. orden != null
			 * @throws Exception - cualquier error que se genere agregando la orden
			 */
			public void addOrden(Orden orden) throws Exception {
				DAOTablaOrden daoOrden = new DAOTablaOrden();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoOrden.setConn(conn);
					daoOrden.addOrden(orden);
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
						daoOrden.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las ordenes que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las ordenes que entran como parï¿½metro
			 * @param ordenes - objeto que modela una lista de ordenes y que estas se pretenden agregar. ordenes != null
			 * @throws Exception - cualquier error que se genera agregando las ordenes
			 */
			public void addOrdenes(List<Orden> ordenes) throws Exception {
				DAOTablaOrden daoOrden = new DAOTablaOrden();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoOrden.setConn(conn);
					Iterator<Orden> it = ordenes.iterator();
					while(it.hasNext())
					{
						daoOrden.addOrden(it.next());
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
						daoOrden.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la orden que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la orden que entra como parï¿½metro
			 * @param orden - orden a actualizar. orden != null
			 * @throws Exception - cualquier error que se genera actualizando las ordenes
			 */
			public void updateOrden(Orden orden) throws Exception {
				DAOTablaOrden daoOrdenes = new DAOTablaOrden();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoOrdenes.setConn(conn);
					daoOrdenes.updateOrden(orden);

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
						daoOrdenes.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la orden que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la orden que entra como parï¿½metro
			 * @param orden - orden a eliminar. orden != null
			 * @throws Exception - cualquier error que se genera actualizando las ordenes
			 */
			public void deleteOrden(Orden orden) throws Exception {
				DAOTablaOrden daoOrdenes = new DAOTablaOrden();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoOrdenes.setConn(conn);
					daoOrdenes.deleteOrden(orden);

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
						daoOrdenes.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//Reserva
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las reservas de la base de datos.
			 * @return reservas - objeto que modela  un arreglo de reservas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Reserva> darReservas() throws Exception {
				List<Reserva> reservas;
				DAOTablaReserva daoReservas = new DAOTablaReserva();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoReservas.setConn(conn);
					reservas = daoReservas.darReservas();

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
						daoReservas.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return reservas;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca la reserva en la base de datos con el id que entra como parï¿½metro.
			 * @param id - Id de la reserva a buscar. id != null
			 * @return reserva - Resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public Reserva buscarReservaPorId(Long id) throws Exception {
				Reserva reserva;
				DAOTablaReserva daoReserva = new DAOTablaReserva();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoReserva.setConn(conn);
					reserva = daoReserva.buscarReservaPorId(id);

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
						daoReserva.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return reserva;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola reserva a la base de datos.
			 * <b> post: </b> se ha agregado la reserva que entra como parï¿½metro
			 * @param reserva - la reserva a agregar. reserva != null
			 * @throws Exception - cualquier error que se genere agregando la reserva
			 */
			public void addReserva(Reserva reserva) throws Exception {
				DAOTablaReserva daoReserva = new DAOTablaReserva();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoReserva.setConn(conn);
					daoReserva.addReserva(reserva);
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
						daoReserva.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las reservas que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las reservas que entran como parï¿½metro
			 * @param reservas - objeto que modela una lista de reservas y que estas se pretenden agregar. reservas != null
			 * @throws Exception - cualquier error que se genera agregando las reservas
			 */
			public void addReservas(List<Reserva> reservas) throws Exception {
				DAOTablaReserva daoReserva = new DAOTablaReserva();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoReserva.setConn(conn);
					Iterator<Reserva> it = reservas.iterator();
					while(it.hasNext())
					{
						daoReserva.addReserva(it.next());
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
						daoReserva.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la reserva que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la reserva que entra como parï¿½metro
			 * @param reserva - reserva a actualizar. reserva != null
			 * @throws Exception - cualquier error que se genera actualizando las reservas
			 */
			public void updateReserva(Reserva reserva) throws Exception {
				DAOTablaReserva daoReservas = new DAOTablaReserva();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoReservas.setConn(conn);
					daoReservas.updateReserva(reserva);

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
						daoReservas.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la reserva que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la reserva que entra como parï¿½metro
			 * @param reserva - reserva a eliminar. reserva != null
			 * @throws Exception - cualquier error que se genera actualizando las reservas
			 */
			public void deleteReserva(Reserva reserva) throws Exception {
				DAOTablaReserva daoReservas = new DAOTablaReserva();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoReservas.setConn(conn);
					daoReservas.deleteReserva(reserva);

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
						daoReservas.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//Ingrediente
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las ingredientes de la base de datos.
			 * @return ingredientes - objeto que modela  un arreglo de ingredientes. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Ingrediente> getIngredientes() throws Exception {
				List<Ingrediente> ingredientes;
				DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoIngrediente.setConnection(conn);
					ingredientes = daoIngrediente.darIngredientes();

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
						daoIngrediente.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return ingredientes;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca la/s ingrediente/s en la base de datos con el id entra como parï¿½metro.
			 * @param name - Nombre de la zona a buscar. name != null
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public Ingrediente buscarIngredientePorId(Long id) throws Exception {
				Ingrediente ingrediente;
				DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoIngrediente.setConnection(conn);
					ingrediente = daoIngrediente.buscarIngredientePorId(id);

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
						daoIngrediente.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return ingrediente;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola zona a la base de datos.
			 * <b> post: </b> se ha agregado la zona que entra como parï¿½metro
			 * @param zona - La zona a agregar. zona != null
			 * @throws Exception - cualquier error que se genere agregando la zona
			 */
			public void addIngrediente(Ingrediente ingrediente,long idUs) throws Exception {
				DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoIngrediente.setConnection(conn);
					daoIngrediente.addIngrediente(ingrediente,idUs);
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
						daoIngrediente.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las ingredientes que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las zonas que entran como parï¿½metro
			 * @param ingredientes - objeto que modela una lista de ingredients y que estas se pretenden agregar. zonas != null
			 * @throws Exception - cualquier error que se genera agregando las zonas
			 */
			public void addIngredientes(List<Ingrediente> ingredientes,long idUs) throws Exception {
				DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoIngrediente.setConnection(conn);
					Iterator<Ingrediente> it = ingredientes.iterator();
					while(it.hasNext())
					{
						daoIngrediente.addIngrediente(it.next(),idUs);
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
						daoIngrediente.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la zona que entra como parï¿½metro
			 * @param zona - Zona a actualizar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void updateIngrediente(Ingrediente ingrediente) throws Exception {
				DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoIngrediente.setConnection(conn);
					daoIngrediente.updateIngrediente(ingrediente);

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
						daoIngrediente.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la zona que entra como parï¿½metro
			 * @param video - Zona a eliminar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void deleteIngrediente(Ingrediente ingrediente) throws Exception {
				DAOTablaIngrediente daoIngrediente = new DAOTablaIngrediente();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoIngrediente.setConnection(conn);
					daoIngrediente.deleteIngrediente(ingrediente);

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
						daoIngrediente.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//Producto
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las ingredientes de la base de datos.
			 * @return ingredientes - objeto que modela  un arreglo de ingredientes. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<ProductoOrden> getProductos() throws Exception {
				List<ProductoOrden> productos;
				DAOTablaProducto daoProducto = new DAOTablaProducto();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoProducto.setConnection(conn);
					productos = daoProducto.getProductos();

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
						daoProducto.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return productos;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca la/s ingrediente/s en la base de datos con el id entra como parï¿½metro.
			 * @param name - Nombre de la zona a buscar. name != null
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public ProductoOrden buscarProductoPorId(Long id) throws Exception {
				ProductoOrden producto;
				DAOTablaProducto daoProducto = new DAOTablaProducto();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoProducto.setConnection(conn);
					producto = daoProducto.buscarProductoPorId(id);

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
						daoProducto.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return producto;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola zona a la base de datos.
			 * <b> post: </b> se ha agregado la zona que entra como parï¿½metro
			 * @param zona - La zona a agregar. zona != null
			 * @throws Exception - cualquier error que se genere agregando la zona
			 */
			public void addProducto(ProductoOrden producto,long idUs) throws Exception {
				DAOTablaProducto daoProducto = new DAOTablaProducto();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoProducto.setConnection(conn);
					daoProducto.addProducto(producto,idUs);
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
						daoProducto.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las ingredientes que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las zonas que entran como parï¿½metro
			 * @param ingredientes - objeto que modela una lista de ingredients y que estas se pretenden agregar. zonas != null
			 * @throws Exception - cualquier error que se genera agregando las zonas
			 */
			public void addProductos(List<ProductoOrden> productos,long idUs) throws Exception {
				DAOTablaProducto daoProducto = new DAOTablaProducto();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoProducto.setConnection(conn);
					Iterator<ProductoOrden> it = productos.iterator();
					while(it.hasNext())
					{
						daoProducto.addProducto(it.next(),idUs);
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
						daoProducto.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la zona que entra como parï¿½metro
			 * @param zona - Zona a actualizar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void updateProducto(ProductoOrden producto) throws Exception {
				DAOTablaProducto daoProducto= new DAOTablaProducto();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoProducto.setConnection(conn);
					daoProducto.updateProducto(producto);

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
						daoProducto.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la zona que entra como parï¿½metro
			 * @param video - Zona a eliminar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void deleteProducto(ProductoOrden producto) throws Exception {
				DAOTablaProducto daoProducto = new DAOTablaProducto();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoProducto.setConnection(conn);
					daoProducto.deleteProducto(producto);

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
						daoProducto.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
		
			//Menu
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las ingredientes de la base de datos.
			 * @return ingredientes - objeto que modela  un arreglo de ingredientes. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Menu> getMenus() throws Exception {
				List<Menu> menus;
				DAOTablaMenu daoMenu = new DAOTablaMenu();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoMenu.setConnection(conn);
					menus = daoMenu.getMenus();

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
						daoMenu.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return menus;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca la/s ingrediente/s en la base de datos con el id entra como parï¿½metro.
			 * @param name - Nombre de la zona a buscar. name != null
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public Menu buscarMenuPorId(Long id) throws Exception {
				Menu menu;
				DAOTablaMenu daoMenu = new DAOTablaMenu();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoMenu.setConnection(conn);
					menu = daoMenu.buscarMenuPorId(id);

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
						daoMenu.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return menu;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola zona a la base de datos.
			 * <b> post: </b> se ha agregado la zona que entra como parï¿½metro
			 * @param zona - La zona a agregar. zona != null
			 * @throws Exception - cualquier error que se genere agregando la zona
			 */
			public void addMenu(Menu menu,long idUs) throws Exception {
				DAOTablaMenu daoMenu = new DAOTablaMenu();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoMenu.setConnection(conn);
					daoMenu.addMenu(menu,idUs);
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
						daoMenu.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las ingredientes que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las zonas que entran como parï¿½metro
			 * @param ingredientes - objeto que modela una lista de ingredients y que estas se pretenden agregar. zonas != null
			 * @throws Exception - cualquier error que se genera agregando las zonas
			 */
			public void addMenus(List<Menu> menus,long idUs) throws Exception {
				DAOTablaMenu daoMenu = new DAOTablaMenu();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoMenu.setConnection(conn);
					Iterator<Menu> it = menus.iterator();
					while(it.hasNext())
					{
						daoMenu.addMenu(it.next(),idUs);
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
						daoMenu.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la zona que entra como parï¿½metro
			 * @param zona - Zona a actualizar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void updateMenu(Menu menu) throws Exception {
				DAOTablaMenu daoMenu = new DAOTablaMenu();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoMenu.setConnection(conn);
					daoMenu.updateMenu(menu);

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
						daoMenu.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la zona que entra como parï¿½metro
			 * @param video - Zona a eliminar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void deleteMenu(Menu menu) throws Exception {
				DAOTablaMenu daoMenu = new DAOTablaMenu();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoMenu.setConnection(conn);
					daoMenu.deleteMenu(menu);

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
						daoMenu.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
			}
			
			//Restaurante
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que retorna todas las ingredientes de la base de datos.
			 * @return ingredientes - objeto que modela  un arreglo de ingredientes. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public List<Restaurante> getRestaurantes() throws Exception {
				List<Restaurante> restaurantes;
				DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurante.setConnection(conn);
					restaurantes = daoRestaurante.getRestaurantes();

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
						daoRestaurante.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return restaurantes;
			}

			/**
			 * Mï¿½todo que modela la transacciï¿½n que busca la/s ingrediente/s en la base de datos con el id entra como parï¿½metro.
			 * @param name - Nombre de la zona a buscar. name != null
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la bï¿½squeda
			 * @throws Exception -  cualquier error que se genere durante la transacciï¿½n
			 */
			public Restaurante buscarRestaurantePorId(Long id) throws Exception {
				Restaurante restaurante;
				DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurante.setConnection(conn);
					restaurante = daoRestaurante.buscarRestaurantePorId(id);

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
						daoRestaurante.cerrarRecursos();
						if(this.conn!=null)
							this.conn.close();
					} catch (SQLException exception) {
						System.err.println("SQLException closing resources:" + exception.getMessage());
						exception.printStackTrace();
						throw exception;
					}
				}
				return restaurante;
			}
			
			/**
			 * Mï¿½todo que modela la transacciï¿½n que agrega una sola zona a la base de datos.
			 * <b> post: </b> se ha agregado la zona que entra como parï¿½metro
			 * @param zona - La zona a agregar. zona != null
			 * @throws Exception - cualquier error que se genere agregando la zona
			 */
			public void addRestaurante(Restaurante restaurante,long idUs) throws Exception {
				DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurante.setConnection(conn);
					daoRestaurante.addRestaurante(restaurante,idUs);
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
						daoRestaurante.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que agrega las ingredientes que entran como parï¿½metro a la base de datos.
			 * <b> post: </b> se han agregado las zonas que entran como parï¿½metro
			 * @param ingredientes - objeto que modela una lista de ingredients y que estas se pretenden agregar. zonas != null
			 * @throws Exception - cualquier error que se genera agregando las zonas
			 */
			public void addRestaurantes(List<Restaurante> restaurantes,long idUs) throws Exception {
				DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
				try 
				{
					//////transacciï¿½n - ACID Example
					this.conn = darConexion();
					conn.setAutoCommit(false);
					daoRestaurante.setConnection(conn);
					Iterator<Restaurante> it = restaurantes.iterator();
					while(it.hasNext())
					{
						daoRestaurante.addRestaurante(it.next(),idUs);
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
						daoRestaurante.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que actualiza la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha actualizado la zona que entra como parï¿½metro
			 * @param zona - Zona a actualizar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void updateRestaurante(Restaurante restaurante) throws Exception {
				DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurante.setConnection(conn);
					daoRestaurante.updateRestaurante(restaurante);

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
						daoRestaurante.cerrarRecursos();
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
			 * Mï¿½todo que modela la transacciï¿½n que elimina la zona que entra como parï¿½metro a la base de datos.
			 * <b> post: </b> se ha eliminado la zona que entra como parï¿½metro
			 * @param video - Zona a eliminar. zona != null
			 * @throws Exception - cualquier error que se genera actualizando las zonas
			 */
			public void deleteRestaurante(Restaurante restaurante) throws Exception {
				DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
				try 
				{
					//////transacciï¿½n
					this.conn = darConexion();
					daoRestaurante.setConnection(conn);
					daoRestaurante.deleteRestaurante(restaurante);

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
						daoRestaurante.cerrarRecursos();
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