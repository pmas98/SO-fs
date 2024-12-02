package filesystem;

import java.util.*;

public class FileSystemSimulator {

    private Directory root;
    private Journal journal;
    private Directory currentDirectory;

    public FileSystemSimulator(String journalPath) {
        this.root = new Directory("", "/");
        this.currentDirectory = root;
        this.journal = new Journal(journalPath);
    }

    public void createFile(String name) {
        String path = currentDirectory.getPath() + name;
        File file = new File(name, path);
        currentDirectory.addEntry(file);
        journal.addEntry(new JournalEntry(
                JournalEntry.OperationType.CREATE_FILE,
                path,
                null
        ));
    }

    public void createDirectory(String name) {
        String path = currentDirectory.getPath() + name;
        Directory dir = new Directory(name, path);
        currentDirectory.addEntry(dir);
        journal.addEntry(new JournalEntry(
                JournalEntry.OperationType.CREATE_DIR,
                path,
                null
        ));
    }

    public void changeDirectory(String path) {
        String oldPath = currentDirectory.getPath();

        if (path.equals("..")) {
            if (currentDirectory == root) {
                System.out.println("Você já está no diretório raiz.");
                return;
            }

            String parentPath = currentDirectory.getPath();
            int lastSlashIndex = parentPath.lastIndexOf('/');
            if (lastSlashIndex != -1) {
                parentPath = parentPath.substring(0, lastSlashIndex);
            }

            if (parentPath.isEmpty()) {
                currentDirectory = root;
            } else {
                Directory parentDir = root.getDirectoryByPath(parentPath);
                if (parentDir == null) {
                    System.out.println("Erro: Diretório pai não encontrado.");
                    return;
                }
                currentDirectory = parentDir;
            }
        } else {
            Directory dir = (Directory) currentDirectory.getEntries().get(path);
            if (dir == null || !dir.isDirectory()) {
                System.out.println("Diretório não encontrado: " + path);
                return;
            }
            currentDirectory = dir;
        }

        String newPath = currentDirectory.getPath();

        journal.addEntry(new JournalEntry(
                JournalEntry.OperationType.CHANGE_DIR,
                oldPath,
                newPath
        ));
    }

    public void deleteEntry(String name) {
        FileSystemEntry entry = currentDirectory.getEntries().get(name);
        if (entry == null) {
            System.out.println("Entrada não encontrada: " + name);
            return;
        }

        currentDirectory.removeEntry(name);
        JournalEntry.OperationType opType = entry.isDirectory()
                ? JournalEntry.OperationType.DELETE_DIR
                : JournalEntry.OperationType.DELETE_FILE;

        journal.addEntry(new JournalEntry(
                opType,
                entry.getPath(),
                null
        ));
    }

    public void renameEntry(String oldName, String newName) {
        FileSystemEntry entry = currentDirectory.getEntries().get(oldName);
        if (entry == null) {
            System.out.println("Entrada não encontrada: " + oldName);
            return;
        }

        String oldPath = entry.getPath();
        String newPath = currentDirectory.getPath() + newName;
        entry.setName(newName);

        currentDirectory.removeEntry(oldName);
        currentDirectory.addEntry(entry);

        JournalEntry.OperationType opType = entry.isDirectory()
                ? JournalEntry.OperationType.RENAME_DIR
                : JournalEntry.OperationType.RENAME_FILE;

        journal.addEntry(new JournalEntry(
                opType,
                oldPath,
                newPath
        ));
    }

    public void copyEntry(String sourceName, String destinationName) {
        FileSystemEntry source = currentDirectory.getEntries().get(sourceName);
        if (source == null) {
            System.out.println("Entrada não encontrada: " + sourceName);
            return;
        }
    
        String destinationPath = currentDirectory.getPath() + destinationName;
    
        if (source.isDirectory()) {
            Directory sourceDir = (Directory) source;
            Directory destDir = new Directory(destinationName, destinationPath);
            currentDirectory.addEntry(destDir);
    
            // Cópia recursiva de diretórios
            for (FileSystemEntry entry : sourceDir.getEntries().values()) {
                String entryDestinationPath = destinationPath + "/" + entry.getName();
                if (entry.isDirectory()) {
                    copyEntry(entry.getName(), entryDestinationPath);
                } else {
                    File fileCopy = new File(entry.getName(), entryDestinationPath);
                    destDir.addEntry(fileCopy);  
                }
            }
        } else {
            File fileCopy = new File(destinationName, destinationPath);
            currentDirectory.addEntry(fileCopy);
        }
    
        journal.addEntry(new JournalEntry(
                JournalEntry.OperationType.COPY_FILE,
                source.getPath(),
                destinationPath
        ));
    }
    
    public void listDirectory() {
        System.out.println("Conteúdo do diretório: " + currentDirectory.getPath());
        for (FileSystemEntry entry : currentDirectory.getEntries().values()) {
            String type = entry.isDirectory() ? "DIR" : "FILE";
            System.out.printf("%s\t%s\n", type, entry.getName());
        }
    }

    public void startShell() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Simulador de Sistema de Arquivos");
        System.out.println("Digite 'help' para ver os comandos disponíveis");

        while (true) {
            System.out.print(currentDirectory.getPath() + "> ");
            String command = scanner.nextLine().trim();

            if (command.equals("exit")) {
                break;
            }

            processCommand(command);
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split("\\s+");
        if (parts.length == 0) {
            return;
        }

        switch (parts[0]) {
            case "help":
                showHelp();
                break;
            case "ls":
                listDirectory();
                break;
            case "mkdir":
                if (parts.length > 1) {
                    createDirectory(parts[1]);
                }
                break;
            case "touch":
                if (parts.length > 1) {
                    createFile(parts[1]);
                }
                break;
            case "rm":
                if (parts.length > 1) {
                    deleteEntry(parts[1]);
                }
                break;
            case "mv":
                if (parts.length > 2) {
                    renameEntry(parts[1], parts[2]);
                }
                break;
            case "cp":
                if (parts.length > 2) {
                    copyEntry(parts[1], parts[2]);
                }
                break;
            case "cd":
                if (parts.length > 1) {
                    changeDirectory(parts[1]);
                }
                break;
            default:
                System.out.println("Comando desconhecido. Digite 'help' para ver os comandos disponíveis.");
        }
    }

    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("ls - Lista o conteúdo do diretório atual");
        System.out.println("mkdir <nome> - Cria um novo diretório");
        System.out.println("touch <nome> - Cria um novo arquivo");
        System.out.println("rm <nome> - Remove um arquivo ou diretório");
        System.out.println("mv <antigo> <novo> - Renomeia um arquivo ou diretório");
        System.out.println("cp <origem> <destino> - Copia um arquivo ou diretório");
        System.out.println("cd <caminho> - Muda o diretório atual");
        System.out.println("exit - Sai do simulador");
    }

    public static void main(String[] args) {
        FileSystemSimulator fs = new FileSystemSimulator("journal.txt");
        fs.startShell();
    }
}
