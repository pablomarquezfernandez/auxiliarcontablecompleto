package co.inphotech.marshando.auxiliarcontable.externos.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TerceroAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class AbonoFacturacion extends ElementoContabilizable {

	public static final int TIPO_FACTURA = 1;
	public static final int TIPO_NOTA_CREDITO = 2;
	public static final int TIPO_NOTA_DEBITO = 3;
	
	
	private long id;
	private double valor;
	private String codigoEmpresa;
	private String descripcion;
	
	private long idCajaCuentaBanco;
	private String nombreCajaCuentaBanco;
	private int tipo;

	private Date fecha;
	private Date ultimaFechaActualizacion;
	
	
	
	
	
	public AbonoFacturacion(long id, String codigoEmpresa, int tipo, double valor, String descripcion, long idCajaCuentaBanco, String nombreCajaCuentaBanco, Date fecha, Date ultimaFechaActualizacion) {
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
	public int getTipoElemento() {
		if( getTipo() == TIPO_FACTURA ) {
			return TIPO_PAGO_FACTURA;
		}
		if( getTipo() == TIPO_NOTA_CREDITO) {
			return TIPO_PAGO_NOTA_CREDITO;
		}
		if( getTipo() == TIPO_NOTA_DEBITO ) {
			return TIPO_PAGO_NOTA_DEBITO;
		}
		return -1;
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
	public String getEnlace() {
		return null;
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
