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
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class NotaDebito extends ElementoContabilizable{

	private long id;
	
	private Date fecha;
	private String codigoEmpresa;
	private Date ultimaFechaActualizacion;
	private String consecutivo;
	private String razonSocialTercero;
	private String nitTercero;
	private double valor;
	private double saldo;
	
	private List<ItemNotaDebito>itemsNotaDebito;
	
	public NotaDebito(long id, String codigoEmpresa, Date fecha, String consecutivo, String razonSocialTercero, String nitTercero, double valor, double saldo, Date ultimaFechaActualizacion) {
		super();
		this.id = id;
		this.codigoEmpresa = codigoEmpresa;
		this.fecha = fecha;
		this.consecutivo = consecutivo;
		this.razonSocialTercero = razonSocialTercero;
		this.nitTercero = nitTercero;
		this.valor = valor;
		this.saldo = saldo;
		this.ultimaFechaActualizacion = ultimaFechaActualizacion;
		
		this.itemsNotaDebito = new ArrayList<ItemNotaDebito>();
	}
	
	
	public long getId() {
		return id;
	}
	public Date getFecha() {
		return fecha;
	}
	public String getConsecutivo() {
		return consecutivo;
	}
	public String getRazonSocialTercero() {
		return razonSocialTercero;
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
	public void insertarItemNotaCredito( ItemNotaDebito itemNotaDebito ) {
		this.itemsNotaDebito.add( itemNotaDebito );
	}
	public List<ItemNotaDebito> getItemsNotaDebito(  ) {
		return itemsNotaDebito;
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
		return "ND - " + getConsecutivo();
	}
	@Override
	public String getDescripcion() {
		return "Nota débito: " + getConsecutivo();
	}
	@Override
	public int getTipoElemento() {
		return TIPO_NOTA_DEBITO;
	}
	@Override
	public String getEnlace() {
		return "/facturacion/nota-debito/" + getId();
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
		JSONArray jdatos = new JSONArray();
		for( int i = 0; i < getItemsNotaDebito().size(); i++ ) {
			ElementoContabilizado elementoContabilizado = getElementoContabilizado();
			if(  elementoContabilizado == null ) {
				elementoContabilizado = empresa.insertarElementoContabilizado(this);
				setElementoContabilizad(elementoContabilizado);
			}
			JSONObject jdato = getItemsNotaDebito().get(i).toJSONAccionContable(getElementoContabilizado(), empresa, this);
			jdatos.put( jdato );
		}
		
		return jdatos;
	}
	@Override
	public JSONArray simular(Empresa empresa) throws Exception {
		JSONArray jdatos = new JSONArray();
		for( int i = 0; i < getItemsNotaDebito().size(); i++ ) {
			JSONObject jdato = getItemsNotaDebito().get(i).toJSONAccionContable(null, empresa, this);
			jdatos.put( jdato );
		}
		return jdatos;
	}
	@Override
	public List<VariableAccionContable> getVariablesAccionContable(Empresa empresa) throws Exception {
		List<VariableAccionContable> variablesAccionContable = new ArrayList<VariableAccionContable>();
		
		for( int i = 0; i < getItemsNotaDebito().size(); i++ ) {
			JSONArray valores = getItemsNotaDebito().get(i).getJSONValores(empresa, this);
			for( int j = 0; j < valores.length();j++ ) {
				JSONObject jvalor = valores.getJSONObject(j);
				
				String codigo = jvalor.getString("codigo");
				double valor = jvalor.getDouble("valor");
				String elemento = getItemsNotaDebito().get(i).getReferencia() + ". " + getItemsNotaDebito().get(i).getDescripcion();
				
				VariableAccionContable variableAccionContable = new VariableAccionContable(codigo, valor, elemento);
				variablesAccionContable.add(variableAccionContable);
			}
		}
		
		
		return variablesAccionContable;
	}
	@Override
	public List<TerceroAccionContable> getTercerosAccionContable(Empresa empresa) throws Exception {
		List<TerceroAccionContable> tercerosAccionContable = new ArrayList<TerceroAccionContable>();
		
		for( int i = 0; i < getItemsNotaDebito().size(); i++ ) {
			JSONArray jterceros = getItemsNotaDebito().get(i).getJSONArrayTerceros(this);
			for( int j = 0; j < jterceros.length();j++ ) {
				JSONObject jtercero = jterceros.getJSONObject(j);
				TerceroAccionContable terceroAccionContable = new TerceroAccionContable(jtercero.getString("codigo"), jtercero.getString("nit") + ". " + jtercero.getString("razonSocialTercero"), getItemsNotaDebito().get(i).getReferencia() + ". " + getItemsNotaDebito().get(i).getDescripcion());
				tercerosAccionContable.add(terceroAccionContable);
			}
		}
		
		
		return tercerosAccionContable;
	}
	@Override
	public List<TextoAccionContable> getTextosAccionContable(Empresa empresa) throws Exception {
		List<TextoAccionContable> textosAccionContable = new ArrayList<TextoAccionContable>();
		
		for( int i = 0; i < getItemsNotaDebito().size(); i++ ) {
			JSONArray jtextos = getItemsNotaDebito().get(i).getJSONTextos(this);
			for( int j = 0; j < jtextos.length();j++ ) {
				JSONObject jtercero = jtextos.getJSONObject(j);
				TextoAccionContable textoAccionContable = new TextoAccionContable(jtercero.getString("codigo"), jtercero.getString("valor"), getItemsNotaDebito().get(i).getReferencia() + ". " + getItemsNotaDebito().get(i).getDescripcion());
				textosAccionContable.add(textoAccionContable);
			}
		}
		
		return textosAccionContable;
	}


	
	
	
}
