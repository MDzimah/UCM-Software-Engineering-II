package presentacion.GUIPase;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import misc.Evento;
import misc.Pair;
import presentacion.IGUI;
import presentacion.VistaDefault;

public class VistaCrearPase extends VistaDefault {
	
	private JButton ok;
	private JButton cancelar;
	private JLabel idCompTeaLabel;
	private JTextField idCompTeaText;
	private JLabel idObraLabel;
	private JTextField idObraText;
	private JLabel cantidadStockLabel;
	private JTextField cantidadStockText;
	private JLabel precioLabel;
	private JTextField precioText;
	
	public VistaCrearPase() {
		this.setTitle("Crear pase");
		ArrayList<Pair<JComponent, JComponent>> componentesEtiquetados = new ArrayList<>();
		componentesEtiquetados.add(new Pair<>(idCompTeaLabel,idCompTeaText));
		this.setVisible(true);	
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}
