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

public class AbonoGasto extends ElementoContabilizable {

	
	
	private long id;
	private String codigoEmpresa;
	private double valor;
	private String descripcion;
	
	private long idCajaCuentaBanco;
	private String nombreCajaCuentaBanco;
	
	private Date fecha;
	private Date ultimaFechaActualizacion;
	
	
	
	
	
	public AbonoGasto(long id, String codigoEmpresa, double valor, String descripcion, long idCajaCuentaBanco, String nombreCajaCuentaBanco, Date fecha, Date ultimaFechaActualizacion) {
		super();
		this.id = id;
		this.codigoEmpresa = codigoEmpresa;
		this.valor = valor;
		this.descripcion = descripcion;
		this.idCajaCuentaBanco = idCajaCuentaBanco;
		this.nombreCajaCuentaBanco = nombreCajaCuentaBanco;
		this.fecha = fecha;
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
	}
	
	
	
	public long getId() {
		return id;
	}
	public double getValor() {
		return valor;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public long getIdCajaCuentaBanco() {
		return idCajaCuentaBanco;
	}
	public String getNombreCajaCuentaBanco() {
		return nombreCajaCuentaBanco;
	}
	public Date getFecha() {
		return fecha;
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
		return descripcion;
	}
	@Override
	public int getTipoElemento() {
		return TIPO_PAGO_GASTO;
	}
	@Override
	public String getEnlace() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Date getUltimaFechaActualizacion() {
		// TODO Auto-generated method stub
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
