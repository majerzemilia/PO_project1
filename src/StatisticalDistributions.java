import jline.console.ConsoleReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Calendar;

public class StatisticalDistributions {

    private ListOfItems items;

    StatisticalDistributions(ListOfItems items) {

        this.items = items;
    }

    private String judgesPerOrder(BufferedWriter writer) throws IOException {

        if (writer != null) writer.write("judges" + "\r\n");
        int length = 0;
        for (Items item : items.getItems()) {
            if (item.getJudges() != null) {
                if (item.getJudges().size() > length) length = item.getJudges().size();
            }
        }

        int tab[] = new int[length + 1];
        for (int i = 0; i < tab.length; i++) tab[i] = 0;
        for (Items item : items.getItems()) {
            if (item.getJudges() != null) tab[item.getJudges().size()]++;
            else tab[0]++;
        }

        String s = "Rozkład statystyczny liczby sędziów przypadających na orzeczenie:" + "\r\n";

        for (int i = 0; i < tab.length; i++)
            if (tab[i] != 0)
                s += "Liczba sędziów: " + i + ", liczba orzeczeń: " + tab[i] + "\r\n";
        if (writer != null) writer.write(s + "\r\n");
        return s;
    }

    public String getJudgesPerOrder(BufferedWriter writer) throws IOException {

        return judgesPerOrder(writer);
    }

    private String getMonth(int month) {

        switch (month) {
            case 0:
                return "Styczeń";
            case 1:
                return "Luty";
            case 2:
                return "Marzec";
            case 3:
                return "Kwiecień";
            case 4:
                return "Maj";
            case 5:
                return "Czerwiec";
            case 6:
                return "Lipiec";
            case 7:
                return "Sierpień";
            case 8:
                return "Wrzesień";
            case 9:
                return "Październik";
            case 10:
                return "Listopad";
            case 11:
                return "Grudzień";
        }
        return null;
    }


    private String sentencesPerMonth(BufferedWriter writer) throws IOException {

        if (writer != null) writer.write("months" + "\r\n");
        int tab[] = new int[12];
        for (int i = 0; i < tab.length; i++) tab[i] = 0;

        for (Items item : items.getItems()) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getJudgmentDate());
            int month = cal.get(Calendar.MONTH);
            tab[month]++;
        }

        String s = "Rozkład statystyczny orzeczeń ze względu na miesiąc:" + "\r\n";
        for (int i = 0; i < tab.length; i++) s += "Miesiąc: " + getMonth(i) + ", liczba orzeczeń: " + tab[i] + "\r\n";
        if (writer != null) writer.write(s + "\r\n");
        return s;
    }

    public String getSentencesPerMonth(BufferedWriter writer) throws IOException {

        return sentencesPerMonth(writer);
    }

    private String courtTypeFromInt(int i) {

        switch (i) {
            case 0:
                return "Sąd powszechny";
            case 1:
                return "Sąd Najwyższy";
            case 2:
                return "Sąd administracyjny";
            case 3:
                return "Trybunał Konstytucyjny";
            case 4:
                return "Krajowa Izba Odwoławcza";
            case 5:
                return "Wojewódzki Sąd Administracyjny";
            case 6:
                return "Naczelny Sąd Administracyjny";
        }
        return null;

    }

    private String sentencesPerCourtType(BufferedWriter writer) throws IOException {

        if (writer != null) writer.write("courts" + "\r\n");
        int tab[] = new int[7];
        for (int i = 0; i < tab.length; i++) tab[i] = 0;

        for (Items item : items.getItems()) {
            tab[item.getCourtType().toInt()]++;
        }

        String s = "Rozkład statystyczny orzeczeń ze względu na rodzaj sądu:" + "\r\n";
        for (int i = 0; i < tab.length; i++)
            s += "Rodzaj sądu: " + courtTypeFromInt(i) + ", liczba orzeczeń: " + tab[i] + "\r\n";
        if (writer != null) writer.write(s + "\r\n");
        return s;
    }

    public String getSentencesPerCourtType(BufferedWriter writer) throws IOException {

        return sentencesPerCourtType(writer);
    }

    private String numberOfOrdersPerJudge(ConsoleReader reader, BufferedWriter writer) throws IOException {

        if (writer != null) writer.write("judge" + "\r\n");
        System.out.println("Podaj imię i nazwisko sędziego");
        String name = reader.readLine();
        if (writer != null) writer.write(name + "\r\n");
        int counter = 0;
        String s = "Liczba orzeczeń: ";

        for (Items item : items.getItems()) {

            if (counter > 0) break;

            if (item.getJudges() == null) break;

            for (Judges judge : item.getJudges()) {

                if (judge.getName().equals(name)) {

                    s += judge.getNumberOfOrders() + "\r\n";
                    counter++;
                    break;
                }
            }

        }
        if (!s.equals("Liczba orzeczeń: ")) {
            if (writer != null) writer.write(s + "\r\n");
            return s;
        } else {
            s = "Podano niedostępne w bazie sędziów lub niepoprawne dane osobowe, poprawna forma to imię i nazwisko " +
                    "sędziego oddzielone spacją (rozróżniana jest wielkość liter)." + "\r\n" + "Wywołaj komendę ponownie " +
                    "i podaj poprawne dane." + "\r\n";
            if (writer != null) writer.write(s + "\r\n");
            return s;

        }
    }

    public String getNumberOfOrdersPerJudge(ConsoleReader reader, BufferedWriter writer) throws IOException {
        return numberOfOrdersPerJudge(reader, writer);
    }
}

