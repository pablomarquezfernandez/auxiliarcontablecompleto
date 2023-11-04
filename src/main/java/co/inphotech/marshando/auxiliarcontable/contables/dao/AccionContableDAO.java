package co.inphotech.marshando.auxiliarcontable.contables.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.contables.vo.AccionContable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class AccionContableDAO {

	
	public static final String SERVICIO = "/accion-contable"; 
	
	public static AccionContable getAccionContable(Empresa empresa, long id) throws Exception{
		AccionContable accionContable = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlAccionContable = System.getenv("contabilidad_url");
		HttpResponse<String>data = Unirest.get( urlAccionContable + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
//		System.out.println(  data.getBody()  );
		JSONObject jaccionContable = new JSONObject(data.getBody());
		accionContable = armarAccionContable(jaccionContable);
		
		
		return accionContable;
	}
	
	public static List<AccionContable> getAccionContables(Empresa empresa) throws Exception{
		List<AccionContable> accionContables = new ArrayList<AccionContable>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlAccionContable = System.getenv("contabilidad_url");
		HttpResponse<String>data = Unirest.get( urlAccionContable + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jaccionContables = new JSONArray(data.getBody());
		
		for( int i = 0; i < jaccionContables.length();i++ ) {
			AccionContable accionContable = armarAccionContable(jaccionContables.getJSONObject(i));
			accionContables.add(accionContable);
		}
		
		
		
		
		return accionContables;
	}
	
	public static AccionContable getAccionContable(Empresa empresa, String numeroIdentificacion) throws Exception{
		AccionContable accionContable = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlAccionContable = System.getenv("contabilidad_url");
		HttpResponse<String>data = Unirest.get( urlAccionContable + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jaccionContables = new JSONArray(data.getBody());
		
		if( jaccionContables.length() == 1 ) {
			accionContable = armarAccionContable(   jaccionContables.getJSONObject( 0 )    );
		}
		
		
		return accionContable;
	}
	
	
	

	public static AccionContable armarAccionContable(JSONObject jaccionContable) throws Exception{
		long id = jaccionContable.getLong("id");
		String nombre = "";
		if( jaccionContable.has("nombre") ) {
			nombre = jaccionContable.getString("nombre");
		}
		return new AccionContable(id, nombre, AccionContable.ESTADO_ACTIVO);
	}
}
