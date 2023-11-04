package co.inphotech.marshando.auxiliarcontable.usuario.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.amazonaws.util.json.JSONArray;

import co.inphotech.marshando.auxiliarcontable.accionescontables.dao.AsientoContableDAO;
import co.inphotech.marshando.auxiliarcontable.accionescontables.dao.ComprobanteContableDAO;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.AsientoContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.ComprobanteContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TerceroAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.dao.AccionContableDAO;
import co.inphotech.marshando.auxiliarcontable.contables.dao.CategoriaContableProductoDAO;
import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoAccionContableDAO;
import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoConfiguracionDAO;
import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoContabilizableDAO;
import co.inphotech.marshando.auxiliarcontable.contables.dao.ElementoContabilizadoDAO;
import co.inphotech.marshando.auxiliarcontable.contables.vo.AccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.CategoriaContableProducto;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoConfiguracion;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.externos.dao.ContratoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.GastoDefectoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.ImpuestoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.vo.Contrato;
import co.inphotech.marshando.auxiliarcontable.externos.vo.GastoDefecto;
import co.inphotech.marshando.auxiliarcontable.externos.vo.Impuesto;

public class Empresa {

	private String codigo;
	private String nombre;
	private String nit;
	private String digitoVerificacion;
	private String imagen;
	private String direccion;
	private String telefono;
	private String correoElectronico;

