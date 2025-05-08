package presentacion.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.AlreadyClienteException;
import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.DuplicateElementException;
import exceptions.InvalidFields;
import exceptions.UnknownClienteException;
import exceptions.UnknownMiemCompTeaException;
import exceptions.UnknownObraException;
import exceptions.UnknownTaquilleroException;
import misc.Messages;
import negocio.cliente.SACliente;
import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import negocio.compTea.SACompTea;
import negocio.compTea.TCompTea;
import negocio.factoria.FactoriaAbstractaNegocio;
import negocio.factura.*;
import negocio.miemCompTea.*;
import negocio.obra.*;
import negocio.pase.SAPase;
import negocio.pase.TPase;
import negocio.taquillero.SATaquillero;
import negocio.taquillero.TTaquillero;
import presentacion.Evento;
import presentacion.GUICliente.VistaActualizarClNormal;
import presentacion.GUICliente.VistaActualizarClVIP;
import presentacion.GUICompTea.VistaActualizarCompania_1;
import presentacion.GUIMiemCompTea.VistaActualizarMiembroCompania_1;
import presentacion.GUIObra.VistaActualizarObra_1;
import presentacion.GUIPase.VistaActualizarPase_1;
import presentacion.GUITaquillero.VistaActualizarTaquillero_1;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {
	@Override
	public void accion(Evento evento, Object datos) {
		switch(evento) {
		//Factura
		case CERRAR_VENTA: {
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			TDatosVenta tDV = (TDatosVenta)datos;
			try {
				int idFac = saFac.crearFactura(tDV);
				
				if (idFac != -1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, idFac);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			}
			catch (BBDDReadException | BBDDWriteException | UnknownClienteException | UnknownTaquilleroException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_FACTURA: {
			try {
				int idFac = (int)datos;
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				TFactura tFacBuscada = saFac.read(idFac);
			
				if(tFacBuscada != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tFacBuscada);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, idFac);
			}
			catch(BBDDReadException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case MOSTRAR_FACTURAS: {
			try {
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				Collection<TFactura> allFacturas = saFac.allFacturas();
				
				if (allFacturas != null && !allFacturas.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, allFacturas); 
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null); 
			}
			catch(BBDDReadException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_FACTURAS_POR_CLIENTE: {
			try {
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				Collection<TFactura> allFacturasPorCliente = saFac.allFacturasPorCliente((int) datos);
				
				if (allFacturasPorCliente != null && !allFacturasPorCliente.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, allFacturasPorCliente);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (int)datos);
			}
			catch(BBDDReadException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		//Cliente
		case ALTA_CLIENTE: {
			try {
				SACliente sa = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				int id = sa.create((TCliente) datos);
				if (id == -1) throw new AlreadyClienteException();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
			}
			catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_CLIENTE: {
			try {
				SACliente sa = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				TCliente tCliente = sa.read((int)datos);
				if (tCliente == null) throw new UnknownClienteException();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tCliente);
			}
			catch (Exception e){
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BAJA_CLIENTE: {
			try {
				SACliente sa = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				int id = sa.delete((int)datos);
				if (id == -1) throw new UnknownClienteException();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
			}
			catch (Exception e){
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case MOSTRAR_CLIENTES: {
			try {
				SACliente sa = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				ArrayList<TCliente> cl = (ArrayList<TCliente>) sa.readAll();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, cl);
			}
			catch (Exception e){
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_CLIENTE: {
			try {
				SACliente sa = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				int id = (int) datos;
				TCliente tCliente = sa.read(id);
				if (tCliente == null) throw new UnknownClienteException();
				if (tCliente.getTipo() == "Normal") {
					VistaActualizarClNormal vistaAct = (VistaActualizarClNormal) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_CLIENTE_NORMAL);
					vistaAct.cargarCliente((TClienteNormal) tCliente,id);
				}
				else if (tCliente.getTipo() == "VIP") {
					VistaActualizarClVIP vistaAct = (VistaActualizarClVIP) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_CLIENTE_VIP);
					vistaAct.cargarCliente((TClienteVIP) tCliente,id);
				}
			}
			catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_CLIENTE_NORMAL: {
			try {
				SACliente saCl = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				TCliente tCliente = (TCliente) datos;
				int id = saCl.update(tCliente);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_CLIENTE_VIP: {
			try {
				SACliente saCl = FactoriaAbstractaNegocio.getInstance().crearSACliente();
				TCliente tCliente = (TCliente) datos;
				int id = saCl.update(tCliente);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		//Taquillero
		case ALTA_TAQUILLERO: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				int idTaq = saTaq.create((TTaquillero) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, idTaq);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BAJA_TAQUILLERO: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				int idDelete = (int) datos;
				int idTaq = saTaq.delete(idDelete);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, idTaq);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case BUSCAR_TAQUILLERO: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				int idBuscar = (int) datos;
				TTaquillero tTaq = saTaq.read(idBuscar);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tTaq);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case BUSCAR_DNI_TAQUILLERO: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				String dniBuscar = (String) datos;
				TTaquillero tTaq = saTaq.readByDNI(dniBuscar);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tTaq);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case MOSTRAR_TAQUILLEROS: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				Collection<TTaquillero> listaTaq = saTaq.readAll();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, listaTaq);
				
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case ACTUALIZAR_TAQUILLERO_0: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				int idBuscar = (int) datos;
				TTaquillero tTaq = saTaq.read(idBuscar);
				VistaActualizarTaquillero_1 vistaActTaq = (VistaActualizarTaquillero_1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_TAQUILLERO_1);
				vistaActTaq.cargarTaquillero(tTaq);
				vistaActTaq.setVisible(true);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case ACTUALIZAR_TAQUILLERO_1: {
			try {
				SATaquillero saTaq = FactoriaAbstractaNegocio.getInstance().crearSATaquillero();
				TTaquillero tTaq = (TTaquillero) datos;
				int idTaq = saTaq.update(tTaq);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, idTaq);
			} catch (Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
			
		//Pase
		case ALTA_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idCreado = saPase.create((TPase) datos);
				if (idCreado != -1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, idCreado);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e); //Esto comprende errores de BD, de que no exista la obra o que no exista la companya teatral
			}
			break;
		}
		case BAJA_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idEliminado = saPase.delete((int) datos);
				if (idEliminado != -1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, idEliminado);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null); //No se ha encontrado el pase
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				TPase tPaseBuscado = saPase.read((int) datos);
				if (tPaseBuscado != null) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tPaseBuscado); //le paso el transfer para que lo muestre
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_PASES:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				Collection<TPase> pases = saPase.readAll();
				if (pases != null && !pases.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, pases); //le paso el transfer para que lo muestre
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_PASE_0:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				TPase tPaseActualizar = saPase.read((int)datos);
				if (tPaseActualizar != null) {
					VistaActualizarPase_1 vista = (VistaActualizarPase_1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_PASE_1);
					vista.cargarPase(tPaseActualizar);
				}
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_PASE_1:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int operacion = saPase.update((TPase) datos);
				if (operacion != -1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_PASES_POR_OBRA:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				ArrayList<TPase> pasesPorObra = saPase.allPasesPorObra((int) datos);
				if (pasesPorObra != null && !pasesPorObra.isEmpty()) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, pasesPorObra);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		//Obra
		case ALTA_OBRA:{
			try{
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				int val = saObra.create((TObra)datos);
				if(val >=0) 
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, val);
				else throw new DuplicateElementException();
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
			
		}
		case BAJA_OBRA:{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				int val = saObra.delete((int)datos);
				if(val>=0)
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)datos);
				else throw new UnknownObraException();
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_OBRA_0:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				TObra obra = saObra.read((int) datos);
				if(obra==null)
					throw new UnknownObraException();
				VistaActualizarObra_1 vista= (VistaActualizarObra_1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_OBRA_1);
				vista.setDatos(obra);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_OBRA_1:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				int val=saObra.update((TObra)datos);
				if(val>=0)
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, ((TObra) datos).getIdObra());
				else
					throw new UnknownObraException();
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case CONSULTAR_OBRA:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				TObra obra = saObra.read((int) datos);
				if(obra != null)
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obra);
				else 
					throw new UnknownObraException();
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_OBRA:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				List<TObra> obras = (List<TObra>) saObra.search((List<String>)datos);
				if(!(obras==null || obras.isEmpty()))
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obras);
				else 
					throw new UnknownObraException();
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_OBRAS:
		{
			try {
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				List<TObra> obras = (List<TObra>) saObra.readAll();
				if(!(obras==null || obras.isEmpty()))
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obras);
				else 
					throw new UnknownObraException();
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		//CompTea
		case ACTUALIZAR_COMPANIA_TEATRAL_0:
		{
			
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				Integer id =(Integer)datos;
				TCompTea newComp= saCompTea.read(id);
				if(newComp!=null) {

					VistaActualizarCompania_1 vista= (VistaActualizarCompania_1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_COMPANIA_TEATRAL_1);

					vista.cargar(newComp);
				}
				else {
					throw new Exception(""+id);
				}
				}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_COMPANIA_TEATRAL_1:
		{
			
			try {
				TCompTea tCompTea =(TCompTea)datos;
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				int id= saCompTea.update(tCompTea);
				if(id!=-1) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);//TODO no se si hay que mostrar aqui
				}
				else {
					throw new Exception("" +id);
				}
				}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				int id =(int)datos;
				TCompTea newComp= saCompTea.read(id);
				if(newComp!= null) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, newComp);
				}
				else {
					throw new Exception(Messages.X_BUSCAR_COMPANIA+" id:"+Integer.toString(id));
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BAJA_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				Integer id =(Integer)datos;
				int id2= saCompTea.delete(id);
				if(id2!=-1) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id2);
				}
				else {
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, id2);
				}
				}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ALTA_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				TCompTea tCompTea =(TCompTea)datos;
				int id2= saCompTea.create(tCompTea);
				if(id2!=-1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)id2);
				else {
					throw new Exception("Ya existe la Compania con ese nombre");
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ANYADIR_MIEMBRO_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				TCompT_MiemCompT tCM =(TCompT_MiemCompT)datos;
				int id2= saCompTea.addMember(tCM);
				if(id2!=-1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)id2);
				else {
					throw new Exception(Messages.NOEXISTEMIEMOCOMP);
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BORRAR_MIEMBRO_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				TCompT_MiemCompT tCompMiem =(TCompT_MiemCompT)datos;
				int id2= saCompTea.removeMember(tCompMiem.getIdCompania(),tCompMiem.getIdMiembroComp());
				if(id2!=-1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)id2);
				else {
					throw new Exception(Messages.NOEXISTERELACION);
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				Collection<TCompTea> tCompsTeas =saCompTea.readAll();
				ArrayList<TCompTea> p= new ArrayList<>(tCompsTeas);
				if(tCompsTeas!=null) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, p);
				}
				else {
				throw new Exception();
				}
			}
			
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		//MiemCompTea
		case ACTUALIZAR_MIEMBRO_COMPANIA_0:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				TMiemCompTea tMiemComp = saMiemComp.read((int)datos);
				if (tMiemComp != null) {
					VistaActualizarMiembroCompania_1 vista= (VistaActualizarMiembroCompania_1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_MIEMBRO_COMPANIA_1);
					vista.cargarMiembro(tMiemComp);
				}
				else {
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new UnknownMiemCompTeaException());
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_MIEMBRO_COMPANIA_1:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				int id= saMiemComp.update((TMiemCompTea) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_MIEMBRO_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				TMiemCompTea tMiemComp = saMiemComp.read((int)datos);
			
				if(tMiemComp != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tMiemComp);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new UnknownMiemCompTeaException());
			}
			catch(Exception e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
			break;
		}
		case ALTA_MIEMBRO_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				int id = saMiemComp.create((TMiemCompTea) datos);
				if(id >= 0) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new DuplicateElementException());
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BAJA_MIEMBRO_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				int id = saMiemComp.delete((int)datos);
				
				if(id != -1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)datos);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new UnknownMiemCompTeaException());
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_MIEMBROS_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				Collection<TMiemCompTea> miembros = saMiemComp.readAll();
				if(miembros != null) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, miembros);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new UnknownMiemCompTeaException());
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		default: break;
	}
	}

}
