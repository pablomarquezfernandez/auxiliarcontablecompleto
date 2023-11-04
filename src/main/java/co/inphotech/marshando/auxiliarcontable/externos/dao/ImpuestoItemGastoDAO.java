package co.inphotech.marshando.auxiliarcontable.externos.dao;

import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.externos.vo.ImpuestoItemFactura;

public class ImpuestoItemGastoDAO {

//	public static ImpuestoItemFactura getImpuestoItemFactura( List<ImpuestoItemFactura>  ) throws Exception{
//		
//	}
	

	
	public static ImpuestoItemFactura armarImpuestoItemFactura(JSONObject jimpuestosItemFactura) throws Exception{
		
		long id = jimpuestosItemFactura.getLong("id");
		
		String codigo = "";
		if( jimpuestosItemFactura.has("codigoImpuesto") ) {
			codigo = jimpuestosItemFactura.getString("codigoImpuesto");
		}
		String nombre = "";
		if( jimpuestosItemFactura.has("nombre") ) {
			nombre = jimpuestosItemFactura.getString("nombre");
		}
		
		
		
		double cantidad = 0;
		if( jimpuestosItemFactura.has("cantidad") ) {
			cantidad = jimpuestosItemFactura.getDouble("cantidad");
		}
		double porcentaje = 0;
		if( jimpuestosItemFactura.has("porcentaje") ) {
			porcentaje = jimpuestosItemFactura.getDouble("porcentaje");
		}
		double valorUnitario = 0;
		if( jimpuestosItemFactura.has("valorUnitario") ) {
			valorUnitario = jimpuestosItemFactura.getDouble("valorUnitario");
		}
		
		
		
		return new ImpuestoItemFactura(id, codigo, nombre, porcentaje, cantidad, valorUnitario);
	}
	
}
