package co.inphotech.marshando.auxiliarcontable.conectores;

import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ConectorAutorizacionAplicacion {

	
	private static ConectorAutorizacionAplicacion conectorAutorizacionUsuarioAPI;
	
	private String urlAutorizacionAplicacion = null;
	private String urlAutorizacion = null;
	private String codigo = null;
	private String token = null;
	
	
	private ConectorAutorizacionAplicacion() throws Exception{
		urlAutorizacionAplicacion = System.getenv("autorizacion_urlAutorizacionAplicacion");
		urlAutorizacion = System.getenv("autorizacion_urlAutorizacion");
		codigo = System.getenv("autorizacion_codigo_auxiliar_contable");
		
//		urlAutorizacionAplicacion = "https://api.marshando.com/api/autorizacion/autenticar-aplicacion";
//		urlAutorizacion = "https://api.marshando.com/api/autorizacion/autorizacion";
//		codigo = "fd77f34f9fbfe682040a26d481902e5b873cf3f4";
	}
	public String getToken(String codigoEmpresa) throws Exception{
		cargarToken(codigo, codigoEmpresa);
		return token;
	}
	public String getURLAutorizacion() throws Exception{
		return urlAutorizacion;
	}
	public static ConectorAutorizacionAplicacion getInstancia() throws Exception{
		if(  conectorAutorizacionUsuarioAPI == null ) {
			conectorAutorizacionUsuarioAPI = new ConectorAutorizacionAplicacion();
		}
		return conectorAutorizacionUsuarioAPI;
	}
	
	
	
	
	public void cargarToken(String codigo, String codigoEmpresa) throws Exception{
		Unirest.setTimeouts(6000000, 6000000);
		HttpResponse<String>data = Unirest.post( urlAutorizacionAplicacion  ).field("codigo", codigo).field("codigoEmpresa", codigoEmpresa).asString();
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jtojen = new JSONObject(data.getBody());
		token = jtojen.getString("password");
	}

	
	
}