	public Empresa(String codigo, String nombre, String nit, String digitoVerificacion, String direccion, String telefono, String correoElectronico, String imagen) {
		super();

		this.codigo = codigo;
		this.nombre = nombre;
		this.nit = nit;
		this.digitoVerificacion = digitoVerificacion;

		this.imagen = imagen;

		this.direccion = direccion;
		this.telefono = telefono;
		this.correoElectronico = correoElectronico;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNit() {
		return nit;
	}

	public String getImagen() {
		return imagen;
	}

	public String getDigitoVerificacion() {
		return digitoVerificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	// ------------------------
	// Acciones contables
	// ------------------------
	public ComprobanteContable ejecutarAccionContable(int tipo, long idElemento) throws Exception {
		ElementoContabilizable elementoContabilizable = ElementoContabilizableDAO.getElementoContabilizable(this, tipo, idElemento);
		ElementoContabilizado elementoContabilizado = elementoContabilizable.getElementoContabilizado();
		
		if( elementoContabilizado == null ) {
			elementoContabilizado = insertarElementoContabilizado(elementoContabilizable);
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);;
		}
		JSONArray jdatos = elementoContabilizable.getDatosEjecucionAccionContable(this);
		ComprobanteContable comprobanteContable = null;
		
		try {
			comprobanteContable = ComprobanteContableDAO.ejecutarAccionContable(this, jdatos);
			
			if( Math.abs(  comprobanteContable.getCredito() - comprobanteContable.getDebito() ) > 10 ) {
				elementoContabilizado.actualizarElementoContabilizado("No cuadran los débitos y créditos", ElementoContabilizado.ESTADO_CONTABILIDAD_ERROR, Calendar.getInstance().getTime());;
				elementoContabilizado.actualizarElementoContabilizado(comprobanteContable, ElementoContabilizado.ESTADO_CONTABILIDAD_ERROR, Calendar.getInstance().getTime());;
			} else {
				elementoContabilizado.actualizarElementoContabilizado(comprobanteContable, ElementoContabilizado.ESTADO_CONTABILIDAD_CONTABILIZADO, Calendar.getInstance().getTime());;
			}
		} catch (Exception e) {
			elementoContabilizado.actualizarElementoContabilizado(e.getMessage(), ElementoContabilizado.ESTADO_CONTABILIDAD_ERROR, Calendar.getInstance().getTime());;
		}
		
		
		return comprobanteContable;
	}
	public void ejecutarAccionesContable(int tipo, int mes, int anio) throws Exception {
		List<ElementoContabilizable> elementosContabilizables  = getElementoContabilizables(tipo, anio, mes);
		for( int i = 0; i < elementosContabilizables.size();i++ ) {
			ElementoContabilizable elementoContabilizable = elementosContabilizables.get(i);
			ejecutarAccionContable(elementoContabilizable.getTipoElemento(), elementoContabilizable.getIdElemento());
		}
	}
	// ------------------------
	// Acciones contables
	// ------------------------
	public List<AsientoContable> getAsientosContables(int tipo, long idElemento) throws Exception {
		ElementoContabilizable elementoContabilizable = getElementoContabilizable(tipo, idElemento);
		JSONArray jdatos = elementoContabilizable.simular(this);
		List<AsientoContable> asientos = AsientoContableDAO.getAsientosContables(this, jdatos);
		return asientos;
	}
	
	
	public AccionContable getAccionContable(long id) throws Exception {
		return AccionContableDAO.getAccionContable(this, id);
	}

	public List<AccionContable> getAccionesContables() throws Exception {
		return AccionContableDAO.getAccionContables(this);
	}

	
	// ------------------------
	// Elemento
	// ------------------------	
	public ElementoContabilizado getElementoContabilizado(long idElemento, int tipo) throws Exception {
		return ElementoContabilizadoDAO.getElementoContabilizado(idElemento, tipo);
	}
	public ElementoContabilizado insertarElementoContabilizado(ElementoContabilizable elementoContabilizable ) throws Exception {
		return ElementoContabilizadoDAO.insertarElementoContabilizado(this, elementoContabilizable,null, null, ElementoContabilizado.ESTADO_CONTABILIDAD_SIN_CONTABILIZAR, null, "", ElementoContabilizado.ESTADO_ACTIVO);
	}
	// ------------------------
	// Elemento
	// ------------------------	
	public ElementoContabilizable getElementoContabilizable(int tipo, long id) throws Exception {
		return ElementoContabilizableDAO.getElementoContabilizable(this, tipo, id );
	}
	public List<ElementoContabilizable> getElementoContabilizables(int tipo, int anio, int mes) throws Exception {
		return ElementoContabilizableDAO.getElementoContabilizables(this, tipo, anio, mes);
	}
	
	// ------------------------
	// Elemento
	// ------------------------
	public ElementoConfiguracion getElementoConfiguracion(long id) throws Exception {
		return ElementoConfiguracionDAO.getElementoConfiguracion(this, id);
	}

	public ElementoConfiguracion getElementoConfiguracion(long idElemento, int tipo) throws Exception {
		return ElementoConfiguracionDAO.getElementoConfiguracion(this, idElemento, tipo);
	}

	public List<ElementoConfiguracion> getElementosConfiguracion(int tipo) throws Exception {
		List<CategoriaContableProducto> categoriasContablesProductos = CategoriaContableProductoDAO
				.getCategoriaContableProductos(this);
		List<ElementoConfiguracion> elementos = new ArrayList<ElementoConfiguracion>();
		for (int i = 0; i < categoriasContablesProductos.size(); i++) {
			CategoriaContableProducto categoriaContableProducto = categoriasContablesProductos.get(i);
			ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(),
					categoriaContableProducto.getId(), categoriaContableProducto.getNombre(),
					ElementoConfiguracion.TIPO_FACTURACION, ElementoConfiguracion.ESTADO_ACTIVO);
			elementos.add(elemento);
		}
		return elementos;
	}

	public ElementoConfiguracion insertarElementoConfiguracion(long idElemento, String nombre, int tipo)
			throws Exception {
		return ElementoConfiguracionDAO.insertarElementoConfiguracion(this, idElemento, nombre, tipo,
				ElementoConfiguracion.ESTADO_ACTIVO);
	}
	// ------------------------
	// 
	// ------------------------
	public List<Impuesto> getImpuestos() throws Exception {
		return ImpuestoDAO.getImpuestos(this);
	}
	
