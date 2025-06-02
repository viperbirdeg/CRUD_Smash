package Swing;

import PersonajeCRUD.Personaje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SecondaryPanel extends JPanel implements SharedDataModel.DataChangeListener {

    ActionListener cambiarPanelListener;
    SharedDataModel dataModel;
    ArrayList<Personaje> personajes;

    public SecondaryPanel(ActionListener cambiarPanelListener,SharedDataModel dataModel){
        this.cambiarPanelListener = cambiarPanelListener;
        this.dataModel = dataModel;
        dataModel.addListener(this);
        personajes = new ArrayList<>();
        setBackground(Color.BLUE);
        setLayout(null);
    }


    @Override
    public void onDataChanged(){
        personajes = dataModel.getData();
        for (Personaje p : personajes){
            System.out.println(p.toString());
        };
    }
}
