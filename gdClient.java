/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gdcopy;

import java.net.*;
import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gdClient {

    public String ip;
    public String testIpAdres;
    public String eerste3delenIp = "";
    public String laatsteDeelIP = "";
    public boolean gevonden = false;

    public void gdClientMetIP(String ipadres) {
        ScanNet sn = new ScanNet();
        @SuppressWarnings("static-access")
        Vector ips = sn.scan();
//        ip = whatsMyIp();
//        ip = ();
        init();
        testIpAdres = ipadres;
//        for (int ipverschil = 0; ipverschil < 255; ipverschil++) {
//            if (tryIpPort(ipverschil)) {
//                getFile(ipverschil);
//                break;
//            }
//            if (tryIpPort(ipverschil * -1)) {
//                getFile(ipverschil * -1);
//                break;
//            }
//        }
        func();
    }

    public void gdClientZoekIP() {
        ScanNet sn = new ScanNet();
        Vector ips = sn.scan();
        int bMax = 0;
        bMax = ips.size();
        for (int a = bMax - 1; a >= 0 && !gevonden ; a--) {
          testIpAdres = (String)ips.get(a) ;
          System.out.println("Checking if sending is from here: "+testIpAdres);
          func();
            
            
        }


    }

    private boolean tryIpPort(int ipverschil) {
        int reken = Integer.parseInt(laatsteDeelIP) + ipverschil;
        if (reken < 2 | reken > 224) {
            return false;
        }
        testIpAdres = eerste3delenIp + reken;

//        System.out.println(testIpAdres);

//        throw new UnsupportedOperationException("Not yet implemented");
        return true;
    }

    private void getFile(int ipverschil) {
//        throw new UnsupportedOperationException("Not yet implemented");
        func();
    }

    void func() {
        boolean nietMeerOphogen = false;
        boolean nietMeerVerlagen = false;
        boolean moetIkOphogen = false;
        int opafaantal = 1;



//        while (!gevonden || (nietMeerOphogen && nietMeerVerlagen)) {

        try {
            Socket s1 = new Socket(testIpAdres, 5432);
            int in = 0;
            InputStream is = s1.getInputStream();
//            FileOutputStream fos = new FileOutputStream(new File(args[1]));
            String naamFile = new String();
            String eonMarker = "";

            int arrayInt[] = new int[1024];
            int i = 0;
//            while ((in = is.read()) != -1) {
//                arrayInt[i++] = in;
//            }
            for (i = 0; i < arrayInt.length; i++) {
                in = is.read();
                arrayInt[i] = in;
//                System.out.println(in);
                char c = (char) in;
//                System.out.println(c);
                naamFile = naamFile + c;
                eonMarker = naamFile;
                if (eonMarker.endsWith("XeindeFileNaamX")) {
                    naamFile = naamFile.substring(0, naamFile.length() - 15);
                    i = arrayInt.length;
                }
//                naamFile = naamFile.concat(naamFile) + new String(intToByteArray(arrayInt[i]));
            }

//            System.out.println(naamFile);
            naamFile = naamFile.trim();
            System.out.println("Yes, starting to transmit: " + naamFile);
            File outFile = new File(naamFile);


//            File outFile = new File("naamFile");
//            FileOutputStream fos = new FileOutputStream(new File(naamFile));
            FileOutputStream fos = new FileOutputStream(outFile);
            while ((in = is.read()) != -1) {
                fos.write(in);
            }

            fos.close();
            is.close();
            s1.close();
            System.out.println(" done");
            gevonden = true;
        } catch (java.net.ConnectException cex) {//                opafaantal++;

//                if (!nietMeerOphogen) {
//                    int reken = Integer.parseInt(laatsteDeelIP) + opafaantal;
//                    testIpAdres = eerste3delenIp + reken;
//                    if (reken >= 225) {
//                        nietMeerOphogen = true;
//                    }
//                    if (nietMeerVerlagen) {
//                        moetIkOphogen = true;
//                    } else {
//                        moetIkOphogen = false;
//                    }
//
//                }
//
//                if (!nietMeerVerlagen) {
//                    int reken = Integer.parseInt(laatsteDeelIP) - opafaantal;
//                    testIpAdres = eerste3delenIp + reken;
//
//                    if (reken <= 2) {
//                        nietMeerVerlagen = true;
//                    }
//                    if (nietMeerOphogen) {
//                        moetIkOphogen = false;
//                    } else {
//                        moetIkOphogen = true;
//                    }
//                }
//
        } catch (IOException ex) {
            Logger.getLogger(gdServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    /*    public static byte[] intToByteArray(int value) {
    //        byte[] b = new byte[4];
    byte[] b = new byte[2];
    //        for (int i = 0; i < 4; i++) {
    for (int i = 0; i < 2; i++) {
    int offset = (b.length - 1 - i) * 8;
    b[i] = (byte) ((value >>> offset) & 0xFF);
    }
    return b;
    }
     */
    String whatsMyIp() //		throws Exception
    {
        String eruit = "";
        try {
            InetAddress thisIp = InetAddress.getLocalHost();
            eruit = thisIp.getHostAddress();
//            System.out.println("Your computers IP number is :" + thisIp.getHostAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eruit;
    }

    private void init() {
////        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}