	// ------------------------
	// Elementos accion contable
	// ------------------------
	public List<ElementoAccionContable> getElementosAccionesContables(int tipo) throws Exception {
		List<ElementoAccionContable> elementosAccionContable = new ArrayList<ElementoAccionContable>();
		if (tipo == ElementoConfiguracion.TIPO_FACTURACION) {
			elementosAccionContable = getElementosAccionContableTipoFacturacion();
		}
		if (tipo == ElementoConfiguracion.TIPO_PAGO_FACTURACION) {
			elementosAccionContable = getElementosAccionContableTipoPagoFacturacion();
		}
		if (tipo == ElementoConfiguracion.TIPO_NOTA_CREDITO) {
			elementosAccionContable = getElementosAccionContableTipoNotaCredito();
		}
		if (tipo == ElementoConfiguracion.TIPO_PAGO_NOTA_CREDITO) {
			elementosAccionContable = getElementosAccionContableTipoPagoNotaCredito();
		}
		if (tipo == ElementoConfiguracion.TIPO_NOTA_DEBITO) {
			elementosAccionContable = getElementosAccionContableTipoNotaDebito();
		}
		if (tipo == ElementoConfiguracion.TIPO_PAGO_NOTA_DEBITO) {
			elementosAccionContable = getElementosAccionContableTipoPagoNotaDebito();
		}

		if (tipo == ElementoConfiguracion.TIPO_GASTO) {
			elementosAccionContable = getElementosAccionContableTipoGasto();
		}
		if (tipo == ElementoConfiguracion.TIPO_PAGO_GASTO) {
			elementosAccionContable = getElementosAccionContableTipoGasto();
		}

		if (tipo == ElementoConfiguracion.TIPO_PERIODO_TRABAJADOR) {
			elementosAccionContable = getElementosAccionContableTipoNomina();
		}
		if (tipo == ElementoConfiguracion.TIPO_PAGO_PERIODO_TRABAJADOR) {
			elementosAccionContable = getElementosAccionContableTipoPagoNomina();
		}
		if (tipo == ElementoConfiguracion.TIPO_PERIODO_SEGURIDAD_SOCIAL) {
			elementosAccionContable = getElementosAccionContableTipoSeguridadSocial();
		}
		if (tipo == ElementoConfiguracion.TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL) {
			elementosAccionContable = getElementosAccionContableTipoPagoSeguridadSocial();
		}

		return elementosAccionContable;
	}

