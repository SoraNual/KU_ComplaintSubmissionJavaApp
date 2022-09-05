package ku.cs.form.services;

import ku.cs.form.models.UserList;

public interface DataSource<T> {

    T readData();
    void writeData(T t);

}
