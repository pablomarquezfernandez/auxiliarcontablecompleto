package co.inphotech.marshando.auxiliarcontable.contables.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import co.inphotech.marshando.auxiliarcontable.contables.vo.AccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoConfiguracion;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.MySQLManager;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;


public class ElementoAccionContableDAO {

	// ---------------------------------------------
	// Eliminar
	// ---------------------------------------------
	public static void actualizarEstadoElementoAccionContable(ElementoAccionContable elementoAccionContable, int estado) throws Exception{
		String sql = "UPDATE ElementoAccionContable SET estado = ? WHERE id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);		
		stm.setInt(1, estado);
		stm.setLong(2, elementoAccionContable.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}

	// ---------------------------------------------
	// Actualizar
	// ---------------------------------------------
	
	
	
	public static void actualizarElementoAccionContable(ElementoAccionContable elementoAccionContable, String codigoEmpresa, ElementoConfiguracion elemento, AccionContable accionContable ) throws Exception{
		String sql = "UPDATE ElementoAccionContable SET codigoEmpresa = ?, idElemento = ?, idAccionContable = ?, nombreAccionContable = ? WHERE id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);		

		stm.setString(1,codigoEmpresa);
		stm.setLong(2,elemento.getId());
		stm.setLong(3,accionContable.getId());
		stm.setString(4,accionContable.getNombre());
		
		stm.setLong(5, elementoAccionContable.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}
	
	public static void actualizarElementoAccionContable(ElementoAccionContable elementoAccionContable, HashMap<String, String>valores) throws Exception{
		int indice = 0;
		String sql = "UPDATE ElementoAccionContable SET ";
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
		stm.setLong(i + 1, elementoAccionContable.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}
	
	// ---------------------------------------------
	// Buscar
	// ---------------------------------------------
	public static ElementoAccionContable getElementoAccionContable( List<ElementoAccionContable>elementosAccionContable, int tipo, long idElemento ) throws Exception{
		ElementoAccionContable elementoAccionContable = null;
		boolean encontro = false;
		for( int i = 0; i < elementosAccionContable.size()  && !encontro;i++ ) {
			ElementoAccionContable elementoAccionContableTemp = elementosAccionContable.get(i);
			if( elementoAccionContableTemp.getElemento().getIdElemento() == idElemento && elementoAccionContableTemp.getElemento().getTipo() == tipo ) {
				elementoAccionContable = elementoAccionContableTemp;
				encontro = true;
			}
		}
		return elementoAccionContable;
	}
	
	public static ElementoAccionContable getElementoAccionContable(Empresa empresa, long id) throws Exception{
		ElementoAccionContable elementoAccionContable = null;
		String sql = "SELECT elementoAccionContable.*, elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion, ElementoAccionContable elementoAccionContable WHERE elementoConfiguracion.id = elementoAccionContable.idElemento AND elementoAccionContable.id = ? AND elementoAccionContable.codigoEmpresa = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setLong(1, id);
		stm.setString(2, empresa.getCodigo());
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoAccionContable = ElementoAccionContableDAO.armarElementoAccionContable( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContable;
	}
	public static ElementoAccionContable getElementoAccionContable(Empresa empresa, ElementoConfiguracion elemento) throws Exception{
		ElementoAccionContable elementoAccionContable = null;
		String sql = "SELECT elementoAccionContable.*, elementoConfiguracion.* "
												+ "		FROM "
												+ "				ElementoConfiguracion elementoConfiguracion, ElementoAccionContable elementoAccionContable "
												+ "		WHERE "
												+ "				elementoConfiguracion.id = elementoAccionContable.idElemento AND "
												+ "				elementoAccionContable.codigoEmpresa = ? AND "
												+ "				elementoAccionContable.idElemento = ? AND elementoConfiguracion.tipo = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, empresa.getCodigo());
		stm.setLong(2, elemento.getId());
		stm.setInt(3, elemento.getTipo());
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoAccionContable = ElementoAccionContableDAO.armarElementoAccionContable( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContable;
	}
	public static ElementoAccionContable getElementoAccionContable(Empresa empresa, long idElemento, int tipo) throws Exception{
		ElementoAccionContable elementoAccionContable = null;
		String sql = "SELECT elementoAccionContable.*, elementoConfiguracion.* "
												+ "		FROM "
												+ "				ElementoConfiguracion elementoConfiguracion, ElementoAccionContable elementoAccionContable "
												+ "		WHERE "
												+ "				elementoConfiguracion.id = elementoAccionContable.idElemento AND "
												+ "				elementoAccionContable.codigoEmpresa = ? AND "
												+ "				elementoConfiguracion.idElemento = ? AND elementoConfiguracion.tipo = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, empresa.getCodigo());
		stm.setLong(2, idElemento);
		stm.setInt(3, tipo);
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoAccionContable = ElementoAccionContableDAO.armarElementoAccionContable( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContable;
	}
	public static List<ElementoAccionContable> getElementoAccionContables(Empresa empresa, int tipo) throws Exception{
		List<ElementoAccionContable>elementoAccionContables = new ArrayList<ElementoAccionContable>();
		String sql = "SELECT elementoAccionContable.*, elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion, ElementoAccionContable elementoAccionContable WHERE elementoConfiguracion.id = elementoAccionContable.idElemento AND elementoAccionContable.estado = ? AND elementoAccionContable.codigoEmpresa = ? AND elementoConfiguracion.tipo = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoAccionContable.ESTADO_ACTIVO);
		stm.setString(2, empresa.getCodigo());
		stm.setInt(3, tipo);
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.armarElementoAccionContable( rs );
			elementoAccionContables.add(elementoAccionContable);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContables;
	}
	
	public static List<ElementoAccionContable> getElementoAccionContables(Empresa empresa) throws Exception{
		List<ElementoAccionContable>elementoAccionContables = new ArrayList<ElementoAccionContable>();
		String sql = "SELECT elementoAccionContable.*, elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion, ElementoAccionContable elementoAccionContable WHERE elementoConfiguracion.id = elementoAccionContable.idElemento AND elementoAccionContable.estado = ? AND elementoAccionContable.codigoEmpresa = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoAccionContable.ESTADO_ACTIVO);
		stm.setString(2, empresa.getCodigo());
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.armarElementoAccionContable( rs );
			elementoAccionContables.add(elementoAccionContable);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContables;
	}
	public static List<ElementoAccionContable> getElementoAccionContables( Empresa empresa, HashMap<String, String>valores ) throws Exception{
		List<ElementoAccionContable>elementoAccionContables = new ArrayList<ElementoAccionContable>();
		String sql = "SELECT elementoAccionContable.*, elementoConfiguracion.* FROM ElementoConfiguracion elementoConfiguracion, ElementoAccionContable elementoAccionContable WHERE elementoConfiguracion.id = elementoAccionContable.idElemento AND elementoAccionContable.estado = ? AND elementoAccionContable.codigoEmpresa = ? ";
		Iterator<String>llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			sql += " AND " + llave + " = ? ";
		}
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoAccionContable.ESTADO_ACTIVO);
		stm.setString(2, empresa.getCodigo());
		
		
		int i = 1;
		llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			stm.setString(1  + i, valores.get(llave));
		}
		
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.armarElementoAccionContable( rs );
			elementoAccionContables.add(elementoAccionContable);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContables;
	}
	
	
	
	public static ElementoAccionContable armarElementoAccionContable(ResultSet rs) throws Exception{
		long id = rs.getLong("elementoAccionContable.id");
		String codigoEmpresa = rs.getString("elementoAccionContable.codigoEmpresa");
		
		long idAccionContable = rs.getLong("elementoAccionContable.idAccionContable");
		String nombreAccionContable = rs.getString("elementoAccionContable.nombreAccionContable");
		int estado = rs.getInt("elementoAccionContable.estado");

		ElementoConfiguracion elemento = ElementoConfiguracionDAO.armarElementoConfiguracion(rs);
		
		return new ElementoAccionContable(id, codigoEmpresa, elemento, idAccionContable, nombreAccionContable, estado);
	}
	// ---------------------------------------------
	// Insertar
	// ---------------------------------------------
	public static ElementoAccionContable insertarElementoAccionContable( Empresa empresa, ElementoConfiguracion elemento, AccionContable accionContable, int estado ) throws Exception{
		ElementoAccionContable elementoAccionContable = null;
		String sql = "INSERT INTO ElementoAccionContable (codigoEmpresa, idElemento, idAccionContable, nombreAccionContable, estado) VALUES (?, ?, ?, ?, ?)";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1,empresa.getCodigo());
		stm.setLong(2,elemento.getId());
		stm.setLong(3,accionContable.getId());
		stm.setString(4,accionContable.getNombre());
		stm.setInt(5, estado);

		
		stm.execute();
		
		long id = UtilMundo.getIdInsercion(conn);
		if(id != -1)
		{
			elementoAccionContable = new ElementoAccionContable(id, empresa.getCodigo(), elemento, accionContable.getId(), accionContable.getNombre(), estado);
		}
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoAccionContable;
	}

}
