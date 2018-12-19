import java.util.*;

public class Top10Statistics{

    private List<Judges> judges;
    private List<ReferencedRegulation> regulations;

    Top10Statistics(ListOfItems items){
        setJudges(items);
        setReferencedRegulations(items);
    }

    private void setJudges(ListOfItems items){

        List<Judges> judges = new LinkedList<>();
        for(Items item: items.getItems()){

            if(item.getJudges() != null) judges.addAll(item.getJudges());
        }
        this.judges = judges;
    }

    private void setReferencedRegulations(ListOfItems items){

        List<ReferencedRegulation> regulations = new LinkedList<>();
        for (Items item: items.getItems()){

            if(item.getReferencedRegulations() != null){
                regulations.addAll(item.getReferencedRegulations());
            }
        }
        this.regulations = regulations;
    }


    public String tenJudges(){

        List<Judges> result = new LinkedList<>(judges);

        Comparator<Judges> comparator =
                Comparator.comparing(Judges::getNumberOfOrders).reversed().thenComparing(Judges::getName);
        result.sort(comparator);

        String s = "10 sędziów, którzy wydali największą liczbę orzeczeń:" + "\n";
        s += result.get(0).getName() + " - Liczba orzeczeń: " + result.get(0).getNumberOfOrders() + "\n";

        int counter = 1;
        for (int i=1; i<result.size(); i++){

            if(!result.get(i).getName().equals(result.get(i-1).getName())){

                s += result.get(i).getName() + " - Liczba orzeczeń: " + result.get(i).getNumberOfOrders() + "\n";
                counter++;
                if (counter == 10) break;
            }
        }
        return s;
    }

    public String tenReferencedRegulations(){

        List <ReferencedRegulation> result = new LinkedList<>(regulations);

        Comparator<ReferencedRegulation> comparator =
                Comparator.comparing(ReferencedRegulation::getNumberOfOrders).reversed().
                        thenComparing(ReferencedRegulation::getJournalNo).thenComparing(ReferencedRegulation::getJournalYear).
                        thenComparing(ReferencedRegulation::getJournalEntry);
        result.sort(comparator);

        String s = "10 ustaw, które są najczęściej przywoływane w orzeczeniach:" + "\n";
        s += result.get(0).getJournalTitle() + " - Liczba wystąpień: " + result.get(0).getNumberOfOrders() + "\n";

        int counter = 1;
        for (int i=1; i<result.size(); i++){

            String i_id = result.get(i).getId();
            String i_id_text = result.get(i).getJournalTitle();
            String i1_id = result.get(i-1).getId();
            String i1_id_text = result.get(i-1).getJournalTitle();

            if(!i_id.equals(i1_id) && !i_id_text.equals(i1_id_text)){
                s += result.get(i).getJournalTitle() + " - Liczba wystąpień: " + result.get(i).getNumberOfOrders() + "\n";
                counter++;
                if (counter == 10) break;
            }
        }
        return s;
    }

}