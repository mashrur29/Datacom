package datacom;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author zero639
 */
public class Server {

    public static void main(String args[]) throws IOException {
        ServerSocket sc = new ServerSocket(2234);
        Socket s = sc.accept();
        DataOutputStream os = new DataOutputStream(s.getOutputStream());
        DataInputStream is = new DataInputStream(s.getInputStream());
        FileWriter fout = new FileWriter("out.txt");
        int mod = 0;
        String str = "";
        
        while (true) {
            int x = is.readInt();
            if (x == -1) {
                mod %= 16;
                int ans = is.readInt();
                if (mod == ans) {
                    System.out.println("OK");
                    fout.append(str);
                    fout.append(System.getProperty("line.separator"));
                    str = "";
                    os.writeUTF("recieved");
                } else {
                    os.writeUTF("error");
                    str = "";
                    System.out.println("error");
                }
                mod = 0;
            } else if (x == -2) {
                break;
            } else {
                mod += x;
                str = str + (char) x;
            }

        }

        fout.close();
        is.close();
        os.close();
        s.close();

    }
}

