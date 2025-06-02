package CRUD;

import Extras.MysqlConnection;
import PersonajeCRUD.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class Swing extends JFrame {

    ArrayList<Personaje> personajes = new ArrayList<>();
    JComboBox I_tipo;
    JTextField I_nombre, I_vida, I_resistencia, I_alcanze;
    JPanel panel, panel_tabla, panel_listado_general;
    JTable tabla_preeliminar, tabla_general;
    DefaultTableModel modelo;
    JScrollPane scroll;
    JButton cerrar_tabla, eliminar_button, insercion_bd, configuraciones_avanzadas, editar_list_preeliminar;


    String golpe_b, haki_b, mana_b;
    int opcion = 0;

    int indice_Fila_Seleccionada = 0;
    boolean fila_seleccionada = true;
    boolean fila_seleccionada_general = true;

    //declaramos 3 variables static para usar como parametros en la conexion
    MysqlConnection sql = new MysqlConnection();
    Connection cx;
    static PreparedStatement ps;
    static ResultSet rs;
    PersonajeController psCtrl = new PersonajeController();

    //Frame principal
    //todo: refactorizar
    public Swing() {
        this.setTitle("CRUD-Smash");
        this.setSize(700, 500);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        J_panel_m();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    //Metodo para Panel principal
    //todo: eliminar
    public void J_panel_m() {
        panel = new JPanel();
        this.getContentPane().add(panel);
        panel.setBackground(Color.white);
        panel.setLayout(null);
        J_button();
        J_label();
        J_text_field();
    }

    //Labels del panel principal
    //todo: eliminar
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

    }

    //campos del panel principal
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

    //botones del panel principal
    public void J_button() {
        JButton agregar = new JButton("Agregar a tabla preliminar");
        agregar.setBounds(170, 400, 200, 70);
        panel.add(agregar);
        agregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = I_tipo.getSelectedItem().toString();
                opcion = eleccion_habilidad_extra(tipo);
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
                act_listado_preeliminar();
            }
        });
    }

    //Configuracion para inciar el listado preeliminar
    public void act_listado_preeliminar() {
        panel_tabla();
        panel.setVisible(false);
    }

    //boton para eleccion del boton extra
    public int eleccion_habilidad_extra(String tipo) {
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

    //metodo para limpiar los campos
    public void limpiar() {
        I_nombre.setText("");
        I_vida.setText("");
        I_alcanze.setText("");
        I_resistencia.setText("");
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

            B_cerrar_tabla();//aqui tengo un boton
            Eliminar_elemento_preeliminar();//boton elemento preeliminar
            B_insertar_bd();//boton para insercion a la bd
            Configuracion_avanzada();//boton para acceder a listado general
            Editar_lista_preeliminar();//editar elemento lista preeliminar
            panel_tabla.add(scroll);
            panel_tabla.add(cerrar_tabla);
            panel_tabla.add(eliminar_button);
            panel_tabla.add(insercion_bd);
            panel_tabla.add(configuraciones_avanzadas);
            panel_tabla.add(editar_list_preeliminar);
        }

        actualizarTabla();
        seleccionar_elemento_tabla();
        Eliminar_elemento_preeliminar();
        panel_tabla.setVisible(true);
        panel.setVisible(false);

    }

    public void Agregar_personajes_preeliminares(int opcion) {
        String nombre = I_nombre.getText();
        String s_vida = I_vida.getText();
        String tipo = I_tipo.getSelectedItem().toString();
        String s_resistencia = I_resistencia.getText();
        String s_alcanze = I_alcanze.getText();

        if (!nombre.isEmpty() && !s_vida.isEmpty() && !tipo.isEmpty() && !s_resistencia.isEmpty() && !s_alcanze.isEmpty()) {

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
            JOptionPane.showMessageDialog(null, "Nombre:" + nombre + "\n" + "Vida:" + vida + "\n" + "Tipo:" + tipo + "\n" + "Resistencia:" + resistencia + "\n" + "Alcanze:" + alcanze);
        } else {
            JOptionPane.showMessageDialog(null, "Rellena todos los campos ");
        }
    }

    public void Editar_lista_preeliminar() {
        editar_list_preeliminar = new JButton("Editar personaje");
        editar_list_preeliminar.setBounds(260, 300, 145, 70);

        editar_list_preeliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre_mod = "", habilidad_mod = "";
                int vida_mod = 0, resistencia_mod = 0, alcanze_mod = 0;

                String opcion_mod = "";
                String opcion_validada = "";

                seleccionar_elemento_tabla();
                if (!personajes.isEmpty() && !fila_seleccionada) {
                    boolean salir = false;
                    do {
                        opcion_mod = JOptionPane.showInputDialog("¿Que deseas modificar?" + "\n" + "Nombre" + "\n" + "Vida" + "\n" + "Resistencia" + "\n" + "Alcanze" + "\n" + "Habilidad" + "\n" + "Salir");
                        opcion_validada = opcion_mod.toLowerCase();

                        switch (opcion_validada) {
                            case "nombre":
                                //validad no genera problema
                                nombre_mod = JOptionPane.showInputDialog("Ingresa el nuevo nombre:");
                                personajes.get(indice_Fila_Seleccionada).setNombre(nombre_mod);
                                JOptionPane.showMessageDialog(null, "Se modifico con exito tu nuevo nombre es : " + nombre_mod);
                                break;
                            case "vida":
                                //validado
                                vida_mod = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la nueva vida:"));
                                personajes.get(indice_Fila_Seleccionada).setVida(vida_mod);
                                JOptionPane.showMessageDialog(null, "Se modifico con exito tu nueva vida es : " + vida_mod);
                                break;
                            case "resistencia":
                                resistencia_mod = Integer.parseInt(JOptionPane.showInputDialog("Ingresa la nueva resistencia:"));
                                personajes.get(indice_Fila_Seleccionada).setResistencia(resistencia_mod);
                                JOptionPane.showMessageDialog(null, "Se modifico con exito tu nueva resistencia es : " + resistencia_mod);
                                break;
                            case "alcanze":
                                alcanze_mod = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el nuevo alcanze:"));
                                personajes.get(indice_Fila_Seleccionada).setAlcance(alcanze_mod);
                                JOptionPane.showMessageDialog(null, "Se modifico con exito tu nuevo alcanze es : " + alcanze_mod);
                                break;
                            case "habilidad":
                                habilidad_mod = JOptionPane.showInputDialog("Ingresa el nuevo nombre de tu habilidad (En el caso del mago anota su cantidad de mana):");

                                if (personajes.get(indice_Fila_Seleccionada).getTipo().equals("Combatiente") || personajes.get(indice_Fila_Seleccionada).getTipo().equals("Mixto")) {
                                    personajes.get(indice_Fila_Seleccionada).setHabilidad(habilidad_mod);
                                } else {
                                    personajes.get(indice_Fila_Seleccionada).setHabilidad(Integer.parseInt(habilidad_mod));
                                }
                                JOptionPane.showMessageDialog(null, "Se modifico con exito tu nueva habilidad  o cantidad de mana es : " + habilidad_mod);
                                break;
                            case "salir":
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "Esa no es una opción valida para modificar o para salir del menu ");
                        }
                        if (opcion_validada.equals("salir")) {
                            salir = true;
                        }
                    } while (!salir);
                } else if (personajes.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No hay elementos parar editar");
                } else {
                    JOptionPane.showMessageDialog(null, "No seleccionaste ningun elemento");

                }
                actualizarTabla();

            }

        });
    }

    public void B_cerrar_tabla() {
        cerrar_tabla = new JButton("Cerrar tabla");
        cerrar_tabla.setBounds(100, 300, 120, 70);
        cerrar_tabla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_tabla.setVisible(false);
                panel.setVisible(true);
            }
        });
    }

    public void Eliminar_elemento_preeliminar() {
        eliminar_button = new JButton("Eliminar jugador");
        eliminar_button.setBounds(430, 300, 150, 70);

        eliminar_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!personajes.isEmpty()) {
                    seleccionar_elemento_tabla();
                    personajes.remove(indice_Fila_Seleccionada);
                    actualizarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "No hay elementos por eliminar checa bien topo");
                }
            }
        });

    }

    public void B_insertar_bd() {
        insercion_bd = new JButton("Insertar al listado general");
        insercion_bd.setBounds(250, 400, 180, 70);
        insercion_bd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!personajes.isEmpty()) {
                    try {
                        insertar_array();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No hay registros preliminares");
                }
            }
        });
    }

    public void Configuracion_avanzada() {
        ImageIcon image = new ImageIcon("Configuracion.png");
        configuraciones_avanzadas = new JButton();
        configuraciones_avanzadas.setBounds(100, 400, 70, 70);
        configuraciones_avanzadas.setIcon(new ImageIcon(image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH)));

        configuraciones_avanzadas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Esta es la configuracion avanzada \nEn el siguiente apartado encontraras  los registros definitivos \nSe puede modificar al gusto del administrador");
                panel_listado_general();
                /*listado_general();*/
            }
        });
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
                    p.getAlcance(),
                    p.getHabilidad()
            };
            modelo.addRow(fila);

        }
        seleccionar_elemento_tabla();
    }

    public void seleccionar_elemento_tabla() {
        tabla_preeliminar.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //Obtener el número de filas seleccionadas
                if (tabla_preeliminar.getSelectedRowCount() == 1) {
                    indice_Fila_Seleccionada = tabla_preeliminar.getSelectedRow();
                    fila_seleccionada = false;
                } else {
                    fila_seleccionada = true;
                }
            }

        });
    }

    //! PRE REVISIÓN
    public void insertar_array() throws SQLException {
        /*!Revisado*/
        boolean response = psCtrl.insertar_array(personajes);

        if (!response) {
            JOptionPane.showMessageDialog(null, "Inserción de nuevos registros exitosos");
            //tengo que reiniciar el Jtable
            modelo.setRowCount(0);
            //tengo que actualizar el array list
            personajes.removeAll(personajes);
            System.out.println("asegurarnos de que la lista se reinicio:" + personajes.size());

        } else {
            JOptionPane.showMessageDialog(null, "No se inserto correctamente");
        }
    }

    public void panel_listado_general() {
        if (panel_listado_general == null) {
            //Configuracion del panel
            panel_listado_general = new JPanel();
            panel_listado_general.setBackground(Color.white);
            panel_listado_general.setLayout(null);
            this.getContentPane().add(panel_listado_general);

            //establecer el modelo
            String[] columnas = {"id", "nombre", "tipo", "vida", "resistencia", "alcanze", "habilidad"};
            modelo = new DefaultTableModel(columnas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            //asignar el modelo
            tabla_general = new JTable(modelo);
            tabla_general.setBounds(105, 1, 470, 300);

            //asignacion del scroll
            scroll = new JScrollPane(tabla_general);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setBounds(105, 1, 470, 300);

            //metodo para activar los botones
            seleccionar_elemento_tabla_general();
            J_button_panel_general();

            /*B_cerrar_tabla();
            Eliminar_elemento_preeliminar();
            B_insertar_bd();
            Configuracion_avanzada();
            Editar_lista_preeliminar();*/
            panel_listado_general.add(scroll);
            //necesito generar un boton para cerrar denuevo el general y regresar al pree o al inicio
            //necesito generar un boton para borrra elemento
            //este boton se reemplazara para busqueda
            //este es para editar elementos de la lista general
        }

        panel_listado_general.setVisible(true);
        panel_tabla.setVisible(false);
        panel.setVisible(false);

    }

    public void seleccionar_elemento_tabla_general() {
        tabla_general.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (tabla_general.getSelectedRowCount() == 1) {
                    indice_Fila_Seleccionada = tabla_general.getSelectedRow();
                    fila_seleccionada_general = false;
                } else {
                    fila_seleccionada_general = true;
                }
            }
        });
    }

    public void J_button_panel_general() {
        //Boton para editar a un jugador desde la bd
        JButton editar_jugador_general = new JButton("Editar jugador");
        editar_jugador_general.setBounds(103, 300, 200, 70);
        panel_listado_general.add(editar_jugador_general);

        editar_jugador_general.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre_mod, habilidad_mod;
                String vida_mod, resistencia_mod, alcanze_mod;

                String opcion_mod;
                String opcion_validada;


                try {
                    Connection cx = null;
                    String consulta_busqueda = "";
                    cx = sql.getDatabaseConnection();

                    if (!fila_seleccionada_general) {
                        boolean salir = false;
                        do {
                            opcion_mod = JOptionPane.showInputDialog("¿Que deseas modificar?" + "\n" + "Nombre" + "\n" + "Vida" + "\n" + "Resistencia" + "\n" + "Alcanze" + "\n" + "Habilidad" + "\n" + "Salir");
                            opcion_validada = opcion_mod.toLowerCase();

                            switch (opcion_validada) {
                                case "nombre":
                                    nombre_mod = JOptionPane.showInputDialog("Ingresa el nuevo nombre:");

                                    //Aqui falta la logica
                                    consulta_busqueda = "UPDATE Personaje SET P_nombre = ? WHERE P_id = ?";
                                    ps = cx.prepareStatement(consulta_busqueda);
                                    ps.setString(1, nombre_mod);
                                    ps.setString(2, String.valueOf(tabla_general.getValueAt(indice_Fila_Seleccionada, 0)));

                                    if (ps.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(null, "Se modifico con exito tu nuevo nombre es : " + nombre_mod);
                                        modelo.setRowCount(0);
                                        listado_general();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No se ha podido actualizar  el nombre\n"
                                                + JOptionPane.ERROR_MESSAGE);
                                    }

                                    break;
                                case "vida":
                                    //validado
                                    vida_mod = JOptionPane.showInputDialog("Ingresa la nueva vida:");
                                    ///Aqui falta la logica
                                    consulta_busqueda = "UPDATE Personaje SET P_vida = ? WHERE P_id = ?";
                                    ps = cx.prepareStatement(consulta_busqueda);
                                    ps.setString(1, vida_mod);
                                    ps.setString(2, String.valueOf(tabla_general.getValueAt(indice_Fila_Seleccionada, 0)));

                                    if (ps.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(null, "Se modifico con exito, tu vida es : " + vida_mod);
                                        modelo.setRowCount(0);
                                        listado_general();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No se ha podido actualizar  la vida\n"
                                                + JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                case "resistencia":
                                    resistencia_mod = JOptionPane.showInputDialog("Ingresa la nueva resistencia:");
                                    ///Aqui falta la logica
                                    consulta_busqueda = "UPDATE Personaje SET P_resistencia = ? WHERE P_id = ?";
                                    ps = cx.prepareStatement(consulta_busqueda);
                                    ps.setString(1, resistencia_mod);
                                    ps.setString(2, String.valueOf(tabla_general.getValueAt(indice_Fila_Seleccionada, 0)));

                                    if (ps.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(null, "Se modifico con exito, tu resistencia es : " + resistencia_mod);
                                        modelo.setRowCount(0);
                                        listado_general();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No se ha podido actualizar  la resistencia \n"
                                                + JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                case "alcanze":
                                    alcanze_mod = JOptionPane.showInputDialog("Ingresa el nuevo alcanze:");
                                    ///Aqui falta la logica
                                    consulta_busqueda = "UPDATE Personaje SET P_alcanze = ? WHERE P_id = ?";
                                    ps = cx.prepareStatement(consulta_busqueda);
                                    ps.setString(1, alcanze_mod);
                                    ps.setString(2, String.valueOf(tabla_general.getValueAt(indice_Fila_Seleccionada, 0)));

                                    if (ps.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(null, "Se modifico con exito, tu alcanze es : " + alcanze_mod);
                                        modelo.setRowCount(0);
                                        listado_general();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No se ha podido actualizar  el alcanze\n"
                                                + JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                case "habilidad":
                                    habilidad_mod = JOptionPane.showInputDialog("Ingresa el nuevo nombre de tu habilidad \n(En el caso del mago anota su cantidad de mana):");

                                    ///Aqui falta la logica
                                    consulta_busqueda = "UPDATE Personaje SET P_habilidad = ? WHERE P_id = ?";
                                    ps = cx.prepareStatement(consulta_busqueda);
                                    ps.setString(1, habilidad_mod);
                                    ps.setString(2, String.valueOf(tabla_general.getValueAt(indice_Fila_Seleccionada, 0)));

                                    if (ps.executeUpdate() > 0) {
                                        JOptionPane.showMessageDialog(null, "Se modifico con exito, tu habilidad es : " + habilidad_mod);
                                        modelo.setRowCount(0);
                                        listado_general();
                                    } else {
                                        JOptionPane.showMessageDialog(null, "No se ha podido actualizar  tu habilidad\n"
                                                + JOptionPane.ERROR_MESSAGE);
                                    }
                                    break;
                                case "salir":
                                    break;
                                default:
                                    JOptionPane.showMessageDialog(null, "Esa no es una opción valida para modificar o para salir del menu ");
                            }
                            if (opcion_validada.equals("salir")) {
                                salir = true;
                            }
                        } while (!salir);
                        modelo.setRowCount(0);
                        listado_general();
                    } else {
                        JOptionPane.showMessageDialog(null, "No seleccionaste ningun elemento");
                    }
                } catch (Exception busqueda) {
                    System.err.println(busqueda);
                } finally {
                    try {
                        sql.closeDatabaseConnection(cx);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }


            }
        });

        //boton para ver el listado general
        JButton visualizar_lista_general = new JButton("Ver lista general");
        visualizar_lista_general.setBounds(378, 300, 200, 70);
        panel_listado_general.add(visualizar_lista_general);

        visualizar_lista_general.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.setRowCount(0);
                listado_general();
            }
        });

        //boton para buscar a un jugador
        JButton buscar_jugador_general = new JButton("buscar jugador");
        buscar_jugador_general.setBounds(103, 400, 200, 70);
        panel_listado_general.add(buscar_jugador_general);

        buscar_jugador_general.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Digita el id de tu jugador:"));
                String[] registros = new String[7];
                Connection cx = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String consulta = "SELECT * FROM Personaje WHERE P_id=?";

                try {
                    cx = sql.getDatabaseConnection();
                    ps = cx.prepareStatement(consulta);
                    ps.setInt(1, id);  // Mejor usar setInt si el campo es numérico
                    rs = ps.executeQuery();

                    if (rs.next()) {
                        registros[0] = rs.getString("P_id");
                        registros[1] = rs.getString("P_nombre");
                        registros[2] = rs.getString("P_tipo");
                        registros[3] = rs.getString("P_vida");
                        registros[4] = rs.getString("P_resistencia");
                        registros[5] = rs.getString("P_alcanze");
                        registros[6] = rs.getString("P_Habilidad");
                        modelo.setRowCount(0);
                        modelo.addRow(registros);
                        JOptionPane.showMessageDialog(null, "Usuario encontrado con éxito");
                    } else {
                        JOptionPane.showMessageDialog(null, "Usuario no encontrado");
                    }

                } catch (Exception exception) {
                    System.err.println("Error: " + exception);
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (ps != null) ps.close();
                        if (cx != null) cx.close();
                    } catch (SQLException ex) {
                        ex.getStackTrace();
                    }
                }
            }
        });

        //boton para eliminar a un jugador
        JButton eliminar_jugador_general = new JButton("Eliminar jugador");
        eliminar_jugador_general.setBounds(378, 400, 200, 70);
        panel_listado_general.add(eliminar_jugador_general);
        eliminar_jugador_general.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cx = null;

                if (!fila_seleccionada_general) {

                    try {
                        String consulta = "DELETE FROM Personaje WHERE P_id=?";
                        cx = sql.getDatabaseConnection();
                        ps = cx.prepareStatement(consulta);

                        ps.setString(1, String.valueOf(tabla_general.getValueAt(indice_Fila_Seleccionada, 0)));

                        if (ps.executeUpdate() > 0) {
                            JOptionPane.showMessageDialog(null, "El registro ha sido eliminado exitosamente operación Exitosa");
                            modelo.setRowCount(0);
                            listado_general();
                        } else {

                            JOptionPane.showMessageDialog(null, "No se ha podido eliminar el registro\n"
                                            + "Inténtelo nuevamente.", "Error en la operación",
                                    JOptionPane.ERROR_MESSAGE);

                        }
                    } catch (Exception eliminar) {
                        System.err.println(eliminar);

                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (ps != null) ps.close();
                            if (cx != null) cx.close();
                        } catch (SQLException ex) {
                            ex.getStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No seleccionaste ningun elemento");
                }

            }
        });

    }

    //! PRE REVISIÓN
    public void listado_general() {
        try {
            ArrayList<String[]> registros = psCtrl.consultaRegistros();
            for (String[] registro : registros) {
                modelo.addRow(registro);
            }
        } catch (Exception e) {
            JOptionPane.showInputDialog(null, "Se borro la base de datos");
            e.printStackTrace();
        }
    }

}