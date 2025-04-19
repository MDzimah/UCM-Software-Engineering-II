package integracion.factura;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import misc.Messages;
import misc.OpsBBDD;
import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;

import java.time.LocalDateTime;
import java.util.*;

public class DAOFacturaImpTests {

    private DAOFacturaImp dao;

    @BeforeEach
    public void setUp() {
        dao = new DAOFacturaImp();
    }

    @Test
    public void testFullLifecycle_CreateReadUpdateDelete() throws Exception {
        // 1. CREATE
        List<TLineaFactura> lineas = new ArrayList<>();
        TLineaFactura linea = new TLineaFactura(101, 2);
        linea.setPrecioVenta(25.5f);
        linea.setIdFactura(1001);
        lineas.add(linea);

        TFactura factura = new TFactura(10, 20, true, LocalDateTime.now(), lineas, 25.5f, 30f);
        int id = dao.create(factura);
        assertTrue(id > 0, "Created ID should be positive.");

        // 2. READ
        TFactura fetched = dao.read(id);
        assertNotNull(fetched);
        assertEquals(10, fetched.getIdCliente());
        assertEquals(20, fetched.getIdTaquillero());
        assertTrue(fetched.getActivo());
        assertEquals(25.5f, fetched.getSubtotal(), 0.01f);
        assertEquals(30f, fetched.getImporte(), 0.01f);

        // 3. UPDATE
        fetched.setIdCliente(200);
        fetched.setIdTaquillero(300);
        fetched.setActivo(false);
        fetched.setSubtotal(100f);
        fetched.setImporte(120f);

        int updatedId = dao.update(fetched);
        assertEquals(id, updatedId);

        TFactura updated = dao.read(id);
        assertEquals(200, updated.getIdCliente());
        assertEquals(300, updated.getIdTaquillero());
        assertFalse(updated.getActivo());
        assertEquals(100f, updated.getSubtotal(), 0.01f);
        assertEquals(120f, updated.getImporte(), 0.01f);

        // 4. DELETE
        int deletedId = dao.delete(id);
        assertEquals(id, deletedId);

        // 5. READ DELETED
        TFactura deleted = dao.read(id);
        assertNull(deleted, "Deleted factura should be null or marked as inactive.");
    }

    @Test
    public void testReadThrowsExceptionIfNotFound() throws Exception {
        int nonExistentId = 999999;
        // Ensure the ID does not exist
        JSONObject db = OpsBBDD.read(Messages.BDFac);
        JSONObject facturas = db.getJSONObject(Messages.KEY_facs);
        facturas.remove(String.valueOf(nonExistentId));
        OpsBBDD.write(db, Messages.BDFac);

        // Attempt to read it
        TFactura result = dao.read(nonExistentId);
        assertNull(result, "Should return null for non-existent ID.");
    }

    @Test
    public void testUpdateDoesNotCreateNewEntry() throws Exception {
        // Create one entry
        List<TLineaFactura> lineas = new ArrayList<>();
        TLineaFactura linea = new TLineaFactura(300, 1);
        linea.setPrecioVenta(10f);
        linea.setIdFactura(3000);
        lineas.add(linea);

        TFactura factura = new TFactura(10, 20, true, LocalDateTime.now(), lineas, 10f, 12f);
        int id = dao.create(factura);

        // Change values and update
        factura.setIdFactura(id);
        factura.setIdCliente(500);
        factura.setIdTaquillero(600);
        factura.setSubtotal(15f);
        factura.setImporte(18f);
        factura.setActivo(false);

        int resultId = dao.update(factura);
        assertEquals(id, resultId);

        // Make sure only one entry exists in the DB (i.e., no duplicate created)
        JSONObject db = OpsBBDD.read(Messages.BDFac);
        JSONObject facturas = db.getJSONObject(Messages.KEY_facs);
        assertTrue(facturas.has(String.valueOf(id)), "Updated ID should exist.");
        assertEquals(1, facturas.keySet().stream().filter(k -> facturas.getJSONObject(k).getInt(Messages.KEY_idCli) == 500).count());
    }
    
    /* Test Scenarios
    testFullLifecycle_CreateReadUpdateDelete    create, read, update, delete    End-to-end scenario: create a factura, verify, update, delete, verify deletion.
    testReadThrowsExceptionIfNotFound           read    Ensures reading a non-existent factura returns null or behaves gracefully.
    testUpdateDoesNotCreateNewEntry            create, update    Confirms update modifies existing entries without duplicating.
    */
}
