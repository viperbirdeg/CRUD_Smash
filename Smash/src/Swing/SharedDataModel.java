package Swing;

import PersonajeCRUD.Personaje;

import java.util.ArrayList;
import java.util.List;

/*
 * Nos permite compartir datos de manera segura
 *  */
@SuppressWarnings("ALL")
public class SharedDataModel {
    private ArrayList<Personaje> personajes;
    private List<DataChangeListener> listeners = new ArrayList<>();

    public SharedDataModel() {
        personajes = new ArrayList<>();
    }

    public void setData(Personaje personaje) {
        personajes.add(personaje);
        notifyListeners();
    }

    public ArrayList<Personaje> getData() {
        return (ArrayList<Personaje>) personajes.clone();
    }

    public void addListener(DataChangeListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (DataChangeListener listener : listeners) {
            listener.onDataChanged();
        }
    }

    public interface DataChangeListener {
        void onDataChanged();
    }
}
