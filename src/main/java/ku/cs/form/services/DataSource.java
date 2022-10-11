package ku.cs.form.services;

public interface DataSource<T> {

    T readData();
    void writeData(T t);
}
