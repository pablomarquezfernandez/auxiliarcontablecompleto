package co.inphotech.marshando.auxiliarcontable.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.net.ssl.HttpsURLConnection;




public class UtilMundo {
	
	
	public static String inputStreamToString(InputStream in) throws Exception
	{
		
		BufferedReader reader = new BufferedReader( new InputStreamReader( in ) );
		StringBuffer sb = new StringBuffer();
		String str;
		while((str = reader.readLine())!= null){
			sb.append(str);
		}
		
		return sb.toString();
	}
	public static void zipArchivos( List<File>archivos, File archivo) throws Exception {
		List<String> srcFiles = new ArrayList<String>();
		for( int i = 0; i < archivos.size();i++ ) {
			srcFiles.add( archivos.get(i).getAbsolutePath() );
		}
		FileOutputStream fos = new FileOutputStream(archivo.getAbsolutePath());
		ZipOutputStream zipOut = new ZipOutputStream(fos);
		int i = 0;
		for (String srcFile : srcFiles) {
			i++;
			System.out.println("-------------->>>>---->>> " + i);
			
			File fileToZip = new File(srcFile);
			FileInputStream fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zipOut.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zipOut.write(bytes, 0, length);
			}
			fis.close();
		}
		zipOut.close();
		fos.close();
	}
	
	public static String getTituloMesAnio(  int anio, int mes) {
		String titulo = "";
		
		if( anio != -1 && mes != -1) {
			titulo = getNombreMes(mes) + "/" + anio;
		}else if(anio != -1) {
			titulo = anio + "";
		}else if(mes != -1) {
			titulo = getNombreMes(mes) + "";
		}
		
		return titulo;
	}
	public static Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	public static int contarOcurrenciasCadena(List<String>listado, String cadena) {
		int cuantas = 0;
		for( int i = 0; i < listado.size();i++ ) {
			String item = listado.get(i);
			if( item.equals(cadena) ) {
				cuantas++;
			}
		}
		return cuantas;
	}
	
	
	
	
	

	
	public static boolean compararIgualesFechas( Date fecha1, Date fecha2 ) {
		Calendar cfecha1 = Calendar.getInstance();
		cfecha1.setTime(fecha1);
		
		Calendar cfecha2 = Calendar.getInstance();
		cfecha2.setTime(fecha2);
		
		return cfecha1.get(Calendar.YEAR) == cfecha2.get(Calendar.YEAR) &&
				cfecha1.get(Calendar.MONTH) == cfecha2.get(Calendar.MONTH) &&
				cfecha1.get(Calendar.DAY_OF_MONTH) == cfecha2.get(Calendar.DAY_OF_MONTH);
				
	}
	
	
	public static String formatearNumeroFacturacionFaceldi(double valor) {
		int numeroDecimales = 6;
		
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0 ) {
			String []sdata = (ddecimales + "").split("\\.");
			decimales = sdata[1];
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= numeroDecimales ) {
			while(decimales.length() < numeroDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < numeroDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "," + decimales;
	}
	
	public static String formatearNumeroFacturacionOASISCOM(double valor) {
		int cantidadDecimales = 4;
		
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0 ) {
			String []sdata = (ddecimales + "").split("\\.");
			decimales = sdata[1];
		}
		long numero = (long) valor;
//		String numero = NumberFormat.getInstance().format( (long) valor ) ;
//		String numero2 = NumberFormat.getInstance().format( valor ) ;
//		String svalor = valor + "";
//		System.out.println(svalor);
//		String []sdata = svalor.split("\\.");
//		System.out.println(sdata.length);
//		String decimales = "0";
//		System.out.println( sdata[0] + " ---  " + sdata[1] + " --- " +  numero2 + " ---" + svalor);
//		if( sdata.length >= 2 ) {
//			decimales = sdata[1];
//		}
//		numero = ((long) valor) + "";
		
		System.out.println( numero + " --- " + decimales );
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "." + decimales;
	}
	public static String formatearNumeroCadenaCUFE(double valor) {
		int cantidadDecimales = 2;
		
		
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0 ) {
			String []sdata = (ddecimales + "").split("\\.");
			decimales = sdata[1];
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "." + decimales;
	}
	public static String formatearNumeroCadena(double valor) {
		int cantidadDecimales = 2;
		
		
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0 ) {
			String []sdata = (ddecimales + "").split("\\.");
			decimales = sdata[1];
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "." + decimales;
	}
	public static String formatearNumeroCompletoDosDecimales(double valor) {
		int cantidadDecimales = 2;
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0) {
			String []sdata = (ddecimales + "").split("\\.");
			if( sdata.length >=2 ) {
				decimales = sdata[1];
			}
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "," + decimales;
	}
	
	
	public static String formatearNumeroCompletoDosDecimalesFactura(double valor) {
		int cantidadDecimales = 2;
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0) {
			String []sdata = (ddecimales + "").split("\\.");
			if( sdata.length >=2 ) {
				decimales = sdata[1];
			}
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return NumberFormat.getInstance().format(numero)  + "." + decimales;
	}
	public static String formatearNumeroCompletoTresDecimales(double valor) {
		int cantidadDecimales = 3;
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0 ) {
			String []sdata = (ddecimales + "").split("\\.");
			decimales = sdata[1];
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "," + decimales;
	}
	
	
	public static String formatearNumeroCompletoTresDecimalesRetenciones(double valor) {
//		int cantidadDecimales = 3;
//		double ddecimales = 0;
//		long lvalor = (long) valor;
//		ddecimales = valor - ((double) lvalor);
//		String decimales = "0";
//		if( ddecimales != 0 ) {
//			String []sdata = (ddecimales + "").split("\\.");
//			decimales = sdata[1];
//		}
//		long numero = (long) valor;
//		
//		
//		if( decimales.length() <= cantidadDecimales ) {
//			while(decimales.length() < cantidadDecimales) {
//				decimales = decimales + "0";
//			}
//		}else{
//			String respuesta = "";
//			for( int i = 0; i < cantidadDecimales;i++ ) {
//				respuesta += decimales.charAt(i);
//			}
//			decimales = respuesta;
//		}
//		
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.HALF_EVEN);
		String nformateado = df.format(  valor );
		System.out.println( nformateado );
		nformateado = nformateado.replaceAll(",", ".");
		System.out.println(  Double.parseDouble( nformateado ) );
		
