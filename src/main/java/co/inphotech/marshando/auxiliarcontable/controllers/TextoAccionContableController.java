package co.inphotech.marshando.auxiliarcontable.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.TextoAccionContable;
import co.inphotech.marshando.auxiliarcontable.contables.vo.ElementoContabilizable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Usuario;

@RestController
@RequestMapping("/controllers")
public class TextoAccionContableController {

	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/previsualizacion-textos-contables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoAccionContables(  @RequestParam String codigo, @RequestParam Long idElemento, @RequestParam Integer tipo ) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
	
		Usuario usuario = Usuario.autorizacionCodigo(codigo);
		Empresa empresa = usuario.getEmpresa();
		ElementoContabilizable elementoContabilizable = empresa.getElementoContabilizable(tipo, idElemento);
		
		
		return  new ResponseEntity<String>(  TextoAccionContable.toJSONArray(elementoContabilizable.getTextosAccionContable(empresa)).toString(), responseHeaders, HttpStatus.OK );
	}
}
