package presentacion;

import java.util.ArrayList;

/**
 * A generic interface for converting between objects of type {@code T} and a tabular data format.
 * <p>
 * This interface is designed to facilitate integration between domain objects and user interface components,
 * particularly table-based views such as {@code JTable}. It allows both:
 * <ul>
 *   <li>Conversion of a list of {@code T} objects into a matrix (for display in tables).</li>
 *   <li>Reconstruction of a {@code T} object from a row of data (after editing in a table).</li>
 * </ul>
 *
 * @param <T> the type of the domain objects involved in the conversion
 */
public interface Convertable<T> {

    /**
     * Converts a list of {@code T} objects into a tabular structure suitable for display.
     * <p>
     * Each entry in the returned list represents a row in the table, where each row is a list
     * of values (columns) corresponding to the fields of a {@code T} object.
     *
     * @param objects a list of {@code T} objects to convert
     * @return a list of rows, where each row is a list of objects representing a {@code T}'s fields
     */
    ArrayList<ArrayList<Object>> matrizDeInformacion(ArrayList<T> objects);

    /**
     * Converts a row of tabular data back into a {@code T} object.
     * <p>
     * This is typically used to reconstruct domain objects after editing rows in a user interface table.
     * Implementing classes should ensure the order and type of values in {@code datosFila}
     * match those expected by the object constructor or field assignment logic.
     *
     * @param fila a list of values representing a single row's data in the table
     * @return a new {@code T} object reconstructed from the given row data
     */
    T filaAObjetoT(ArrayList<Object> fila);
}

