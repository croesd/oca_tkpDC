import java.util.*;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Welkom bij Yahtzee!");
        YahtzeeSpel yahtzee = new YahtzeeSpel();
        yahtzee.spelen();
    }
}

class YahtzeeSpel {

    List<Dobbelsteen> dobbelstenen = new ArrayList<>();
    int[] blokkeerArray = new int[5];
    Scanner scanner;
    Speler speler = new Speler();

    YahtzeeSpel() {
        for (int i = 0; i < 5; i++) {
            dobbelstenen.add(new Dobbelsteen());
        }
        scanner = new Scanner(System.in);
    }

    String getInput(){
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("q")){
            quit();
            scanner.close();
            System.exit(0);
        }
        return input;
    }

    void spelen() {
        System.out.println("Wat is je naam? \n");
        String naam = scanner.nextLine();
        System.out.println("Hallo " + naam + ", wanneer je klaar bent om te spelen, klik op Enter!");
        String input = getInput();
        boolean isEersteRonde = true;

        while (true) {
            if (input.equals("q")) {
                break;
            } else {
                if(!isEersteRonde) {
                    vasthouden();
                }

                int[] worpen = new int[5];
                for (Dobbelsteen dobbelsteen: dobbelstenen) {
                    int i = dobbelstenen.indexOf(dobbelsteen);
                    if (blokkeerArray[i] == 0) {
                        worpen[i] = dobbelsteen.werpen();
                    }
                    worpen[i] = dobbelsteen.uitslagWorp;
                }
                Worp worp = new Worp(worpen);
                worp.worpUitslag();
                speler.worpGeschiedenis.add(worp);
                isEersteRonde = false;

//                System.out.println("Huidige waarden: " + worp.toString());
            }
        }
    }

    void vasthouden() {
        blokkeerArray = new int[5];
        System.out.println("Welke posities wilt u vasthouden? 0 voor geen anders bv 124");
        String input = getInput();
        try{
            int inputInt = Integer.parseInt(input);

            while (inputInt > 0) {
                int laatsteNummer = inputInt % 10;
                inputInt = inputInt / 10;
                blokkeerArray[laatsteNummer - 1] = 1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Geef een geldige input.");
            vasthouden();
        }

    }

    void quit() {
        speler.printWorpGeschiedenis();
    }
}

class Dobbelsteen {
    int uitslagWorp;

    int werpen() {
        uitslagWorp = new Random().nextInt(6) + 1;
        return uitslagWorp;
    }
}

class Worp {
    int[] worp;

    Worp(int[] worpen) {
        this.worp = worpen;
    }

    void worpUitslag() {
        System.out.println("WORP");
        System.out.println(("12345"));
        for(int i = 0; i < 5; i++) {
            System.out.print(worp[i]);
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return Arrays.toString(worp);
    }
}

class Speler {
    List<Worp> worpGeschiedenis = new ArrayList<>();

    void printWorpGeschiedenis() {
        for(int i = 0; i<worpGeschiedenis.size(); i++) {

            System.out.println("Worp " + (i + 1) + ":"  + worpGeschiedenis.get(i));
        }
    }
}