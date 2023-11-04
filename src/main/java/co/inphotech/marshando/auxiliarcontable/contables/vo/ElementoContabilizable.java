package co.inphotech.marshando.auxiliarcontable.contables.vo;

import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TerceroAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.util.UtilMundo;

public abstract class ElementoContabilizable {

	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_INACTIVO = 2;
	
	
	
	public static final int TIPO_FACTURA = 1;
	public static final int TIPO_PAGO_FACTURA = 2;
	public static final int TIPO_NOTA_CREDITO = 9;
	public static final int TIPO_PAGO_NOTA_CREDITO = 10;
	public static final int TIPO_NOTA_DEBITO = 11;
	public static final int TIPO_PAGO_NOTA_DEBITO = 12;
	
	
	public static final int TIPO_GASTO = 5;
	public static final int TIPO_PAGO_GASTO = 6;
	
	
	public static final int TIPO_PERIODO_TRABAJADOR = 3;
	public static final int TIPO_PAGO_PERIODO_TRABAJADOR = 4;
	public static final int TIPO_PERIODO_SEGURIDAD_SOCIAL = 7;
	public static final int TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL = 8;
	
	
	private ElementoContabilizado elementoContabilizado;


	// ------------------------------
	// Constructor 
	// ------------------------------
	public ElementoContabilizable(  ){

	}
	// ------------------------------
	// Gets
	// ------------------------------
	public abstract long getIdElemento();
	public abstract String getCodigoEmpresa();
	public abstract String getNombre();
	public abstract String getDescripcion();
	public abstract int getTipoElemento();
	public abstract String getEnlace();
	public abstract Date getUltimaFechaActualizacion();
	public abstract Date getFecha();
	
	
	
	public ElementoContabilizado getElementoContabilizado() {
		return elementoContabilizado;
	}
	public void setElementoContabilizad(ElementoContabilizado elementoContabilizado) {
		this.elementoContabilizado = elementoContabilizado;
	}
	public int getEstadoContabilizado() {
		int estadoContabilizado = ElementoContabilizado.ESTADO_CONTABILIDAD_SIN_CONTABILIZAR;
		if( getElementoContabilizado() != null ) {
			estadoContabilizado = getElementoContabilizado().getEstadoContabilidad();
		}
		
		return estadoContabilizado;
	}
	
	// ------------------------------
	// Contabilidad
	// ------------------------------
	public abstract JSONArray getDatosEjecucionAccionContable( Empresa empresa ) throws Exception;
	public abstract JSONArray simular( Empresa empresa ) throws Exception;
	
	// ------------------------------
	// Contabilidad
	// ------------------------------
	
	public abstract List<VariableAccionContable> getVariablesAccionContable(Empresa empresa) throws Exception;
	public abstract List<TerceroAccionContable> getTercerosAccionContable(Empresa empresa) throws Exception;
	public abstract List<TextoAccionContable> getTextosAccionContable(Empresa empresa) throws Exception;
	// ------------------------------
	// ToJSON
	// ------------------------------
	public JSONObject toJSON( ) throws Exception{
		JSONObject jelementoContabilizable = new JSONObject();
		jelementoContabilizable.put("codigoEmpresa", getCodigoEmpresa() );
		jelementoContabilizable.put("nombre", getNombre() );
		jelementoContabilizable.put("descripcion", getDescripcion() );
		jelementoContabilizable.put("tipoElemento", getTipoElemento() );
		jelementoContabilizable.put("idElemento", getIdElemento() );
		jelementoContabilizable.put("enlace", getEnlace());
		jelementoContabilizable.put("estadoContabilizado", getEstadoContabilizado());
		
		if( getElementoContabilizado() == null ) {
			jelementoContabilizable.put("elementoContabilizado", new JSONObject());
		}else {
			jelementoContabilizable.put("elementoContabilizado", getElementoContabilizado().toJSON());
		}
		
		
		
		jelementoContabilizable.put("ultimaFechaActualizacion", UtilMundo.dateToStringInputHTML(getUltimaFechaActualizacion()));
		jelementoContabilizable.put("fecha", UtilMundo.dateToStringInputHTML(getFecha()));

		return jelementoContabilizable;
	}
	public static JSONArray toJSONArray( List<ElementoContabilizable> elementoContabilizables ) throws Exception{
		JSONArray jelementoContabilizables = new JSONArray();
		for(int i = 0; i < elementoContabilizables.size(); i++){
			jelementoContabilizables.put(elementoContabilizables.get(i).toJSON( ) );
		}
		return jelementoContabilizables;
	}
}
