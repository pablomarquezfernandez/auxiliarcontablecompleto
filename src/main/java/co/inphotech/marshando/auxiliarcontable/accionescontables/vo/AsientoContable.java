package co.inphotech.marshando.auxiliarcontable.accionescontables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class AsientoContable {

	
	private String codigoCuentaPUC;
	private String descripcionCuentaPUC;
	
	private String descripcion;
	
	private String nitTercero;
	private String razonSocialTercero;
	private String nombreAccionContable;
	
	private double saldoInicial;
	private double credito;
	private double debito;
	
	
	
	public AsientoContable(String codigoCuentaPUC, String descripcionCuentaPUC, String descripcion, String nitTercero, String razonSocialTercero, double saldoInicial, double credito, double debito, String nombreAccionContable) {
		super();
		this.codigoCuentaPUC = codigoCuentaPUC;
		this.descripcionCuentaPUC = descripcionCuentaPUC;
		this.descripcion = descripcion;
		this.nitTercero = nitTercero;
		this.razonSocialTercero = razonSocialTercero;
		this.saldoInicial = saldoInicial;
		this.credito = credito;
		this.debito = debito;
		this.nombreAccionContable = nombreAccionContable;
	}
	
	
	
	public String getCodigoCuentaPUC() {
		return codigoCuentaPUC;
	}
	public String getDescripcionCuentaPUC() {
		return descripcionCuentaPUC;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getNitTercero() {
		return nitTercero;
	}
	public String getNombreAccionContable() {
		return nombreAccionContable;
	}
	
	
	public String getRazonSocialTercero() {
		return razonSocialTercero;
	}
	public double getSaldoInicial() {
		return saldoInicial;
	}
	public double getCredito() {
		return credito;
	}
	public double getDebito() {
		return debito;
	}
	
	public JSONObject toJSON() throws Exception{
		JSONObject jasiento = new JSONObject();
		
		jasiento.put("codigoCuentaPUC", getCodigoCuentaPUC());
		jasiento.put("descripcionCuentaPUC", getDescripcionCuentaPUC());
		jasiento.put("descripcion", getDescripcion());
		jasiento.put("nitTercero", getNitTercero());
		jasiento.put("razonSocialTercero", getRazonSocialTercero());
		jasiento.put("nombreAccionContable", getNombreAccionContable() );
		
		jasiento.put("saldoInicial", getSaldoInicial());
		jasiento.put("ssaldoInicial", UtilMundo.formatearNumeroCompletoDosDecimalesFactura(getSaldoInicial()) );
		
		jasiento.put("credito", getCredito());
		jasiento.put("scredito", UtilMundo.formatearNumeroCompletoDosDecimalesFactura(getCredito())  );
		
		jasiento.put("debito", getDebito());
		jasiento.put("sdebito", UtilMundo.formatearNumeroCompletoDosDecimalesFactura(getDebito())  );
		
		
		return jasiento;
	}
	
	public static JSONArray toJSONArray( List<AsientoContable> asientosContables ) throws Exception{
		JSONArray jasientosContables = new JSONArray();
		for(int i = 0; i < asientosContables.size(); i++){
			jasientosContables.put(asientosContables.get(i).toJSON( ) );
		}
		return jasientosContables;
	}
	
}
