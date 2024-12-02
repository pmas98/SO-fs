package filesystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Journal {

    private List<JournalEntry> entries;
    private String journalPath;

    public Journal(String journalPath) {
        this.entries = new ArrayList<>();
        this.journalPath = journalPath;
        loadJournal();
    }

    public void addEntry(JournalEntry entry) {
        entries.add(entry);
        saveJournal();
    }

    private void loadJournal() {
        java.io.File journalFile = new java.io.File(journalPath); 
        if (!journalFile.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(journalFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entries.add(JournalEntry.fromString(line));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar journal: " + e.getMessage());
        }
    }

    private void saveJournal() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(journalPath))) {
            for (JournalEntry entry : entries) {
                writer.write(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar journal: " + e.getMessage());
        }
    }
}
