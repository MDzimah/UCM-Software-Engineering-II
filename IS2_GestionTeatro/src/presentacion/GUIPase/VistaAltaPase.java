package presentacion.GUIPase;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import misc.Messages;
import misc.Pair;
import negocio.pase.TPase;
import presentacion.Evento;
import presentacion.IGUI;
import presentacion.ViewUtils;
import presentacion.VistaDefault;
import presentacion.controlador.Controlador;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class VistaAltaPase extends VistaDefault {
	
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
	
	public VistaAltaPase() {
		this.setTitle("CREAR PASE");
		ok = new JButton("Aceptar");
		cancelar = new JButton("cancelar:");
		idCompTeaLabel = new JLabel("id de la companya teatral:");
		idCompTeaText = new JTextField(20);
		idObraLabel = new JLabel("id de la obra:");
		idObraText = new JTextField(20);
		cantidadStockLabel = new JLabel("stock:");
		cantidadStockText = new JTextField(20);
		precioLabel = new JLabel("precio de compra:");
		precioText = new JTextField(20);
		ArrayList<Pair<JComponent, JComponent>> componentesEtiquetados = new ArrayList<>();
		componentesEtiquetados.add(new Pair<>(idCompTeaLabel,idCompTeaText));
		componentesEtiquetados.add(new Pair<>(idObraLabel,idObraText));
		componentesEtiquetados.add(new Pair<>(cantidadStockLabel,cantidadStockText));
		componentesEtiquetados.add(new Pair<>(precioLabel,precioText));
		super.initComps(componentesEtiquetados, ok, cancelar);
		this.setVisible(true);
		ok.addActionListener(e -> {
			int idCompTea = Integer.valueOf(idCompTeaText.getText());
			int idObra = Integer.valueOf(idObraText.getText());
			int stock = Integer.valueOf(cantidadStockText.getText());
			int precio = Integer.valueOf(precioText.getText());
			TPase tPase = new TPase(-1, idCompTea, idObra, true, null, stock, precio); //Buscar forma de hacer la fecha
			Controlador.getInstance().accion(Evento.ALTA_PASE, tPase);
			this.dispose();
		});
		cancelar.addActionListener(e ->{
			this.setVisible(false);
			this.dispose();
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_OK) {
			ViewUtils.createDialogMessage(Messages.EX_PASE_CREADO);
		}
		else if(evento == Evento.RES_KO) {
			ViewUtils.createErrorDialogMessage(Messages.X_PASE_CREADO + ' ' + Messages.MOTIVO.formatted(((Exception)datos).getMessage()));
		}
	}

}
