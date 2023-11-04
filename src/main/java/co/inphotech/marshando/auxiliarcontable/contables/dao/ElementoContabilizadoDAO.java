package co.inphotech.marshando.auxiliarcontable.contables.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.MySQLManager;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;


public class ElementoContabilizadoDAO {

	// ---------------------------------------------
	// Eliminar
	// ---------------------------------------------
	public static void actualizarEstadoElementoContabilizado(ElementoContabilizado elementoContabilizado, int estado) throws Exception{
		String sql = "UPDATE ElementoContabilizado SET estado = ? WHERE id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);		
		stm.setInt(1, estado);
		stm.setLong(2, elementoContabilizado.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}

	// ---------------------------------------------
	// Actualizar
	// ---------------------------------------------
	
	
	
	public static void actualizarElementoContabilizado(ElementoContabilizado elementoContabilizado, int tipoElemento, long idElemento,  String codigoComprobanteContable, String consecutivoComprobanteContable, int estadoContabilidad, Date fechaContabilizado, Date ultimaFechaActualizacion, String mensajeError) throws Exception{
		String sql = "UPDATE ElementoContabilizado SET tipoElemento = ?, codigoComprobanteContable = ?, estadoContabilidad = ?, fechaContabilizado = ?, consecutivoComprobanteContable = ?, ultimaFechaActualizacion = ?, mensajeError = ?,  idElemento = ? WHERE id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);		

		stm.setInt(1, tipoElemento);
		stm.setString(2,codigoComprobanteContable);
		stm.setInt(3, estadoContabilidad);
		stm.setString(4, UtilMundo.getDBDateSinHora(fechaContabilizado) );
		
		stm.setString(5, consecutivoComprobanteContable );
		stm.setString(6, UtilMundo.getDBDateSinHora(ultimaFechaActualizacion)  );
		stm.setString(7, mensajeError );
		stm.setLong(8, idElemento);
		
		stm.setLong(9, elementoContabilizado.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}
	
	public static void actualizarElementoContabilizado(ElementoContabilizado elementoContabilizado, HashMap<String, String>valores) throws Exception{
		int indice = 0;
		String sql = "UPDATE ElementoContabilizado SET ";
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
		stm.setLong(i + 1, elementoContabilizado.getId());
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);

	}
	
	// ---------------------------------------------
	// Buscar
	// ---------------------------------------------
	public static ElementoContabilizado getElementoContabilizado(List<ElementoContabilizado>elementosContabilizados, long idElemento, int tipo){
		ElementoContabilizado elementoContabilizado = null;
		boolean encontro = false;
		for(  int i = 0; i < elementosContabilizados.size() && !encontro;i++) {
			ElementoContabilizado elementoContabilizadoTemp = elementosContabilizados.get(i);
			if( elementoContabilizadoTemp.getIdElemento() == idElemento &&  elementoContabilizadoTemp.getTipoElemento() == tipo ) {
				elementoContabilizado = elementoContabilizadoTemp;
				encontro = true;
			}
		}
		return elementoContabilizado;
	}
	public static ElementoContabilizado getElementoContabilizado(long id) throws Exception{
		ElementoContabilizado elementoContabilizado = null;
		String sql = "SELECT elementoContabilizado.* "
										+ "	FROM "
										+ "			ElementoContabilizado elementoContabilizado "
										+ "	WHERE "
										+ "			elementoContabilizado.id = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setLong(1, id);
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoContabilizado = ElementoContabilizadoDAO.armarElementoContabilizado( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoContabilizado;
	}
	public static ElementoContabilizado getElementoContabilizado(long idElemento, int tipo) throws Exception{
		ElementoContabilizado elementoContabilizado = null;
		String sql = "SELECT elementoContabilizado.* "
										+ "	FROM "
										+ "			ElementoContabilizado elementoContabilizado "
										+ "	WHERE "
										+ "			elementoContabilizado.idElemento = ? AND elementoContabilizado.tipoElemento = ? ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setLong(1, idElemento);
		stm.setInt(2, tipo);
		
		
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			elementoContabilizado = ElementoContabilizadoDAO.armarElementoContabilizado( rs );
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoContabilizado;
	}
	public static List<ElementoContabilizado> getElementoContabilizados() throws Exception{
		List<ElementoContabilizado>elementoContabilizados = new ArrayList<ElementoContabilizado>();
		String sql = "SELECT elementoContabilizado.* "
									+ "		FROM "
									+ "			ElementoContabilizado elementoContabilizado "
									+ "		WHERE "
									+ "			elementoContabilizado.estado = ?";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoContabilizado.ESTADO_ACTIVO);
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.armarElementoContabilizado( rs );
			elementoContabilizados.add(elementoContabilizado);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoContabilizados;
	}
	
	public static List<ElementoContabilizado> getElementoContabilizados( Empresa empresa, int tipo, int anio, int mes ) throws Exception{
		List<ElementoContabilizado>elementoContabilizados = new ArrayList<ElementoContabilizado>();
		String sql = "SELECT elementoContabilizado.* "
									+ "		FROM "
									+ "			ElementoContabilizado elementoContabilizado "
									+ "		WHERE "
									+ "			elementoContabilizado.codigoEmpresa = ? AND"
									+ "			elementoContabilizado.estado = ? AND elementoContabilizado.tipoElemento = ? ";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		if(  anio != -1) {
			sql += "  AND YEAR(elementoContabilizado.ultimaFechaActualizacion) = ? ";
		}
		if(  mes != -1) {
			sql += "  AND MONTH(elementoContabilizado.ultimaFechaActualizacion) = ? ";
		}
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setString(1, empresa.getCodigo());
		stm.setInt(2, ElementoContabilizado.ESTADO_ACTIVO);
		stm.setInt(3, tipo);
		
		int inicio = 4;
		if(  anio != -1) {
			stm.setInt(inicio, anio);
			inicio++;
		}
		if(  mes != -1) {
			stm.setInt(inicio, mes);
			inicio++;
		}
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.armarElementoContabilizado( rs );
			elementoContabilizados.add(elementoContabilizado);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoContabilizados;
	}
	
	public static List<ElementoContabilizado> getElementoContabilizados( HashMap<String, String>valores ) throws Exception{
		List<ElementoContabilizado>elementoContabilizados = new ArrayList<ElementoContabilizado>();
		String sql = "SELECT elementoContabilizado.* "
										+ "		FROM "
										+ "				ElementoContabilizado elementoContabilizado "
										+ "		WHERE "
										+ "			elementoContabilizado.idElementoContabilizable = elementoContabilizable.id AND "
										+ "				elementoContabilizado.estado = ? ";
		Iterator<String>llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			sql += " AND " + llave + " = ? ";
		}
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setInt(1, ElementoContabilizado.ESTADO_ACTIVO);
		
		int i = 1;
		llaves = valores.keySet().iterator();
		while( llaves.hasNext() ) {
			String llave = llaves.next();
			stm.setString(1  + i, valores.get(llave));
		}
		
		
		ResultSet rs = stm.executeQuery();
		while( rs.next() )
		{
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.armarElementoContabilizado( rs );
			elementoContabilizados.add(elementoContabilizado);
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoContabilizados;
	}
	
	
	
	public static ElementoContabilizado armarElementoContabilizado(ResultSet rs) throws Exception{
		long id = rs.getLong("elementoContabilizado.id");
		long idElemento = rs.getLong("elementoContabilizado.idElemento");
		int tipoElemento = rs.getInt("elementoContabilizado.tipoElemento");
		String codigoEmpresa = rs.getString("elementoContabilizado.codigoEmpresa");
		String codigoComprobanteContable = rs.getString("elementoContabilizado.codigoComprobanteContable");
		String consecutivoComprobanteContable = rs.getString("elementoContabilizado.consecutivoComprobanteContable");
		int estadoContabilidad = rs.getInt("elementoContabilizado.estadoContabilidad");
		int estado = rs.getInt("elementoContabilizado.estado");
		
		Date fechaContabilizado = UtilMundo.getDateDBSinHora(rs.getString("elementoContabilizado.fechaContabilizado"));
		Date ultimaFechaActualizacion = UtilMundo.getDateDB(rs.getString("elementoContabilizado.ultimaFechaActualizacion"));
		String mensajeError = rs.getString("elementoContabilizado.mensajeError");
		
		return new ElementoContabilizado(id, codigoEmpresa, idElemento, tipoElemento, codigoComprobanteContable, consecutivoComprobanteContable, estadoContabilidad, fechaContabilizado, ultimaFechaActualizacion, mensajeError, estado);
	}
	// ---------------------------------------------
	// Insertar
	// ---------------------------------------------
	public static ElementoContabilizado insertarElementoContabilizado( Empresa empresa, ElementoContabilizable elementoContabilizable, String codigoComprobanteContable, String consecutivoComprobanteContable, int estadoContabilidad, Date fechaContabilizado, String mensajeError, int estado ) throws Exception{
		ElementoContabilizado elementoContabilizado = null;
		String sql = "INSERT INTO ElementoContabilizado (tipoElemento, codigoComprobanteContable, estadoContabilidad, estado, fechaContabilizado,      idElemento, consecutivoComprobanteContable, codigoEmpresa, ultimaFechaActualizacion, mensajeError) VALUES (?, ?, ?, ?, ?,     ?, ?, ?, ?, ?)";
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		
		stm.setInt(1, elementoContabilizable.getTipoElemento());
		stm.setString(2, codigoComprobanteContable);
		stm.setInt(3, estadoContabilidad);
		stm.setInt(4, estado);
		stm.setString(5, UtilMundo.getDBDateSinHora(fechaContabilizado));
		
		
		stm.setLong(6, elementoContabilizable.getIdElemento());
		stm.setString(7, consecutivoComprobanteContable);
		stm.setString(8, empresa.getCodigo());
		stm.setString(9, UtilMundo.getDBDate(elementoContabilizable.getUltimaFechaActualizacion()));
		stm.setString(10, mensajeError);
		
		stm.execute();
		
		long id = UtilMundo.getIdInsercion(conn);
		if(id != -1)
		{
			elementoContabilizado = new ElementoContabilizado(id, empresa.getCodigo(), elementoContabilizable.getIdElemento(), elementoContabilizable.getTipoElemento(), codigoComprobanteContable, consecutivoComprobanteContable, estadoContabilidad, fechaContabilizado, elementoContabilizable.getUltimaFechaActualizacion(), mensajeError, estado);
		}
		
		MySQLManager.getInstance().desconectar(conn);
		return elementoContabilizado;
	}

}
