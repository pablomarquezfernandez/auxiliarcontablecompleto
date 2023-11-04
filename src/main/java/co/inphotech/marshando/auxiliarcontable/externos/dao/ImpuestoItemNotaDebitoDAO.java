package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.List;

import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.externos.vo.ImpuestoItemNotaDebito;

public class ImpuestoItemNotaDebitoDAO {

	public static ImpuestoItemNotaDebito getImpuestoItemNotaDebito( List<ImpuestoItemNotaDebito>impuestosItemNotaDebito, String codigo  ) throws Exception{
		ImpuestoItemNotaDebito impuestoItemNotaDebito = null;
		boolean encontro = false;
		
		for( int i = 0; i < impuestosItemNotaDebito.size() && !encontro;i++ ) {
			if( impuestosItemNotaDebito.get(i).getCodigo().equals(codigo) ) {
				encontro = true;
				impuestoItemNotaDebito = impuestosItemNotaDebito.get(i);
			}
		}
		
		return impuestoItemNotaDebito;
	}
	

	
	public static ImpuestoItemNotaDebito armarImpuestoItemNotaDebito(JSONObject jimpuestosItemNotaDebito) throws Exception{
		
		long id = jimpuestosItemNotaDebito.getLong("id");
		
		String codigo = "";
		if( jimpuestosItemNotaDebito.has("codigoImpuesto") ) {
			codigo = jimpuestosItemNotaDebito.getString("codigoImpuesto");
		}
		String nombre = "";
		if( jimpuestosItemNotaDebito.has("nombre") ) {
			nombre = jimpuestosItemNotaDebito.getString("nombre");
		}
		
		
		
		double cantidad = 0;
		if( jimpuestosItemNotaDebito.has("cantidad") ) {
			cantidad = jimpuestosItemNotaDebito.getDouble("cantidad");
		}
		double porcentaje = 0;
		if( jimpuestosItemNotaDebito.has("porcentaje") ) {
			porcentaje = jimpuestosItemNotaDebito.getDouble("porcentaje");
		}
		double valorUnitario = 0;
		if( jimpuestosItemNotaDebito.has("valorUnitario") ) {
			valorUnitario = jimpuestosItemNotaDebito.getDouble("valorUnitario");
		}
		
		
		
		return new ImpuestoItemNotaDebito(id, codigo, nombre, porcentaje, cantidad, valorUnitario);
	}
	
}
