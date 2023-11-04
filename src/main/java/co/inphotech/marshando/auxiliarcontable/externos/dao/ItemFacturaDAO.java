package co.inphotech.marshando.auxiliarcontable.externos.dao;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.externos.vo.ImpuestoItemFactura;
import co.inphotech.marshando.auxiliarcontable.externos.vo.ItemFactura;

public class ItemFacturaDAO {

	
	

	
	public static ItemFactura armarItemFactura(JSONObject jitemFactura) throws Exception{
		
		long id = jitemFactura.getLong("id");
		
		String referencia = "";
		if( jitemFactura.has("referencia") ) {
			referencia = jitemFactura.getString("referencia");
		}
		
		
		String descripcion = "";
		if( jitemFactura.has("descripcion") ) {
			descripcion = jitemFactura.getString("descripcion");
		}
		
		double precioUnitarioBruto = 0;
		if( jitemFactura.has("precioUnitario") ) {
			precioUnitarioBruto = jitemFactura.getDouble("precioUnitario");
		}
		
		double total = 0;
		if( jitemFactura.has("total") ) {
			total = jitemFactura.getDouble("total");
		}
		
		double cantidad = 0;
		if( jitemFactura.has("cantidad") ) {
			cantidad = jitemFactura.getDouble("cantidad");
		}
		
		long idProducto = -1;
		if( jitemFactura.has("idProducto") ) {
			idProducto = jitemFactura.getLong("idProducto");
		}
		
		long idCategoriaContable = -1;
		if( jitemFactura.has("idCategoriaContable") ) {
			idCategoriaContable = jitemFactura.getLong("idCategoriaContable");
		}
		
		ItemFactura item = new ItemFactura(id, idProducto, idCategoriaContable, referencia, descripcion, precioUnitarioBruto, cantidad, total);
		
		JSONArray jimpuestosItemFactura = jitemFactura.getJSONArray("impuestosItemFactura");
		for( int i = 0; i < jimpuestosItemFactura.length();i++ ) {
			ImpuestoItemFactura impuesto = ImpuestoItemFacturaDAO.armarImpuestoItemFactura(jimpuestosItemFactura.getJSONObject(i));
			item.insertarImpuestosItemFactura(impuesto);
		}
		
		return item;
	}
	
}
