package PersonajeCRUD;

import CRUD.MysqlConnection;
import CRUD.Personaje;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonajeController {

    //Variables para instancia
    MysqlConnection sql = new MysqlConnection();
    Connection cx = null;
    static PreparedStatement ps;
    static ResultSet rs;

    //Method insert ("/post")
    public boolean insertar_array(ArrayList<Personaje> personajes) throws RuntimeException {

        try {
            cx = sql.getDatabaseConnection();
            ps = cx.prepareStatement("INSERT INTO Personaje(P_nombre,P_tipo,P_vida,P_resistencia,P_alcanze ,P_habilidad ) VALUES (?,?,?,?,?,?)");
            int resultado = 0;
            for (int i = 0; i < personajes.size(); i++) {
                Personaje p = personajes.get(i);
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getTipo());
                ps.setInt(3, p.getVida());
                ps.setInt(4, p.getResistencia());
                ps.setInt(5, p.getAlcance());
                ps.setString(6, p.getHabilidad());
                resultado = ps.executeUpdate();
            }

            if (resultado != 0) return false;
            return true;

            /*
                modelo.setRowCount(0);
                //tengo que actualizar el array list
                personajes.removeAll(personajes);
                System.out.println("asegurarnos de que la lista se reinicio:" + personajes.size());
             */

        } catch(Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                sql.closeDatabaseConnection(cx);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
