package co.inphotech.marshando.auxiliarcontable.controllers;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.json.JSONObject;

import co.inphotech.marshando.auxiliarcontable.accionescontables.vo.AsientoContable;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Empresa;
import co.inphotech.marshando.auxiliarcontable.usuario.vo.Usuario;

@RestController
@RequestMapping("/controllers")
public class AsientosContablesController {

	
	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable/previsualizacion-asientos-contables", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoAccionContables(  @RequestParam String codigo, @RequestParam Long idElemento, @RequestParam Integer tipo ) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
		try {
			Usuario usuario = Usuario.autorizacionCodigo(codigo);
			Empresa empresa = usuario.getEmpresa();
			List<AsientoContable>asientosContables = empresa.getAsientosContables(tipo, idElemento);
			return  new ResponseEntity<String>(  AsientoContable.toJSONArray(asientosContables).toString(), responseHeaders, HttpStatus.OK );
		} catch (Exception e) {
			e.printStackTrace();
			JSONObject jrta = new JSONObject();
			jrta.put("respuesta", "no_exito");
			jrta.put("mensaje", e.getMessage());
			return  new ResponseEntity<String>(  jrta.toString(), responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR );
		}
		
	}
}