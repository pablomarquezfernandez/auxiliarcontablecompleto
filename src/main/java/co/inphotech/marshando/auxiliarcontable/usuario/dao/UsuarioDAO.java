package co.inphotech.marshando.auxiliarcontable.usuario.dao;

import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionUsuario;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Usuario;

public class UsuarioDAO {

	public static Usuario autorizacion(String codigo) throws Exception{
		Unirest.setTimeouts(6000000, 6000000);
		
		HttpResponse<String>data = null;
		try {
			
			
			String token = ConectorAutorizacionUsuario.getInstancia().getToken(codigo);
			String urlAutorizacion = ConectorAutorizacionUsuario.getInstancia().getURLAutorizacion();
			data = Unirest.post( urlAutorizacion ).header("authorization", token).asString();
			
			
			JSONObject jrta = new JSONObject( data.getBody() );
			JSONObject jusuario = jrta.getJSONObject("usuario");

			
			
			return armarUsuario(jusuario);
		} catch (Exception e) {
			try {
				e.printStackTrace();
				JSONObject jmensaje = new JSONObject( data.getBody() );
				throw new Exception( jmensaje.getString("mensaje") );
			} catch (Exception e2) {
			}
			
		}
		return null;
	}
	

	public static Usuario armarUsuario( JSONObject jusuario ) throws Exception{
		String codigoUsuario = jusuario.getString("codigo");
		String codigoEmpresa = jusuario.getString("codigoEmpresa");
		JSONObject jempresa = jusuario.getJSONObject("empresa");
//		String nombres = jusuario.getString("nombres");
//		String apellidos = jusuario.getString("apellidos");
//		String correo = jusuario.getString("correo");
		
		return new Usuario(codigoUsuario, codigoEmpresa, jempresa);
	}
}
