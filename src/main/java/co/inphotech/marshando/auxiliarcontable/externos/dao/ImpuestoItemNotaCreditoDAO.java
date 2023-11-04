package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.List;

import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.externos.vo.ImpuestoItemNotaCredito;

public class ImpuestoItemNotaCreditoDAO {

	public static ImpuestoItemNotaCredito getImpuestoItemNotaCredito( List<ImpuestoItemNotaCredito>impuestosItemNotaCredito, String codigo  ) throws Exception{
		ImpuestoItemNotaCredito impuestoItemNotaCredito = null;
		boolean encontro = false;
		
		for( int i = 0; i < impuestosItemNotaCredito.size() && !encontro;i++ ) {
			if( impuestosItemNotaCredito.get(i).getCodigo().equals(codigo) ) {
				encontro = true;
				impuestoItemNotaCredito = impuestosItemNotaCredito.get(i);
			}
		}
		
		return impuestoItemNotaCredito;
	}
	

	
	public static ImpuestoItemNotaCredito armarImpuestoItemNotaCredito(JSONObject jimpuestosItemNotaCredito) throws Exception{
		
		long id = jimpuestosItemNotaCredito.getLong("id");
		
		String codigo = "";
		if( jimpuestosItemNotaCredito.has("codigoImpuesto") ) {
			codigo = jimpuestosItemNotaCredito.getString("codigoImpuesto");
		}
		String nombre = "";
		if( jimpuestosItemNotaCredito.has("nombre") ) {
			nombre = jimpuestosItemNotaCredito.getString("nombre");
		}
		
		
		
		double cantidad = 0;
		if( jimpuestosItemNotaCredito.has("cantidad") ) {
			cantidad = jimpuestosItemNotaCredito.getDouble("cantidad");
		}
		double porcentaje = 0;
		if( jimpuestosItemNotaCredito.has("porcentaje") ) {
			porcentaje = jimpuestosItemNotaCredito.getDouble("porcentaje");
		}
		double valorUnitario = 0;
		if( jimpuestosItemNotaCredito.has("valorUnitario") ) {
			valorUnitario = jimpuestosItemNotaCredito.getDouble("valorUnitario");
		}
		
		
		
		return new ImpuestoItemNotaCredito(id, codigo, nombre, porcentaje, cantidad, valorUnitario);
	}
	
}
