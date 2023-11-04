package co.inphotech.marshando.auxiliarcontable.externos.dao;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.externos.vo.ImpuestoItemNotaCredito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.ItemNotaCredito;

public class ItemNotaCreditoDAO {

	
	

	
	public static ItemNotaCredito armarItemNotaCredito(JSONObject jitemNotaCredito) throws Exception{
		
		long id = jitemNotaCredito.getLong("id");
		
		String referencia = "";
		if( jitemNotaCredito.has("referencia") ) {
			referencia = jitemNotaCredito.getString("referencia");
		}
		
		
		String descripcion = "";
		if( jitemNotaCredito.has("descripcion") ) {
			descripcion = jitemNotaCredito.getString("descripcion");
		}
		
		double precioUnitarioBruto = 0;
		if( jitemNotaCredito.has("precioUnitario") ) {
			precioUnitarioBruto = jitemNotaCredito.getDouble("precioUnitario");
		}
		
		double total = 0;
		if( jitemNotaCredito.has("total") ) {
			total = jitemNotaCredito.getDouble("total");
		}
		
		double cantidad = 0;
		if( jitemNotaCredito.has("cantidad") ) {
			cantidad = jitemNotaCredito.getDouble("cantidad");
		}
		
		long idProducto = -1;
		if( jitemNotaCredito.has("idProducto") ) {
			idProducto = jitemNotaCredito.getLong("idProducto");
		}
		
		long idCategoriaContable = -1;
		if( jitemNotaCredito.has("idCategoriaContable") ) {
			idCategoriaContable = jitemNotaCredito.getLong("idCategoriaContable");
		}
		
		ItemNotaCredito item = new ItemNotaCredito(id, idProducto, idCategoriaContable, referencia, descripcion, precioUnitarioBruto, cantidad, total);
		
		JSONArray jimpuestosItemNotaCredito = jitemNotaCredito.getJSONArray("impuestosItemNotaCredito");
		for( int i = 0; i < jimpuestosItemNotaCredito.length();i++ ) {
			ImpuestoItemNotaCredito impuesto = ImpuestoItemNotaCreditoDAO.armarImpuestoItemNotaCredito(jimpuestosItemNotaCredito.getJSONObject(i));
			item.insertarImpuestosItemNotaCredito(impuesto);
		}
		
		return item;
	}
	
}
