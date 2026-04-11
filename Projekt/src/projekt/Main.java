package projekt;

import java.util.ArrayList;
import java.util.List;

public class Main {

    // seznam zaměstnanců (dynamická struktura)
    static List<Zamestnanec> zamestnanci = new ArrayList<>();

    // proměnná pro automatické ID
    static int nextId = 1;

    public static void main(String[] args) {

        // vytvoření zaměstnanců a přidání do seznamu
        zamestnanci.add(new Zamestnanec(nextId++, "Jan", "Novak", 1990));
        zamestnanci.add(new Zamestnanec(nextId++, "Petr", "Svoboda", 1985));

        // výpis všech zaměstnanců
        for (Zamestnanec z : zamestnanci) {
            System.out.println(z);
        }
    }
}
