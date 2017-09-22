package datacom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author zero639
 */
public class Client {

    static int pop(int num) {
        int bit = 0;

        while (num != 0) {
            if (num % 2 != 0) {
                bit++;
            }
            num /= 2;
        }

        return bit;
    }

    static int modify(int num) {
        int bit = pop(num);
        num = num << 1;
        if (bit % 2 != 0) {
            num = num | 0;
        } else {
            num = num | 1;
        }
        return num;
    }

    static String binary(int num) {
        String s = "";
        while (num != 0) {
            if (num % 2 != 0) {
                s += "1";
            } else {
                s += "0";
            }
            num /= 2;
        }

        return new StringBuilder(s).reverse().toString();
    }

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 5234);
        DataOutputStream is = new DataOutputStream(s.getOutputStream());

        BufferedReader br = new BufferedReader(new FileReader("in.txt"));
        String line;

        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            System.out.println(line);
            for (int i = 0; i < line.length(); i++) {

                char t = line.charAt(i);
                int temp = (int) t;
                //System.out.println(temp);
                temp = modify(temp);
                System.out.println(binary(temp));
                is.writeInt(Integer.parseInt(binary(temp)));

            }

            is.writeInt(Integer.parseInt(binary(modify(10))));
        }

        is.writeInt(-1);
        is.close();
        s.close();
    }

}

