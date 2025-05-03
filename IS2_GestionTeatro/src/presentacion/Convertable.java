package presentacion;

/**
 * A generic interface that defines methods for mapping domain objects to and from a tabular format.
 *
 * <p>This interface is primarily intended to support integration between domain models and UI components,
 * such as {@link javax.swing.JTable}. Implementing this interface allows each domain object to act as a
 * table row whose fields can be accessed and modified by index.</p>
 *
 * <p>Typical use cases include:</p>
 * <ul>
 *   <li>Displaying a list of domain objects in a table by accessing their fields via column indices.</li>
 *   <li>Allowing in-place editing of table cells and updating the underlying object accordingly.</li>
 * </ul>
 *
 * <p>Implementations must provide logic for both retrieving and setting the value of a specific field
 * based on the column index.</p>
 *
 * @param <T> the type of the implementing class (self-referential generic pattern)
 */
public interface Convertable<T> {
    /**
     * Returns the value of the field at the specified column index.
     *
     * <p>This method is typically used by table renderers to retrieve cell values for display.</p>
     *
     * @param columnIndex the index of the column (field) to retrieve
     * @return the value of the specified column; can be {@code null}
     */
    Object getColumnValue(int columnIndex);

    /**
     * Updates the field at the specified column index with the given value.
     *
     * <p>This method is typically used to apply user edits from table cells back into the object.</p>
     *
     * @param col the index of the column (field) to update
     * @param value the new value to set; may require casting inside the implementation
     */
    void setColumnValue(int col, Object value);
}