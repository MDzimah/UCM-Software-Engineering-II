package presentacion.controlador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.BBDDReadException;
import exceptions.BBDDWriteException;
import exceptions.InvalidFields;
import exceptions.UnknownClienteException;
import exceptions.UnknownTaquilleroException;
import misc.Messages;
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
import presentacion.GUICompTea.VistaActualizarCompania1;
import presentacion.GUIFactura.AbrirVenta;
import presentacion.GUIMiemCompTea.VistaActualizarMiembroCompania_1;
import presentacion.GUIObra.VistaActualizarObra_1;
import presentacion.GUIPase.VistaActualizarPaseDescarga;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class ControladorImp extends Controlador {
	@Override
	public void accion(Evento evento, Object datos) {
		switch(evento) {
		
		//Factura
		case ANYADIR_PASE_A_VENTA: {
			try {
				TLineaFactura newTLf = (TLineaFactura)datos;
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				boolean res = saFac.anyadirPaseAVenta(newTLf, AbrirVenta.getCarrito());
				
				if (res) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, newTLf.getIdPase());
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			}
			catch(BBDDReadException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case QUITAR_PASE_DE_VENTA: {
			try {
				TLineaFactura tLfAQuitar = (TLineaFactura)datos;
				SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
				boolean res = saFac.quitarPaseDeVenta(tLfAQuitar, AbrirVenta.getCarrito());
						
				if (res) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tLfAQuitar.getIdPase());
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, tLfAQuitar.getIdPase());
			}
			catch(BBDDReadException e) {
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
		case CERRAR_VENTA: {
			SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();
			TDatosVenta tDV = (TDatosVenta)datos;
			try {
				saFac.crearFactura(tDV);
			}
			catch (BBDDReadException | BBDDWriteException | UnknownClienteException | UnknownTaquilleroException e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			
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
		
		
		
		
		//Cliente
		case ALTA_CLIENTE: 
		case BUSCAR_CLIENTE: 
		case BAJA_CLIENTE:
		case MOSTRAR_CLIENTES: 
		case ACTUALIZAR_CLIENTE: 
		
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
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tTaq);
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
				saPase.create((TPase) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BAJA_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idBuscado = (int) datos;
				saPase.delete(idBuscado);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BUSCAR_PASE: {
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idBuscado = (int) datos;
				TPase tPaseBuscado = saPase.read(idBuscado);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tPaseBuscado); //le paso el transfer para que lo muestre
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_PASES:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				Collection<TPase> pases = saPase.readAll();
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, pases); //le paso el transfer para que lo muestre
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_PASE_CARGA:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idBuscado = (int)datos;
				TPase tPaseActualizar = saPase.read(idBuscado);
				VistaActualizarPaseDescarga vista = (VistaActualizarPaseDescarga) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_PASE_DESCARGA);
				vista.cargarPase(tPaseActualizar);
			} catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_PASE_DESCARGA:{
			try {
				SAObra saPase = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				saPase.update((TObra) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case MOSTRAR_PASES_POR_OBRA:{
			try {
				SAPase saPase = FactoriaAbstractaNegocio.getInstance().crearSAPase();
				int idObra = (int) datos;
				ArrayList<TPase> pasesPorObra = saPase.allPasesPorObra(idObra);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, pasesPorObra);
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
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, val);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case BAJA_OBRA:{
			try {
				Integer i = Integer.valueOf((String)datos);
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				saObra.delete(i);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)datos);
			}
			catch(NumberFormatException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new InvalidFields());
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case ACTUALIZAR_OBRA_0:
		{
			try {
				Integer i = Integer.valueOf((String)datos);
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				TObra obra = saObra.read(i);
				VistaActualizarObra_1 vista= (VistaActualizarObra_1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_OBRA_1);
				vista.setDatos(obra);
			}
			catch(NumberFormatException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new InvalidFields());
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
				saObra.update((TObra)datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, ((TObra) datos).getIdObra());
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		case CONSULTAR_OBRA:
		{
			try {
				Integer i = Integer.valueOf((String)datos);
				SAObra saObra = FactoriaAbstractaNegocio.getInstance().crearSAObra();
				TObra obra = saObra.read(i);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obra);
			}
			catch(NumberFormatException e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, new InvalidFields());
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
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obras);
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
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, obras);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
			break;
		}
		
		//CompTea
		case ACTUALIZAR0_COMPANIA_TEATRAL:
		{
			
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				Integer id =(Integer)datos;
				TCompTea newComp= saCompTea.read(id);
				if(newComp!=null) {
				/*FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, newComp);*///TODO no se si hay que mostrar aqui
					VistaActualizarCompania1 vista= (VistaActualizarCompania1) FactoriaAbstractaPresentacion.getInstance().createVista(Evento.ACTUALIZAR_OBRA_1);
					vista.cargar(newComp);
				}
				else {
					throw new InvalidFields();
				}
				}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
		}
		case ACTUALIZAR1_COMPANIA_TEATRAL:
		{
			
			try {
				TCompTea tCompTea =(TCompTea)datos;
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				int id= saCompTea.update(tCompTea);
				if(id!=-1) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);//TODO no se si hay que mostrar aqui
				}
				else {
					throw new Exception("no se pudo actualizar la compania con id: "+id);
				}
				}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e);
			}
		}
		case BUSCAR_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				Integer id =(Integer)datos;
				TCompTea newComp= saCompTea.read(id);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, newComp);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
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
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
		}
		case ALTA_COMPANIA_TEATRAL:
		{
			try {
				SACompTea saCompTea=FactoriaAbstractaNegocio.getInstance().crearSACompTea();
				TCompTea tCompTea =(TCompTea)datos;
				int id2= saCompTea.create(tCompTea);
				if(id2!=-1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)id2);
				else {
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (int)id2);
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: " +e.getMessage());
			}
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
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: no hay companias que mostrar");
				}
			}
			
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, "Error: "+ e.getMessage());
			}
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
					FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, Messages.ID_NO_ENCONTRADO);
				}
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
			break;
		}
		case ACTUALIZAR_MIEMBRO_COMPANIA_1:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				saMiemComp.update((TMiemCompTea) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, null);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
			break;
		}
		case BUSCAR_MIEMBRO_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				TMiemCompTea tMiemComp = saMiemComp.read((int)datos);
			
				if(tMiemComp != null)	FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, tMiemComp);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (int)datos);
			}
			catch(Exception e) {
				 FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
			
			break;
		}
		case CONTRATAR_MIEMBRO_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				int id = saMiemComp.create((TMiemCompTea) datos);
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, id);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
			break;
		}
		case DESPEDIR_MIEMBRO_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				int id = saMiemComp.delete((int)datos);
				
				if(id != -1) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, (int)datos);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, (int)datos);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
			break;
		}
		case MOSTRAR_MIEMBROS_COMPANIA:
		{
			try {
				SAMiemCompTea saMiemComp = FactoriaAbstractaNegocio.getInstance().crearSAMiemCompTea();
				Collection<TMiemCompTea> miembros = saMiemComp.readAll();
				if(miembros != null) FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_OK, miembros);
				else FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, null);
			}
			catch(Exception e) {
				FactoriaAbstractaPresentacion.getInstance().createVista(evento).actualizar(Evento.RES_KO, e.getMessage());
			}
			break;
		}
		
		//Todas las ventanas que abren a otras (las que no sean de CU)
		default: break;
	}
	}

}
