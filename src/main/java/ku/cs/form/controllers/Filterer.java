package ku.cs.form.controllers;

public interface Filterer<T> {
    boolean filter(T o);
}
