import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day7 {
    private static Directory currentDir;

    private static Directory root;

    public static void main(String[] args) throws Exception {
        var inputPath = Path.of("src/Day7.txt");
        var input = Files.readAllLines(inputPath);


        input.forEach(Day7::handle);

        root.print(0);

        List<Directory> dirs = root.getChildDirsWithSizeLessThan(100_000);

        int part1 = dirs.stream()
                        .mapToInt(Directory::getSize)
                        .sum();

        System.out.println(dirs);
        System.out.println("part1 = " + part1);

        int totalFileSystemSize = 70_000_000;
        int minFreeSpaceNeeded = 30_000_000;
        int rootSize = root.getSize();


        int freeSpaceLeftByRoot = totalFileSystemSize - rootSize;
        int sizeWeNeedToFreeUp = minFreeSpaceNeeded - freeSpaceLeftByRoot;

        int part2 = root
                .getChildDirs()
                .stream()
                .mapToInt(Directory::getSize)
                .sorted()
                .dropWhile(size -> size < sizeWeNeedToFreeUp)
                .findFirst()
                .getAsInt();

        System.out.println("part2 = " + part2);
    }

    private static void handle(String line) {
        if (isInstruction(line)) {
            handleInstruction(line);
        } else {
            handleLsOutput(line);
        }
    }

    private static void handleInstruction(String line) {
        switch (line) {
            case "$ cd /" -> {
                print(line);
                currentDir = new Directory("/");
                root = currentDir;
            }
            case "$ ls" -> print(line);
            case "$ cd .." -> {
                print(line);
                currentDir = currentDir.getParent();
            }
            case default -> {
                // cd dirName
                print(line);
                String dirName = line.replace("$ cd ", "");
                currentDir = currentDir.findChildDirByName(dirName);
            }
        }
    }

    private static void handleLsOutput(String line) {
        if (isDirectory(line)) {
            currentDir.addItem(
                    Directory.ofInputString(line)
            );
        } else {
            currentDir.addItem(
                    File.ofInputString(line)
            );
        }
    }

    private static void print(String x) {
        System.out.println(x);
    }

    private static boolean isDirectory(String line) {
        return line.startsWith("dir");
    }

    private static boolean isInstruction(String line) {
        return line.startsWith("$");
    }


    interface Item {
        int getSize();

        String getName();

        Item getParent();

        void setParent(Directory directory);

        void print(int indentation);

        default String createIndentationString(int indentation) {
            return "-".repeat(indentation);
        }

        boolean isDirectory();
    }

    static class File implements Item {

        private final String name;

        private final int size;

        private Directory parent;

        File(String name, int size, Directory parent) {
            this.name = name;
            this.size = size;
            this.parent = parent;
        }

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Item getParent() {
            return parent;
        }

        public void setParent(Directory parent) {
            this.parent = parent;
        }

        @Override
        public void print(int indentation) {
            String indent = createIndentationString(indentation);
            System.out.println(indent + "📄 " + name + " " + size);
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

        public static Item ofInputString(String input) {
            var sizeAndFileName = input.split(" ");

            return new File(
                    sizeAndFileName[1],
                    Integer.parseInt(sizeAndFileName[0])
            );
        }
    }

    static class Directory implements Item {

        @Override
        public String toString() {
            return "Directory{" +
                    "name='" + name + '\'' +
                    '}';
        }

        private final String name;

        private final List<Item> items = new ArrayList<>();

        private Directory parent;

        private int size = -1;

        Directory(String name) {
            this.name = name;
        }

        public void addItem(Item item) {
            item.setParent(this);
            items.add(item);
        }

        @Override
        public int getSize() {
            if (size == -1) {
                size = items.stream()
                            .mapToInt(Item::getSize)
                            .sum();
            }

            return size;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setParent(Directory parent) {
            this.parent = parent;
        }

        @Override
        public void print(int indentation) {
            String indent = createIndentationString(indentation);
            System.out.println(indent + "📁 " + name);
            items.forEach(item -> item.print(indentation + 2));
        }

        @Override
        public boolean isDirectory() {
            return true;
        }

        @Override
        public Directory getParent() {
            return parent;
        }

        public Directory findChildDirByName(String name) {
            return ((Directory) items.stream()
                                     .filter(item -> item.getName().equals(name))
                                     .findAny()
                                     .get());
        }

        public List<Directory> getChildDirsWithSizeLessThan(int maxSize) {
            return this.getChildDirsStream()
                       .stream()
                       .peek(System.out::println)
                       .filter(dir -> dir.getSize() <= maxSize)
                       .toList();
        }

        private List<Directory> getChildDirs() {
            var subDirectories = items.stream()
                                      .filter(Item::isDirectory)
                                      .map(item -> (Directory) item)
                                      .collect(Collectors.toCollection(ArrayList::new));

            int initialSize = subDirectories.size();

            for (int i = 0; i < initialSize; i++) {
                Directory currentDir = subDirectories.get(i);
                subDirectories.addAll(currentDir.getChildDirs());
            }

            return subDirectories;
        }

        private List<Directory> getChildDirsStream() {
            return items.stream()
                        .filter(Item::isDirectory)
                        .map(item -> (Directory) item)
                        .flatMap(dir -> {
                            List<Directory> childDirs = dir.getChildDirs();
                            if (childDirs.isEmpty()) {
                                return Stream.of(dir);
                            } else {
                                return childDirs.stream();
                            }
                        })
                        .toList();
        }

        public static Directory ofInputString(String input) {
            return new Directory(input.replace("dir ", ""));
        }
    }
}