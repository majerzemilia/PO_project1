import jline.console.ConsoleReader;
import org.jline.reader.LineReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class SingleActions {

    private Map<String, Items> redundantItems;

    SingleActions(Map<String, Items> redundantItems) {

        this.redundantItems = redundantItems;
    }

    private String help(String action) {

        switch (action) {
            case "rubrum":
                System.out.println("Podaj sygnatury orzeczeń oddzielone przecinkiem i spacją");
                break;
            case "content":
                System.out.println("Podaj sygnaturę orzeczenia");
                break;
        }
        return null;
    }


    private String extractedTextContent(ConsoleReader reader, BufferedWriter writer) throws IOException {

        help("content");
        if (writer != null) writer.write("content" + "\r\n");
        String sign = reader.readLine();
        if (writer != null) writer.write(sign + "\r\n");
        String s = "";
        if (redundantItems.containsKey(sign)) {
            s += redundantItems.get(sign).getTextContent() + "\r\n" + "\r\n";
        } else
            s += sign + " nie jest dostępna w bazie orzeczeń lub nie jest poprawną sygnaturą, poprawna sygnatura " +
                    "to np. KIO 276/14." + "\r\n" + "Wywołaj komendę ponownie i podaj poprawne dane."
                    + "\r\n";
        if (writer != null) writer.write(s);
        return s;
    }

    public String getExtractedTextContent(ConsoleReader reader, BufferedWriter writer) throws IOException {
        return extractedTextContent(reader, writer);
    }


    private String rubrumResult(ConsoleReader reader, BufferedWriter writer) throws IOException {

        help("rubrum");
        if (writer != null) writer.write("rubrum" + "\r\n");
        String[] a = reader.readLine().split(", ");
        for (String str : a) writer.write(str + " ");
        if (writer != null) writer.write("\r\n");
        String s = "";
        for (int i = 0; i < a.length; i++) {
            if (redundantItems.containsKey(a[i])) {
                s += new Rubrum(a[i], redundantItems).toString() + "\r\n";
            } else
                s += a[i] + " nie jest dostępna w bazie orzeczeń lub nie jest poprawną sygnaturą, poprawna sygnatura " +
                        "to np. KIO 276/14." + "\r\n" + "Wywołaj komendę ponownie i podaj poprawne dane."
                        + "\r\n" + "\r\n";
        }
        if (writer != null) writer.write(s);
        return s;
    }

    public String getRubrumResult(ConsoleReader reader, BufferedWriter writer) throws IOException {
        return rubrumResult(reader, writer);
    }

    public String info() {

        return "Aby wyświetlić rubrum jednego lub wielu orzeczeń, wpisz: rubrum" + "\n"
                + "Aby wyświetlić uzasadnienie orzeczenia o określonej sygnaturze, wpisz: content" + "\n"
                + "Aby wyświetlić liczbę orzeczeń dla wybranego sędziego, wpisz: judge" + "\n"
                + "Aby wyświetlić 10 sędziów, którzy wydali największą liczbę orzeczeń, wpisz: judges" + "\n"
                + "Aby wyświetlić rozkład statystyczny orzeczeń ze względu na miesiąc, wpisz: months" + "\n"
                + "Aby wyświetlić rozkład statystyczny orzeczeń ze względu na rodzaj sądu, wpisz: courts" + "\n"
                + "Aby wyświetlić 10 ustaw, które są najczęściej przywoływane w orzeczeniach, wpisz: regulations" + "\n"
                + "Aby wyświetlić rozkład statystyczny liczby sędziów przypadających na orzeczenie, wpisz: jury" + "\n";
    }

}
