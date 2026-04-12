package simplegui;

import java.io.*;

public class AuthManager {

    private static final String FILE_PATH = "accounts.txt";

    // ================= ENCRYPT =================
    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            result.append((char) (c + 3));
        }
        return result.toString();
    }

    // ================= DECRYPT =================
    public static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            result.append((char) (c - 3));
        }
        return result.toString();
    }

    // ================= REGISTER =================
    public static boolean register(String id, String username, String password) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {

            // check duplicate ID
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equals(id)) {
                    br.close();
                    return false; // ID already exists
                }
            }
            br.close();

            String encryptedPass = encrypt(password);
            fw.write(id + "|" + username + "|" + encryptedPass + "\n");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ================= LOGIN =================
    public static boolean login(String id, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length == 3) {
                    String savedID = data[0];
                    String savedPass = decrypt(data[2]);

                    if (savedID.equals(id) && savedPass.equals(password)) {
                        return true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}