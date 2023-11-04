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

public class AbonoNomina extends ElementoContabilizable{

	public static final int TIPO_TRABAJADOR = 1;
	public static final int TIPO_SEGURIDAD_SOCIAL = 2;
	
	
	private long id;
	private double valor;
	private String descripcion;
	private String codigoEmpresa;
	
	private long idCajaCuentaBanco;
	private String nombreCajaCuentaBanco;
	private int tipo;

	private Date fecha;
	private Date ultimaFechaActualizacion;

	
	public AbonoNomina(long id, String codigoEmpresa, int tipo, double valor, String descripcion, long idCajaCuentaBanco, String nombreCajaCuentaBanco, Date fecha, Date ultimaFechaActualizacion) {
		super();
		this.id = id;
		this.codigoEmpresa = codigoEmpresa;
		this.tipo = tipo;
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
	public String getCodigoEmpresa() {
		return codigoEmpresa;
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
	public int getTipo() {
		return tipo;
	}



	@Override
	public long getIdElemento() {
		return getId();
	}
	@Override
	public String getNombre() {
		return descripcion;
	}
	@Override
	public String getEnlace() {
		return null;
	}
	@Override
	public Date getUltimaFechaActualizacion() {
		return ultimaFechaActualizacion;
	}
	@Override
	public int getTipoElemento() {
		if( getTipo() == TIPO_TRABAJADOR ) {
			return TIPO_PAGO_PERIODO_TRABAJADOR;
		}
		if( getTipo() == TIPO_SEGURIDAD_SOCIAL ) {
			return TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL;
		}
		return -1;
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
