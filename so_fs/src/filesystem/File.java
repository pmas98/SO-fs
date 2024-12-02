package filesystem;

public class File extends FileSystemEntry {

    private byte[] content;

    public File(String name, String path) {
        super(name, path);
        this.content = new byte[0];
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
        this.lastModifiedTime = System.currentTimeMillis();
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
