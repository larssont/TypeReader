import java.util.*;

public class TypeReader {

    private static final String APP_NAME = "TypeReader";

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
        commands.put("add", TypeReader::runCmdAdd);
        commands.put("type", TypeReader::runCmdType);
        commands.put("list", TypeReader::runCmdList);
        commands.put("exit", TypeReader::runCmdExit);

        // TODO: Read saved books into memory

        run();
    }

    private void run() {

        showWelcome();

        while (!exit) {
            String in = readInput();
            // Invoke some command
            try {
                commands.get(in).run();
            } catch (NullPointerException e) {
                runCmdInvalid();
            }
        }
    }

    private void showWelcome() {
        System.out.println("Welcome to TypeReader!");
    }

    private static String readInput() {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine().trim();
        if (in.isEmpty()) {
            System.out.println("Empty input, try again");
            return readInput();
        }
        return in;
    }

    private static void runCmdAdd() {
        System.out.println("Enter name of text:");
        String name = readInput();
        System.out.println("Enter full path for text file:");
        String filePath = readInput();

        if (!FileProcessor.isFileExisting(filePath)) {
            String msg = "File doesn't exist, make sure this is the correct path:";
            System.out.println(msg);
            return;
        }

        textProcessor.addText(name, filePath);
        System.out.println("Text added!");
    }

    private static void runCmdType() {
        System.out.println("Select text to type:");
        String name = readInput();

        List<String> text = textProcessor.getText(name);
        System.out.println(text);

    }

    private static void runCmdList() {
        String[] texts = textProcessor.listTexts();

        if (texts.length == 0) {
            System.out.println("No texts have been added.");
            return;
        }

        System.out.println(Arrays.toString(texts));
    }

    private static void runCmdExit() {
        System.out.println("Goodbye!");
        exit = true;
    }

    private void runCmdInvalid() {
        System.out.println("Invalid command, try again.");
    }

    public static void main(String[] args) {

        TypeReader tr = new TypeReader();

        tr.start();

    }
}
