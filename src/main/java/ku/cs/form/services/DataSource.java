package ku.cs.form.services;

import ku.cs.form.models.Report;

import java.io.FileNotFoundException;

public interface DataSource<T> {

    T readData();
    void writeData(T t);
}
