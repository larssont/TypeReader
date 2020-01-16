import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileProcessor {

    private static String appDir;
    private static String userHomeDir = System.getProperty("user.home");

    public FileProcessor(String appName) {
        FileProcessor.appDir = createDir(appName, userHomeDir);
    }

    public boolean addText(String name, String inputFilePath) {

        if (isFileExisting(formatFilePath(appDir, name))) {
            return false;
        }

        String textDirPath = createDir(name, appDir);
        String outputFile = "text.txt";
        String outputFilePath = formatFilePath(textDirPath, outputFile);
        File textFile = new File(outputFilePath);

        try {
            if (textFile.createNewFile()) {
                copyFileContents(inputFilePath, outputFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public String getAbsolutePath(String input) {
        File f = new File(input);
        return f.getAbsolutePath();
    }

    private String formatFilePath(String dir, String name) {
        return String.format("%s/%s", dir, name);
    }
    public void getText(String textID, File dir) {
        ArrayList<String> contents = findText(textID, dir);
        System.out.println(contents);
    }

    private void copyFileContents(String inputFilePath, String outputFilePath) {
        try {
            FileReader fr = new FileReader(inputFilePath);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(outputFilePath, true);
            String s;

            while ((s = br.readLine()) != null) { // read a line
                fw.write(String.format("%s\n",s)); // write to output file
                fw.flush();
            }
            br.close();
            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean isFileExisting(String filePath) {
        File f = new File(filePath);
        return f.exists();
    }

    private String createDir(String dirName, String path) {

        String dirPath = String.format("%s/%s", path, dirName);
        File dir = new File(dirPath);

        dir.mkdir();
        return dir.getAbsolutePath();
    }

    private ArrayList<String> findText(String textID, File parentDirectory) {
        // Search for file here in correct directory
        File[] files = parentDirectory.listFiles();
        if (files != null) {
            for(File file : files) {
                if (file.getName().equalsIgnoreCase(textID)) {
                    return readText(file.getAbsolutePath());
                }
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<String> readText(String filePath) {
        // Reads all lines of file from filePath
        try {
            return new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
        }
        catch (IOException e) {
            return null;
        }
    }

}
