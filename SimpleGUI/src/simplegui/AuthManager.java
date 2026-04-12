package simplegui;

import java.io.*;

public class AuthManager {

    private static final String FILE_PATH = "accounts.txt";

    public static boolean register(String id, String username, String password) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");
                if (data[0].equals(id)) {
                    br.close();
                    return false;
                }
            }
            br.close();

            FileWriter fw = new FileWriter(FILE_PATH, true);
            fw.write(id + "|" + username + "|" + CaesarCipher.encrypt(password) + "\n");
            fw.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static boolean login(String id, String password) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\\|");

                if (data.length == 3) {
                    String savedID = data[0];
                    String savedPass = CaesarCipher.decrypt(data[2]);

                    if (savedID.equals(id) && savedPass.equals(password)) {
                        br.close();
                        return true;
                    }
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}