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
import co.inphotech.marshando.auxiliarcontable.externos.vo.Factura;
import co.inphotech.marshando.auxiliarcontable.externos.vo.ItemFactura;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class FacturaDAO {

	
	public static final String SERVICIO = "/factura"; 
	
	public static Factura getFactura(Empresa empresa, long id) throws Exception{
		Factura factura = null;
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlFactura + SERVICIO + "/"  + id).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONObject jfactura = new JSONObject(data.getBody());
		factura = armarFacturaConItems(empresa, jfactura);
		
		
		
		return factura;
	}
	public static List<Factura> getFacturas(Empresa empresa, int mes, int anio) throws Exception{
		List<Factura> facturas = new ArrayList<Factura>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("facturacion_url");
		HttpRequest request = Unirest.get( urlFactura + SERVICIO ).header("authorization", token).queryString("estadoCierre", "2");
		
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
		JSONArray jfacturas = new JSONArray(data.getBody());
		
		for( int i = 0; i < jfacturas.length();i++ ) {
			Factura factura = armarFactura(empresa, jfacturas.getJSONObject(i));
			facturas.add(factura);
		}
		
		return facturas;
	}
	
	public static List<Factura> getFacturas(Empresa empresa, Date ultimaFechaActualizacion) throws Exception{
		List<Factura> facturas = new ArrayList<Factura>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlFactura + SERVICIO ).header("authorization", token).routeParam("estadoCierre", "2").routeParam("ultimaFechaActualizacion", UtilMundo.dateToStringInputHTML(ultimaFechaActualizacion)).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jfacturas = new JSONArray(data.getBody());
		
		for( int i = 0; i < jfacturas.length();i++ ) {
			Factura factura = armarFactura(empresa, jfacturas.getJSONObject(i));
			facturas.add(factura);
		}
		
		
		
		
		return facturas;
	}
	public static List<Factura> getFacturas(Empresa empresa) throws Exception{
		List<Factura> facturas = new ArrayList<Factura>();
		
		String codigoEmpresa = empresa.getCodigo();
		
		String token = ConectorAutorizacionAplicacion.getInstancia().getToken( codigoEmpresa );
		String urlFactura = System.getenv("facturacion_url");
		HttpResponse<String>data = Unirest.get( urlFactura + SERVICIO ).header("authorization", token).asString();
		
		
		if(data.getStatus() != 200) {
			throw new Exception(data.getBody());
		}
		JSONArray jfacturas = new JSONArray(data.getBody());
		
		for( int i = 0; i < jfacturas.length();i++ ) {
			Factura factura = armarFactura(empresa, jfacturas.getJSONObject(i));
			facturas.add(factura);
		}
		
		return facturas;
	}
	
	

	public static Factura armarFactura(Empresa empresa, JSONObject jfactura) throws Exception{
		long id = jfactura.getLong("id");
		String consecutivo = jfactura.getString("consecutivoCompleto");
		Date fecha = UtilMundo.stringToDateHTMLInput( jfactura.getString("sfecha") );
		Date utimaFechaActualizacion = UtilMundo.stringToDateHTMLHoraInput( jfactura.getString("sultimaFechaActualizacion") );
		
		
		String razonSocialTercero = jfactura.getString("razonSocialTercero");
		String nitTercero = jfactura.getString("nitTercero");
		
		double valor = jfactura.getDouble("valor");
		double saldo = jfactura.getDouble("saldo");
		
		
		return new Factura(id, empresa.getCodigo(), fecha, consecutivo, razonSocialTercero, nitTercero, valor, saldo, utimaFechaActualizacion);
	}
	public static Factura armarFacturaConItems(Empresa empresa, JSONObject jfactura) throws Exception{
		Factura factura = armarFactura(empresa, jfactura);
		JSONArray jitemsFactura = jfactura.getJSONArray("items");
		
		for( int i = 0; i <  jitemsFactura.length(); i++) {
			JSONObject jitemFactura = jitemsFactura.getJSONObject(i);
			ItemFactura itemFactura = ItemFacturaDAO.armarItemFactura(jitemFactura);
			factura.insertarItemFactura(itemFactura);
		}
		
		
		
		return factura;
	}
	
}
