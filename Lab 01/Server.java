package datacom;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author zero639
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(5234);
        Socket s = server.accept();
        DataInputStream is = new DataInputStream(s.getInputStream());
        FileWriter fout = new FileWriter("out.txt");
        while (true) {
            String input;
            int bit, fbit;

            bit = is.readInt();
            fbit = bit;
            if (bit == -1) {
                break;
            }
            System.out.println(bit);
            int no1 = 0;
            while (bit > 0) {
                no1 += bit % 10;
                bit /= 10;
            }
            if (no1 % 2 == 0) {
                System.out.print("error\n");
            } else {
                input = Integer.toString(fbit);
                int asci = Integer.parseInt(input, 2);
                asci = asci >> 1;
                if (asci == 10) {
                    fout.append(System.getProperty("line.separator"));
                } else {
                    char c = (char) asci;
                    System.out.println(c);
                    fout.append(c);
                }
            }

        }

        fout.close();
        is.close();
        s.close();

    }

}

