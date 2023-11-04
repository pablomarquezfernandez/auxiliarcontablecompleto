package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.GastoDefecto;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class GastoDefectoDAO {

	
	public static final String SERVICIO = "/gasto-defecto"; 
	
	public static GastoDefecto getGastoDefecto(Empresa empresa, long id) throws Exception{
		GastoDefecto gastoDefecto = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlGastoDefecto = System.getenv("gastos_url");
		HttpResponse<String>data = Unirest.get( urlGastoDefecto + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jgastoDefecto = new JSONObject(data.getBody());
		gastoDefecto = armarGastoDefecto(jgastoDefecto);
		
		
		return gastoDefecto;
	}
	
	public static List<GastoDefecto> getGastoDefectos(Empresa empresa) throws Exception{
		List<GastoDefecto> gastoDefectos = new ArrayList<GastoDefecto>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlGastoDefecto = System.getenv("gastos_url");
		HttpResponse<String>data = Unirest.get( urlGastoDefecto + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jgastoDefectos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jgastoDefectos.length();i++ ) {
			GastoDefecto gastoDefecto = armarGastoDefecto(jgastoDefectos.getJSONObject(i));
			gastoDefectos.add(gastoDefecto);
		}
		
		
		
		
		return gastoDefectos;
	}
	
	

	public static GastoDefecto armarGastoDefecto(JSONObject jgastoDefecto) throws Exception{
		long id = jgastoDefecto.getLong("id");
		String nombre = "";
		if( jgastoDefecto.has("nombre") ) {
			nombre = jgastoDefecto.getString("nombre");
		}
		String codigo = id + "";
		
		
		return new GastoDefecto(id, nombre, codigo);
	}
}
