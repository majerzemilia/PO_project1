import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = { "text"})

public class ReferencedRegulation{

    private String journalTitle;
    private int journalNo;
    private int journalYear = 0;
    private int journalEntry;
    private int numberOfOrders;


    public String getJournalTitle(){
        return journalTitle;
    }

    public void setJournalTitle(String journalTitle){
        this.journalTitle = journalTitle;
    }

    public int getJournalNo(){
        return journalNo;
    }

    public void setJournalNo(int journalNo){
        this.journalNo = journalNo;
    }

    public int getJournalYear(){
        return journalYear;
    }

    public void setJournalYear(int journalYear){
        this.journalYear = journalYear;
    }

    public int getJournalEntry(){
        return journalEntry;
    }

    public void setJournalEntry(int journalEntry){
        this.journalEntry = journalEntry;
    }

    public Integer getNumberOfOrders(){
        return this.numberOfOrders;
    }

    public void setNumberOfOrders(ListOfItems items){

        int result = 0;

        String id = this.getId();

        for(Items item: items.getItems()){

            if(item.getReferencedRegulations() != null){
                for (ReferencedRegulation regulation : item.getReferencedRegulations()){

                    String comparingId;
                    if(this.getJournalYear() == 0) comparingId = regulation.getJournalTitle();
                    else comparingId = regulation.getId();

                    if (comparingId.equals(id)){
                        result++;
                        break;
                    }
                }
            }
        }
        numberOfOrders = result;
    }

    public String getId(){

        if(this.journalYear != 0) return this.journalNo + "." + this.journalYear + "." + this.journalEntry;
        else return this.getJournalTitle();
    }
}
