package co.inphotech.marshando.auxiliarcontable.externos.vo;

import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TerceroAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class Gasto extends ElementoContabilizable {

	private long id;
	
	
	private String consecutivo;
	private String codigoEmpresa;
	private String descripcion;
	private Date fecha;
	private Date ultimaFechaActualizacion;
	
	private String razonSocialTercero;
	private String nitTercero;
	private double valor;
	private double saldo;
	
	private List<ItemGasto>itemsGasto;
	
	
	public Gasto(long id, String codigoEmpresa, String consecutivo, String descripcion, Date fecha, Date ultimaFechaActualizacion, String razonSocialTercero, String nitTercero, double valor, double saldo) {
		super();
		this.id = id;
		this.consecutivo = consecutivo;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
		this.razonSocialTercero = razonSocialTercero;
		this.nitTercero = nitTercero;
		this.valor = valor;
		this.saldo = saldo;
	}
	
	
	
	public long getId() {
		return id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public String getRazonSocialTercero() {
		return razonSocialTercero;
	}
	public String getConsecutivo() {
		return consecutivo;
	}
	public String getNitTercero() {
		return nitTercero;
	}
	public double getValor() {
		return valor;
	}
	public double getSaldo() {
		return saldo;
	}
	public void insertarItemsGasto( ItemGasto itemGasto  ) {
		this.itemsGasto.add( itemGasto );
	}
	@Override
	public long getIdElemento() {
		return getId();
	}
	@Override
	public String getCodigoEmpresa() {
		return codigoEmpresa;
	}
	@Override
	public String getNombre() {
		return "Gasto " + getConsecutivo();
	}
	@Override
	public int getTipoElemento() {
		return TIPO_PAGO_GASTO;
	}
	@Override
	public String getEnlace() {
		return "/gastos/gasto/" + getId();
	}
	@Override
	public Date getUltimaFechaActualizacion() {
		return ultimaFechaActualizacion;
	}
	
	
	// ---------------------------------
	// 
	// ---------------------------------
	@Override
	public JSONArray getDatosEjecucionAccionContable( Empresa empresa ) throws Exception {
		return null;
	}
	@Override
	public JSONArray simular(Empresa empresa) throws Exception {
		return null;
	}
	@Override
	public List<VariableAccionContable> getVariablesAccionContable(Empresa empresa) throws Exception {
		return null;
	}
	@Override
	public List<TerceroAccionContable> getTercerosAccionContable(Empresa empresa) throws Exception {
		return null;
	}
	@Override
	public List<TextoAccionContable> getTextosAccionContable(Empresa empresa) throws Exception {
		return null;
	}
}
