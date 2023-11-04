package co.inphotech.marshando.auxiliarcontable.conectores;

import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

public class ConectorAutorizacionUsuario {

	
	private static ConectorAutorizacionUsuario conectorAutorizacionUsuarioAPI;
	
	private String urlAutorizacionUsuario = null;
	private String urlAutorizacion = null;
	private String token = null;
	
	
	private ConectorAutorizacionUsuario() throws Exception{
		urlAutorizacionUsuario = System.getenv("autorizacion_urlAutorizacionUsuario");
		urlAutorizacion = System.getenv("autorizacion_urlAutorizacion");
	}
	public String getToken(String codigo) throws Exception{
		cargarToken(codigo);
		return token;
	}
	public String getURLAutorizacion() throws Exception{
		return urlAutorizacion;
	}
	public static ConectorAutorizacionUsuario getInstancia() throws Exception{
		if(  conectorAutorizacionUsuarioAPI == null ) {
			conectorAutorizacionUsuarioAPI = new ConectorAutorizacionUsuario();
		}
		return conectorAutorizacionUsuarioAPI;
	}
	
	
	
	
	public void cargarToken(String codigo) throws Exception{
		Unirest.setTimeouts(6000000, 6000000);
		HttpResponse<String>data = Unirest.post( urlAutorizacionUsuario  ).field("codigo", codigo).asString();
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jtojen = new JSONObject(data.getBody());
		token = jtojen.getString("password");
	}

	
	
}
