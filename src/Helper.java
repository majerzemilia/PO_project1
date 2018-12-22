import jline.console.ConsoleReader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;


public class Helper {


    public void printAndSplit(ConsoleReader reader, Map<String, Items> redundantItems, ListOfItems items, BufferedWriter writer) throws IOException {

        System.out.println("Aby wyświetlić listę dostępnych komend, wpisz: help. Aby zakończyć, wpisz: quit" + "\n");
        String start = "";

        while (true) {
            start = reader.readLine();
            switch (start) {
                case "rubrum":
                    System.out.println(new SingleActions(redundantItems).getRubrumResult(reader, writer));
                    break;
                case "content":
                    System.out.println(new SingleActions(redundantItems).getExtractedTextContent(reader, writer));
                    break;
                case "judge":
                    System.out.println(new StatisticalDistributions(items).getNumberOfOrdersPerJudge(reader, writer));
                    break;
                case "judges":
                    System.out.println(new Top10Statistics(items).getTenJudges(writer));
                    break;
                case "months":
                    System.out.println(new StatisticalDistributions(items).getSentencesPerMonth(writer));
                    break;
                case "courts":
                    System.out.println(new StatisticalDistributions(items).getSentencesPerCourtType(writer));
                    break;
                case "regulations":
                    System.out.println(new Top10Statistics(items).getTenReferencedRegulations(writer));
                    break;
                case "jury":
                    System.out.println(new StatisticalDistributions(items).getJudgesPerOrder(writer));
                    break;
                case "help":
                    System.out.println(new SingleActions(redundantItems).info());
                    break;
                case "quit":
                    return;
                default:
                    System.out.println("Podaj odpowiednią komendę lub wpisz: help" + "\n");
                    break;
            }
        }
    }
}
