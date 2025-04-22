package integracion.factura;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import integracion.factoria.FactoriaAbstractaIntegracion;
import misc.Messages;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class DAOFacturaImpTest {

    private DAOFactura daoFac = FactoriaAbstractaIntegracion.getInstance().crearDAOFactura();

    private String contenidoOriginal;
    
    @AfterEach
    void devolverOgContenido() throws Exception{
    	FileWriter file = new FileWriter(Messages.PATH_TO_BBDD.formatted(Messages.BDFac));
	    file.write(this.contenidoOriginal); 
	    file.flush();
	    file.close();
    }
    
    @Test
    public void test() throws Exception {
    	//Antes de hacer el test, guardamos lo que había
    	
    	this.contenidoOriginal = new String(Files.readAllBytes(Paths.get(Messages.PATH_TO_BBDD.formatted(Messages.BDFac))));
    	
 
	        //1. CREATE
        List<TLineaFactura> lineas = new ArrayList<>();
        TLineaFactura linea = new TLineaFactura(101, 2);
        TLineaFactura linea2 = new TLineaFactura(300, 23);
        linea.setPrecioVenta(25.5f);
        linea2.setPrecioVenta(30000.89f);
        lineas.add(linea);

        TFactura factura = new TFactura(10, 20, true, LocalDateTime.now(), lineas, 25.5f + 30000.89f, 30f);
        
        linea.setIdPase(21);
        linea.setCantidad(3004);
        linea.setPrecioVenta(300021f);
        linea2.setCantidad(400);
        linea2.setPrecioVenta(3.89f);
        TFactura factura2 = new TFactura(30, 30, true, LocalDateTime.now(), lineas, 300021f + 30000.89f, 600f);
        int id1 = daoFac.create(factura);
        int id2 = daoFac.create(factura2);
        assertEquals(1, id1);
        assertEquals(2, id2);

        //2. READ
        TFactura fetched = daoFac.read(id1);
        //Comprobamos varios datos de las facturas
        assertNotNull(fetched);
        assertEquals(10, fetched.getIdCliente());
        assertEquals(20, fetched.getIdTaquillero());
        assertTrue(fetched.getActivo());
        assertEquals(30f, fetched.getSubtotal());
        assertEquals(25.5f + 30000.89f, fetched.getImporte());
        //Para ver si da bien el id de la factura a las líneas
        assertEquals(((ArrayList<TLineaFactura>)fetched.getCarrito()).get(0).getIdFactura(), id1);
        assertEquals(((ArrayList<TLineaFactura>)fetched.getCarrito()).get(1).getIdFactura(), id1);
        
        fetched = daoFac.read(99);
        assertNull(fetched); //No se leen facturas inexistentes
        
        fetched = daoFac.read(id2);
        assertNotNull(fetched);
        assertEquals(10, fetched.getIdCliente());
        assertEquals(20, fetched.getIdTaquillero());
        assertTrue(fetched.getActivo());
        assertEquals(600f, fetched.getSubtotal());
        assertEquals(300021f + 30000.89f, fetched.getImporte());
        assertEquals(((ArrayList<TLineaFactura>)fetched.getCarrito()).get(0).getIdFactura(), id2);
        assertEquals(((ArrayList<TLineaFactura>)fetched.getCarrito()).get(1).getIdFactura(), id2);

        //3. UPDATE
        fetched.setIdCliente(200);
        fetched.setIdTaquillero(300);
        fetched.setActivo(false);
        fetched.setSubtotal(100f);

        int updatedId = daoFac.update(fetched);
        assertEquals(id2, updatedId); //Id de la factura actualizada coincide con el que tenía antes

        TFactura updated = daoFac.read(id2);
        assertEquals(200, updated.getIdCliente());
        assertEquals(300, updated.getIdTaquillero());
        assertFalse(updated.getActivo());
        assertEquals(100f, updated.getSubtotal());
        
        fetched.setIdCliente(32);
        assertEquals(-1, daoFac.update(fetched)); //No se debe actualizar ya que se actualizó activo a false
        
        fetched.setIdFactura(300);
        updatedId = daoFac.update(fetched);
        assertEquals(-1, updatedId); //No se actualizan facturas inexistentes

        //5. READALL
        ArrayList<TFactura> facs = (ArrayList<TFactura>) daoFac.readAll();
        assertEquals(1, facs.size()); //Solo hay una factura activa en el momento (la que tiene id1)
        assertEquals(20, facs.get(0).getIdTaquillero());

        //4. DELETE
        int deletedId = daoFac.delete(id2);
        assertEquals(-1, deletedId); //La factura se puso a activo falso en el update
        deletedId = daoFac.delete(id1);
        assertEquals(id1, deletedId);
        deletedId = daoFac.delete(398); 
        assertEquals(-1, deletedId); //No se borran facturas con id inexistente

        //Comprobamos que ambas facturas se han borrado con read
        TFactura deleted = daoFac.read(id1);
        assertNull(deleted);
        deleted = daoFac.read(id2);
        assertNull(deleted);
        
        //5. READALL (después de borrar todas las facturas)
        assertTrue(daoFac.readAll().isEmpty());
    }
}
