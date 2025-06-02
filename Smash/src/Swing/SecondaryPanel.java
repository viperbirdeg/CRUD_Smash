package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SecondaryPanel extends JPanel {

    ActionListener cambiarPanelListener;
    SharedDataModel dataModel;

    public SecondaryPanel(ActionListener cambiarPanelListener,SharedDataModel dataModel){
        this.cambiarPanelListener = cambiarPanelListener;
        this.dataModel = dataModel;
        setBackground(Color.BLUE);
        setLayout(null);
    }

}
