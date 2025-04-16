package presentacion.GUIfactura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import eventos.Evento;
import misc.Constants;
import misc.Messages;
import misc.PanelUtils;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.superClases.TablaDefault;


@SuppressWarnings("serial")
public class VistaMostrarFacs extends JFrame implements IGUI {
	private JButton mostrar;
	private JButton cancel;
	
	public VistaMostrarFacs(boolean paraDiferenciarConConstructorPorDefecto) {}
	
	public VistaMostrarFacs() {
		super("MOSTRAR FACTURAS");
		this.mostrar = new JButton("Mostrar");
		this.cancel = new JButton("Cancelar");
		JPanel responsePanel = PanelUtils.createResponsePair(this.mostrar, this.cancel);
		responsePanel.setSize(Constants.getScaledScreenDimension(2, 2));
		
		mostrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Controlador.getInstance().accion(Evento.MOSTRAR_FACTURAS, null);
				dispose();
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { dispose(); }
		});
	}
	
	@Override
	public void actualizar(Evento evento, Object datos) {
		if (evento == Evento.RES_MOSTRAR_FACTURAS_OK) {
			@SuppressWarnings("unchecked")
			Collection<Object> castedData = (Collection<Object>)datos;
			String[] nomCols = {"ID","ID CLIENTE", "ID TAQUILLERO", "FECHA", "PASES COMPRADOS", "IMPORTE"};
			TablaDefault tb = new TablaDefault(nomCols, castedData, "MOSTRAR FACTURAS");
			tb.setVisible(true);
		}
		else if(evento == Evento.RES_MOSTRAR_FACTURAS_KO) PanelUtils.panelMessage(this, Messages.X_MOSTRAR_FACTURAS);
	}

}
