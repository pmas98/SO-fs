package filesystem;

import java.util.HashMap;
import java.util.Map;

public class Directory extends FileSystemEntry {

    private Map<String, FileSystemEntry> entries;

    public Directory(String name, String path) {
        super(name, path);
        this.entries = new HashMap<>();
    }

    public Map<String, FileSystemEntry> getEntries() {
        return entries;
    }

    public void addEntry(FileSystemEntry entry) {
        entries.put(entry.getName(), entry);
    }

    public void removeEntry(String name) {
        entries.remove(name);
    }

    public Directory getDirectoryByPath(String path) {
        if (this.getPath().equals(path)) {
            return this;
        }

        for (FileSystemEntry entry : this.getEntries().values()) {
            if (entry.isDirectory()) {
                Directory subDir = (Directory) entry;
                Directory found = subDir.getDirectoryByPath(path);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
