package misc;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

import org.json.JSONObject;

public class OpsBBDD {
	public static JSONObject read(String BBDD) {
		String contenidos = "";
		try {
			contenidos = new String(Files.readAllBytes(Paths.get(BBDD)));
		} 
		catch (IOException e) {
			 JOptionPane.showMessageDialog(null,
		                Messages.ERROR_LECTURA_BBDD.formatted(BBDD),
		                "Error Lectura",
		                JOptionPane.ERROR_MESSAGE);
		}
		return new JSONObject(contenidos);
	}
	
	public static void write(JSONObject bdInfo, String BBDD) {
		try (FileWriter file = new FileWriter(BBDD)) {
            file.write(bdInfo.toString()); 
            file.flush(); //Para asegurar que el buffer se quede vacío
            JOptionPane.showMessageDialog(null, Messages.EX_ESCRITURA_BBDD.formatted(BBDD));
        } 
		catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    Messages.ERROR_ESCRITURA_BBDD.formatted(BBDD),
                    "Error Escritura",
                    JOptionPane.ERROR_MESSAGE);
        }
	}
}
