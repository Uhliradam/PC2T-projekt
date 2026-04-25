package projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlDatabaze {

    private static final String URL = "jdbc:hsqldb:file:zamestnanci_db/data;shutdown=true";
    private static final String USER = "SA";
    private static final String HESLO = "";
    private static void vytvorTabulky(Connection conn) throws SQLException {

        String sqlZam = "CREATE TABLE IF NOT EXISTS zamestnanci ("
                + "id INTEGER PRIMARY KEY,"
                + "typ VARCHAR(1),"
                + "jmeno VARCHAR(30),"
                + "prijmeni VARCHAR(30),"
                + "rok_narozeni INTEGER)";

        String sqlSpol = "CREATE TABLE IF NOT EXISTS spoluprace ("
                + "id_zamestnance INTEGER,"
                + "id_kolegy INTEGER,"
                + "uroven VARCHAR(20))";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlZam);
            stmt.execute(sqlSpol);
        }
    }

    public static void uloz(List<Zamestnanec> zamestnanci) {

        try (Connection conn = DriverManager.getConnection(URL, USER, HESLO)) {

            vytvorTabulky(conn);

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("DELETE FROM zamestnanci");
                stmt.execute("DELETE FROM spoluprace");
            }

            String insertZam = "INSERT INTO zamestnanci (id, typ, jmeno, prijmeni, rok_narozeni) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(insertZam)) {

                for (Zamestnanec z : zamestnanci) {

                    String typ;

                    if (z instanceof DatovyAnalytik) {
                        typ = "A";
                    } else {
                        typ = "B";
                    }

                    ps.setInt(1, z.getId());
                    ps.setString(2, typ);
                    ps.setString(3, z.getJmeno());
                    ps.setString(4, z.getPrijmeni());
                    ps.setInt(5, z.getRokNarozeni());
                    ps.executeUpdate();
                }
            }

            String insertSpol = "INSERT INTO spoluprace (id_zamestnance, id_kolegy, uroven) VALUES (?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(insertSpol)) {

                for (Zamestnanec z : zamestnanci) {

                    for (Spoluprace s : z.getSpolupracovnici()) {

                        ps.setInt(1, z.getId());
                        ps.setInt(2, s.getKolega().getId());
                        ps.setString(3, s.getUroven().name());
                        ps.executeUpdate();
                    }
                }
            }

            System.out.println("Data uložena do SQL databáze.");
        } catch (SQLException e) {
            System.out.println("Chyba při ukládání do SQL: " + e.getMessage());
        }
    }

    public static List<Zamestnanec> nacti() {

        List<Zamestnanec> seznam = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, HESLO)) {

            vytvorTabulky(conn);

            String selectZam = "SELECT id, typ, jmeno, prijmeni, rok_narozeni FROM zamestnanci";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectZam)) {

                while (rs.next()) {

                    int id = rs.getInt("id");
                    String typ = rs.getString("typ");
                    String jmeno = rs.getString("jmeno");
                    String prijmeni = rs.getString("prijmeni");
                    int rok = rs.getInt("rok_narozeni");

                    Zamestnanec z;

                    if (typ.equals("A")) {
                        z = new DatovyAnalytik(id, jmeno, prijmeni, rok);
                    } else {
                        z = new BezpecnostniSpecialista(id, jmeno, prijmeni, rok);
                    }

                    seznam.add(z);
                }
            }

            String selectSpol = "SELECT id_zamestnance, id_kolegy, uroven FROM spoluprace";

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(selectSpol)) {

                while (rs.next()) {

                    int id1 = rs.getInt("id_zamestnance");
                    int id2 = rs.getInt("id_kolegy");
                    String urovenText = rs.getString("uroven");

                    Zamestnanec z1 = null;
                    Zamestnanec z2 = null;

                    for (Zamestnanec z : seznam) {

                        if (z.getId() == id1) {
                            z1 = z;
                        }

                        if (z.getId() == id2) {
                            z2 = z;
                        }
                    }

                    if (z1 != null && z2 != null) {
                        z1.pridejSpolupraci(z2, UrovenSpoluprace.valueOf(urovenText));
                    }
                }
            }

            if (!seznam.isEmpty()) {
                System.out.println("Data načtena z SQL databáze (" + seznam.size() + " zaměstnanců).");
            }

        } catch (SQLException e) {
            // SQL je jen záloha, program funguje i bez ní
            System.out.println("Chyba při načítání z SQL: " + e.getMessage());
        }

        return seznam;
    }
}
