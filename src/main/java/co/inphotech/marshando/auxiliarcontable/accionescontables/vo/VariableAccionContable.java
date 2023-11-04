package co.inphotech.marshando.auxiliarcontable.accionescontables.vo;

import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public class VariableAccionContable {

	private String codigo;
	private double valor;
	private String elemento;
	
	
	
	public VariableAccionContable(String codigo, double valor, String elemento) {
		super();
		this.codigo = codigo;
		this.valor = valor;
		this.elemento = elemento;
	}
	
	
	
	public String getElemento() {
		return elemento;
	}
	public String getCodigo() {
		return codigo;
	}
	public double getValor() {
		return valor;
	}
	public void sumarValor( double valor ) {
		this.valor += valor;
	}
	
	public JSONObject toJSON() throws Exception{
		JSONObject jvariableAccionContable = new JSONObject();
		
		jvariableAccionContable.put("codigo", getCodigo());
		jvariableAccionContable.put("valor", getValor());
		jvariableAccionContable.put("elemento", getElemento());
		jvariableAccionContable.put("svalor", UtilMundo.formatearNumero(getValor()) );
		
		return jvariableAccionContable;
	}
	public static JSONArray toJSONArray( List<VariableAccionContable> variablesAccionContable ) throws Exception{
		JSONArray jvariablesAccionContable = new JSONArray();
		for(int i = 0; i < variablesAccionContable.size(); i++){
			jvariablesAccionContable.put(variablesAccionContable.get(i).toJSON( ) );
		}
		return jvariablesAccionContable;
	}
}
