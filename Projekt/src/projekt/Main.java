package projekt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // dynamický seznam zaměstnanců
    static List<Zamestnanec> zamestnanci = new ArrayList<>();

    // automatické generování ID
    static int nextId = 1;

    // Scanner pro načítání z klávesnice
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        boolean konec = false;

        // hlavní smyčka programu
        while (!konec) {

            vypisMenu();

            int volba = sc.nextInt();
            sc.nextLine(); // vyčištění ENTERU

            switch (volba) {

                case 1:
                    pridejZamestnance();
                    break;

                case 2:
                    vypisZamestnance();
                    break;

                case 0:
                    konec = true;
                    break;

                default:
                    System.out.println("Neplatná volba");
            }
        }

        System.out.println("Program ukončen.");
    }

    // metoda pro výpis menu
    static void vypisMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1 - Přidat zaměstnance");
        System.out.println("2 - Vypsat zaměstnance");
        System.out.println("0 - Konec");
        System.out.println("Volba: ");
    }

    // metoda pro přidání zaměstnance
    static void pridejZamestnance() {

        // výběr skupiny
    	System.out.println("Vyber skupinu:");
        System.out.println("1 - Datový analytik");
        System.out.println("2 - Bezpečnostní specialista");

        int typ = sc.nextInt();
        sc.nextLine(); // vyčištění bufferu

        // načtení údajů
        System.out.print("Zadej jméno: ");
        String jmeno = sc.nextLine();

        System.out.print("Zadej příjmení: ");
        String prijmeni = sc.nextLine();

        System.out.print("Zadej rok narození: ");
        int rok = sc.nextInt();
        sc.nextLine();

        Zamestnanec z;

        // vytvoření podle typu
        if (typ == 1) {
            z = new DatovyAnalytik(nextId++, jmeno, prijmeni, rok);
        } else {
            z = new BezpecnostniSpecialista(nextId++, jmeno, prijmeni, rok);
        }

        // přidání do seznamu
        zamestnanci.add(z);

        System.out.println("Zaměstnanec přidán.");
    }

    // metoda pro výpis zaměstnanců
    static void vypisZamestnance() {

        // kontrola jestli není prázdný seznam
        if (zamestnanci.isEmpty()) {
            System.out.println("Žádní zaměstnanci.");
            return;
        }

        // výpis všech
        for (Zamestnanec z : zamestnanci) {
            System.out.println(z);
        }
    }
}