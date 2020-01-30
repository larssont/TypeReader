import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TextProcessor {

    private static List<Text> texts = new ArrayList<>();

    private File textRootDir;

    public TextProcessor(File textRootDir) {
        this.textRootDir = textRootDir;
        loadTexts();
    }

    public void addText(String name, String inputFilePath) {
        // Adds text to textRootDir
        try {
            Text text = new Text(textRootDir, name);
            texts.add(text);
            writeTextObjToFile(text);

            FileProcessor.copyFileContents(inputFilePath, text.getTextFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTexts() {

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

    public List<String> listTexts() {
        // Lists texts inside of textRootDir
        return texts.stream().map(Text::toString).collect(Collectors.toList());
    }


    public ArrayList<String> findText(String name) {
        for (Text t: texts) {
            if (t.toString().equals(name)) return parseText(t.getTextFile());
        }
        return null;
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

            texts.add(t);
            System.out.println("The Object was successfully read from a file.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
