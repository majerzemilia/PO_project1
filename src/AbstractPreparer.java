import java.io.File;

abstract class AbstractPreparer {

    public File[] prepareListOfFiles(String path) throws IllegalArgumentException {


        File folder = new File(path);
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Podano niepoprawną ścieżkę do folderu. Uruchom program ponownie, " +
                    "podając ścieżkę do folderu zawierającego poprawne pliki." + "\n");
        }

        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null) {
            throw new IllegalArgumentException("Folder " + folder.getName() + " nie zawiera plików w odpowiednim formacie. " +
                    "Uruchom program ponownie, podając ścieżkę do folderu zawierającego poprawne pliki."
                    + "\n");
        }
        return listOfFiles;
    }
}
