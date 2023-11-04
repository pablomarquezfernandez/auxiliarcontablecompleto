package co.inphotech.marshando.auxiliarcontable.contables.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoConfiguracion;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.MySQLManager;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;


public class ElementoConfiguracionDAO {

	// ---------------------------------------------
	// Eliminar
	// ---------------------------------------------
	public static void actualizarEstadoElementoConfiguracion(ElementoConfiguracion elementoConfiguracion, int estado) throws Exception{
		String sql = "UPDATE ElementoConfiguracion SET estado = ? WHERE id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);		
		stm.setInt(1, estado);
		stm.setLong(2, elementoConfiguracion.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}

	// ---------------------------------------------
	// Actualizar
	// ---------------------------------------------
	
	
	
	public static void actualizarElementoConfiguracion(ElementoConfiguracion elementoConfiguracion, String codigoEmpresa, long idElementoConfiguracion, String nombre, int tipo) throws Exception{
		String sql = "UPDATE ElementoConfiguracion SET codigoEmpresa = ?, nombre = ?, tipo = ?, idElementoConfiguracion = ? WHERE id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);		

		stm.setString(1,codigoEmpresa);
		stm.setString(2,nombre);
		stm.setInt(3, tipo);
		stm.setLong(4, idElementoConfiguracion);

		
		stm.setLong(4, elementoConfiguracion.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}
	
	public static void actualizarElementoConfiguracion(ElementoConfiguracion elementoConfiguracion, HashMap<String, String>valores) throws Exception{
		int indice = 0;
		String sql = "UPDATE ElementoConfiguracion SET ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		Iterator<String>llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			if( indice == 0 ) {
				sql += llave + " = ? ";
			} else {
				sql += ", " + llave + " = ? ";
			}
			indice++;
		}
		sql += "WHERE id = ?";
		
		PreparedStatement stm = conn.prepareStatement(sql);		


		int i = 1;
		llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			stm.setString(i, valores.get(llave));
			i++;
		}
		stm.setLong(i + 1, elementoConfiguracion.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}
	
	// ---------------------------------------------
	// Buscar
	// ---------------------------------------------
	
	public static ElementoConfiguracion getElementoConfiguracion( Empresa empresa, long id) throws Exception{
		ElementoConfiguracion elementoConfiguracion = null;
		String sql = "SELECT elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion WHERE elementoConfiguracion.id = ? AND elementoConfiguracion.codigoEmpresa = ? ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setLong(1, id);
		stm.setString(2, empresa.getCodigo());
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoConfiguracion = ElementoConfiguracionDAO.armarElementoConfiguracion( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoConfiguracion;
	}
	public static ElementoConfiguracion getElementoConfiguracion( Empresa empresa, long idElementoConfiguracion, int tipo) throws Exception{
		ElementoConfiguracion elementoConfiguracion = null;
		String sql = "SELECT elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion WHERE elementoConfiguracion.idElementoConfiguracion = ? AND elementoConfiguracion.tipo = ? AND elementoConfiguracion.codigoEmpresa = ? ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setLong(1, idElementoConfiguracion);
		stm.setInt(2, tipo);
		stm.setString(3, empresa.getCodigo());
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoConfiguracion = ElementoConfiguracionDAO.armarElementoConfiguracion( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoConfiguracion;
	}
	public static List<ElementoConfiguracion> getElementoConfiguracions(Empresa empresa, int tipo) throws Exception{
		List<ElementoConfiguracion>elementoConfiguracions = new ArrayList<ElementoConfiguracion>();
		String sql = "SELECT elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion WHERE elementoConfiguracion.estado = ? AND elementoConfiguracion.codigoEmpresa = ? AND elementoConfiguracion.tipo = ? ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoConfiguracion.ESTADO_ACTIVO);
		stm.setString(2, empresa.getCodigo());
		stm.setInt(2, tipo);
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoConfiguracion elementoConfiguracion = ElementoConfiguracionDAO.armarElementoConfiguracion( rs );
			elementoConfiguracions.add(elementoConfiguracion);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoConfiguracions;
	}
	public static List<ElementoConfiguracion> getElementoConfiguracions(Empresa empresa) throws Exception{
		List<ElementoConfiguracion>elementoConfiguracions = new ArrayList<ElementoConfiguracion>();
		String sql = "SELECT elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion WHERE elementoConfiguracion.estado = ? AND elementoConfiguracion.codigoEmpresa = ? ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoConfiguracion.ESTADO_ACTIVO);
		stm.setString(2, empresa.getCodigo());
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoConfiguracion elementoConfiguracion = ElementoConfiguracionDAO.armarElementoConfiguracion( rs );
			elementoConfiguracions.add(elementoConfiguracion);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoConfiguracions;
	}
	public static List<ElementoConfiguracion> getElementoConfiguracions( Empresa empresa, HashMap<String, String>valores ) throws Exception{
		List<ElementoConfiguracion>elementoConfiguracions = new ArrayList<ElementoConfiguracion>();
		String sql = "SELECT elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion WHERE elementoConfiguracion.estado = ? AND elementoConfiguracion.codigoEmpresa = ? ";
		Iterator<String>llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			sql += " AND " + llave + " = ? ";
		}
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoConfiguracion.ESTADO_ACTIVO);
		stm.setString(2, empresa.getCodigo());
		
		int i = 2;
		llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			stm.setString(1  + i, valores.get(llave));
		}
		
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoConfiguracion elementoConfiguracion = ElementoConfiguracionDAO.armarElementoConfiguracion( rs );
			elementoConfiguracions.add(elementoConfiguracion);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoConfiguracions;
	}
	
	
	
	public static ElementoConfiguracion armarElementoConfiguracion(ResultSet rs) throws Exception{
		long id = rs.getLong("elementoConfiguracion.id");
		long idElementoConfiguracion = rs.getLong("elementoConfiguracion.idElementoConfiguracion");
		String codigoEmpresa = rs.getString("elementoConfiguracion.codigoEmpresa");
		String nombre = rs.getString("elementoConfiguracion.nombre");
		int tipo = rs.getInt("elementoConfiguracion.tipo");
		int estado = rs.getInt("elementoConfiguracion.estado");

		return new ElementoConfiguracion(id, codigoEmpresa, idElementoConfiguracion, nombre, tipo, estado);
	}
	// ---------------------------------------------
	// Insertar
	// ---------------------------------------------
	public static ElementoConfiguracion insertarElementoConfiguracion( Empresa empresa, long idElementoConfiguracion, String nombre, int tipo, int estado ) throws Exception{
		ElementoConfiguracion elementoConfiguracion = null;
		String sql = "INSERT INTO ElementoConfiguracion (codigoEmpresa, nombre, tipo, estado, idElementoConfiguracion) VALUES (?, ?, ?, ?, ?)";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1,empresa.getCodigo());
		stm.setString(2,nombre);
		stm.setInt(3, tipo);
		stm.setInt(4, estado);
		stm.setLong(5, idElementoConfiguracion);
		
		stm.execute();
		
		long id = UtilMundo.getIdInsercion(conn);
		if(id != -1)
		{
			elementoConfiguracion = new ElementoConfiguracion(id, empresa.getCodigo(), idElementoConfiguracion, nombre, tipo, estado);
		}
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoConfiguracion;
	}

}
