package filesystem;

import java.io.Serializable;

public class JournalEntry implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum OperationType {
        CREATE_FILE,
        DELETE_FILE,
        RENAME_FILE,
        CREATE_DIR,
        DELETE_DIR,
        RENAME_DIR,
        COPY_FILE,
        CHANGE_DIR
    }

    private OperationType operation;
    private String sourcePath;
    private String targetPath;
    private long timestamp;

    public JournalEntry(OperationType operation, String sourcePath, String targetPath) {
        this.operation = operation;
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("[%d] %s: %s -> %s", 
            timestamp, operation, sourcePath, targetPath);
    }

    public static JournalEntry fromString(String line) {
        String[] parts = line.split(" ", 3); 
    
        if (parts.length < 3) {
            throw new IllegalArgumentException("Formato de entrada inválido no journal.");
        }
    
        long timestamp = Long.parseLong(parts[0].substring(1, parts[0].length() - 1));
        
        OperationType operation = OperationType.valueOf(parts[1].replace(":", ""));
        
        String[] pathParts = parts[2].split("->");
        if (pathParts.length != 2) {
            throw new IllegalArgumentException("Formato de paths inválido no journal.");
        }
        String sourcePath = pathParts[0].trim();
        String targetPath = pathParts[1].trim();
    
        return new JournalEntry(operation, sourcePath, targetPath);
    }
    
}
