package co.inphotech.marshando.auxiliarcontable.util;

import java.sql.Connection;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;


public class MySQLManager {
	public static final String ABIERTA = "ABIERTA";
	public static final String CERRADA = "CERRADA";
	public static final String DANIADA = "DANIADA";
	// ----------------------------
		// Atributos
		// ----------------------------

//		private static final long TIEMPO_APERTURA = 5000;
		/**
		 * Instancia.
		 */
		private static MySQLManager instance;

		/**
		 * Data source donde estan las conexiones.
		 */
		private DataSource ds;

		private Hashtable<Connection, String> conexiones;
		
		
		private String usuario;
		private String contrasena;
		private String host;
		private String baseDatos;

		// ----------------------------
		// Constructor
		// ----------------------------
		/**
		 * Constructor del Manejador de Conexiones.
		 */
		private MySQLManager() {
			try {
				conexiones = new Hashtable<Connection, String>();
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				
//				Properties propiedades = new Properties();;
//				InputStream in = MySQLManager.class.getResourceAsStream("/resources/db.properties");
//				propiedades.load(in);
				
				
				
				usuario = System.getenv("db_usuario");
				contrasena = System.getenv("db_contrasena");
				host = System.getenv("db_host");
				baseDatos = System.getenv("db_basedatos_auxiliar_contable");
//				System.out.println(usuario + " -- " + contrasena + " -- " + baseDatos + " -- " + host);
				
				connectDB();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		


		private void connectDB() {
			// AMAZON TRIBUNA
			try {
				ds = setupDataSource("jdbc:mysql://" + host + "/" + baseDatos+ "?autoReconnectForPools=true&autoReconnect=true",usuario, contrasena,20 ,70, 80 );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// ----------------------------
		// M�todos
		// ----------------------------
		/**
		 * Devuelve la conexi�n a la base de datos.
		 * 
		 * @return La conexi�n a la base de datos.
		 * @throws Exception
		 *             Lanza esta excepci�n en caso de no poder dar una conexi�n
		 */
		public Connection darConexion(String sql) throws Exception {
			//String id = Calendar.getInstance().getTimeInMillis() + "";
			// Get connection from pooled data source
			try {
				Connection conn = ds.getConnection();
				
//				long time = Calendar.getInstance().getTimeInMillis();
//				
//				conexiones.put(conn,  ""+time);
//				String sqlTemp = "INSERT INTO Conexiones (sql_insertar, estado, idConexiones) VALUES (?,?,?)";
//				PreparedStatement stm = conn.prepareStatement(sqlTemp);
//				stm.setString(1, sql);
//				stm.setString(2, MySQLManager.ABIERTA);
//				stm.setLong(3, time);;
//				stm.execute();
//				stm.close();


				
				return conn;
			} catch (Exception e) {
				e.printStackTrace();
				connectDB();
				return ds.getConnection();
			}
		}

		// ----------------------------
		// Metodos del singleton
		// ----------------------------
		/**
		 * Devuelve la instancia de Connection Manager.
		 * 
		 * @return La instancia del connection manager.
		 */
		public static MySQLManager getInstance() {
			if (instance == null) {
				instance = new MySQLManager();
			}
			return instance;
		}


		public void desconectar(Connection conn) throws Exception {
//			String data = "";
//
//			Enumeration<Connection> conns = conexiones.keys();
//			boolean encontro = false;
//			while (!encontro && conns.hasMoreElements()) {
//				Connection connTemp = conns.nextElement();
//				if (conn == connTemp) {
//					data = conexiones.get(conn);
//					conexiones.remove(conn);
//				}
//			}
//			try {
//				PreparedStatement stm = conn.prepareStatement("DELETE FROM Conexiones WHERE idConexiones = ?");
//				stm.setLong(1, Long.parseLong(data));
//				stm.execute();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
////			

			conn.close();
		}

		/**
		 * Crea un Pool de conexiones utilizando DBCP de apache
		 * 
		 * @param connectURI
		 * @param username
		 * @param password
		 * @param min
		 * @param max
		 * @return
		 */
		private DataSource setupDataSource(String connectURI, String username,
				String password, int minIdle, int maxActive, int maxIdle) {

			//
			// First, we'll need a ObjectPool that serves as the
			// actual pool of connections.
			//
			// We'll use a GenericObjectPool instance, although
			// any ObjectPool implementation will suffice.
			//
			GenericObjectPool connectionPool = new GenericObjectPool(null);
			connectionPool.setMinIdle(minIdle);
			connectionPool.setMaxActive(maxActive);
			connectionPool.setMaxIdle(maxIdle);
			connectionPool.setTestOnBorrow(true);
			connectionPool.setMinEvictableIdleTimeMillis(5000);

			//
			// Next, we'll create a ConnectionFactory that the
			// pool will use to create Connections.
			// We'll use the DriverManagerConnectionFactory,
			// using the connect string passed in the command line
			// arguments.
			//
			ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
					connectURI, username, password);

			//
			// Now we'll create the PoolableConnectionFactory, which wraps
			// the "real" Connections created by the ConnectionFactory with
			// the classes that implement the pooling functionality.
			//
			PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
					connectionFactory, connectionPool, null, null, false, true);
			poolableConnectionFactory.setValidationQuery("SELECT 1+1;");

			//
			// Finally, we create the PoolingDriver itself,
			// passing in the object pool we created.
			//
			PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

			return dataSource;
		}

//		public void cerrarConexionesAbiertas() {
//			try {
//				Enumeration<Connection> conns = conexiones.keys();
//				while (conns.hasMoreElements()) {
//					Connection connTemp = conns.nextElement();
//					String[] data = conexiones.get(connTemp).split("-");
//					long tiempoInicial = Long.parseLong(data[1]);
//					long tiepoFinal = Calendar.getInstance().getTimeInMillis();
//					long tiempoAlAire = tiepoFinal - tiempoInicial;
//					if (tiempoAlAire > TIEMPO_APERTURA) {
//						closeConnection(connTemp, "", MySQLManager.DANIADA);
//					}
//				}
//			} catch (Exception e) {}
//		}

		public int conexionesAbiertas() {
			return conexiones.size();
		}
}
