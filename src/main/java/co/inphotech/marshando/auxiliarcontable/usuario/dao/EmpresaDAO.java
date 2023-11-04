package co.inphotech.marshando.auxiliarcontable.usuario.dao;

import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionUsuario;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class EmpresaDAO {

	
	public static Empresa getEmpresa(String token) throws Exception{
		Unirest.setTimeouts(6000000, 6000000);
		
		HttpResponse<String>data = null;
		try {
			String urlAutorizacion = ConectorAutorizacionUsuario.getInstancia().getURLAutorizacion();
			data = Unirest.post( urlAutorizacion ).header("Authorization", token).asString();
			
			
			JSONObject jrta = new JSONObject( data.getBody() );
			JSONObject jempresa = jrta.getJSONObject("empresa");
			
			String codigo = jempresa.getString("codigoMarshando");
			String nombre = jempresa.getString("nombre");
			String nit = jempresa.getString("nit");
			String digitoVerificacion = jempresa.getString("digitoVerificacion");
			String direccion = jempresa.getString("direccion");
			String correoElectronico = jempresa.getString("correoElectronico");
			String telefono = jempresa.getString("telefono");
			String imagen = jempresa.getString("imagenCuadrado");
			
			return new Empresa(codigo, nombre, nit, digitoVerificacion, direccion, telefono, correoElectronico, imagen);
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
}
