package co.inphotech.marshando.auxiliarcontable.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.util.json.JSONObject;

@RestController
@RequestMapping("/controllers")
public class DefectoController {

	@CrossOrigin
	@PostMapping(value = "/auxiliar-contable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getElementoContabilizados(   ) throws Exception{
		HttpHeaders responseHeaders = new HttpHeaders();
	
		JSONObject jdato = new JSONObject();
		jdato.put("AuxiliarContable", "AuxiliarContable");
		
		
		return  new ResponseEntity<String>( jdato.toString(), responseHeaders, HttpStatus.OK );
	}
}
