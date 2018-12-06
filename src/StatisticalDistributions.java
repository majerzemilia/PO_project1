import java.util.Calendar;

public class StatisticalDistributions {

    public void judgesPerOrder (ListOfItems items){

        int length = 0;
        for (Items item: items.getItems()){
            if (item.getJudges().size() > length) length = item.getJudges().size();
        }

        int tab[] = new int[length+1];
        for (int i=0; i<tab.length; i++) tab[i] = 0;
        for (Items item: items.getItems()){
            tab[item.getJudges().size()]++;
        }

        System.out.println("Rozkład statystyczny liczby sędziów przypadających na orzeczenie:");
        for (int i=0; i<tab.length; i++) if(tab[i] != 0) System.out.println("Liczba sędziów: "+ i +
                ", liczba orzeczeń: " + tab[i]);
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


    public void sentencesPerMonth(ListOfItems items){

        int tab[] = new int[12];
        for (int i=0; i<tab.length; i++) tab[i] = 0;

        for (Items item: items.getItems()){
            if(item.getJudgmentType().toString().equals("Wyrok")) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(item.getJudgmentDate());
                int month = cal.get(Calendar.MONTH);
                tab[month]++;
            }
        }

        System.out.println("Rozkład statystyczny wyroków ze względu na miesiąc:");
        for (int i=0; i<tab.length; i++) System.out.println(getMonth(i) + ": " + tab[i] + " wyroków");
    }

    private String courtTypeFromInt (int i)
    {
        switch(i) {
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
        }
        return null;

    }

    public void sentencesPerCourtType(ListOfItems items){

        int tab[] = new int[5];
        for (int i=0; i<tab.length; i++) tab[i] = 0;

        for (Items item: items.getItems()){
            if(item.getJudgmentType().toString().equals("Wyrok")) tab[item.getCourtType().toInt()]++;
        }

        System.out.println("Rozkład statystyczny wyroków ze względu na rodzaj sądu:");
        for (int i=0; i<tab.length; i++) System.out.println("Rodzaj sądu: " +
                courtTypeFromInt(i)+ ", " + tab[i] + " wyroków");

    }
}

