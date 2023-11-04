package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.Impuesto;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class ImpuestoDAO {

	
	public static final String SERVICIO = "/impuesto"; 
	

	
	public static List<Impuesto> getImpuestos(Empresa empresa) throws Exception{
		List<Impuesto> impuestos = new ArrayList<Impuesto>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlImpuesto = System.getenv("impuestos_url");
		HttpResponse<String>data = Unirest.get( urlImpuesto + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jimpuestos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jimpuestos.length();i++ ) {
			Impuesto impuesto = armarImpuesto(jimpuestos.getJSONObject(i));
			impuestos.add(impuesto);
		}
		
		
		
		
		return impuestos;
	}
	
	

	public static Impuesto armarImpuesto(JSONObject jimpuesto) throws Exception{
		long id = jimpuesto.getLong("id");
		String nombre = "";
		if( jimpuesto.has("nombre") ) {
			nombre = jimpuesto.getString("nombre");
		}
		String codigo = "";
		if( jimpuesto.has("codigo") ) {
			codigo = jimpuesto.getString("codigo");
		}
		
		
		
		return new Impuesto(id, nombre, codigo);
	}
}
