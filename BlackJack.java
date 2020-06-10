import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BlackJack {

    public static void main(String[] args) {
        Spel spel = new Spel();
        spel.spelen();
    }
}

class Spel {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Kaart> speelKaarten = new ArrayList<>();

    ArrayList<Speler> spelers = new ArrayList<>();


    String getInput() {
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("q")) {
            scanner.close();
            eindTotalen();
            System.exit(0);
        }
        return input;
    }

    void spelen() {
        //Spelersaantal + naam vragen
        System.out.println("welkom bij BlackJack, hoeveel spelers doen mee? ");
        int aantalSpelers = 0;
        while (aantalSpelers == 0) {
            try{
                aantalSpelers = Integer.parseInt(getInput());
                for (int i = 0; i < aantalSpelers; i++) {
                    System.out.println("Speler " + (i + 1) + " , wat is jouw naam?");
                    Speler speler = new Speler(getInput());
                    spelers.add(speler);
                }
            } catch (NumberFormatException e) {
                System.out.println("Voer een geldige nummer in");
            }

        }

        //kaarten schudden
        speelKaarten.addAll(kaartenKlaarzetten('s'));
        speelKaarten.addAll(kaartenKlaarzetten('h'));
        speelKaarten.addAll(kaartenKlaarzetten('k'));
        speelKaarten.addAll(kaartenKlaarzetten('r'));
        Collections.shuffle(speelKaarten);

        //spelen
        for (int i = 0; i < spelers.size(); i++) {
            while (spelers.get(i).actief) {
                System.out.println(spelers.get(i).naam + ", kies k voor een nieuwe kaart of p om te passen");
                switch (getInput().toLowerCase()) {
                    case "k":
                        kaartUitdelen(spelers.get(i));
                        break;
                    case "p":
                        pass(spelers.get(i));
                        spelers.get(i).actief = false;
                        break;
                    default:
                        System.out.println("Ongeldige invoer");
                }
            }

        }
        Speler spelerGewonnen = controleerOfGewonnen(spelers);
        eindTotalen();
        if (spelerGewonnen != null) {
            System.out.println("Gefeliciteerd " + spelerGewonnen.naam + ", je hebt gewonnen");
        } else {
            System.out.println("Helaas, niemand heeft gewonnen");
        }

    }

    ArrayList<Kaart> kaartenKlaarzetten(char kleur) {
        ArrayList<Kaart> kaarten = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            Kaart kaart = new Kaart(kleur, i + 1);
            kaart.setPunten();
            kaarten.add(kaart);
        }
        return kaarten;
    }

    void kaartUitdelen(Speler speler) {
        speler.toevoegenKaart(speelKaarten.get(0));
        speelKaarten.remove(0);
        System.out.println("Jouw kaarten zijn: " + speler.kaarten);
        System.out.println("Je hebt " + speler.totalePunten + " punten.");
    }

    void pass(Speler speler) {
        System.out.println("Jouw kaarten zijn: " + speler.kaarten);
        System.out.println("Je hebt " + speler.totalePunten + " punten.");
    }

    Speler controleerOfGewonnen(ArrayList<Speler> spelers) {
        Speler spelerGewonnen = null;
        if(spelers.size() == 1) {
            if(spelers.get(0).totalePunten == 21) {
                System.out.println(spelers.get(0).naam + " ,je hebt gewoonen!");
                spelerGewonnen = spelers.get(0);
            }
            else if (spelers.get(0).totalePunten > 21){
                System.out.println(spelers.get(0).naam + " , je bent helaas over 21 gegaan.");
            } else {
                System.out.println(spelers.get(0).naam + " , je hebt nog geen blackjack");
            }
        } else {
            for (Speler speler: spelers) {
                if(speler.totalePunten > 21) {
                    continue;
                } else {
                    if(speler == null || speler.totalePunten > spelerGewonnen.totalePunten) {
                        spelerGewonnen = speler;
                    }
                }

            }

        }
        return spelerGewonnen;

    }

    void eindTotalen() {
        for (int i = 0; i < spelers.size(); i++) {
            System.out.println(spelers.get(i).naam + ": " + spelers.get(i).totalePunten + " punten");
        }
    }
}

class Speler {
    String naam;
    ArrayList<Kaart> kaarten = new ArrayList<>();
    int totalePunten;
    boolean actief = true;

    public Speler() {
    }

    public Speler(String naam) {
        this.naam = naam;
    }

    void toevoegenKaart(Kaart kaart) {
        kaarten.add(kaart);
        totalePunten += kaart.punten;
    }

}

class Kaart {
    int punten;
    char kaartKleur;
    int kaartNummer;

    public Kaart(char kleur, int kaartNummer) {
        this.kaartKleur = kleur;
        this.kaartNummer = kaartNummer;
    }

    public void setPunten() {
        if (kaartNummer < 11) {
            punten = kaartNummer;
        } else if (kaartNummer > 10 && kaartNummer < 13) {
            punten = 10;
        } else if (kaartNummer == 13) {
            punten = 11;
        } else {
            System.out.println("Ongeldige kaartnummer");
        }

    }

    @Override
    public String toString() {

        return kaartKleur + Integer.toString(kaartNummer); //+ "= " + Integer.toString(punten);
    }

}
