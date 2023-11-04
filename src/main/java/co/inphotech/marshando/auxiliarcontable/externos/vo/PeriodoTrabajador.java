package co.inphotech.marshando.auxiliarcontable.externos.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;

import co.inphotech.marshando.auxiliarcontable.accionescontables.dao.AsientoContableDAO;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.AsientoContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TerceroAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.externos.dao.PeriodoNovedadTrabajadorNomina;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class PeriodoTrabajador extends ElementoContabilizable{

	
	private long id;
	
	
	private String codigoEmpresa;
	private Date ultimaFechaActualizacion;
	private Date fecha;
	private long idTrabajador;
	private String nombreTrabajador;
	private String documentoTrabajador;
	private long idPeriodo;
	private String periodo;
	private long idContrato;
	private String nombreContrato;
	private String nombrePeriodo;
	
	private List<PeriodoNovedadTrabajadorNomina>periodoNovedadesTrabajadorNomina;
	
	
	public PeriodoTrabajador(long id, String codigoEmpresa, long idTrabajador, long idContrato, long idPeriodo,
												String nombreTrabajador, String documentoTrabajador, String periodo, 
												Date ultimaFechaActualizacion, Date fecha,
												String nombreContrato, String nombrePeriodo) {
		super();
		this.codigoEmpresa = codigoEmpresa;
		this.id = id;
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
		this.fecha = fecha;
		this.idTrabajador = idTrabajador;
		this.nombreTrabajador = nombreTrabajador;
		this.documentoTrabajador = documentoTrabajador;
		this.periodo = periodo;
		this.idContrato = idContrato;
		this.nombreContrato = nombreContrato;
		this.nombrePeriodo = nombrePeriodo;
		
		this.periodoNovedadesTrabajadorNomina = new ArrayList<PeriodoNovedadTrabajadorNomina>();
	}
	public long getId() {
		return id;
	}
	public long getIdPeriodo() {
		return idPeriodo;
	}
	public Date getFecha() {
		return fecha;
	}
	public long getIdTrabajador() {
		return idTrabajador;
	}
	public String getNombreTrabajador() {
		return nombreTrabajador;
	}
	public String getDocumentoTrabajador() {
		return documentoTrabajador;
	}
	public String getPeriodo() {
		return periodo;
	}
	public long getIdContrato() {
		return idContrato;
	}
	public String getNombreContrato() {
		return nombreContrato;
	}
	public String getNombrePeriodo() {
		return nombrePeriodo;
	}
	public void insertarPeriodoNovedadTrabajadorNomina( PeriodoNovedadTrabajadorNomina periodoNovedadTrabajadorNomina ) {
		this.periodoNovedadesTrabajadorNomina.add(periodoNovedadTrabajadorNomina);
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
		return getDocumentoTrabajador() + ". " + getNombreTrabajador() + ": " +  getPeriodo();
	}
	@Override
	public String getDescripcion() {
		return "Periodo: " + getPeriodo() + " - " + getDocumentoTrabajador() + ". " + getNombreTrabajador();
	}
	@Override
	public int getTipoElemento() {
		return ElementoContabilizable.TIPO_PERIODO_TRABAJADOR;
	}
	@Override
	public String getEnlace() {
		return "/nomina/periodo-trabajador/" + idPeriodo + "/" + idTrabajador;
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
