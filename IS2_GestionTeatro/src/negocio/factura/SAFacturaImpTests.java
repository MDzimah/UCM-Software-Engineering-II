package negocio.factura;


import org.junit.jupiter.api.*;

import exceptions.UnknownClienteException;
import exceptions.UnknownTaquilleroException;
import negocio.factoria.FactoriaAbstractaNegocio;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SAFacturaImpTests {

    private SAFactura saFac = FactoriaAbstractaNegocio.getInstance().crearSAFactura();

    @Test
    public void testFullLifecycle_CreateReadUpdateDelete() throws Exception {
        // Assumes the existence of these test entities
        int idCliente = 1;      // Should be an active client in DB
        int idTaquillero = 1;   // Should be a valid taquillero
        int idPase = 101;       // Should be an existing pase with available stock

        // 1. CREATE
        TLineaFactura linea = new TLineaFactura(idPase, 2);
        Collection<TLineaFactura> carrito = new ArrayList<>();
        carrito.add(linea);
        TDatosVenta datosVenta = new TDatosVenta(idCliente, idTaquillero, carrito);

        int idFactura = saFac.create(datosVenta);
        assertTrue(idFactura > 0, "Factura ID should be positive after creation");

        // 2. READ
        TFactura factura = saFac.read(idFactura);
        assertNotNull(factura, "Factura should not be null");
        assertEquals(idCliente, factura.getIdCliente());
        assertEquals(idTaquillero, factura.getIdTaquillero());
        assertTrue(factura.getImporte() >= 0);
        assertTrue(factura.getSubtotal() >= 0);
        assertTrue(factura.getCarrito().size() > 0);

        TLineaFactura retrievedLinea = factura.getCarrito().iterator().next();
        assertEquals(idFactura, retrievedLinea.getIdFactura());

        // 3. UPDATE
        factura.setActivo(false);  // Just a basic update
        int updatedId = saFac.update(factura);
        assertEquals(idFactura, updatedId, "Updated ID should match original ID");

        TFactura updatedFactura = saFac.read(idFactura);
        assertFalse(updatedFactura.getActivo(), "Factura should be marked as inactive");

        // 4. DELETE
        int deletedId = saFac.delete(idFactura);
        assertEquals(idFactura, deletedId, "Deleted ID should match");

        TFactura deleted = saFac.read(idFactura);
        assertNotNull(deleted);
        assertFalse(deleted.getActivo(), "Deleted factura should be inactive");
    }

    @Test
    public void testCreateThrowsOnUnknownClientOrTaquillero() {
        Collection<TLineaFactura> carrito = List.of(new TLineaFactura(101, 1));

        TDatosVenta datosConClienteInvalido = new TDatosVenta(999999, 1, carrito);
        TDatosVenta datosConTaquilleroInvalido = new TDatosVenta(1, 999999, carrito);

        assertThrows(UnknownClienteException.class, () -> saFac.create(datosConClienteInvalido));
        assertThrows(UnknownTaquilleroException.class, () -> saFac.create(datosConTaquilleroInvalido));
    }

    @Test
    public void testReadReturnsNullIfNotFound() throws Exception {
        TFactura factura = saFac.read(999999); // Nonexistent
        assertNull(factura, "Should return null if factura does not exist");
    }

    @Test
    public void testDeleteReturnsMinusOneIfNotFound() throws Exception {
        int deleted = saFac.delete(999999);
        assertEquals(-1, deleted, "Delete should return -1 if factura doesn't exist");
    }

    /*Test-Uses-Result
    testFullLifecycle_CreateReadUpdateDelete    create, read, update, delete    Full scenario: valid venta created, validated, updated, deleted.
    testCreateThrowsOnUnknownClientOrTaquillero create                         Should throw exception if client or taquillero does not exist.
    testReadReturnsNullIfNotFound               read                           Reading unknown ID returns null.
    testDeleteReturnsMinusOneIfNotFound         delete                         Returns -1 if trying to delete non-existent factura.
    */
}
