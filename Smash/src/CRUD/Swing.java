package CRUD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Swing extends JFrame {
    static ArrayList<Personaje> personajes = new ArrayList<>();
    static JTextField I_nombre, I_vida, I_resistencia, I_alcanze;
    static JTable tabla_preeliminar;
    static JComboBox I_tipo;
    static String golpe_b, haki_b, mana_b;
    static int opcion = 0;
    static JPanel panel, panel_tabla;
    static JButton cerrar_tabla,eliminar_button,insercion_bd;
    static DefaultTableModel modelo;
    static JScrollPane scroll;
    static int indice_Fila_Seleccionada=0;

    //declaramos 3 variables static para usar como parametros en la conexion
    public static final String URL ="jdbc:mysql://localhost:3306/Smash?autoReconnect=true&useSSL=false";
    public static final String usuario = "root";
    public static final String pass = "Readingsteiner9";
    static PreparedStatement ps ;
    static ResultSet rs ;

    public Swing() {
        this.setTitle("CRUD-Smash");
        this.setSize(700, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        Act_paneles();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void Act_paneles() {
        J_panel_m();
        J_button();
        J_label();
        J_text_field();
    }

    public void J_panel_m() {
        panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setBackground(Color.white);
        panel.setLayout(null);
    }

    public void J_label() {
        JLabel nombre = new JLabel("Ingresa el nombre:");
        nombre.setBounds(150, 43, 300, 100);
        panel.add(nombre);

        JLabel vida = new JLabel("Cantidad de vida 1-100:");
        vida.setBounds(130, 100, 300, 100);
        panel.add(vida);

        JLabel tipo = new JLabel("Selecciona el tipo :");
        tipo.setBounds(155, 153, 300, 100);
        panel.add(tipo);

        JLabel resistencia = new JLabel("Ingresa la resistencia:");
        resistencia.setBounds(140, 208, 300, 100);
        panel.add(resistencia);

        JLabel alcanze = new JLabel("Ingresa el alcanze:");
        alcanze.setBounds(150, 263, 300, 100);
        panel.add(alcanze);

        /*JLabel buscar = new JLabel("Buscar a tu personaje :");
        buscar.setBounds(140, 1, 300, 70);
        panel.add(buscar);*/
    }

    public void J_text_field() {
        String[] tipo = {"Combatiente", "Mago", "Mixto"};

        I_nombre = new JTextField();
        I_nombre.setBounds(300, 80, 200, 30);
        I_nombre.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        panel.add(I_nombre);

        I_vida = new JTextField();
        I_vida.setBounds(300, 135, 200, 30);
        I_vida.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        panel.add(I_vida);

        I_tipo = new JComboBox(tipo);
        I_tipo.setBounds(300, 190, 200, 30);
        panel.add(I_tipo);

        I_resistencia = new JTextField();
        I_resistencia.setBounds(300, 245, 200, 30);
        I_resistencia.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        panel.add(I_resistencia);

        I_alcanze = new JTextField();
        I_alcanze.setBounds(300, 300, 200, 30);
        I_alcanze.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        panel.add(I_alcanze);

        /*JTextField buscar = new JTextField("Nombre");
        buscar.setBounds(300, 20, 200, 30);
        buscar.setBorder(BorderFactory.createLineBorder(Color.decode("#000000")));
        panel.add(buscar);*/
    }

    public void J_button() {
        JButton agregar = new JButton("Agregar a tabla preliminar");
        agregar.setBounds(170, 400, 200, 70);
        panel.add(agregar);
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = I_tipo.getSelectedItem().toString();
                //aqui tengo que asegurar que haya rellenado los campos antes
                opcion = boton_extra(tipo);
                Agregar_personajes_preeliminares(opcion);
                limpiar();
            }
        });

        JButton consultar = new JButton("Ver listado reciente");
        consultar.setBounds(370, 400, 200, 70);
        panel.add(consultar);
        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_tabla();
                panel.setVisible(false);
            }
        });
    }
    //boton extra para habilidad especial
    public int boton_extra(String tipo) {
        switch (tipo) {
            case "Combatiente":
                golpe_b = JOptionPane.showInputDialog("Nombra a la habilidad de tu combatiente:");
                opcion = 1;
                break;
            case "Mago":
                mana_b = JOptionPane.showInputDialog("Nombra tu ataque de mana :");
                opcion = 2;
                break;
            case "Mixto":
                haki_b = JOptionPane.showInputDialog("Digita si es haki de armadura o del rey:");
                opcion = 3;
                break;
        }
        return opcion;
    }

    public void limpiar() {
        I_nombre.setText("");
        I_vida.setText("");
        I_alcanze.setText("");
        I_resistencia.setText("");
    }

    public void Agregar_personajes_preeliminares(int opcion) {
        String nombre = I_nombre.getText();
        String s_vida = I_vida.getText();
        String tipo = I_tipo.getSelectedItem().toString();
        String s_resistencia = I_resistencia.getText();
        String s_alcanze = I_alcanze.getText();

        if(!nombre.isEmpty()&&!s_vida.isEmpty()&&!tipo.isEmpty()&&!s_resistencia.isEmpty()&&!s_alcanze.isEmpty()){

            int vida = Integer.parseInt(I_vida.getText());
            int resistencia = Integer.parseInt(I_resistencia.getText());
            int alcanze = Integer.parseInt(I_alcanze.getText());
            switch (opcion) {
                case 1:
                    personajes.add(new Combatiente(nombre, tipo, vida, resistencia, alcanze, golpe_b));
                    break;
                case 2:
                    personajes.add(new Mago(nombre, tipo, vida, resistencia, alcanze, mana_b));
                    break;
                case 3:
                    personajes.add(new Mixto(nombre, tipo, vida, resistencia, alcanze, haki_b));
            }
            JOptionPane.showMessageDialog(null,"Nombre:"+nombre+"\n"+"Vida:"+vida+"\n"+"Tipo:"+tipo+"\n"+"Resistencia:"+resistencia+"\n"+"Alcanze:"+alcanze);
        }else {
            JOptionPane.showMessageDialog(null,"Rellena todos los campos ");
            }
        }

    public void panel_tabla() {
        if (panel_tabla == null) {
            panel_tabla = new JPanel();
            panel_tabla.setBackground(Color.white);
            panel_tabla.setLayout(null);
            this.getContentPane().add(panel_tabla);

            String[] columnas = {"id", "nombre", "tipo", "vida", "resistencia", "alcanze", "habilidad"};
            modelo = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tabla_preeliminar = new JTable(modelo);
            tabla_preeliminar.setBounds(105, 1, 470, 300);

            scroll = new JScrollPane(tabla_preeliminar);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setBounds(105, 1, 470, 300);

            B_cerrar_tabla();
            Eliminar_elemento_preeliminar();
            panel_tabla.add(scroll);
            panel_tabla.add(cerrar_tabla);
            panel_tabla.add(eliminar_button);
        }

        actualizarTabla();
        seleccionar_elemento_tabla();
        Eliminar_elemento_preeliminar();
        panel_tabla.setVisible(true);
        panel.setVisible(false);

    }

    public void B_cerrar_tabla(){
        cerrar_tabla = new JButton("Cerrar tabla");
        cerrar_tabla.setBounds(100, 300, 200, 70);
        cerrar_tabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_tabla.setVisible(false);
                panel.setVisible(true);
            }
        });
    }

    public void B_insertar_bd(){
        insercion_bd = new JButton("Insertar al listado general");
        insercion_bd.setBounds(250, 300, 200, 70);
        insercion_bd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    
    public void Eliminar_elemento_preeliminar(){
        eliminar_button = new JButton("Eliminar jugador preeliminar");
        eliminar_button.setBounds(335, 300, 245, 70);

            eliminar_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!personajes.isEmpty()){
                        seleccionar_elemento_tabla();
                        personajes.remove(indice_Fila_Seleccionada);
                        actualizarTabla();
                    }else{
                        JOptionPane.showMessageDialog(null,"No hay elementos por eliminar checa bien topo");
                    }
                }
            }
            );

    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (int i = 0; i < personajes.size(); i++) {
            Personaje p = personajes.get(i);
            Object[] fila = {
                    i + 1,
                    p.getNombre(),
                    p.getTipo(),
                    p.getVida(),
                    p.getResistencia(),
                    p.getAlcanze(),
                    p.getha()
            };
            modelo.addRow(fila);

        }
        seleccionar_elemento_tabla();
    }

    public void seleccionar_elemento_tabla(){
        tabla_preeliminar.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Obtener el número de filas seleccionadas
                if (tabla_preeliminar.getSelectedRowCount() == 1) {
                    indice_Fila_Seleccionada = tabla_preeliminar.getSelectedRow();
                    System.out.println("Fila seleccionada: " + indice_Fila_Seleccionada);
                }
            }

        });
    }
    //metodo para la conexion a la bd
    public static Connection getConection(){
        Connection cx = null ;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cx = (Connection) DriverManager.getConnection(URL,usuario,pass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cx;
    }

    public void  insertar_array(){
        //tenemos que declarar el objeto conexion como null
        Connection cx = null ;

        try {
            cx = getConection();
            ps = cx.prepareStatement("INSERT INTO Personaje(P_nombre,P_tipo,P_vida,P_resistencia,P_alcanze ,P_habilidad ) VALUES (?,?,?,?,?,?)");
            int resultado = 0;
            for(int i = 0;i<personajes.size();i++){
                Personaje p = personajes.get(i);
                ps.setString(i,p.getNombre());
                ps.setString(i,p.getTipo());
                ps.setInt(i,p.getVida());
                ps.setInt(i,p.getResistencia());
                ps.setInt(i,p.getAlcanze());
                ps.setString(i,p.getha());
                resultado = ps.executeUpdate();
            }
            if(resultado>0){
                JOptionPane.showMessageDialog(null,"Insercion de nuevos registros exitosos");
            }else{
                JOptionPane.showMessageDialog(null,"No se inserto correctamente");
            }

        }catch (Exception e){
            System.err.println("Error:"+e);
        }
    }

    public static void main(String[] args) {
        new Swing();

    }
}
