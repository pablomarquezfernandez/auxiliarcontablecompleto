package co.inphotech.marshando.auxiliarcontable.contables.dao;

import java.util.ArrayList;
import java.util.List;

import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizado;
import co.inphotech.marshando.auxiliarcontable.externos.dao.AbonoFacturacionDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.AbonoGastoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.AbonoNominaDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.FacturaDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.GastoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.NotaCreditoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.NotaDebitoDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.PeriodoSeguridadSocialDAO;
import co.inphotech.marshando.auxiliarcontable.externos.dao.PeriodoTrabajadorDAO;
import co.inphotech.marshando.auxiliarcontable.externos.vo.AbonoFacturacion;
import co.inphotech.marshando.auxiliarcontable.externos.vo.AbonoGasto;
import co.inphotech.marshando.auxiliarcontable.externos.vo.AbonoNomina;
import co.inphotech.marshando.auxiliarcontable.externos.vo.Factura;
import co.inphotech.marshando.auxiliarcontable.externos.vo.Gasto;
import co.inphotech.marshando.auxiliarcontable.externos.vo.NotaCredito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.NotaDebito;
import co.inphotech.marshando.auxiliarcontable.externos.vo.PeriodoSeguridadSocial;
import co.inphotech.marshando.auxiliarcontable.externos.vo.PeriodoTrabajador;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;

public  class ElementoContabilizableDAO {

	
	
	public static ElementoContabilizable getElementoContabilizable(Empresa empresa, int tipo, long idElemento) throws Exception {
		ElementoContabilizable elementoContabilizable = null;
		if (tipo == ElementoContabilizable.TIPO_FACTURA) {
			elementoContabilizable = ElementoContabilizableDAO.getElementoContabilizableFactura(empresa, idElemento);
		}
		if (tipo == ElementoContabilizable.TIPO_NOTA_CREDITO) {
			elementoContabilizable = ElementoContabilizableDAO.getElementoContabilizableNotaCredito(empresa, idElemento);
		}
		if (tipo == ElementoContabilizable.TIPO_NOTA_DEBITO) {
			elementoContabilizable = ElementoContabilizableDAO.getElementoContabilizableNotaDebito(empresa, idElemento);
		}
		
		return elementoContabilizable;
	}
	
	public static ElementoContabilizable getElementoContabilizableFactura(Empresa empresa, long idElemento) throws Exception {
		ElementoContabilizable elementoContabilizable = FacturaDAO.getFactura(empresa, idElemento);
		ElementoContabilizado elementoContabilizado = empresa.getElementoContabilizado(idElemento, ElementoContabilizable.TIPO_FACTURA);
		elementoContabilizable.setElementoContabilizad(elementoContabilizado);;
		return elementoContabilizable;
	}
	public static ElementoContabilizable getElementoContabilizableNotaCredito(Empresa empresa, long idElemento) throws Exception {
		ElementoContabilizable elementoContabilizable = NotaCreditoDAO.getNotaCredito(empresa, idElemento);
		ElementoContabilizado elementoContabilizado = empresa.getElementoContabilizado(idElemento, ElementoContabilizable.TIPO_NOTA_CREDITO);
		elementoContabilizable.setElementoContabilizad(elementoContabilizado);;
		return elementoContabilizable;
	}
	public static ElementoContabilizable getElementoContabilizableNotaDebito(Empresa empresa, long idElemento) throws Exception {
		ElementoContabilizable elementoContabilizable = NotaDebitoDAO.getNotaDebito(empresa, idElemento);
		ElementoContabilizado elementoContabilizado = empresa.getElementoContabilizado(idElemento, ElementoContabilizable.TIPO_NOTA_DEBITO);
		elementoContabilizable.setElementoContabilizad(elementoContabilizado);;
		return elementoContabilizable;
	}
	
	
	
	
	
