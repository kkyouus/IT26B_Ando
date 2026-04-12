package simplegui;

import java.io.*;

public class AuthManager {
    
    private static final String FILE_PATH = "accounts.txt";

    // ================================
    // CAESAR CIPHER (ENCRYPT)
    // ================================
    public static String encrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            result.append((char) (c + 3));
        }

        return result.toString();
    }

    // ================================
    // CAESAR CIPHER (DECRYPT)
    // ================================
    public static String decrypt(String text) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            result.append((char) (c - 3));
        }

        return result.toString();
    }

    // ================================
    // REGISTER ACCOUNT
    // ================================
    public static boolean register(String id, String username, String password) {

        // ❗ check empty
        if (id.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        // ❗ check duplicate ID
        if (isIDExist(id)) {
            return false;
        }

        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {
            String encryptedPass = encrypt(password);
            fw.write(id + "|" + username + "|" + encryptedPass + "\n");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================================
    // LOGIN CHECK
    // ================================
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

    // ================================
    // CHECK IF ID EXISTS
    // ================================
    private static boolean isIDExist(String id) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data[0].equals(id)) {
                    return true;
                }
            }

        } catch (Exception e) {
            // ignore if file not exist yet
        }

        return false;
    }
}