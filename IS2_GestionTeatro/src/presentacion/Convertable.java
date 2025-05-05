package presentacion;

//El propio objeto T es la fila y sus atributos las columnas
public interface Convertable<T> {
    Object getColumnValue(int columnIndex);
    void setColumnValue(int col, String value) throws Exception;
}