package integracion.factura;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import misc.Messages;
import misc.OpsBBDD;
import negocio.factura.TLineaFactura;

public class DAOLineaFacturaImpTests {

    private DAOLineaFacturaImp dao;

    @BeforeEach
    public void setUp() {
        dao = new DAOLineaFacturaImp();
    }

    @Test
    public void testFullLifecycle_CreateReadUpdateDelete() throws Exception {
        // 1. CREATE
        TLineaFactura linea = new TLineaFactura(101, 2); // idPase = 101, cantidad = 2
        linea.setPrecioVenta(25.5f);
        linea.setIdFactura(1001);

        int id = dao.create(linea);
        assertTrue(id > 0, "Created ID should be positive.");

        // 2. READ
        TLineaFactura fetched = dao.read(id);
        assertNotNull(fetched);
        assertEquals(101, fetched.getIdPase());
        assertEquals(2, fetched.getCantidad());
        assertEquals(25.5f, fetched.getPrecioVenta(), 0.01f);
        assertEquals(1001, fetched.getIdFactura());

        // 3. UPDATE
        fetched.setIdPase(202);
        fetched.setCantidad(5);
        fetched.setPrecioVenta(60.0f);
        fetched.setIdFactura(2002);
        int updatedId = dao.update(fetched);
        assertEquals(id, updatedId);

        TLineaFactura updated = dao.read(id);
        assertEquals(202, updated.getIdPase());
        assertEquals(5, updated.getCantidad());
        assertEquals(60.0f, updated.getPrecioVenta(), 0.01f);
        assertEquals(2002, updated.getIdFactura());

        // 4. DELETE
        int deletedId = dao.delete(id);
        assertEquals(id, deletedId);

        // 5. READ DELETED
        TLineaFactura deleted = dao.read(id);
        assertNotNull(deleted);
        assertEquals(0, deleted.getCantidad(), "Deleted line should be reset or removed logically.");
    }

    @Test
    public void testReadThrowsExceptionIfNotFound() throws Exception {
        int nonExistentId = 999999;
        // Ensure the ID does not exist
        JSONObject db = OpsBBDD.read(Messages.BDLinFac);
        JSONObject lineas = db.getJSONObject(Messages.KEY_LineasFac);
        lineas.remove(String.valueOf(nonExistentId));
        OpsBBDD.write(db, Messages.BDLinFac);

        // Attempt to read it
        TLineaFactura result = dao.read(nonExistentId);
        assertNull(result, "Should return null for non-existent ID.");
    }

    @Test
    public void testUpdateDoesNotCreateNewEntry() throws Exception {
        // Create one entry
        TLineaFactura linea = new TLineaFactura(300, 1);
        linea.setPrecioVenta(10f);
        linea.setIdFactura(3000);
        int id = dao.create(linea);

        // Change values and update
        linea.setIdLineaFactura(id);
        linea.setPrecioVenta(15f);
        linea.setCantidad(3);
        linea.setIdPase(301);
        linea.setIdFactura(3001);

        int resultId = dao.update(linea);
        assertEquals(id, resultId);

        // Make sure only one entry exists in the DB (i.e., no duplicate created)
        JSONObject db = OpsBBDD.read(Messages.BDLinFac);
        JSONObject lineas = db.getJSONObject(Messages.KEY_LineasFac);
        assertTrue(lineas.has(String.valueOf(id)), "Updated ID should exist.");
        assertEquals(1, lineas.keySet().stream().filter(k -> lineas.getJSONObject(k).getInt(Messages.KEY_idPase) == 301).count());
    }
    
    /*Test-Uses-Result
    testFullLifecycle_CreateReadUpdateDelete	create, read, update, delete	End-to-end scenario: create a line, verify, update, delete, verify deletion.
    testReadThrowsExceptionIfNotFound	read	Ensures reading a non-existent line returns null or behaves gracefully.
    testUpdateDoesNotCreateNewEntry	create, update	Confirms update modifies existing entries without duplicating.
    */
}
