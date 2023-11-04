package co.inphotech.marshando.auxiliarcontable.contables.vo;

import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoContabilizadoDAO;

public class ElementoContabilizado {

	public static final int ESTADO_ACTIVO = 1;
	public static final int ESTADO_INACTIVO = 2;
	
	public static final int ESTADO_CONTABILIDAD_SIN_CONTABILIZAR = 1;
	public static final int ESTADO_CONTABILIDAD_MODIFICACION = 2;
	public static final int ESTADO_CONTABILIDAD_CONTABILIZADO = 3;
	public static final int ESTADO_CONTABILIDAD_ERROR = 4;
	
	// ------------------------------
	// Atributos
	// ------------------------------
	private long id;
	private long idElemento;
	private String codigoEmpresa;
	private int tipoElemento;
	private String codigoComprobanteContable;
	private String consecutivoComprobanteContable;
	private Date fechaContabilizado;
	
	private int estadoContabilidad;
	private Date ultimaFechaActualizacion;
	private String mensajeError;
	
	private int estado;

	// ------------------------------
	// Constructor 
	// ------------------------------
	public ElementoContabilizado( long id, String codigoEmpresa,  long idElemento, int tipoElemento, String codigoComprobanteContable, String consecutivoComprobanteContable, int estadoContabilidad, Date fechaContabilizado, Date ultimaFechaActualizacion, String mensajeError, int estado ){
		this.id = id;
		
		this.idElemento = idElemento;
		this.tipoElemento = tipoElemento;
		
		this.codigoEmpresa = codigoEmpresa;
		this.codigoComprobanteContable = codigoComprobanteContable;

		this.consecutivoComprobanteContable = consecutivoComprobanteContable;
		this.estadoContabilidad = estadoContabilidad;
		this.estado = estado;
		this.fechaContabilizado = fechaContabilizado;
		this.tipoElemento = tipoElemento;
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
		this.mensajeError = mensajeError;
	}
	// ------------------------------
	// Gets
	// ------------------------------
	public long getId(){
		return id;
	}
	public long getIdElemento(){
		return idElemento;
	}
	public String getCodigoEmpresa(){
		return codigoEmpresa;
	}
	public String getCodigoComprobanteContable(){
		return codigoComprobanteContable;
	}
	public String getConsecutivoComprobanteContable(){
		return consecutivoComprobanteContable;
	}
	public String getEnlaceComprobanteContable(){
		return "/contabilidad/comprobante-contable/" + getCodigoComprobanteContable();
	}
	public int getEstadoContabilidad(){
		return estadoContabilidad;
	}
	public int getTipoElemento(){
		return tipoElemento;
	}
	public int getEstado(){
		return estado;
	}
	public Date getFechaContabilizado(){
		return fechaContabilizado;
	}
	public Date getUltimaFechaActualizacion(){
		return ultimaFechaActualizacion;
	}
	public String getMensajeError(){
		return mensajeError;
	}
	
	// ------------------------------
	// Actualizar
	// ------------------------------
	public void actualizarElementoContabilizado(  int tipoElemento, long idElemento, String codigoComprobanteContable, String consecutivoComprobanteContable, Date fechaContabilizado, int estadoContabilidad, Date ultimaFechaActualizacion, String  mensajeError) throws Exception
	{
		ElementoContabilizadoDAO.actualizarElementoContabilizado(this,  tipoElemento, idElemento, codigoComprobanteContable, consecutivoComprobanteContable, estadoContabilidad, fechaContabilizado, ultimaFechaActualizacion, mensajeError);
		
		this.tipoElemento = tipoElemento;
		this.idElemento = idElemento;
		this.codigoComprobanteContable = codigoComprobanteContable;
		this.consecutivoComprobanteContable = consecutivoComprobanteContable;
		this.estadoContabilidad = estadoContabilidad;
		this.fechaContabilizado = fechaContabilizado;
		this.tipoElemento = tipoElemento;
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
		this.mensajeError = mensajeError;
	}
	
	public void actualizarElementoContabilizado( ComprobanteContable comprobanteContable, int estadoContabilidad, Date fechaContabilizado ) throws Exception
	{
		actualizarElementoContabilizado(tipoElemento, idElemento, comprobanteContable.getCodigo(), comprobanteContable.getConsecutivo(), fechaContabilizado, estadoContabilidad, ultimaFechaActualizacion, mensajeError);
	}
	public void actualizarElementoContabilizado( String mensajeError, int estadoContabilidad, Date fechaContabilizado ) throws Exception
	{
		actualizarElementoContabilizado(tipoElemento, idElemento, codigoComprobanteContable, consecutivoComprobanteContable, fechaContabilizado, estadoContabilidad, ultimaFechaActualizacion, mensajeError);
	}
	
//	public void actualizarElementoContabilizado(  int estadoContabilidad ) throws Exception
//	{
//		actualizarElementoContabilizado(elementoContabilizable, tipo, codigoComprobanteContable, enlaceComprobanteContable, Calendar.getInstance().getTime(), estadoContabilidad);;
//	}
	// ------------------------------
	// Eliminar
	// ------------------------------
	public void eliminar( ) throws Exception{
		ElementoContabilizadoDAO.actualizarEstadoElementoContabilizado(this, ElementoContabilizado.ESTADO_INACTIVO );
		this.estado  = ElementoContabilizado.ESTADO_INACTIVO;
	}
	// ------------------------------
	// ToJSON
	// ------------------------------
	public JSONObject toJSON( ) throws Exception{
		JSONObject jelementoContabilizado = new JSONObject();
		jelementoContabilizado.put("id", getId());
		jelementoContabilizado.put("tipoElemento", getTipoElemento());
		jelementoContabilizado.put("codigoComprobanteContable", getCodigoComprobanteContable());
		jelementoContabilizado.put("enlaceComprobanteContable", getEnlaceComprobanteContable());
		jelementoContabilizado.put("consecutivoComprobanteContable", getConsecutivoComprobanteContable());
		jelementoContabilizado.put("mensajeError", getMensajeError());
		
		
		jelementoContabilizado.put("estadoContabilidad", getEstadoContabilidad());
		jelementoContabilizado.put("estado", getEstado());

		return jelementoContabilizado;
	}
	public static JSONArray toJSONArray( List<ElementoContabilizado> elementoContabilizados ) throws Exception{
		JSONArray jelementoContabilizados = new JSONArray();
		for(int i = 0; i < elementoContabilizados.size(); i++){
			jelementoContabilizados.put(elementoContabilizados.get(i).toJSON( ) );
		}
		return jelementoContabilizados;
	}
}
