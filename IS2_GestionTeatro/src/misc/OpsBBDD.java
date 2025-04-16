package misc;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;

public class OpsBBDD {
	public static JSONObject read(String BBDD) throws BBDDReadException {
		try {
			String contenidos = new String(Files.readAllBytes(Paths.get(BBDD)));
			return new JSONObject(contenidos);
		}
		catch(IOException e) {
			throw new BBDDReadException(Messages.ERROR_ESCRITURA_BBDD, e);
		}
	}
	
	public static void write(JSONObject info, String BBDD) throws BBDDWriteException {
		try(FileWriter file = new FileWriter(BBDD)){
	        file.write(info.toString()); 
	        file.flush(); //Para asegurar que el buffer se quede vacío
	        JOptionPane.showMessageDialog(null, Messages.EX_ESCRITURA_BBDD.formatted(BBDD));
		}
		catch(IOException e) {
			throw new BBDDWriteException(Messages.ERROR_LECTURA_BBDD, e);
		}
	}
}
