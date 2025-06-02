package PersonajeCRUD;

import Extras.MysqlConnection;

import java.sql.*;
import java.util.ArrayList;

public class PersonajeController {

    //Variables para instancia
    MysqlConnection sql = new MysqlConnection();
    Connection cx = null;
    static PreparedStatement ps;
    static ResultSet rs;

    //! (-/post) Required: characters, Response: boolean
    public boolean insertar_array(ArrayList<Personaje> personajes) throws RuntimeException, SQLException {
        try {
            cx = sql.getDatabaseConnection();
            ps = cx.prepareStatement("INSERT INTO Personaje(P_nombre,P_tipo,P_vida,P_resistencia,P_alcanze ,P_habilidad ) VALUES (?,?,?,?,?,?)");
            int resultado = 0;
            for (Personaje p : personajes) {
                ps.setString(1, p.getNombre());
                ps.setString(2, p.getTipo());
                ps.setInt(3, p.getVida());
                ps.setInt(4, p.getResistencia());
                ps.setInt(5, p.getAlcance());
                ps.setString(6, p.getHabilidad());
                resultado = ps.executeUpdate();
            }
            return (resultado != 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            sql.closeDatabaseConnection(cx);
        }
    }

    //! (-/getAll) Required: none, Response: allCharacters
    public ArrayList<String[]>  consultaRegistros() throws SQLException {
        String consulta = "SELECT * FROM personaje_vw ";
        ArrayList<String[]> registros = new ArrayList<String[]>();
        try {
            cx = sql.getDatabaseConnection();
            ps = cx.prepareStatement(consulta);
            rs = ps.executeQuery();
            while (rs.next()) {
                String[] registro = new String[7];
                registro[0] = rs.getString("id");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("tipo");
                registro[3] = rs.getString("vida");
                registro[4] = rs.getString("resistencia");
                registro[5] = rs.getString("alcance");
                registro[6] = rs.getString("habilidad");
                registros.add(registro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (cx != null) cx.close();
        }
        return registros;
    }
}
