package Swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    CardLayout cardLayout;
    JPanel cardPanel;
    MainPanel mainPanel;
    SecondaryPanel secondPanel;
    SharedDataModel dataModel;

    //*Main Constructor
    public MainFrame() {
        //*Frame config
        setTitle("CRUD-Smash");
        setSize(700, 500);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //?Actions
        addPanelListeners();

        //? Visibility
        setVisible(true);
    }

    public void addPanelListeners() {
        dataModel = new SharedDataModel();
        //*CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        //*Eventos de los paneles
        mainPanel = new MainPanel(e -> cardLayout.show(cardPanel, "SecondPanel"), dataModel);
        secondPanel = new SecondaryPanel(e -> cardLayout.show(cardPanel, "MainPanel"), dataModel);
        //*Añadir al MainPanel
        cardPanel.add(mainPanel, "MainPanel");
        cardPanel.add(secondPanel, "SecondPanel");
        cardLayout.show(cardPanel, "MainPanel");
        //*Añadir al frame
        add(cardPanel);
    }

}
