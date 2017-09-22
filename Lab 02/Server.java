package datacom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 *
 * @author zero639
 */
class slot {

    int src, dst;
    String data;

    public slot(int src, int dst, String data) {
        this.src = src;
        this.dst = dst;
        this.data = data;
    }

    public slot(String str) {
        StringTokenizer stk = new StringTokenizer(str, "-");
        String st = stk.nextToken();
        st = stk.nextToken();
        src = Integer.parseInt(st);
        st = stk.nextToken();
        dst = Integer.parseInt(st);
        st = stk.nextToken();
        data = st;
    }

    public String toString() {
        return "[-" + src + "-" + dst + "-" + data + "-]";
    }

}

class Frame {

    slot sl[];

    public Frame(slot sl[]) {
        this.sl = sl;
    }

    public Frame(String str) {
        slot sltemp[] = new slot[5];
        int ind = 0;
        StringTokenizer stk = new StringTokenizer(str, "&");
        String strtemp = stk.nextToken();
        while (true) {
            strtemp = stk.nextToken();
            if (strtemp.equals("@")) {
                break;
            } else {
                sltemp[ind] = new slot(strtemp);
                ind++;
            }

        }
        sl = new slot[ind];
        for (int i = 0; i < ind; i++) {
            sl[i] = sltemp[i];

        }
    }

    public String toString() {
        String ret = "#";
        for (int i = 0; i < sl.length; i++) {
            ret = ret + "&" + sl[i].toString();
        }
        ret = ret + "&" + "@";
        return ret;
    }

}

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(2234);
        Socket s = server.accept();
        DataOutputStream os = new DataOutputStream(s.getOutputStream());

        String str1 = "", str2 = "", str3 = "", str4 = "", str5 = "";
        int ind = 1;
        BufferedReader br1 = new BufferedReader(new FileReader("in1.txt"));
        BufferedReader br2 = new BufferedReader(new FileReader("in2.txt"));
        BufferedReader br3 = new BufferedReader(new FileReader("in3.txt"));
        BufferedReader br4 = new BufferedReader(new FileReader("in4.txt"));
        BufferedReader br5 = new BufferedReader(new FileReader("in5.txt"));

        String ln1, ln2, ln3, ln4, ln5;
        int pnt1 = 0, pnt2 = 0, pnt3 = 0, pnt5 = 0, pnt4 = 0;

        while ((ln1 = br1.readLine()) != null) {
            str1 += ln1;
            str1 += "~";
        }

        while ((ln2 = br2.readLine()) != null) {
            str2 += ln2;
            str2 += "~";
        }

        while ((ln3 = br3.readLine()) != null) {
            str3 += ln3;
            str3 += "~";
        }

        while ((ln4 = br4.readLine()) != null) {
            str4 += ln4;
            str4 += "~";
        }

        while ((ln5 = br5.readLine()) != null) {
            str5 += ln5;
            str5 += "~";
        }

        while (pnt1 < str1.length() || pnt2 < str2.length() || pnt3 < str3.length() || pnt4 < str4.length() || pnt5 < str5.length()) {
            slot s1, s2, s3, s4, s5;
            String s_1 = "", s_2 = "", s_3 = "", s_4 = "", s_5 = "";
            int lim1 = pnt1 + 10, lim2 = pnt2 + 10, lim3 = pnt3 + 10, lim4 = pnt4 + 10, lim5 = pnt5 + 10;

            for (int i = pnt1; i < Math.min(lim1, str1.length()); i++, pnt1++) {
                s_1 += str1.charAt(i);
            }

            for (int i = pnt2; i < Math.min(lim2, str2.length()); i++, pnt2++) {
                s_2 += str2.charAt(i);
            }

            for (int i = pnt3; i < Math.min(lim3, str3.length()); i++, pnt3++) {
                s_3 += str3.charAt(i);
            }

            for (int i = pnt4; i < Math.min(lim4, str4.length()); i++, pnt4++) {
                s_4 += str4.charAt(i);
            }

            for (int i = pnt5; i < Math.min(lim5, str5.length()); i++, pnt5++) {
                s_5 += str5.charAt(i);
            }
            
            slot sl[] = new slot[5];
            sl[0] = new slot(1, 1, s_1);
            sl[1] = new slot(2, 2, s_2);
            sl[2] = new slot(3, 3, s_3);
            sl[3] = new slot(4, 4, s_4);
            sl[4] = new slot(5, 5, s_5);
            Frame f = new Frame(sl);
            System.out.println("Frame " + ind + "is: " + f.toString());
            os.writeUTF(f.toString());
            ind++;
        }

        os.writeUTF("00000");
        os.close();
        s.close();
    }
}

