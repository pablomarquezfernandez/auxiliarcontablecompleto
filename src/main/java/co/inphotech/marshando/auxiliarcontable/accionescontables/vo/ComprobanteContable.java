package co.inphotech.marshando.auxiliarcontable.accionescontables.vo;

import com.amazonaws.util.json.JSONObject;

public class ComprobanteContable {

	
	private String codigo;
	private String descripcion;
	private String consecutivo;
	private double credito;
	private double debito;
	private double saldoInicial;
	
	
	public ComprobanteContable(String codigo,  String descripcion, String consecutivo, double credito, double debito, double saldoInicial ) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.consecutivo = consecutivo;
		
		this.credito = credito;
		this.debito = debito;
		this.saldoInicial = saldoInicial;
	}
	
	public String getCodigo() {
		return codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public String getConsecutivo() {
		return consecutivo;
	}
	public double getCredito() {
		return credito;
	}
	public double getDebito() {
		return debito;
	}
	public double getSaldoInicial() {
		return saldoInicial;
	}

	
	
	
	
	
	public JSONObject toJSON() throws Exception{
		JSONObject  jcomprobanteContable = new JSONObject();
		
		jcomprobanteContable.put("codigo", getCodigo());
		jcomprobanteContable.put("descripcion", getDescripcion() );
		jcomprobanteContable.put("consecutivo", getConsecutivo() );
		
		return jcomprobanteContable;
	}
}
