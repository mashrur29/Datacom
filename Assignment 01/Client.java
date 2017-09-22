package datacom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;


public class Client {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 1234);
        DataOutputStream os = new DataOutputStream(s.getOutputStream());
        DataInputStream is = new DataInputStream(s.getInputStream());
        FileReader fin = new FileReader("in.txt");
        Scanner sc = new Scanner(fin);
        Random r = new Random();
        while (sc.hasNext()) {
            String str = sc.nextLine();
            int c[] = new int[str.length()];
            int x = 0;
            for (int i = 0; i < str.length(); i++) {
                c[i] = str.charAt(i);
                x += str.charAt(i);
            }

            while (true) {
                int y = r.nextInt();
                y%=100;
                if (y < 30) {
                    x++;
                }
                x %= 16;
                for (int i = 0; i < c.length; i++) {
                    os.writeInt(c[i]);
                }
                os.writeInt(-1);
                os.writeInt(x);

                String sig = is.readUTF();
                if (sig.equals("recieved")) {
                    break;
                } else {
                    System.err.println("error");
                }
            }

        }
        os.writeInt(-2);
        is.close();
        os.close();
        s.close();
    }
}
