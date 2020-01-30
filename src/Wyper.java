import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

public class Wyper {

    private static final String APP_NAME = "Wyper";

    private static boolean exit = false;
    private static FileProcessor fileProcessor;
    private static TextProcessor textProcessor;
    private static Map<String, Runnable> commands;

    private void start() {

        fileProcessor = new FileProcessor(
                APP_NAME,
                "texts",
                "images",
                "settings"
                );

        textProcessor = new TextProcessor(fileProcessor.getTextDir());

        commands = new HashMap<>();

        // Populate commands map
        commands.put("add", Wyper::runCmdAdd);
        commands.put("type", Wyper::runCmdType);
        commands.put("list", Wyper::runCmdList);
        commands.put("exit", Wyper::runCmdExit);

        // TODO: Read saved books into memory

        run();
    }

    private void run() {

        showWelcome();

        while (!exit) {
            String in = readCmdInput().toLowerCase();
            // Invoke some command
            commands.get(in).run();
            try {
            } catch (NullPointerException e) {
                runCmdInvalid();
            }
        }
    }

    private void showWelcome() {
        System.out.println("Welcome to TypeReader!");
    }

    private static String readCmdInput() {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine().trim();
        if (in.isEmpty()) {
            System.out.println("Empty input, try again");
            return readCmdInput();
        }
        return in;
    }

    private static void runCmdAdd() {
        System.out.println("Enter name of text:");
        String name = readCmdInput();
        System.out.println("Enter full path for text file:");
        String filePath = readCmdInput();

        try {
            textProcessor.addText(name, filePath);
        } catch (FileAlreadyExistsException e) {
            System.out.printf("A text with the name \"%s\" already exists.\n", name);
            return;
        } catch (FileNotFoundException e) {
            System.out.printf("Couldn't find a file located at \"%s\".\n", filePath);
            return;
        }

        System.out.println("Text added!");
    }

    private static void runCmdType() {
        System.out.println("Select text to type:");
        String name = readCmdInput();

        List<String> text = textProcessor.findText(name);
        if (text == null) {
            System.out.printf("No text found by the name of: %s\n", name);
            return;
        }

        TypingActivity ta = new TypingActivity(text);
        System.out.println(ta.start());

    }

    private static void runCmdStats() {
        System.out.println("Select text for stats:");
        String name = readCmdInput();

        //textProcessor.getTexts();
    }

    private static void runCmdList() {
        List<String> texts = textProcessor.listTexts();

        if (texts.size() == 0) {
            System.out.println("No texts have been added.");
            return;
        }

        System.out.println(texts);
    }

    private static void runCmdExit() {
        System.out.println("Goodbye!");
        exit = true;
    }

    private void runCmdInvalid() {
        System.out.println("Invalid command, try again.");
    }

    public static void main(String[] args) {

        Wyper tr = new Wyper();

        tr.start();

    }
}
