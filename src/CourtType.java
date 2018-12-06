public enum CourtType {

    COMMON, // sąd powszechny
    SUPREME, // Sąd Najwyższy
    ADMINISTRATIVE, // sąd administracyjny
    CONSTITUTIONAL_TRIBUNAL, // Trybunał Konstytucyjny
    NATIONAL_APPEAL_CHAMBER; // Krajowa Izba Odwoławcza

    public String toString()
    {
        switch(this) {
            case COMMON:
                return "Sąd powszechny";
            case SUPREME:
                return "Sąd Najwyższy";
            case ADMINISTRATIVE:
                return "Sąd administracyjny";
            case CONSTITUTIONAL_TRIBUNAL:
                return "Trybunał Konstytucyjny";
            case NATIONAL_APPEAL_CHAMBER:
                return "Krajowa Izba Odwoławcza";
        }
        return null;
    }

    public int toInt()
    {
        switch(this) {
            case COMMON:
                return 0;
            case SUPREME:
                return 1;
            case ADMINISTRATIVE:
                return 2;
            case CONSTITUTIONAL_TRIBUNAL:
                return 3;
            case NATIONAL_APPEAL_CHAMBER:
                return 4;
        }
        return -1;
    }

}
