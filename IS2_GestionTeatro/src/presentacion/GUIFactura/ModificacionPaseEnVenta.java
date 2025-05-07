package presentacion.GUIFactura;
import java.util.*;

import javax.swing.*;

import misc.*;
import negocio.factura.*;
import presentacion.ViewUtils;
import presentacion.VistaDefault;

@SuppressWarnings("serial")
public abstract class ModificacionPaseEnVenta extends VistaDefault {
	private JLabel lIdPase;
	private JSpinner sIdPase;
	private JLabel lCtdad;
	private JSpinner sCtdad;
	private JButton ok;
	private JButton cancel;
	
	void initComps() {
		lIdPase = new JLabel("Id pase:");
		sIdPase = ViewUtils.integerSpinner(0, 0, Integer.MAX_VALUE, 1);
		lCtdad = new JLabel("Cantidad:");
	    sCtdad = ViewUtils.integerSpinner(1, 1, Integer.MAX_VALUE, 1);
		ok = new JButton("Aceptar");
		cancel = new JButton("Cancelar");
		
		ArrayList<Pair<JComponent, JComponent>> labeledComponents = new ArrayList<>();
		labeledComponents.add(new Pair<>(lIdPase, sIdPase));
		labeledComponents.add(new Pair<>(lCtdad, sCtdad));
		super.initComps(labeledComponents, ok, cancel);
	}
	
	void okAndCancelListener() {
		ok.addActionListener(e->{
			try {
				//Id pase
				sIdPase.commitEdit();
				int idPase = (int)sIdPase.getValue(); 
				//Cantidad
				sCtdad.commitEdit();
				int ctdad = (int)sCtdad.getValue(); 
				
				TLineaFactura tLf = new TLineaFactura(idPase, ctdad);
				if(this.accion(tLf)) dispose();
			}
			catch(Exception ex) {
				sCtdad.updateUI();
				sIdPase.updateUI();
			}
		});
		
		cancel.addActionListener(e->{dispose();});
	}
	
	abstract boolean accion(TLineaFactura tLineaFactura);
}
