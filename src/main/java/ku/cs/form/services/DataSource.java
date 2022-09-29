package ku.cs.form.services;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface DataSource<T> {

    T readData();
    void writeData(T t);

}
