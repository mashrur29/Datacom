package datacom;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author zero639
 */
public class Server {

    static String tobit(int ascii) {
        String ret = "";
        while (ascii > 0) {
            int mod = ascii % 2;
            ret = Integer.toString(mod) + ret;
            ascii /= 2;
        }
        while (ret.length() < 8) {
            ret = "0" + ret;
        }
        return ret;

    }

    static String datatooutput(char c11, char c12, int code) {
        String bit = c11 + "" + c11 + "" + c11 + "" + c11 + "";

        bit = bit + c12 + "" + c12 + "" + c12 + "" + c12 + "";

        int ret = Integer.parseInt(bit, 2);
        ret = ret ^ code;

        bit = "";
        while (ret > 0) {
            int mod = ret % 2;
            bit = Integer.toString(mod) + bit;
            ret /= 2;
        }
        while (bit.length() < 8) {
            bit = "0" + bit;
        }
        return bit;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ServerSocket s1 = new ServerSocket(1234);
        Socket s = s1.accept();
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        Scanner sc1 = new Scanner(new FileReader("in1.txt"));
        Scanner sc2 = new Scanner(new FileReader("in2.txt"));
        Scanner sc3 = new Scanner(new FileReader("in3.txt"));
        
        while (sc1.hasNext() || sc2.hasNext() || sc3.hasNext()) {
            String in1 = "", in2 = "", in3 = "";
            if (sc1.hasNext()) {
                in1 += sc1.nextLine();
            }
            if (sc2.hasNext()) {
                in2 += sc2.nextLine();
            }
            if (sc3.hasNext()) {
                in3 += sc3.nextLine();
            }

            String bn1 = "", bn2 = "", bn3 = "";

            for (int i = 0; i < in1.length(); i++) {
                int acsii = (int) in1.charAt(i);
                bn1 += Server.tobit(acsii);
            }
            for (int i = 0; i < in2.length(); i++) {
                int acsii = (int) in2.charAt(i);
                bn2 += Server.tobit(acsii);
            }
            for (int i = 0; i < in3.length(); i++) {
                int acsii = (int) in3.charAt(i);
                bn3 += Server.tobit(acsii);
            }
            bn1 += Server.tobit(12);
            bn2 += Server.tobit(12);
            bn3 += Server.tobit(12);
            int maxl = 0;
            if (maxl < bn1.length()) {
                maxl = bn1.length();
            }
            if (maxl < bn2.length()) {
                maxl = bn2.length();
            }
            if (maxl < bn3.length()) {
                maxl = bn3.length();
            }

            while (bn1.length() < maxl || bn2.length() < maxl || bn3.length() < maxl) {
                if (bn1.length() < maxl) {
                    bn1 += "0";
                }
                if (bn2.length() < maxl) {
                    bn2 += "0";
                }
                if (bn3.length() < maxl) {
                    bn3 += "0";
                }
            }

            System.out.println(bn1 + " \n" + bn2 + " \n" + bn3);

            int code1 = Integer.parseInt("01010101", 2);
            int code2 = Integer.parseInt("00110011", 2);
            int code3 = Integer.parseInt("00000000", 2);

            int b1 = 0, b2 = 0, b3 = 0;

            while (b1 < maxl - 1) {
                char c11 = bn1.charAt(b1);
                b1++;
                char c12 = bn1.charAt(b1);
                b1++;
                String datas1 = Server.datatooutput(c11, c12, code1);

                char c21 = bn2.charAt(b2);
                b2++;
                char c22 = bn2.charAt(b2);
                b2++;

                String datas2 = Server.datatooutput(c21, c22, code2);

                char c31 = bn3.charAt(b3);
                b3++;
                char c32 = bn3.charAt(b3);
                b3++;

                String datas3 = Server.datatooutput(c31, c32, code3);

                int data[] = new int[8];
                for (int i = 0; i < data.length; i++) {
                    data[i] = 0;
                    if (datas1.charAt(i) == '0') {
                        data[i]++;
                    } else {
                        data[i]--;
                    }

                    if (datas2.charAt(i) == '0') {
                        data[i]++;
                    } else {
                        data[i]--;
                    }

                    if (datas3.charAt(i) == '0') {
                        data[i]++;
                    } else {
                        data[i]--;
                    }
                    System.out.print(data[i] + " ");
                    dout.writeInt(data[i]);
                }
                System.out.println();

            }
        }

        dout.writeInt(-10);
        dout.close();
        s.close();

    }

}
