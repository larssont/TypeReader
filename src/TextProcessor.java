import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.stream.Collectors;

public class TextProcessor {

    private static List<Text> texts = new ArrayList<>();

    private File textRootDir;

    public TextProcessor(File textRootDir) {
        this.textRootDir = textRootDir;
        importTextsObj();
    }

    public void addText(String name, String inputFilePath) throws IOException {
        // Adds text to textRootDir

        if (!FileProcessor.isFileExisting(inputFilePath)) {
            throw new FileNotFoundException("");
        }

        if (containsText(name)) {
            throw new FileAlreadyExistsException("");
        }

        Text text = new Text(textRootDir, name);
        String textPath = text.getTextFile().getAbsolutePath();

        if (!FileProcessor.copyFileContents(inputFilePath, textPath)) {
            throw new IOException();
        }

        writeTextObjToFile(text);
        text.load();
        texts.add(text);
    }

    private boolean containsText(String textName) {
        for (String name : listTexts()) {
            if (name.equalsIgnoreCase(textName)) {
                return true;
            }
        }
        return false;
    }

    public List<String> listTexts() {
        // Lists texts inside of textRootDir
        return texts.stream().map(Text::toString).collect(Collectors.toList());
    }

    public Text findText(String name) {
        for (Text t: texts) {
            if (t.toString().equals(name)) return t;
        }
        return null;
    }

    private void importTextsObj() {

        File[] textDirs = textRootDir.listFiles();
        File[] files;

        String textObjName = Text.getTextObjName();

        if (textDirs != null) {
            for (File dir : textDirs) {
                files = dir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.getName().equals(textObjName)) {
                            readTextObjFromFile(f);
                        }
                    }
                }
            }
        }
    }

    private void writeTextObjToFile(Text t) {
        try {
            FileOutputStream fileOut = new FileOutputStream(t.getObjFile());
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(t);
            objectOut.close();

            System.out.println("The Object was successfully written to a file.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void readTextObjFromFile(File f) {
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Text t = (Text) objectIn.readObject();
            objectIn.close();

            // TODO: Fix so text loads updated stats as well (completion etc.)
            texts.add(t);
            t.load();
            System.out.println("The Object was successfully read from a file.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
