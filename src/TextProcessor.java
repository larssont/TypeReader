import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class TextProcessor {

    private File textRootDir;
    private final String TEXT_FILE_NAME = "text.txt";

    public TextProcessor(File textRootDir) {
        this.textRootDir = textRootDir;
    }

    public void addText(String name, String inputFilePath) {
        // Adds text to textRootDir

        File textDir = FileProcessor.newSubFile(textRootDir, name);
        textDir.mkdir();

        File textFile = FileProcessor.newSubFile(textDir, TEXT_FILE_NAME);

        try {
            if (textFile.createNewFile()) {
                FileProcessor.copyFileContents(inputFilePath, textFile.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] listTexts() {
        // Lists texts inside of textRootDir
        return textRootDir.list();
    }

    public ArrayList<String> getText(String textName) {
        // Search for file here in correct directory

        File textDir = FileProcessor.searchDir(textRootDir, textName);
        File textFile = FileProcessor.searchDir(textDir, TEXT_FILE_NAME);

        if (textDir == null) return null;

        return parseText(textFile);
    }

    private ArrayList<String> parseText(File f) {
        // Reads all lines of file from filePath
        try {
            return new ArrayList<>(Files.readAllLines(f.toPath()));
        }
        catch (IOException e) {
            return null;
        }
    }

}
