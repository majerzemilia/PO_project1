import java.util.Map;

public class SingleActions {

    private Map<String, Items> redundantItems;

    SingleActions(Map<String, Items> redundantItems){

        this.redundantItems = redundantItems;
    }

    public String printTextContent(String sign){

        String s = "";
        if(redundantItems.containsKey(sign)) {
            s += redundantItems.get(sign).getTextContent() + "\n";
        }
        else s += sign + " nie jest poprawną sygnaturą, poprawna sygnatura " +
                "to np. KIO 276/14." + "\n" + "Wywołaj komendę ponownie i podaj poprawne dane."
                + "\n";
        return s;
    }


    public String rubrumResult(String[] a){

        String s = "";
        for(int i = 0; i < a.length; i++){
            if(redundantItems.containsKey(a[i])){
                s += new Rubrum(a[i], redundantItems).toString() + "\n";
            }
            else s += a[i] + " nie jest poprawną sygnaturą, poprawna sygnatura " +
                    "to np. KIO 276/14." + "\n" + "Wywołaj komendę ponownie i podaj poprawne dane."
                    + "\n" + "\n";
        }
        return s;
    }

    public String info (){

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
