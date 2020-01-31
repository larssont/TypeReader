import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Text implements Serializable {

    private File textDir;
    private File textFile;
    private File objFile;

    private ArrayList<String> lines;

    private String name;
    private static final String TEXT_FILE_NAME = "text.txt";
    private static final String TEXT_OBJ_NAME = "obj.txt";

    private int totalWords;
    private int correctWords;
    private int completedWords;

    public Text(File textRootDir, String name) throws IOException {

        File textDir = FileProcessor.newSubFile(textRootDir, name);
        File textFile = FileProcessor.newSubFile(textDir, TEXT_FILE_NAME);
        File objFile = FileProcessor.newSubFile(textDir, TEXT_OBJ_NAME);

        textDir.mkdir();
        textFile.createNewFile();

        this.name = name;
        this.textDir = textDir;
        this.textFile = textFile;
        this.objFile = objFile;
    }

    public void load() {
        lines = renderText();
        totalWords = countWords();
    }

    public void addProgress(int correctWords, int completedWords) {
        this.correctWords+=correctWords;
        this.completedWords+=completedWords;
    }

    public File getTextDir() {
        return textDir;
    }

    public File getTextFile() {
        return textFile;
    }

    public File getObjFile() {
        return objFile;
    }

    public static String getTextObjName() {
        return TEXT_OBJ_NAME;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public int getCompletedWords() {
        return completedWords;
    }

    public int getCorrectWords() {
        return correctWords;
    }

    public int getIncorrectWords() {
        return completedWords - correctWords;
    }

    public float getCompletionPercentage() {
        return ((float)completedWords/(float)totalWords)*100;
    }

    public float getCorrectPercentage() {
        return ((float)correctWords/(float)completedWords)*100;
    }

    public List<String> getLines() {
        return lines;
    }

    private int countWords() {
        int a = 0;
        System.out.println(lines.toString());
        for (String s : lines) {
            a += s.split(" ").length;
        }
        return a;
    }

    public ArrayList<String> renderText() {

        ArrayList<String> output = new ArrayList<>();
        BufferedReader br;
        String line;

        try {
            br = new BufferedReader(new FileReader(textFile));
            while( (line = br.readLine()) != null){
                if (line.trim().length() > 0) {
                    output.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public String toString() {
        return name;
    }
}
