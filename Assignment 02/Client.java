package datacom;

import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
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

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {

        FileWriter fout[] = new FileWriter[5];
        fout[0] = new FileWriter("out1.txt");
        fout[1] = new FileWriter("out2.txt");
        fout[2] = new FileWriter("out3.txt");
        fout[3] = new FileWriter("out4.txt");
        fout[4] = new FileWriter("out5.txt");

        Socket s = new Socket("localhost", 2234);
        DataInputStream is = new DataInputStream(s.getInputStream());
        String in = "";
        while (true) {
            in = is.readUTF();
            if (in.equals("00000")) {
                break;
            }
            Frame fr = new Frame(in);
            System.out.println(fr.toString());
            slot sl[] = new slot[5];
            sl[0] = fr.sl[0];
            sl[1] = fr.sl[1];
            sl[2] = fr.sl[2];
            sl[3] = fr.sl[3];
            sl[4] = fr.sl[4];
            for (int i = 0; i < 5; i++) {

                if (sl[i].data.contains("~")) {
                    int ind = sl[i].data.indexOf("~");
                    fout[sl[i].dst - 1].append(sl[i].data.substring(0, ind - 1));
                    fout[sl[i].dst - 1].append(System.getProperty("line.separator"));
                    fout[sl[i].dst - 1].append(sl[i].data.substring(ind + 1, sl[i].data.length()));
                } else {
                    fout[sl[i].dst - 1].append(sl[i].data);
                }

            }

        }
        is.close();
        s.close();
        fout[0].close();
        fout[1].close();
        fout[2].close();
        fout[3].close();
        fout[4].close();
    }

}

