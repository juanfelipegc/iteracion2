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
import vos.Orden;
import vos.Reserva;
import vos.RestauranteUs;
import vos.Zona;
import dao.DAOTablaAdministradorUs;
import dao.DAOTablaClienteUs;
import dao.DAOTablaIngrediente;
import dao.DAOTablaOrden;
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
			 * @return zonas - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la b�squeda
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
			 * @return ListaVideos - objeto que modela  un arreglo de zonas. Este arreglo contiene el resultado de la b�squeda
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
			
			//AdministradorUs
			
			/**
			 * M�todo que modela la transacci�n que retorna todos administradoes de la base de datos.
			 * @return administradores - objeto que modela un arreglo de administradores. Este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<AdministradorUs> darAdministradoresUs() throws Exception {
				List<AdministradorUs> administradores;
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que busca el/los administrador/es en la base de datos con el nombre entra como par�metro.
			 * @param name - Nombre de los administradores a buscar. name != null
			 * @return administradores - objeto que modela  un arreglo de administradores. Este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<AdministradorUs> buscarAdministradoresUsPorName(String nombre) throws Exception {
				List<AdministradorUs> administradores;
				DAOTablaAdministradorUs daoAdministradoresUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n
					this.conn = darConexion();
					daoAdministradoresUs.setConn(conn);
					administradores = daoAdministradoresUs.buscarAdministradorPorName(nombre);

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
			 * M�todo que modela la transacci�n que busca el administrador en la base de datos con el id que entra como par�metro.
			 * @param id - Id del administrador a buscar. id != null
			 * @return transacci�n - Resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public AdministradorUs buscarAdministradorUsPorId(Long id) throws Exception {
				AdministradorUs administrador;
				DAOTablaAdministradorUs daoAdministradoresUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega un solo administrador a la base de datos.
			 * <b> post: </b> se ha agregado el administrador que entra como par�metro
			 * @param administrador - el administrador a agregar. administrador != null
			 * @throws Exception - cualquier error que se genere agregando el administrador
			 */
			public void addAdministradorUs(AdministradorUs administrador) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n
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
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega los administradores que entran como par�metro a la base de datos.
			 * <b> post: </b> se han agregado los administradores que entran como par�metro
			 * @param administradores - objeto que modela una lista de administradores y que estos se pretenden agregar. administradores != null
			 * @throws Exception - cualquier error que se genera agregando los administradores
			 */
			public void addAdministradoresUs(List<AdministradorUs> administradores) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n - ACID Example
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
			 * M�todo que modela la transacci�n que actualiza el administrador que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha actualizado el administrador que entra como par�metro
			 * @param administrador - administrador a actualizar. administrador != null
			 * @throws Exception - cualquier error que se genera actualizando los administradores
			 */
			public void updateAdministradorUs(AdministradorUs administradorUs) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que elimina el administrador que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha eliminado el administrador que entra como par�metro
			 * @param administrador - administrador a eliminar. administrador != null
			 * @throws Exception - cualquier error que se genera actualizando los administradores
			 */
			public void deleteAdministradorUs(AdministradorUs administrador) throws Exception {
				DAOTablaAdministradorUs daoAdministradorUs = new DAOTablaAdministradorUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transaccion que retorna todos los clientes de la base de datos.
			 * @return clientes - objeto que modela  un arreglo de clientes. este arreglo contiene el resultado de la b�squeda
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
			 * M�todo que modela la transaccion que busca el/los cliente/s en la base de datos con el nombre entra como par�metro.
			 * @param nombre - Nombre del cliente a buscar. nombre != null
			 * @return Listaclientes - objeto que modela  un arreglo de clientes. este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transaccion
			 */
			public List<ClienteUs> buscarClientesUsPorName(String nombre) throws Exception {
				List<ClienteUs> clientes;
				DAOTablaClienteUs daoClienteUs = new DAOTablaClienteUs();
				try 
				{
					//////transaccion
					this.conn = darConexion();
					daoClienteUs.setConn(conn);
					clientes = daoClienteUs.buscarClientePorName(nombre);

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
			 * M�todo que modela la transaccion que busca el cliente en la base de datos con el id que entra como par�metro.
			 * @param id - Id del cliente a buscar. id != null
			 * @return cliente - Resultado de la b�squeda
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
			 * M�todo que modela la transaccion que agrega un s�lo cliente a la base de datos.
			 * <b> post: </b> se ha agregado el cliente que entra como par�metro
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
			 * M�todo que modela la transaccion que agrega los clientes que entran como par�metro a la base de datos.
			 * <b> post: </b> se han agregado los clientes que entran como par�metro
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
			 * M�todo que modela la transaccion que actualiza el cliente que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha actualizado el cliente que entra como par�metro
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
			 * M�todo que modela la transaccion que elimina el cliente que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha eliminado el cliente que entra como par�metro
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
			 * M�todo que modela la transacci�n que retorna todos los restauranteus de la base de datos.
			 * @return restaurantesUs - objeto que modela  un arreglo de restauranteus. este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<RestauranteUs> darRestaurantesUs() throws Exception {
				List<RestauranteUs> restaurantesUs;
				DAOTablaRestauranteUs daoRestaurantes = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que busca el/los restauranteus en la base de datos con el nombre entra como par�metro.
			 * @param nombre - Nombre del restauranteus a buscar. nombre != null
			 * @return Listarestauranteuss - objeto que modela  un arreglo de restauranteuss. este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<RestauranteUs> buscarRestauranteUsPorName(String nombre) throws Exception {
				List<RestauranteUs> restaurantesUs;
				DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que busca el restauranteus en la base de datos con el id que entra como par�metro.
			 * @param id - Id del restauranteus a buscar. id != null
			 * @return restauranteUs - Resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public RestauranteUs buscarRestauranteUsPorId(Long id) throws Exception {
				RestauranteUs restauranteUs;
				DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega un solo restauranteus a la base de datos.
			 * <b> post: </b> se ha agregado el restauranteus que entra como par�metro
			 * @param restauranteUs - el restauranteus a agregar. restauranteUs != null
			 * @throws Exception - cualquier error que se genere agregando el restauranteUs
			 */
			public void addRestauranteUs(RestauranteUs restauranteUs) throws Exception {
				DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega los restauranteus que entran como par�metro a la base de datos.
			 * <b> post: </b> se han agregado los restauranteus que entran como par�metro
			 * @param restaurantes - objeto que modela una lista de restauranteuss y se estos se pretenden agregar. restaurantes != null
			 * @throws Exception - cualquier error que se genera agregando los restaurantes
			 */
			public void addRestaurantesUs(List<RestauranteUs> restaurantes) throws Exception {
				DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n - ACID Example
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
			 * M�todo que modela la transacci�n que actualiza el restauranteus que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha actualizado el restauranteus que entra como par�metro
			 * @param restauranteUs - restauranteus a actualizar. restauranteUs != null
			 * @throws Exception - cualquier error que se genera actualizando los restauranteus
			 */
			public void updateRestauranteUs(RestauranteUs restauranteUs) throws Exception {
				DAOTablaRestauranteUs daoRestaurantesUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que elimina el restauranteus que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha eliminado el restauranteus que entra como par�metro
			 * @param restauranteUs - restauranteus a eliminar. restauranteUs != null
			 * @throws Exception - cualquier error que se genera actualizando los restauranteus
			 */
			public void deleteRestauranteUs(RestauranteUs restauranteUs) throws Exception {
				DAOTablaRestauranteUs daoRestauranteUs = new DAOTablaRestauranteUs();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que retorna todas las ordenes de la base de datos.
			 * @return ordenes - objeto que modela  un arreglo de ordenes. Este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<Orden> darOrdenes() throws Exception {
				List<Orden> ordenes;
				DAOTablaOrden daoOrdenes = new DAOTablaOrden();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que busca la orden en la base de datos con el id que entra como par�metro.
			 * @param id - Id de la orden a buscar. id != null
			 * @return orden - Resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public Orden buscarOrdenPorId(Long id) throws Exception {
				Orden orden;
				DAOTablaOrden daoOrden = new DAOTablaOrden();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega una sola orden a la base de datos.
			 * <b> post: </b> se ha agregado la orden que entra como par�metro
			 * @param orden - la orden a agregar. orden != null
			 * @throws Exception - cualquier error que se genere agregando la orden
			 */
			public void addOrden(Orden orden) throws Exception {
				DAOTablaOrden daoOrden = new DAOTablaOrden();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega las ordenes que entran como par�metro a la base de datos.
			 * <b> post: </b> se han agregado las ordenes que entran como par�metro
			 * @param ordenes - objeto que modela una lista de ordenes y que estas se pretenden agregar. ordenes != null
			 * @throws Exception - cualquier error que se genera agregando las ordenes
			 */
			public void addOrdenes(List<Orden> ordenes) throws Exception {
				DAOTablaOrden daoOrden = new DAOTablaOrden();
				try 
				{
					//////transacci�n - ACID Example
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
			 * M�todo que modela la transacci�n que actualiza la orden que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha actualizado la orden que entra como par�metro
			 * @param orden - orden a actualizar. orden != null
			 * @throws Exception - cualquier error que se genera actualizando las ordenes
			 */
			public void updateOrden(Orden orden) throws Exception {
				DAOTablaOrden daoOrdenes = new DAOTablaOrden();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que elimina la orden que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha eliminado la orden que entra como par�metro
			 * @param orden - orden a eliminar. orden != null
			 * @throws Exception - cualquier error que se genera actualizando las ordenes
			 */
			public void deleteOrden(Orden orden) throws Exception {
				DAOTablaOrden daoOrdenes = new DAOTablaOrden();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que retorna todas las reservas de la base de datos.
			 * @return reservas - objeto que modela  un arreglo de reservas. Este arreglo contiene el resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public List<Reserva> darReservas() throws Exception {
				List<Reserva> reservas;
				DAOTablaReserva daoReservas = new DAOTablaReserva();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que busca la reserva en la base de datos con el id que entra como par�metro.
			 * @param id - Id de la reserva a buscar. id != null
			 * @return reserva - Resultado de la b�squeda
			 * @throws Exception -  cualquier error que se genere durante la transacci�n
			 */
			public Reserva buscarReservaPorId(Long id) throws Exception {
				Reserva reserva;
				DAOTablaReserva daoReserva = new DAOTablaReserva();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega una sola reserva a la base de datos.
			 * <b> post: </b> se ha agregado la reserva que entra como par�metro
			 * @param reserva - la reserva a agregar. reserva != null
			 * @throws Exception - cualquier error que se genere agregando la reserva
			 */
			public void addReserva(Reserva reserva) throws Exception {
				DAOTablaReserva daoReserva = new DAOTablaReserva();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que agrega las reservas que entran como par�metro a la base de datos.
			 * <b> post: </b> se han agregado las reservas que entran como par�metro
			 * @param reservas - objeto que modela una lista de reservas y que estas se pretenden agregar. reservas != null
			 * @throws Exception - cualquier error que se genera agregando las reservas
			 */
			public void addReservas(List<Reserva> reservas) throws Exception {
				DAOTablaReserva daoReserva = new DAOTablaReserva();
				try 
				{
					//////transacci�n - ACID Example
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
			 * M�todo que modela la transacci�n que actualiza la reserva que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha actualizado la reserva que entra como par�metro
			 * @param reserva - reserva a actualizar. reserva != null
			 * @throws Exception - cualquier error que se genera actualizando las reservas
			 */
			public void updateReserva(Reserva reserva) throws Exception {
				DAOTablaReserva daoReservas = new DAOTablaReserva();
				try 
				{
					//////transacci�n
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
			 * M�todo que modela la transacci�n que elimina la reserva que entra como par�metro a la base de datos.
			 * <b> post: </b> se ha eliminado la reserva que entra como par�metro
			 * @param reserva - reserva a eliminar. reserva != null
			 * @throws Exception - cualquier error que se genera actualizando las reservas
			 */
			public void deleteReserva(Reserva reserva) throws Exception {
				DAOTablaReserva daoReservas = new DAOTablaReserva();
				try 
				{
					//////transacci�n
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
			
}