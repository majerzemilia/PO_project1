public enum JudgmentType {
    DECISION, // postanowienie
    RESOLUTION, // uchwała
    SENTENCE, // wyrok
    REGULATION, // zarządzenie
    REASONS; // uzasadnienie, system źródłowy orzeczeń sądów powszechnych czasem dzieli orzeczenie na dwa odrębne: orzeczenie i jego uzasadnienie




    public String toString()
    {
        switch(this) {
            case DECISION:
                return "Postanowienie";
            case RESOLUTION:
                return "Uchwała";
            case SENTENCE:
                return "Wyrok";
            case REGULATION:
                return "Zarządzenie";
            case REASONS:
                return "Uzasadnienie";
        }
        return null;
    }
}
