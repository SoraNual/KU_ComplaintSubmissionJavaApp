package ku.cs.form.services;

public interface Filterer<T> {
    boolean filter(T o);
}