	public static List<ElementoContabilizable> getElementoContabilizables( Empresa empresa, int tipo, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizables = new ArrayList<ElementoContabilizable>();
		if (tipo == ElementoContabilizable.TIPO_FACTURA) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesFactura(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_NOTA_CREDITO) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesNotaCredito(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_NOTA_DEBITO) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesNotaDebito(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PERIODO_TRABAJADOR) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesPeriodoNominaTrabajador(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PERIODO_SEGURIDAD_SOCIAL) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesPeriodoNominaSeguridadSocial(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_GASTO) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesGasto(empresa, anio, mes);
		}
		
		
		if (tipo == ElementoContabilizable.TIPO_PAGO_FACTURA ) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesAbonoFactura(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PAGO_NOTA_CREDITO ) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesAbonoNotasCredito(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PAGO_NOTA_DEBITO ) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesAbonoNotasDebito(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PAGO_GASTO ) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesAbonoGastos(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PAGO_PERIODO_TRABAJADOR ) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesAbonosNominaTrabajador(empresa, anio, mes);
		}
		if (tipo == ElementoContabilizable.TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL ) {
			elementosContabilizables = ElementoContabilizableDAO.getElementosContabilizablesAbonosNominaSeguridadSocial(empresa, anio, mes);
		}
		
		return elementosContabilizables;
	}
	
	
	public static List<ElementoContabilizable> getElementosContabilizablesFactura(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();

		List<Factura> facturas = FacturaDAO.getFacturas(empresa, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_FACTURA, anio, mes);

		for (int i = 0; i < facturas.size(); i++) {
			Factura factura = facturas.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, factura.getId(), ElementoContabilizable.TIPO_FACTURA);
			ElementoContabilizable elementoContabilizable = factura;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	
	public static List<ElementoContabilizable> getElementosContabilizablesPeriodoNominaTrabajador(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();

		List<PeriodoTrabajador> periodosTrabajador = PeriodoTrabajadorDAO.getPeriodosTrabajadores(empresa, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PERIODO_TRABAJADOR, anio, mes);

		for (int i = 0; i < periodosTrabajador.size(); i++) {
			PeriodoTrabajador periodoTrabajador = periodosTrabajador.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, periodoTrabajador.getId(), ElementoContabilizable.TIPO_PERIODO_TRABAJADOR);
			ElementoContabilizable elementoContabilizable = periodoTrabajador;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	public static List<ElementoContabilizable> getElementosContabilizablesPeriodoNominaSeguridadSocial(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<PeriodoSeguridadSocial> periodosSeguridadSocial = PeriodoSeguridadSocialDAO.getPeriodosTrabajadores(empresa, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PERIODO_SEGURIDAD_SOCIAL, anio, mes);

		for (int i = 0; i < periodosSeguridadSocial.size(); i++) {
			PeriodoSeguridadSocial periodoSeguridadSocial = periodosSeguridadSocial.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, periodoSeguridadSocial.getId(), ElementoContabilizable.TIPO_PERIODO_SEGURIDAD_SOCIAL);
			ElementoContabilizable elementoContabilizable = periodoSeguridadSocial;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	public static List<ElementoContabilizable> getElementosContabilizablesGasto(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<Gasto> gastos = GastoDAO.getGastos(empresa, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_GASTO, anio, mes);

		for (int i = 0; i < gastos.size(); i++) {
			Gasto gasto = gastos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, gasto.getId(), ElementoContabilizable.TIPO_GASTO);
			ElementoContabilizable elementoContabilizable = gasto;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);;
			
		}
		return elementosContabilizadosDefinitivo;
	}
	public static List<ElementoContabilizable> getElementosContabilizablesNotaCredito(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<NotaCredito> notasCredito = NotaCreditoDAO.getNotaCreditos(empresa, anio, mes);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_NOTA_CREDITO, anio, mes);

		for (int i = 0; i < notasCredito.size(); i++) {
			NotaCredito notaCredito = notasCredito.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, notaCredito.getId(), ElementoContabilizable.TIPO_NOTA_CREDITO);
			ElementoContabilizable elementoContabilizable = notaCredito;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	
	public static List<ElementoContabilizable> getElementosContabilizablesNotaDebito(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<NotaDebito> notasDebito = NotaDebitoDAO.getNotaDebitos(empresa, anio, mes);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_NOTA_DEBITO, anio, mes);

		for (int i = 0; i < notasDebito.size(); i++) {
			NotaDebito notaDebito = notasDebito.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, notaDebito.getId(), ElementoContabilizable.TIPO_NOTA_DEBITO);
			ElementoContabilizable elementoContabilizable = notaDebito;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}

	public static List<ElementoContabilizable> getElementosContabilizablesAbonoFactura(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<AbonoFacturacion> abonos =  AbonoFacturacionDAO.getAbonosFacturas(empresa, AbonoFacturacion.TIPO_FACTURA, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PAGO_FACTURA, anio, mes);

		for (int i = 0; i < abonos.size(); i++) {
			AbonoFacturacion abonoFacturacion = abonos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, abonoFacturacion.getId(), ElementoContabilizable.TIPO_PAGO_FACTURA);
			ElementoContabilizable elementoContabilizable = abonoFacturacion;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	public static List<ElementoContabilizable> getElementosContabilizablesAbonoNotasCredito(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<AbonoFacturacion> abonos =  AbonoFacturacionDAO.getAbonosFacturas(empresa, AbonoFacturacion.TIPO_NOTA_CREDITO, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PAGO_NOTA_CREDITO, anio, mes);

		for (int i = 0; i < abonos.size(); i++) {
			AbonoFacturacion abono = abonos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, abono.getId(), ElementoContabilizable.TIPO_PAGO_NOTA_CREDITO);
			ElementoContabilizable elementoContabilizable = abono;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	public static List<ElementoContabilizable> getElementosContabilizablesAbonoNotasDebito(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<AbonoFacturacion> abonos =  AbonoFacturacionDAO.getAbonosFacturas(empresa, AbonoFacturacion.TIPO_NOTA_DEBITO, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PAGO_NOTA_DEBITO, anio, mes);

		for (int i = 0; i < abonos.size(); i++) {
			AbonoFacturacion abono = abonos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, abono.getId(), ElementoContabilizable.TIPO_PAGO_NOTA_DEBITO);
			ElementoContabilizable elementoContabilizable = abono;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	
	public static List<ElementoContabilizable> getElementosContabilizablesAbonoGastos(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<AbonoGasto> abonos =  AbonoGastoDAO.getAbonosGastos(empresa, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PAGO_GASTO, anio, mes);

		for (int i = 0; i < abonos.size(); i++) {
			AbonoGasto abono = abonos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, abono.getId(), ElementoContabilizable.TIPO_PAGO_GASTO);
			ElementoContabilizable elementoContabilizable = abono;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	
	public static List<ElementoContabilizable> getElementosContabilizablesAbonosNominaTrabajador(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<AbonoNomina> abonos =  AbonoNominaDAO.getAbonosNominas(empresa, AbonoNomina.TIPO_TRABAJADOR, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PAGO_PERIODO_TRABAJADOR, anio, mes);

		for (int i = 0; i < abonos.size(); i++) {
			AbonoNomina abono = abonos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, abono.getId(), ElementoContabilizable.TIPO_PAGO_PERIODO_TRABAJADOR);
			ElementoContabilizable elementoContabilizable = abono;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	public static List<ElementoContabilizable> getElementosContabilizablesAbonosNominaSeguridadSocial(Empresa empresa, int anio, int mes) throws Exception {
		List<ElementoContabilizable> elementosContabilizadosDefinitivo = new ArrayList<ElementoContabilizable>();
		List<AbonoNomina> abonos =  AbonoNominaDAO.getAbonosNominas(empresa, AbonoNomina.TIPO_SEGURIDAD_SOCIAL, mes, anio);
		List<ElementoContabilizado> elementosContabilizados = ElementoContabilizadoDAO.getElementoContabilizados(empresa, ElementoContabilizable.TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL, anio, mes);

		for (int i = 0; i < abonos.size(); i++) {
			AbonoNomina abono = abonos.get(i);
			ElementoContabilizado elementoContabilizado = ElementoContabilizadoDAO.getElementoContabilizado( elementosContabilizados, abono.getId(), ElementoContabilizable.TIPO_PAGO_PERIODO_SEGURIDAD_SOCIAL);
			ElementoContabilizable elementoContabilizable = abono;
			elementoContabilizable.setElementoContabilizad(elementoContabilizado);
			elementosContabilizadosDefinitivo.add( elementoContabilizable );
			
		}
		return elementosContabilizadosDefinitivo;
	}
	
	
	
}
