package co.inphotech.marshando.auxiliarcontable.externos.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.amazonaws.util.json.JSONArray;
import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TerceroAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public class Factura extends ElementoContabilizable{

	private long id;
	
	private String codigoEmpresa;
	private Date fecha;
	private Date ultimaFechaActualizacion;
	private String consecutivo;
	private String razonSocialTercero;
	private String nitTercero;
	private double valor;
	private double saldo;
	
	private List<ItemFactura>itemsFactura;
	
	public Factura( long id, String codigoEmpresa, Date fecha, String consecutivo, String razonSocialTercero, String nitTercero, double valor, double saldo, Date ultimaFechaActualizacion ) {
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
		
		this.itemsFactura = new ArrayList<ItemFactura>();
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
	public List<ItemFactura> getItemsFactura() {
		return this.itemsFactura;
	}
	public void insertarItemFactura( ItemFactura itemFactura ) {
		this.itemsFactura.add( itemFactura );
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
		return "F - " + getConsecutivo();
	}
	@Override
	public String getDescripcion() {
		return "Factura " + getConsecutivo();
	}
	@Override
	public int getTipoElemento() {
		return ElementoContabilizable.TIPO_FACTURA;
	}
	@Override
	public String getEnlace() {
		return "/facturacion/factura/" + getId();
	}
	@Override
	public Date getUltimaFechaActualizacion() {
		return ultimaFechaActualizacion;
	}
	
	
	// ---------------------------------
	// 
	// ---------------------------------
	public JSONArray getDatosEjecucionAccionContable( Empresa empresa ) throws Exception{
		JSONArray jdatos = new JSONArray();
		for( int i = 0; i < getItemsFactura().size(); i++ ) {
			ElementoContabilizado elementoContabilizado = getElementoContabilizado();
			if(  elementoContabilizado == null ) {
				elementoContabilizado = empresa.insertarElementoContabilizado(this);
				setElementoContabilizad(elementoContabilizado);
			}
			JSONObject jdato = getItemsFactura().get(i).toJSONAccionContable(getElementoContabilizado(), empresa, this);
			jdatos.put( jdato );
		}
		
		return jdatos;
	}
	public JSONArray simular( Empresa empresa ) throws Exception {
		JSONArray jdatos = new JSONArray();
		for( int i = 0; i < getItemsFactura().size(); i++ ) {
			JSONObject jdato = getItemsFactura().get(i).toJSONAccionContable(null, empresa, this);
			jdatos.put( jdato );
		}
		return jdatos;
	}
	
	
	
	
	
	
	public List<VariableAccionContable> getVariablesAccionContable(Empresa empresa) throws Exception {
		List<VariableAccionContable> variablesAccionContable = new ArrayList<VariableAccionContable>();
		
		for( int i = 0; i < getItemsFactura().size(); i++ ) {
			JSONArray valores = getItemsFactura().get(i).getJSONValores(empresa, this);
			for( int j = 0; j < valores.length();j++ ) {
				JSONObject jvalor = valores.getJSONObject(j);
				
				String codigo = jvalor.getString("codigo");
				double valor = jvalor.getDouble("valor");
				String elemento = getItemsFactura().get(i).getReferencia() + ". " + getItemsFactura().get(i).getDescripcion();
				
				VariableAccionContable variableAccionContable = new VariableAccionContable(codigo, valor, elemento);
				variablesAccionContable.add(variableAccionContable);
			}
		}
		
		
		return variablesAccionContable;
	}
	
	public List<TerceroAccionContable> getTercerosAccionContable(Empresa empresa) throws Exception {
		List<TerceroAccionContable> tercerosAccionContable = new ArrayList<TerceroAccionContable>();
		
		for( int i = 0; i < getItemsFactura().size(); i++ ) {
			JSONArray jterceros = getItemsFactura().get(i).getJSONArrayTerceros(this);
			for( int j = 0; j < jterceros.length();j++ ) {
				JSONObject jtercero = jterceros.getJSONObject(j);
				TerceroAccionContable terceroAccionContable = new TerceroAccionContable(jtercero.getString("codigo"), jtercero.getString("nit") + ". " + jtercero.getString("razonSocialTercero"), getItemsFactura().get(i).getReferencia() + ". " + getItemsFactura().get(i).getDescripcion());
				tercerosAccionContable.add(terceroAccionContable);
			}
		}
		
		
		return tercerosAccionContable;
	}
	
	public List<TextoAccionContable> getTextosAccionContable(Empresa empresa) throws Exception {
		List<TextoAccionContable> textosAccionContable = new ArrayList<TextoAccionContable>();
		
		for( int i = 0; i < getItemsFactura().size(); i++ ) {
			JSONArray jtextos = getItemsFactura().get(i).getJSONTextos(this);
			for( int j = 0; j < jtextos.length();j++ ) {
				JSONObject jtercero = jtextos.getJSONObject(j);
				TextoAccionContable textoAccionContable = new TextoAccionContable(jtercero.getString("codigo"), jtercero.getString("valor"), getItemsFactura().get(i).getReferencia() + ". " + getItemsFactura().get(i).getDescripcion());
				textosAccionContable.add(textoAccionContable);
			}
		}
		
		return textosAccionContable;
	}


	
	
}