	//
	// Nomina
	public List<ElementoAccionContable> getElementosAccionContableTipoNomina() throws Exception {
		List<Contrato> contratos = ContratoDAO.getContratos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PERIODO_TRABAJADOR);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < contratos.size(); i++) {
			Contrato contrato = contratos.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_PERIODO_TRABAJADOR, contrato.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), contrato.getId(),
						contrato.getNombre(), ElementoConfiguracion.TIPO_PERIODO_TRABAJADOR,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoPagoNomina() throws Exception {
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PAGO_PERIODO_TRABAJADOR);
		List<ElementoAccionContable> elementosAccionContableDefinitivo = new ArrayList<ElementoAccionContable>();

		if (elementosAccionContable.size() == 0) {
			ElementoConfiguracion elementoConfiguracion = new ElementoConfiguracion(-1, getCodigo(), -1,
					"Pago periodo trabajador", ElementoConfiguracion.TIPO_PAGO_PERIODO_TRABAJADOR,
					ElementoConfiguracion.ESTADO_ACTIVO);
			ElementoAccionContable elementoAccionContable = new ElementoAccionContable(-1, getCodigo(),
					elementoConfiguracion, -1, "", ElementoAccionContable.ESTADO_ACTIVO);
			elementosAccionContableDefinitivo.add(elementoAccionContable);
		} else {
			elementosAccionContableDefinitivo.add(elementosAccionContable.get(0));
		}

		return elementosAccionContableDefinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoSeguridadSocial() throws Exception {
		List<Contrato> contratos = ContratoDAO.getContratos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PERIODO_SEGURIDAD_SOCIAL);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < contratos.size(); i++) {
			Contrato contrato = contratos.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_PERIODO_SEGURIDAD_SOCIAL, contrato.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), contrato.getId(),
						contrato.getNombre(), ElementoConfiguracion.TIPO_PERIODO_SEGURIDAD_SOCIAL,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoPagoSeguridadSocial() throws Exception {
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL);
		List<ElementoAccionContable> elementosAccionContableDefinitivo = new ArrayList<ElementoAccionContable>();

		if (elementosAccionContable.size() == 0) {
			ElementoConfiguracion elementoConfiguracion = new ElementoConfiguracion(-1, getCodigo(), -1,
					"Pago periodo seguridad social", ElementoConfiguracion.TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL,
					ElementoConfiguracion.ESTADO_ACTIVO);
			ElementoAccionContable elementoAccionContable = new ElementoAccionContable(-1, getCodigo(),
					elementoConfiguracion, -1, "", ElementoAccionContable.ESTADO_ACTIVO);
			elementosAccionContableDefinitivo.add(elementoAccionContable);
		} else {
			elementosAccionContableDefinitivo.add(elementosAccionContable.get(0));
		}

		return elementosAccionContableDefinitivo;
	}

	//
	// Gastos
	public List<ElementoAccionContable> getElementosAccionContableTipoPagoGasto() throws Exception {
		List<GastoDefecto> gastosDefecto = GastoDefectoDAO.getGastoDefectos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PAGO_GASTO);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < gastosDefecto.size(); i++) {
			GastoDefecto gastoDefecto = gastosDefecto.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_PAGO_GASTO, gastoDefecto.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), gastoDefecto.getId(),
						gastoDefecto.getNombre(), ElementoConfiguracion.TIPO_PAGO_GASTO,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoGasto() throws Exception {
		List<GastoDefecto> gastosDefecto = GastoDefectoDAO.getGastoDefectos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_GASTO);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < gastosDefecto.size(); i++) {
			GastoDefecto gastoDefecto = gastosDefecto.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_PAGO_FACTURACION, gastoDefecto.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), gastoDefecto.getId(),
						gastoDefecto.getNombre(), ElementoConfiguracion.TIPO_PAGO_FACTURACION,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	//
	// Factuacion
	public List<ElementoAccionContable> getElementosAccionContableTipoFacturacion() throws Exception {
		List<CategoriaContableProducto> categoriasContablesProductos = CategoriaContableProductoDAO
				.getCategoriaContableProductos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_FACTURACION);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < categoriasContablesProductos.size(); i++) {
			CategoriaContableProducto categoria = categoriasContablesProductos.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_FACTURACION, categoria.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), categoria.getId(),
						categoria.getNombre(), ElementoConfiguracion.TIPO_FACTURACION,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoNotaCredito() throws Exception {
		List<CategoriaContableProducto> categoriasContablesProductos = CategoriaContableProductoDAO
				.getCategoriaContableProductos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_NOTA_CREDITO);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < categoriasContablesProductos.size(); i++) {
			CategoriaContableProducto categoria = categoriasContablesProductos.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_NOTA_CREDITO, categoria.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), categoria.getId(),
						categoria.getNombre(), ElementoConfiguracion.TIPO_NOTA_CREDITO,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoNotaDebito() throws Exception {
		List<CategoriaContableProducto> categoriasContablesProductos = CategoriaContableProductoDAO
				.getCategoriaContableProductos(this);
		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_NOTA_DEBITO);
		List<ElementoAccionContable> elementosAccionContableDefeinitivo = new ArrayList<ElementoAccionContable>();

		for (int i = 0; i < categoriasContablesProductos.size(); i++) {
			CategoriaContableProducto categoria = categoriasContablesProductos.get(i);
			ElementoAccionContable elementoAccionContable = ElementoAccionContableDAO.getElementoAccionContable(
					elementosAccionContable, ElementoConfiguracion.TIPO_NOTA_DEBITO, categoria.getId());
			if (elementoAccionContable == null) {
				ElementoConfiguracion elemento = new ElementoConfiguracion(-1, getCodigo(), categoria.getId(),
						categoria.getNombre(), ElementoConfiguracion.TIPO_NOTA_DEBITO,
						ElementoConfiguracion.ESTADO_ACTIVO);
				elementoAccionContable = new ElementoAccionContable(-1, getCodigo(), elemento, -1, "",
						ElementoAccionContable.ESTADO_ACTIVO);
			}
			elementosAccionContableDefeinitivo.add(elementoAccionContable);
		}
		return elementosAccionContableDefeinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoPagoFacturacion() throws Exception {

		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PAGO_FACTURACION);
		List<ElementoAccionContable> elementosAccionContableDefinitivo = new ArrayList<ElementoAccionContable>();

		if (elementosAccionContable.size() == 0) {
			ElementoConfiguracion elementoConfiguracion = new ElementoConfiguracion(-1, getCodigo(), -1,
					"Pago de factura", ElementoConfiguracion.TIPO_FACTURACION, ElementoConfiguracion.ESTADO_ACTIVO);
			ElementoAccionContable elementoAccionContable = new ElementoAccionContable(-1, getCodigo(),
					elementoConfiguracion, -1, "", ElementoAccionContable.ESTADO_ACTIVO);
			elementosAccionContableDefinitivo.add(elementoAccionContable);
		} else {
			elementosAccionContableDefinitivo.add(elementosAccionContable.get(0));
		}

		return elementosAccionContableDefinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoPagoNotaCredito() throws Exception {

		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PAGO_NOTA_CREDITO);
		List<ElementoAccionContable> elementosAccionContableDefinitivo = new ArrayList<ElementoAccionContable>();

		if (elementosAccionContable.size() == 0) {
			ElementoConfiguracion elementoConfiguracion = new ElementoConfiguracion(-1, getCodigo(), -1,
					"Pago de nota crédito", ElementoConfiguracion.TIPO_PAGO_NOTA_CREDITO,
					ElementoConfiguracion.ESTADO_ACTIVO);
			ElementoAccionContable elementoAccionContable = new ElementoAccionContable(-1, getCodigo(),
					elementoConfiguracion, -1, "", ElementoAccionContable.ESTADO_ACTIVO);
			elementosAccionContableDefinitivo.add(elementoAccionContable);
		} else {
			elementosAccionContableDefinitivo.add(elementosAccionContable.get(0));
		}

		return elementosAccionContableDefinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionContableTipoPagoNotaDebito() throws Exception {

		List<ElementoAccionContable> elementosAccionContable = ElementoAccionContableDAO
				.getElementoAccionContables(this, ElementoConfiguracion.TIPO_PAGO_NOTA_DEBITO);
		List<ElementoAccionContable> elementosAccionContableDefinitivo = new ArrayList<ElementoAccionContable>();

		if (elementosAccionContable.size() == 0) {
			ElementoConfiguracion elementoConfiguracion = new ElementoConfiguracion(-1, getCodigo(), -1,
					"Pago de nota débito", ElementoConfiguracion.TIPO_PAGO_NOTA_DEBITO,
					ElementoConfiguracion.ESTADO_ACTIVO);
			ElementoAccionContable elementoAccionContable = new ElementoAccionContable(-1, getCodigo(),
					elementoConfiguracion, -1, "", ElementoAccionContable.ESTADO_ACTIVO);
			elementosAccionContableDefinitivo.add(elementoAccionContable);
		} else {
			elementosAccionContableDefinitivo.add(elementosAccionContable.get(0));
		}

		return elementosAccionContableDefinitivo;
	}

	public List<ElementoAccionContable> getElementosAccionesContables() throws Exception {
		return ElementoAccionContableDAO.getElementoAccionContables(this);
	}

	public ElementoAccionContable getElementoAccionContable(long id) throws Exception {
		return ElementoAccionContableDAO.getElementoAccionContable(this, id);
	}

	public ElementoAccionContable getElementoAccionContable(ElementoConfiguracion elemento) throws Exception {
		return ElementoAccionContableDAO.getElementoAccionContable(this, elemento);
	}

	public ElementoAccionContable insertarElementoAccionContable(long idElemento, int tipo, String nombre,
			AccionContable accionContable) throws Exception {
		ElementoConfiguracion elemento = getElementoConfiguracion(idElemento, tipo);
		if (elemento == null) {
			elemento = insertarElementoConfiguracion(idElemento, nombre, tipo);
		}
		ElementoAccionContable elementoAccionContable = getElementoAccionContable(elemento);
		if (elementoAccionContable == null) {
			elementoAccionContable = ElementoAccionContableDAO.insertarElementoAccionContable(this, elemento,
					accionContable, ElementoAccionContable.ESTADO_ACTIVO);
		} else {
			elementoAccionContable.actualizarElementoAccionContable(elemento, accionContable);
		}

		return elementoAccionContable;
	}
}
