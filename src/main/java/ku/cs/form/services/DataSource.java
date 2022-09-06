package ku.cs.form.services;

import java.io.FileNotFoundException;

public interface DataSource<T> {

    T readData() throws FileNotFoundException;
    void writeData(T t);

}
