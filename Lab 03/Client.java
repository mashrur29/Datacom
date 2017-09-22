package datacom;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author zero639
 */
public class Client {

    static int vtod(int d[], int c[]) {
        int ret = 0;
        for (int i = 0; i < d.length; i++) {
            ret += d[i] * c[i];
        }
        ret /= 4;
        return ret;
    }

    public static void main(String[] args) throws IOException {
        FileWriter fout[] = new FileWriter[3];
        fout[0] = new FileWriter("out1.txt");
        fout[1] = new FileWriter("out2.txt");
        fout[2] = new FileWriter("out3.txt");

        Socket s = new Socket("localhost", 1234);
        DataInputStream din = new DataInputStream(s.getInputStream());

        int code1[] = {1, -1, 1, -1};
        int code2[] = {1, 1, -1, -1};   
        int code3[] = {1, 1, 1, 1};

        String b1 = "", b2 = "", b3 = "";

        int data[] = new int[4];
        int i = 0;

        while (true) {
            int x = din.readInt();
            if (x == -10) {
                break;
            }
            data[i] = x;
            int d1, d2, d3;
            i++;
            if (i == 4) {
                i = 0;
                d1 = Client.vtod(data, code1);
                d2 = Client.vtod(data, code2);
                d3 = Client.vtod(data, code3);
                if (d1 == 1) {
                    d1 = 0;
                } else {
                    d1 = 1;
                }
                if (d2 == 1) {
                    d2 = 0;
                } else {
                    d2 = 1;
                }
                if (d3 == 1) {
                    d3 = 0;
                } else {
                    d3 = 1;
                }

                b1 += Integer.toString(d1);
                b2 += Integer.toString(d2);
                b3 += Integer.toString(d3);
            }
        }
        String bt1 = "", bt2 = "", bt3 = "";
        for (i = 0; i < b1.length();) {
            bt1 += b1.charAt(i);
            bt2 += b2.charAt(i);
            bt3 += b3.charAt(i);
            i++;
            if (i % 8 == 0) {
                System.out.println(bt1);
                System.out.println(bt2);
                System.out.println(bt3);
                char f1 = (char) Integer.parseInt(bt1, 2);
                char f2 = (char) Integer.parseInt(bt2, 2);
                char f3 = (char) Integer.parseInt(bt3, 2);
                System.out.println(f1 + " " + f2 + " " + f3);
                System.out.println();

                if ((int) f1 == 12) {
                    fout[0].append(System.getProperty("line.separator"));
                } else if ((int) f1 != 0) {
                    fout[0].append(f1);
                }

                if ((int) f2 == 12) {
                    fout[1].append(System.getProperty("line.separator"));
                } else if ((int) f2 != 0) {
                    fout[1].append(f2);
                }

                if ((int) f3 == 12) {
                    fout[2].append(System.getProperty("line.separator"));
                } else if ((int) f3 != 0) {
                    fout[2].append(f3);
                }
                bt1 = "";
                bt2 = "";
                bt3 = "";
            }
        }

        fout[0].close();
        fout[1].close();
        fout[2].close();

        din.close();
        s.close();
    }
}
