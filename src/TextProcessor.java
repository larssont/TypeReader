import com.sun.deploy.util.ArrayUtil;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
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

        ArrayList<String> output = new ArrayList<>();
        BufferedReader br;
        String line;
        try {
            br = new BufferedReader(new FileReader(f));
            while( (line = br.readLine()) != null){
                if (line.trim().length() > 0) {
                    output.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Oops! Please check for the presence of file in the path specified.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Oops! Unable to read the file.");
            e.printStackTrace();
        }

        return output;
    }

}
