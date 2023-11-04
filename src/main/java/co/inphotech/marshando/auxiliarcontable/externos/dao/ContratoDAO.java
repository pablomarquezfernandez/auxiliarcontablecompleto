package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.Contrato;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class ContratoDAO {

	
	public static final String SERVICIO = "/contrato"; 
	
	public static Contrato getContrato(Empresa empresa, long id) throws Exception{
		Contrato contrato = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlContrato = System.getenv("nomina_url");
		HttpResponse<String>data = Unirest.get( urlContrato + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jcontrato = new JSONObject(data.getBody());
		contrato = armarContrato(jcontrato);
		
		
		return contrato;
	}
	
	public static List<Contrato> getContratos(Empresa empresa) throws Exception{
		List<Contrato> contratos = new ArrayList<Contrato>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlContrato = System.getenv("nomina_url");
		HttpResponse<String>data = Unirest.get( urlContrato + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jcontratos = new JSONArray(data.getBody());
		
		for( int i = 0; i < jcontratos.length();i++ ) {
			Contrato contrato = armarContrato(jcontratos.getJSONObject(i));
			contratos.add(contrato);
		}
		
		
		
		
		return contratos;
	}
	
	

	public static Contrato armarContrato(JSONObject jcontrato) throws Exception{
		long id = jcontrato.getLong("id");
		String nombre = "";
		if( jcontrato.has("nombre") ) {
			nombre = jcontrato.getString("nombre");
		}
		String codigo = id + "";
		
		
		return new Contrato(id, nombre, codigo);
	}
}
