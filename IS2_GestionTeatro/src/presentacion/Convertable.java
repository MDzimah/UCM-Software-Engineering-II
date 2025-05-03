package presentacion;

import java.util.ArrayList;

/**
 * An interface for converting a list of objects of type {@code T} into a matrix-like 
 * structure represented by a {@code List} of {@code Object[]} arrays.
 *
 * <p>Classes implementing this interface should define a way to transform data from objects 
 * of type {@code T} into a tabular format, which can be useful in various scenarios, 
 * such as displaying data in a user interface, exporting data to files, or performing 
 * matrix-based computations.</p>
 *
 * @param <T> the type of the objects to be converted into a matrix format
 */
public interface Convertable<T> {

    /**
     * Converts a list of objects of type {@code T} into a matrix-like structure.
     *
     * <p>The returned list represents a matrix where each element is a row, and each row is an 
     * array of objects representing the data fields of an individual {@code T} object.</p>
     *
     * <p>The conversion logic can vary depending on the structure of {@code T}, but the 
     * result should always be a tabular format that can be used for further operations or presentation.</p>
     *
     * @param objects a list of objects of type {@code T} to be converted into a matrix
     * @return a list of {@code Object[]} arrays, where each array represents a row in the matrix,
     *         and each row contains the data fields corresponding to one {@code T} object
     */
    public ArrayList<ArrayList<Object>> matrizDeInformacion(ArrayList<T> objects);
}
