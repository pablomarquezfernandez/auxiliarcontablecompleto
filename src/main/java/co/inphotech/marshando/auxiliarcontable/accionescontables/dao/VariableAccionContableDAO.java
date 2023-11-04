package co.inphotech.marshando.auxiliarcontable.accionescontables.dao;

import java.util.List;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.VariableAccionContable;

public class VariableAccionContableDAO {

	
	
	public static VariableAccionContable getVariableAccionContable( List<VariableAccionContable> variablesAccionContable, String codigo, String elemento ) {
		VariableAccionContable variableAccionContable = null;
		boolean encontro = false;
		
		for( int i = 0; i < variablesAccionContable.size() && !encontro; i++ ) {
			VariableAccionContable variableAccionContableTemp = variablesAccionContable.get(i);
			if( variableAccionContableTemp.getCodigo().equals(codigo)  &&  variableAccionContableTemp.getElemento().equals(elemento) ) {
				variableAccionContable = variableAccionContableTemp;
				encontro = true;
			}
		}
		
		return variableAccionContable;
	}
	
	
	
	
}
