package misc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;

public class OpsBBDD {
	public static JSONObject read(String BDSubs) throws BBDDReadException {
		try {
			String contenidos = new String(Files.readAllBytes(Paths.get(Messages.PATH_TO_BBDD.formatted(BDSubs))));
			return new JSONObject(contenidos);
		}
		catch(IOException e) {
			throw new BBDDReadException(Messages.ERROR_LECTURA_BBDD.formatted(BDSubs), e);
		}
	}
	
	public static void write(JSONObject info, String BDSubs) throws BBDDWriteException {
		try(FileWriter file = new FileWriter(Messages.PATH_TO_BBDD.formatted(BDSubs))){
	        file.write(info.toString(4)); 
	        file.flush(); //Para asegurar que el buffer se quede vacío
		}
		catch(IOException e) {
			throw new BBDDWriteException(Messages.ERROR_ESCRITURA_BBDD.formatted(BDSubs), e);
		}
	}
	
	public static boolean isEmpty(String BDSubs) {
		return new File(Messages.PATH_TO_BBDD.formatted(BDSubs)).length() == 0;
	}
}