//		if(decimales.endsWith("0")) {
//			decimales = decimales.substring(0, decimales.length() - 1);
//		}
//		if(decimales.endsWith("0")) {
//			decimales = decimales.substring(0, decimales.length() - 1);
//		}
//		if(decimales.endsWith("0")) {
//			decimales = decimales.substring(0, decimales.length() - 1);
//		}
//		if( decimales.equals("") ) {
//			return numero + "";
//		}
//		return numero + "." + decimales;
		return nformateado;
	}
	
	public static String formatearNumeroDosDecimales(double valor) {
		int cantidadDecimales = 2;
		double ddecimales = 0;
		long lvalor = (long) valor;
		ddecimales = valor - ((double) lvalor);
		String decimales = "0";
		if( ddecimales != 0 ) {
			String []sdata = (ddecimales + "").split("\\.");
			decimales = sdata[1];
		}
		long numero = (long) valor;
		
		
		if( decimales.length() <= cantidadDecimales ) {
			while(decimales.length() < cantidadDecimales) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < cantidadDecimales;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return NumberFormat.getInstance().format(numero)  + "," + decimales;
	}
	
	public static String formatearNumeroFacturacionDosDecimales(double valor) {
		String svalor = valor + "";
		System.out.println(svalor);
		String []sdata = svalor.split("\\.");
		String decimales = "0";
		if( sdata.length >= 2 ) {
			decimales = sdata[1];
		}
		String numero = ((long) valor ) + "" ;
		
		
		if( decimales.length() <= 2 ) {
			while(decimales.length() < 2) {
				decimales = decimales + "0";
			}
		}else{
			String respuesta = "";
			for( int i = 0; i < 2;i++ ) {
				respuesta += decimales.charAt(i);
			}
			decimales = respuesta;
		}
		
		return numero + "." + decimales;
	}
	public static Date getZeroTimeFecha(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date = calendar.getTime();
		return date;
	}
	public static String numeroToString(long numero)
	{
		String snumero  = "";
		long millones = numero/1000000;
		if( millones > 0 )
		{
			if( millones  == 1)
			{
				snumero += "un millón "; 
			}else{
				snumero += UtilMundo.numero3DecimasToString((int) millones) + " millones ";
			}
		}
		long miles = (numero%1000000)/1000;
		if( miles > 0 )
		{
			if( miles  == 1)
			{
				snumero += "MIL "; 
			}else{
				snumero += UtilMundo.numero3DecimasToString((int) miles) + " mil ";
			}
		}
		long fin = numero%1000;
		if( fin > 0 )
		{
			snumero += UtilMundo.numero3DecimasToString((int) fin ) ;
		}
		return snumero;
	}
	public static String numero3DecimasToString(int numero)
	{
		int centenas = numero/100;
		int decenas = (numero%100)/10;
		int unidades = numero%10;
		String snumero = "";
		if(centenas == 1 && decenas == 0 && unidades == 0)
		{
			return "cien";
		}else{
			if(centenas == 1)
			{
				snumero = "ciento";
			}
			else if(centenas == 2)
			{
				snumero = "doscientos";
			}
			else if(centenas == 3)
			{
				snumero = "trescientos";
			}
			else if(centenas == 4)
			{
				snumero = "cuatrocientos";
			}
			else if(centenas == 5)
			{
				snumero = "quinientos";
			}
			else if(centenas == 6)
			{
				snumero = "seicientos";
			}
			else if(centenas == 7)
			{
				snumero = "setecientos";
			}
			else if(centenas == 8)
			{
				snumero = "ochocientos";
			}
			else if(centenas == 9)
			{
				snumero = "novecientos";
			}
			//
			// Decenas
			if(decenas == 1 && unidades == 1)
			{
				snumero += " once";
			}
			else if(decenas == 1 && unidades == 2)
			{
				snumero += " doce";
			}
			else if(decenas == 1 && unidades == 3)
			{
				snumero += " trece";
			}
			else if(decenas == 1 && unidades == 4)
			{
				snumero += " catorce";
			}
			else if(decenas == 1 && unidades == 5)
			{
				snumero += " quince";
			}
			else{
				if(decenas == 1 )
				{
					snumero += " diez";
				}
				else if(decenas == 2 )
				{
					snumero += " veinte";
				}
				else if(decenas == 3 )
				{
					snumero += " treinta";
				}
				else if(decenas == 4 )
				{
					snumero += " cuarenta";
				}
				else if(decenas == 5 )
				{
					snumero += " cincuenta";
				}
				else if(decenas == 6 )
				{
					snumero += " sesenta";
				}
				else if(decenas == 7 )
				{
					snumero += " setenta";
				}
				else if(decenas == 8 )
				{
					snumero += " ochenta";
				}
				else if(decenas == 9 )
				{
					snumero += " noventa";
				}
				//
				// Unidades
				if(unidades == 1)
				{
					snumero += " uno";
				}
				else if(unidades == 2)
				{
					snumero += " dos";
				}
				else if(unidades == 3)
				{
					snumero += " tres";
				}
				else if(unidades == 4)
				{
					snumero += " cuatro";
				}
				else if(unidades == 5)
				{
					snumero += " cinco";
				}
				else if(unidades == 6)
				{
					snumero += " seis";
				}
				else if(unidades == 7)
				{
					snumero += " siete";
				}
				else if(unidades == 8)
				{
					snumero += " ocho";
				}
				else if(unidades == 9)
				{
					snumero += " nueve";
				}
			}
			
			return snumero.trim();
		}
		
		
	}
	public static String formatearHTML(String mensaje)
	{
		mensaje = mensaje.replaceAll("á", "&aacute;");
		mensaje = mensaje.replaceAll("é", "&eacute;");
		mensaje = mensaje.replaceAll("í", "&iacute;");
		mensaje = mensaje.replaceAll("ó", "&oacute;");
		mensaje = mensaje.replaceAll("ú", "&uacute;");
		mensaje = mensaje.replaceAll("ñ", "&ntilde;");
		
		mensaje = mensaje.replaceAll("Á", "&Aacute;");
		mensaje = mensaje.replaceAll("É", "&Eacute;");
		mensaje = mensaje.replaceAll("Í", "&Iacute;");
		mensaje = mensaje.replaceAll("Ó", "&Oacute;");
		mensaje = mensaje.replaceAll("Ú", "&Uacute;");
		mensaje = mensaje.replaceAll("Ñ", "&Ntilde;");
		
		
		return mensaje;
	}
	
	public static String formatearSinTildesN(String mensaje)
	{
		mensaje = mensaje.replaceAll("á", "a");
		mensaje = mensaje.replaceAll("é", "e");
		mensaje = mensaje.replaceAll("í", "i");
		mensaje = mensaje.replaceAll("ó", "o");
		mensaje = mensaje.replaceAll("ú", "u");
		mensaje = mensaje.replaceAll("ñ", "n");
		
		mensaje = mensaje.replaceAll("Á", "A");
		mensaje = mensaje.replaceAll("É", "E");
		mensaje = mensaje.replaceAll("Í", "I");
		mensaje = mensaje.replaceAll("Ó", "O");
		mensaje = mensaje.replaceAll("Ú", "U");
		mensaje = mensaje.replaceAll("Ñ", "N");
		
		mensaje = mensaje.replaceAll("&", "Y");
		
		
		return mensaje;
	}
	
	
	public static String letrasYNumeroAleatorios(int cantidad){
		Random r = new Random();
		String data = "";
		String alphabet = "1234567890abcdefghijklmnopqrstuvwxyz";
		for (int i = 0; i < cantidad; i++) {
//			System.out.println(alphabet.charAt(r.nextInt(alphabet.length())));
			data += alphabet.charAt(r.nextInt(alphabet.length()));
		}
		return data.toUpperCase();
	}
	
	public static String leerIS(InputStream in) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		String resultado = "";
		String data = br.readLine();
		while( data != null){
			resultado += data + "\n";
			data = br.readLine();
		}
		
		return resultado;
	}
	public static Date stringToDateHTMLInput(String fecha){
		try {
			String []data = fecha.split("-");
			int dia = Integer.parseInt(data[2]);
			int mes = Integer.parseInt(data[1]) - 1;
			int anio = Integer.parseInt(data[0]);
			
			Calendar cfecha = Calendar.getInstance();
			cfecha.set(Calendar.DAY_OF_MONTH, dia);
			cfecha.set(Calendar.MONTH, mes);
			cfecha.set(Calendar.YEAR, anio);
			
			//System.out.println( cfecha.getTime()  );
			
			return cfecha.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return  null;
	}
	public static Date stringToDateHTMLHoraInput(String fecha){
		try {
			String []dfecha = fecha.split(" ");
			
			String []data = dfecha[0].split("-");
			int dia = Integer.parseInt(data[2]);
			int mes = Integer.parseInt(data[1]) - 1;
			int anio = Integer.parseInt(data[0]);
			
			Calendar cfecha = Calendar.getInstance();
			cfecha.set(Calendar.DAY_OF_MONTH, dia);
			cfecha.set(Calendar.MONTH, mes);
			cfecha.set(Calendar.YEAR, anio);
			
			
			data = dfecha[1].split(":");
			cfecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(data[0]));
			cfecha.set(Calendar.MINUTE, Integer.parseInt(data[1]) );
			cfecha.set(Calendar.SECOND, 0);
			
			//System.out.println( cfecha.getTime()  );
			
			return cfecha.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return  null;
	}
	
	public static String dateToStringInputHTML(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTime(fecha);
//			<input type="date" id="fechaFirmaContrato_editarDatosContrato" value="" class="form-control" placeholder="Fecha firma contrato">
			String sanio = cfecha.get(Calendar.YEAR) + "";
			String smes = ( cfecha.get(Calendar.MONTH) + 1) + "";
			if(smes.length() == 1){
				smes = "0" + smes;
			}
			String sdia = cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if(sdia.length() == 1){
				sdia = "0" + sdia;
			}
			return sanio + "-" + smes  + "-" + sdia;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return "";
		
	}
	public static Date getDateBancolmbia(String sfecha){
		try {
			Calendar cFecha = Calendar.getInstance();
			int anio = Integer.parseInt( sfecha.substring(0, 4) ) ;
			int mes = Integer.parseInt( sfecha.substring(4, 6) );
			int dia = Integer.parseInt( sfecha.substring(6, 8) ) ;
			System.out.println(" ** " + anio + " -- " + mes + " -- " + dia);
			
			
			if( anio < 2000 || anio > 2050 || dia > 31 || mes > 12) {
				throw new Exception();
			}
			
			cFecha.set(Calendar.YEAR, anio );
			cFecha.set(Calendar.MONTH, mes -1);
			cFecha.set(Calendar.DAY_OF_MONTH, dia);
			
			return cFecha.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			
			
			try {
				Calendar cFecha = Calendar.getInstance();
				
				String dia = sfecha.substring(0, 2);
				String mes = sfecha.substring(2, 4);
				String anio = sfecha.substring(4, 8);
				
				
				
				System.out.println(" ** " + anio + " -- " + mes + " -- " + dia);
				
				cFecha.set(Calendar.YEAR, Integer.parseInt(anio) );
				cFecha.set(Calendar.MONTH, Integer.parseInt(mes) -1);
				cFecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
				
				return cFecha.getTime();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
		
	}
	public static String getTextoDate(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTime(fecha);;
			
			String dia = cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if( dia.length() == 1 ) {
				dia = "0" + dia;
			}
			String mes = UtilMundo.getNombreMes( cfecha.get(Calendar.MONTH) + 1  ) + "";
			
			String anio = cfecha.get(Calendar.YEAR)  + "";
			
			return dia + " de " + mes + " del año " + anio;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	public static String getDateOasis(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTime(fecha);;
			
			String dia = cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if( dia.length() == 1 ) {
				dia = "0" + dia;
			}
			String mes = (cfecha.get(Calendar.MONTH) + 1 )+ "";
			if( mes.length() == 1 ) {
				mes = "0" + mes;
			}
			String anio = cfecha.get(Calendar.YEAR)  + "";
			
			return dia + "/" + mes + "/" + anio;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	public static String getDateHoraOasis(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTime(fecha);;
			
			String dia = cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if( dia.length() == 1 ) {
				dia = "0" + dia;
			}
			String mes = (cfecha.get(Calendar.MONTH) + 1 )+ "";
			if( mes.length() == 1 ) {
				mes = "0" + mes;
			}
			String anio = cfecha.get(Calendar.YEAR)  + "";
			
			
			String hora = cfecha.get( Calendar.HOUR_OF_DAY ) + "";
			if( hora.length() == 1 ) {
				hora = "0" + hora;
			}
			String minutos = cfecha.get( Calendar.MINUTE ) + "";
			if( minutos.length() == 1 ) {
				minutos = "0" + minutos;
			}
			String segundos = cfecha.get( Calendar.SECOND ) + "";
			if( segundos.length() == 1 ) {
				segundos = "0" + segundos;
			}
			
			String milisegundos = cfecha.get( Calendar.MILLISECOND ) + "";
			if( milisegundos.length() == 2 ) {
				milisegundos = "0" + milisegundos;
			}
			if( milisegundos.length() == 1 ) {
				milisegundos = "00" + milisegundos;
			}
			
			return dia + "/" + mes + "/" + anio + " " + hora + ":" + minutos + ":" + segundos + ":" + milisegundos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	public static Date getDateDBSinHoraPegado(String sfecha){
		try {
			Calendar cFecha = Calendar.getInstance();
			String anio = sfecha.replaceAll(" ", "").substring(0, 4);
			String mes = sfecha.replaceAll(" ", "").substring(4, 6);
			String dia = sfecha.replaceAll(" ", "").substring(6, 8);
//			
//			System.out.println(" ** " + anio + " -- " + mes + " -- " + dia);
			
			cFecha.set(Calendar.YEAR, Integer.parseInt(anio) );
			cFecha.set(Calendar.MONTH, Integer.parseInt(mes) -1);
			cFecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dia));
			
			return cFecha.getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static void verificarContrasena(String contrasena) throws Exception{
		if(contrasena.length() < 8)
		{
			throw new Exception("Contraseñas deben tener por lo menos 8 caracteres");
		}
		
	}
	
	public static String formatearNumero( double numero ){
		return NumberFormat.getInstance().format(numero);
	}
	public static String formatearNumero( long numero ){
		return NumberFormat.getInstance().format(numero);
	}
	public static String getExtensionArchivo(String nombreArchivo) throws Exception{
		String []data = nombreArchivo.split("\\.");
		return data[ data.length - 1];
	}
	
	public static int diferenciaDias(Date fechaPosterior, Date fechaAnterior) throws Exception{
		int diferencia = 0;
		try {
			long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
			diferencia = (int) ( ( fechaPosterior.getTime() - fechaAnterior.getTime() )/MILLSECS_PER_DAY );
		} catch (Exception e) {
		}
			 
		return diferencia;
	}
	public static void escribitArchivo(String archivo, String texto) throws Exception{
		FileWriter myWriter = new FileWriter(archivo);
		myWriter.write(texto);
		myWriter.close();
	}
	public static String leerArchivo(String archivo) throws Exception{
		FileReader fr = new FileReader(archivo);
		BufferedReader br = new BufferedReader(fr);
	
		
		String sCurrentLine;
		String texto = "";
		while ((sCurrentLine = br.readLine()) != null) {
			texto += sCurrentLine + "\n";
		}
		br.close();
		return texto;
	}
	
	
	public static String leerArchivoUTF8(String archivo) throws Exception{
//		FileReader fr =new FileInputStream("DirectionResponse.xml"), "UTF-8");
//		BufferedReader br = new BufferedReader(fr);
//		BufferedReader br = new BufferedReader(new InputStreamReader(
//			    new FileInputStream("DirectionResponse.xml"), "UTF-8"));
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), "UTF-8"));
		String sCurrentLine;
		String texto = "";
		while ((sCurrentLine = br.readLine()) != null) {
			texto += sCurrentLine + "\n";
		}
		br.close();
		return texto;
	}
	public static void validarContrasena(String contrasena) throws Exception{
		if(contrasena.length() < 8)
		{
			throw new Exception("La contrasena debe tener por lo menos 8 caracteres");
		}
	}
	public static String getPropiedad( String nombre ) throws Exception
	{
		String valor = "";
		String sql = "SELECT * FROM Propiedades WHERE nombre = ?";
		
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, nombre);
		ResultSet rs = stm.executeQuery();
		
		if( rs.next() )
		{
			valor = rs.getString("valor");
		}
		
		MySQLManager.getInstance().desconectar(conn);
		
		return valor;
	}
	public static void actualizarPropiedad( String nombre, String valor ) throws Exception
	{
		String sql = "UPDATE Propiedades SET valor = ? WHERE nombre = ?";
		
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, valor);
		stm.setString(2, nombre);
		
		stm.execute();
		MySQLManager.getInstance().desconectar(conn);
		
	}
	public static String getMD5( String texto ) throws Exception
	{
		String retorno = null;
		String sql = "SELECT MD5(?)";
		
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, texto);
		ResultSet rs = stm.executeQuery();
		
		if( rs.next() )
		{
			retorno = rs.getString(1);
		}
		
		MySQLManager.getInstance().desconectar(conn);
		
		return retorno;
	}
	public static String generarSHA364( String input ) throws Exception{
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		byte[] messageDigest = md.digest(input.getBytes()); 
		BigInteger no = new BigInteger(1, messageDigest);
		String hashtext = no.toString(16);
		
		while (hashtext.length() < 32) { 
            hashtext = "0" + hashtext; 
        }
		
		return hashtext;
	}
	
	
	public static void print_content(HttpsURLConnection con) {
		if (con != null) {

			try {

				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	public static int calcularDiferenciaDias(Date fechaInicial, Date fechaFinal) throws Exception
	{
		int dias = 0;
		String sql = "SELECT DATEDIFF(?,?) as dias";
		
		Connection conn = MySQLManager.getInstance().darConexion(sql);
		PreparedStatement stm = conn.prepareStatement(sql);
		stm.setString(1, getDBDateSinHora(fechaInicial) );
		stm.setString(2, getDBDateSinHora(fechaFinal) );
		
		
		ResultSet rs = stm.executeQuery();
		
		if(rs.next())
		{
			dias = rs.getInt("dias");
		}
		
		
		MySQLManager.getInstance().desconectar(conn);
		return Math.abs( dias );
	}
	public static long getIdInsercion(Connection conn) throws Exception
	{
		long id = -1;
		String sql = "SELECT LAST_INSERT_ID() as id";
		PreparedStatement stm = conn.prepareStatement(sql);
		
		ResultSet rs = stm.executeQuery();
		if( rs.next() )
		{
			id = rs.getLong("id");
		}
		return id;
	}public static String getHora(Date fecha){
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			
			int hora = calendar.get(Calendar.HOUR_OF_DAY);
			String shora = hora+ "";
			if(shora.length() < 2){
				shora = "0" + shora;
			}
			int minutos = calendar.get(Calendar.MINUTE);
			String sminutos = minutos+ "";
			if(sminutos.length() < 2){
				sminutos = "0" + sminutos;
			}
			
			
			String sfecha = shora + ":" + sminutos;
			return sfecha;
		} catch (Exception e) {
			
		}
		return null;
	}
	public static String getJSONDate(Date fecha){
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			String sfecha = calendar.get( Calendar.DAY_OF_MONTH )  + "/" + (calendar.get( Calendar.MONTH ) + 1) + "/" + calendar.get( Calendar.YEAR ) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":00";
			return sfecha;
		} catch (Exception e) {
			
		}
		return null;
		
	}
	public static String getDBDate(Date fecha){
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			String sfecha = calendar.get( Calendar.YEAR ) + "-" + (calendar.get( Calendar.MONTH ) + 1) + "-" + calendar.get( Calendar.DAY_OF_MONTH ) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":00";
			return sfecha;
		} catch (Exception e) {
			
		}
		return null;
		
	}
	public static String getDBDateSinHora(Date fecha){
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			String sfecha = calendar.get( Calendar.YEAR ) + "-" + (calendar.get( Calendar.MONTH ) + 1) + "-" + calendar.get( Calendar.DAY_OF_MONTH );
			return sfecha;
		} catch (Exception e) {
			
		}
		return null;
		
	}
	
	public static String getNombreMes(Date fecha){
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			String nombre = "Enero";
			int mes = calendar.get(Calendar.MONTH);
			if( mes == 1 ) {
				nombre = "Febrero";
			}
			if( mes == 2 ) {
				nombre = "Marzo";
			}
			if( mes == 3 ) {
				nombre = "Abril";
			}
			if( mes == 4 ) {
				nombre = "Mayo";
			}
			if( mes == 5 ) {
				nombre = "Junio";
			}
			if( mes == 6 ) {
				nombre = "Julio";
			}
			if( mes == 7 ) {
				nombre = "Agosto";
			}
			if( mes == 8 ) {
				nombre = "Septiémbre";
			}
			if( mes == 9 ) {
				nombre = "Octubre";
			}
			if( mes == 10 ) {
				nombre = "Noviembre";
			}
			if( mes == 11 ) {
				nombre = "Diciembre";
			}
			
			return nombre;
		} catch (Exception e) {
			
		}
		return "";
		
	}
	public static String getNombreMes(int mes){
		try {
			
			String nombre = "Enero";
			if( mes == 2 ) {
				nombre = "Febrero";
			}
			if( mes == 3 ) {
				nombre = "Marzo";
			}
			if( mes == 4 ) {
				nombre = "Abril";
			}
			if( mes == 5 ) {
				nombre = "Mayo";
			}
			if( mes == 6 ) {
				nombre = "Junio";
			}
			if( mes == 7 ) {
				nombre = "Julio";
			}
			if( mes == 8 ) {
				nombre = "Agosto";
			}
			if( mes == 9 ) {
				nombre = "Septiémbre";
			}
			if( mes == 10 ) {
				nombre = "Octubre";
			}
			if( mes == 11 ) {
				nombre = "Noviembre";
			}
			if( mes == 12 ) {
				nombre = "Diciembre";
			}
			
			return nombre;
		} catch (Exception e) {
			
		}
		return "";
		
	}
	public static String getNombreCortoMes(int mes){
		try {
			
			String nombre = "Ene";
			if( mes == 2 ) {
				nombre = "Feb";
			}
			if( mes == 3 ) {
				nombre = "Mar";
			}
			if( mes == 4 ) {
				nombre = "Abril";
			}
			if( mes == 5 ) {
				nombre = "Mayo";
			}
			if( mes == 6 ) {
				nombre = "Jun";
			}
			if( mes == 7 ) {
				nombre = "Jul";
			}
			if( mes == 8 ) {
				nombre = "Agos";
			}
			if( mes == 9 ) {
				nombre = "Sept";
			}
			if( mes == 10 ) {
				nombre = "Oct";
			}
			if( mes == 11 ) {
				nombre = "Nov";
			}
			if( mes == 12 ) {
				nombre = "Dic";
			}
			
			return nombre;
		} catch (Exception e) {
			
		}
		return "";
		
	}
	public static Date getDateDB(String sfecha){
		try {
			Calendar cFecha = Calendar.getInstance();
			String []data = sfecha.split(" ");
			String []fecha = data[0].split("-");
			String []tiempo = data[1].split(":");
			
			cFecha.set(Calendar.YEAR, Integer.parseInt(fecha[0]));
			cFecha.set(Calendar.MONTH, Integer.parseInt(fecha[1]) -1);
			cFecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fecha[2]));
			
			cFecha.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tiempo[0]));
			cFecha.set(Calendar.MINUTE, Integer.parseInt(tiempo[1]));
			cFecha.set(Calendar.MILLISECOND,0 );
			
			return cFecha.getTime();
		} catch (Exception e) {
			
		}
		return null;
		
	}
	public static Date getDateDBSinHora(String sfecha){
		try {
			Calendar cFecha = Calendar.getInstance();
			String []fecha = sfecha.split("-");
			
			cFecha.set(Calendar.YEAR, Integer.parseInt(fecha[0]));
			cFecha.set(Calendar.MONTH, Integer.parseInt(fecha[1]) -1);
			cFecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fecha[2]));
			
			return cFecha.getTime();
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
		
	}
	public static String dateToStringConHora(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			
			String shora = cfecha.get(Calendar.HOUR_OF_DAY) + "";
			if(shora.length() == 1){
				shora = "0" +shora;
			}
			String sminutos = cfecha.get(Calendar.MINUTE) + "";
			if(sminutos.length() == 1){
				sminutos = "0" +sminutos;
			}
			
			return cfecha.get(Calendar.DAY_OF_MONTH) + "/" + ( cfecha.get(Calendar.MONTH) + 1)  + "/" + cfecha.get(Calendar.YEAR) + " " + 
										shora + ":" + sminutos;
		} catch (Exception e) {
		}
		return "";
		
	}
	public static String dateToStringCadena(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			
			String smes =( cfecha.get(Calendar.MONTH) + 1) + "";
			if(smes.length() == 1){
				smes = "0" +smes;
			}
			String sdia =  cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if(sdia.length() == 1){
				sdia = "0" +sdia;
			}
			
			return cfecha.get(Calendar.YEAR)  + "-" +  smes  + "-" + sdia;
		} catch (Exception e) {
		}
		return "";
		
	}
	public static String horaToStringCadena(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			
			String shora = cfecha.get(Calendar.HOUR_OF_DAY) + "";
			if(shora.length() == 1){
				shora = "0" +shora;
			}
			String sminutos = cfecha.get(Calendar.MINUTE) + "";
			if(sminutos.length() == 1){
				sminutos = "0" +sminutos;
			}
			
			return shora  + ":" + sminutos  + ":00-05:00" ;
		} catch (Exception e) {
		}
		return "";
		
	}
	public static String horaToStringCadenaCUFE(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			
			String shora = cfecha.get(Calendar.HOUR_OF_DAY) + "";
			if(shora.length() == 1){
				shora = "0" +shora;
			}
			String sminutos = cfecha.get(Calendar.MINUTE) + "";
			if(sminutos.length() == 1){
				sminutos = "0" +sminutos;
			}
			
			return shora  + ":" + sminutos  + ":00" ;
		} catch (Exception e) {
		}
		return "";
		
	}
	public static String dateToString(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			return cfecha.get(Calendar.DAY_OF_MONTH) +  "/" + (cfecha.get(Calendar.MONTH) + 1) + "/" + cfecha.get(Calendar.YEAR) ;
		} catch (Exception e) {
		}
		return "";
		
	}
	public static String dateToStringFullcalendar(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			
			
			String smes = (cfecha.get(Calendar.MONTH) + 1) + "";
			if(smes.length() == 1){
				smes = "0" +smes;
			}
			String sdia = cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if(sdia.length() == 1){
				sdia = "0" + sdia;
			}
			
			
			String shora = cfecha.get(Calendar.HOUR_OF_DAY) + "";
			if(shora.length() == 1){
				shora = "0" +shora;
			}
			String sminutos = cfecha.get(Calendar.MINUTE) + "";
			if(sminutos.length() == 1){
				sminutos = "0" +sminutos;
			}
			
			return cfecha.get(Calendar.YEAR) + "-" + smes  + "-" + sdia + "T" +  shora + ":" + sminutos+":00.008";
		} catch (Exception e) {
		}
		return "";
	}
	public static String dateToStringFaceldi(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			String sdia = cfecha.get(Calendar.DAY_OF_MONTH) + "";
			if( sdia.length() == 1 ) {
				sdia = "0" + sdia;
			}
			String smes = (cfecha.get(Calendar.MONTH) + 1) + "";
			if( smes.length() == 1 ) {
				smes = "0" + smes;
			}
			
			return sdia +  "/" + smes + "/" + cfecha.get(Calendar.YEAR) ;
		} catch (Exception e) {
		}
		return "";
		
	}
	
	public static String dateToStringSinEspacios(Date fecha){
		try {
			Calendar cfecha = Calendar.getInstance();
			cfecha.setTimeInMillis(fecha.getTime());
			
			int mes = cfecha.get(Calendar.MONTH) + 1;
			String smes = mes + "";
			if( smes.length() == 1 ) {
				smes = "0" + smes;
			}
			int dia = cfecha.get(Calendar.DAY_OF_MONTH) + 1;
			String sdia = dia + "";
			if( sdia.length() == 1 ) {
				sdia = "0" + sdia;
			}
			return  cfecha.get(Calendar.YEAR) + "" +  smes  + "" + sdia;
		} catch (Exception e) {
		}
		return "";
		
	}
	public static Date stringToDateSiigo(String sfecha){
		try {
			String []dataFecha = sfecha.trim().split("/");
			
			Calendar cfecha = Calendar.getInstance();
			cfecha.set(Calendar.YEAR, Integer.parseInt( dataFecha[0] ));
			cfecha.set(Calendar.MONTH, Integer.parseInt( dataFecha[1] ) - 1);
			cfecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt( dataFecha[2] ) );
			
			return cfecha.getTime();
		} catch (Exception e) {
		}
		return null;
		
	}
	
	public static Date stringToDate(String sfecha){
		try {
			String []dataFecha = sfecha.trim().split("/");
			
			Calendar cfecha = Calendar.getInstance();
			cfecha.set(Calendar.YEAR, Integer.parseInt( dataFecha[2] ));
			cfecha.set(Calendar.MONTH, Integer.parseInt( dataFecha[1] ) - 1);
			cfecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt( dataFecha[0] ) );
			
			return cfecha.getTime();
		} catch (Exception e) {
		}
		return null;
		
	}
	public static Date stringToDateBancoOccidente(String sfecha){
		try {
			String []dataFecha = sfecha.trim().split("/");
			
			Calendar cfecha = Calendar.getInstance();
			cfecha.set(Calendar.YEAR, Integer.parseInt( dataFecha[0] ));
			cfecha.set(Calendar.MONTH, Integer.parseInt( dataFecha[1] ) - 1);
			cfecha.set(Calendar.DAY_OF_MONTH, Integer.parseInt( dataFecha[2] ) );
			
			return cfecha.getTime();
		} catch (Exception e) {
		}
		return null;
		
	}
	public static String peticionesGET(String ip, String administradorServicio, String accion) throws Exception{
		
		URL url = new URL("http://" + ip + "/" + administradorServicio + "/" + accion);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	
		conn.setDoOutput(true);
		conn.setInstanceFollowRedirects(false);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("charset", "utf-8");
		conn.setUseCaches(false);
	
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String input;
		String retorno = "";
	
		while ((input = br.readLine()) != null) {
			retorno += input;
		}
		br.close();
		
		return retorno;
	
	}
	
	
	
}
