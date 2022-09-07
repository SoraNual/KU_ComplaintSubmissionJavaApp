package ku.cs.form.services;

import java.io.FileNotFoundException;

public interface DataSource<T> {

    T readData();
    void writeData(T t);

}
