import java.util.*;

public class Top10Statistics {

    private List<Judges> judges;
    private List<ReferencedRegulation> regulations;

    Top10Statistics(ListOfItems items) {
        setJudges(items);
        setReferencedRegulations(items);
    }

    private void setJudges(ListOfItems items) {

        List<Judges> judges = new LinkedList<>();
        for (int i = 0; i < items.getItems().size(); i++) {

            judges.addAll(items.getItems().get(i).getJudges());
        }
        this.judges = judges;
    }

    private void setReferencedRegulations(ListOfItems items){

        List<ReferencedRegulation> regulations = new LinkedList<>();
        for (int i = 0; i < items.getItems().size(); i++) {

            regulations.addAll(items.getItems().get(i).getReferencedRegulations());
        }
        this.regulations = regulations;
    }


    public void tenJudges() {

        List<Judges> result = new LinkedList<>(judges);

        Comparator<Judges> comparator =
                Comparator.comparing(Judges::getNumberOfOrders).reversed().thenComparing(Judges::getName);
        result.sort(comparator);

        System.out.println("10 sędziów, którzy wydali największą liczbę orzeczeń:");
        System.out.println(result.get(0).getName() + " - Liczba orzeczeń: " + result.get(0).getNumberOfOrders());

        int counter = 1;
        for (int i=1; i<result.size(); i++){
            if(!result.get(i).getName().equals(result.get(i-1).getName())){
                System.out.println(result.get(i).getName() + " - Liczba orzeczeń: " + result.get(i).getNumberOfOrders());
                counter++;
                if (counter == 10) break;
            }
        }
    }

    public void tenReferencedRegulations(){

        List <ReferencedRegulation> result = new LinkedList<>(regulations);

        Comparator<ReferencedRegulation> comparator =
                Comparator.comparing(ReferencedRegulation::getNumberOfOrders).reversed().
                        thenComparing(ReferencedRegulation::getJournalNo).thenComparing(ReferencedRegulation::getJournalYear).
                        thenComparing(ReferencedRegulation::getJournalEntry);
        result.sort(comparator);

        System.out.println("10 ustaw, które są najczęściej przywoływane w orzeczeniach:");
        System.out.println(result.get(0).getJournalTitle() + " - Liczba wystąpień: " + result.get(0).getNumberOfOrders());

        int counter = 1;
        for (int i=1; i<result.size(); i++){

            String i_id = String.valueOf(result.get(i).getJournalNo()) + "." + //id of regulation in the loop for i
                    String.valueOf(result.get(i).getJournalYear()) + "." +
                    String.valueOf(result.get(i).getJournalEntry());
            String i1_id = String.valueOf(result.get(i - 1).getJournalNo()) + "." + //id of regulation in the loop for i-1
                    String.valueOf(result.get(i - 1).getJournalYear()) + "." +
                    String.valueOf(result.get(i - 1).getJournalEntry());

            if(!i_id.equals(i1_id)){
                System.out.println(result.get(i).getJournalTitle() + " - Liczba wystąpień: " + result.get(i).getNumberOfOrders());
                counter++;
                if (counter == 10) break;
            }
        }
    }

}