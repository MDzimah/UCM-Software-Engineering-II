package misc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;

public class OpsBBDD {
	public static JSONObject read(String BBDD) throws BBDDReadException {
		try {
			String contenidos = new String(Files.readAllBytes(Paths.get(Messages.PATH_TO_BBDD.formatted(BBDD))));
			return new JSONObject(contenidos);
		}
		catch(IOException e) {
			throw new BBDDReadException(Messages.ERROR_LECTURA_BBDD.formatted(BBDD), e);
		}
	}
	
	public static void write(JSONObject info, String BBDD) throws BBDDWriteException {
		try(FileWriter file = new FileWriter(Messages.PATH_TO_BBDD.formatted(BBDD))){
	        file.write(info.toString()); 
	        file.flush(); //Para asegurar que el buffer se quede vacío
		}
		catch(IOException e) {
			throw new BBDDWriteException(Messages.ERROR_ESCRITURA_BBDD.formatted(BBDD), e);
		}
	}
}
