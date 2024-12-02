package filesystem;

public abstract class FileSystemEntry {

    protected String name;
    protected String path;
    protected long creationTime;
    protected long lastModifiedTime;

    public FileSystemEntry(String name, String path) {
        this.name = name;
        this.path = path;
        this.creationTime = System.currentTimeMillis();
        this.lastModifiedTime = this.creationTime;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setName(String name) {
        this.name = name;
        this.lastModifiedTime = System.currentTimeMillis();
    }

    public abstract boolean isDirectory();
}
