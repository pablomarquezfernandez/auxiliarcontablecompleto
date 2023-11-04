package co.inphotech.marshando.auxiliarcontable.externos.dao;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.externos.vo.ImpuestoItemNotaDebito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.ItemNotaDebito;

public class ItemNotaDebitoDAO {

	
	

	
	public static ItemNotaDebito armarItemNotaDebito(JSONObject jitemNotaDebito) throws Exception{
		
		long id = jitemNotaDebito.getLong("id");
		
		String referencia = "";
		if( jitemNotaDebito.has("referencia") ) {
			referencia = jitemNotaDebito.getString("referencia");
		}
		
		
		String descripcion = "";
		if( jitemNotaDebito.has("descripcion") ) {
			descripcion = jitemNotaDebito.getString("descripcion");
		}
		
		double precioUnitarioBruto = 0;
		if( jitemNotaDebito.has("precioUnitario") ) {
			precioUnitarioBruto = jitemNotaDebito.getDouble("precioUnitario");
		}
		
		double total = 0;
		if( jitemNotaDebito.has("total") ) {
			total = jitemNotaDebito.getDouble("total");
		}
		
		double cantidad = 0;
		if( jitemNotaDebito.has("cantidad") ) {
			cantidad = jitemNotaDebito.getDouble("cantidad");
		}
		
		long idProducto = -1;
		if( jitemNotaDebito.has("idProducto") ) {
			idProducto = jitemNotaDebito.getLong("idProducto");
		}
		
		long idCategoriaContable = -1;
		if( jitemNotaDebito.has("idCategoriaContable") ) {
			idCategoriaContable = jitemNotaDebito.getLong("idCategoriaContable");
		}
		
		ItemNotaDebito item = new ItemNotaDebito(id, idProducto, idCategoriaContable, referencia, descripcion, precioUnitarioBruto, cantidad, total);
		
		JSONArray jimpuestosItemNotaDebito = jitemNotaDebito.getJSONArray("impuestosItemNotaDebito");
		for( int i = 0; i < jimpuestosItemNotaDebito.length();i++ ) {
			ImpuestoItemNotaDebito impuesto = ImpuestoItemNotaDebitoDAO.armarImpuestoItemNotaDebito(jimpuestosItemNotaDebito.getJSONObject(i));
			item.insertarImpuestosItemNotaDebito(impuesto);
		}
		
		return item;
	}
	
}
