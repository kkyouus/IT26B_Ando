package simplegui;

import java.io.*;
import java.util.ArrayList;

public class PlayerManager {

    private static final String FILE_PATH = "players.txt";

    // =========================
    // SAVE PLAYERS TO FILE
    // =========================
    public static void savePlayers(ArrayList<String[]> players) {
        try (FileWriter fw = new FileWriter(FILE_PATH)) {

            for (String[] p : players) {
                fw.write(String.join("|", p) + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // LOAD PLAYERS FROM FILE
    // =========================
    public static ArrayList<String[]> loadPlayers() {
        ArrayList<String[]> players = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                players.add(line.split("\\|"));
            }

        } catch (Exception e) {
            // ignore if file empty
        }

        return players;
    }
}