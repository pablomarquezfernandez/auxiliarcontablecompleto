package co.inphotech.marshando.auxiliarcontable.externos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequest;

import co.inphotech.marshando.auxiliarcontable.conectores.ConectorAutorizacionAplicacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.AbonoNomina;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class AbonoNominaDAO {

	
	public static final String SERVICIO = "/abono"; 
	
	public static List<AbonoNomina> getAbonosNominas(Empresa empresa, int tipo, int mes, int anio) throws Exception{
		List<AbonoNomina> abonosNominas = new ArrayList<AbonoNomina>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("nomina_url");
		HttpRequest request = Unirest.get( urlFactura + SERVICIO ).header("authorization", token).queryString("tipo", tipo + "" );
		
		
		
		if( mes != -1 ) {
			request = request.queryString("mesUltimaFechaActualizacion", mes + "" );
		}
		if( anio != -1 ) {
			request = request.queryString("anioUltimaFechaActualizacion", anio + "" );
		}
		
		
		HttpResponse<String>data = request.asString();
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jabonosNomina = new JSONArray(data.getBody());
		
		for( int i = 0; i < jabonosNomina.length();i++ ) {
			AbonoNomina abonoNomina = armarAbonoNomina(empresa, jabonosNomina.getJSONObject(i));
			abonosNominas.add(abonoNomina);
		}
		
		return abonosNominas;
	}
	
	
	public static AbonoNomina armarAbonoNomina( Empresa empresa, JSONObject jabonoNomina ) throws Exception{
		
		long id = jabonoNomina.getLong("id");
		String descripcion = jabonoNomina.getString("descripcion");
		double valor = jabonoNomina.getDouble("valor");
		int tipo = jabonoNomina.getInt("tipo");
		
		long idCajaCuentaBanco = jabonoNomina.getLong("idCajaCuentaBanco");
		String nombreCajaCuentaBanco = jabonoNomina.getString("nombreCajaCuentaBanco");
		
		
		Date fecha = UtilMundo.stringToDateHTMLInput( jabonoNomina.getString("sfecha") );
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jabonoNomina.getString("sultimaFechaActualizacion") );
		
		
		return new AbonoNomina(id, empresa.getCodigo(), tipo, valor, descripcion, idCajaCuentaBanco, nombreCajaCuentaBanco, fecha, utimaFechaActualizacion);
	}
}
