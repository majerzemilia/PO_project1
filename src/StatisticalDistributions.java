import java.util.Calendar;

public class StatisticalDistributions {

    public String judgesPerOrder (ListOfItems items){

        int length = 0;
        for (Items item: items.getItems()){
            if (item.getJudges().size() > length) length = item.getJudges().size();
        }

        int tab[] = new int[length+1];
        for (int i=0; i<tab.length; i++) tab[i] = 0;
        for (Items item: items.getItems()){
            tab[item.getJudges().size()]++;
        }

        String s = "Rozkład statystyczny liczby sędziów przypadających na orzeczenie:" + "\n";

        for (int i=0; i<tab.length; i++) if(tab[i] != 0)
            s += "Liczba sędziów: "+ i + ", liczba orzeczeń: " + tab[i] + "\n";
        return s;
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


    public String sentencesPerMonth(ListOfItems items){

        int tab[] = new int[12];
        for (int i=0; i<tab.length; i++) tab[i] = 0;

        for (Items item: items.getItems()){
                Calendar cal = Calendar.getInstance();
                cal.setTime(item.getJudgmentDate());
                int month = cal.get(Calendar.MONTH);
                tab[month]++;
        }

        String s = "Rozkład statystyczny orzeczeń ze względu na miesiąc:" + "\n";
        for (int i=0; i<tab.length; i++) s += getMonth(i) + ": " + tab[i] + " orzeczeń" + "\n";
        return s;
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

    public String sentencesPerCourtType(ListOfItems items){

        int tab[] = new int[5];
        for (int i=0; i<tab.length; i++) tab[i] = 0;

        for (Items item: items.getItems()){
            tab[item.getCourtType().toInt()]++;
        }

        String s = "Rozkład statystyczny orzeczeń ze względu na rodzaj sądu:" + "\n";
        for (int i=0; i<tab.length; i++) s += "Rodzaj sądu: " + courtTypeFromInt(i)+ ", " + tab[i] + " orzeczeń" + "\n";
        return s;
    }
}

