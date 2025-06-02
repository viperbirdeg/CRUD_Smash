package Swing;

import PersonajeCRUD.Combatiente;
import PersonajeCRUD.Mago;
import PersonajeCRUD.Mixto;
import PersonajeCRUD.Personaje;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
public class MainPanel extends JPanel implements SharedDataModel.DataChangeListener {

    ArrayList<Personaje> personajes = new ArrayList<>();
    JTextField txtNombre, txtVida, txtResistencia, txtAlcance;
    JComboBox cBoxTipo;
    ActionListener cambiarPanelesListener;
    SharedDataModel dataModel;

    Map<String, Integer> datos = new HashMap<String, Integer>() {{
        put("Combatiente", 1);
        put("Mago", 2);
        put("Mixto", 3);
    }};

    //*Main panel
    public MainPanel(ActionListener cambiarPanelesListener, SharedDataModel dataModel) {
        this.cambiarPanelesListener = cambiarPanelesListener;
        this.dataModel = dataModel;
        dataModel.addListener(this);
        setBackground(Color.WHITE);
        setLayout(null);
        textFields();
        buttons();
        labels();
    }

    //*Main panel labels
    public void labels() {
        JLabel nombre = new JLabel("Ingresa el nombre:");
        nombre.setBounds(150, 43, 300, 100);
        add(nombre);

        JLabel vida = new JLabel("Cantidad de vida 1-100:");
        vida.setBounds(130, 100, 300, 100);
        add(vida);

        JLabel tipo = new JLabel("Selecciona el tipo :");
        tipo.setBounds(155, 153, 300, 100);
        add(tipo);

        JLabel resistencia = new JLabel("Ingresa la resistencia:");
        resistencia.setBounds(140, 208, 300, 100);
        add(resistencia);

        JLabel alcance = new JLabel("Ingresa el alcance:");
        alcance.setBounds(150, 263, 300, 100);
        add(alcance);
    }

    //*Main panel text fields
    public void textFields() {
        String[] tipo = {"Combatiente", "Mago", "Mixto"};
        Border border = BorderFactory.createLineBorder(Color.decode("#000000"));
        txtNombre = new JTextField();
        txtNombre.setBounds(300, 80, 200, 30);
        txtNombre.setBorder(border);
        add(txtNombre);

        txtVida = new JTextField();
        txtVida.setBounds(300, 135, 200, 30);
        txtVida.setBorder(border);
        add(txtVida);

        cBoxTipo = new JComboBox(tipo);
        cBoxTipo.setBounds(300, 190, 200, 30);
        add(cBoxTipo);

        txtResistencia = new JTextField();
        txtResistencia.setBounds(300, 245, 200, 30);
        txtResistencia.setBorder(border);
        add(txtResistencia);

        txtAlcance = new JTextField();
        txtAlcance.setBounds(300, 300, 200, 30);
        txtAlcance.setBorder(border);
        add(txtAlcance);

    }

    //*Main panel buttons
    public void buttons() {
        JButton agregar = new JButton("Agregar a tabla preliminar");
        agregar.setBounds(170, 400, 200, 70);
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = cBoxTipo.getSelectedItem().toString();
                personajePreliminar(datos.get(tipo));
                limpiar();
            }
        });
        add(agregar);
        JButton consultar = new JButton("Ver listado reciente");
        consultar.setBounds(370, 400, 200, 70);
        consultar.addActionListener(cambiarPanelesListener);
        add(consultar);
    }

    //*Agragar el personaje al array preliminar
    public void personajePreliminar(int opcion) {
        String[] mensaje = {"Unrecognized",
                "Nombra a la habilidad de tu combatiente:",
                "Nombra tu ataque de mana :",
                "Digita si es haki de armadura o del rey:"
        };
        String preliminar = JOptionPane.showInputDialog(mensaje[opcion]);
        String nombre = txtNombre.getText();
        String tipo = cBoxTipo.getSelectedItem().toString();

        {
            String vida = txtVida.getText();
            String resistencia = txtResistencia.getText();
            String alcance = txtAlcance.getText();

            if (nombre.isEmpty() || vida.isEmpty() || tipo.isEmpty() || resistencia.isEmpty() || alcance.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Rellena todos los campos ");
                return;
            }
        }
        int vida = Integer.parseInt(txtVida.getText());
        int resistencia = Integer.parseInt(txtResistencia.getText());
        int alcance = Integer.parseInt(txtAlcance.getText());
        switch (opcion) {
            case 1:
                dataModel.setData(new Combatiente(nombre, tipo, vida, resistencia, alcance, preliminar));
                break;
            case 2:
                dataModel.setData(new Mago(nombre, tipo, vida, resistencia, alcance, preliminar));
                break;
            case 3:
                dataModel.setData(new Mixto(nombre, tipo, vida, resistencia, alcance, preliminar));
        }
        for (Personaje p : personajes) {
            p.toString();
        }
        JOptionPane.showMessageDialog(null, "Nombre:" + nombre + "\n" + "Vida:" + vida + "\n" + "Tipo:" + tipo + "\n" + "Resistencia:" + resistencia + "\n" + "Alcanze:" + alcance);
    }

    //*Limpiar los campos
    public void limpiar() {
        txtNombre.setText("");
        txtVida.setText("");
        txtAlcance.setText("");
        txtResistencia.setText("");
    }

    @Override
    public void onDataChanged() {
        personajes = dataModel.getData();
    }
}
