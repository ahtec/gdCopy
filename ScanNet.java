package gdcopy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.TreeMap;
import java.util.Vector;

public class ScanNet {

    public static Vector scan() {
        Vector outIPS = new Vector();
//        int[] bounds = ScanNet.rangeFromCidr("10.0.2.255/24");
        String iprange = ip();
        String myip = iprange;
        
        iprange = iprange.replace(".", ",");
        String[] werkarry = iprange.split(",");
        iprange = werkarry[0] + "." + werkarry[1] + "." + werkarry[2] + ".255/24";
        System.out.println("I shall scan this range "+iprange);

        int[] bounds = ScanNet.rangeFromCidr(iprange);

        for (int i = bounds[0]; i <= bounds[1]; i++) {
            String address = InetRange.intToIp(i);
            if ( address.compareTo(myip) == 0 )  continue;
            InetAddress ip = null;
            try {
                ip = InetAddress.getByName(address);
            } catch (UnknownHostException ex) {
//                Logger.getLogger(ScanNet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {

                if (ip.isReachable(100)) {
                    // Try for one tenth of a second
                    System.out.printf("Address %s is reachable\n", ip);
                    outIPS.add(ip.toString().replace("/", ""));
                }
            } catch (IOException ex) {
//                Logger.getLogger(ScanNet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return outIPS;
    }

    public static int[] rangeFromCidr(String cidrIp) {
        int maskStub = 1 << 31;
        String[] atoms = cidrIp.split("/");
        int mask = Integer.parseInt(atoms[1]);
//        System.out.println(mask);

        int[] result = new int[2];
        result[0] = InetRange.ipToInt(atoms[0]) & (maskStub >> (mask - 1)); // lower bound
        result[1] = InetRange.ipToInt(atoms[0]); // upper bound
//        System.out.println(InetRange.intToIp(result[0]));
//        System.out.println(InetRange.intToIp(result[1]));

        return result;
    }

    static class InetRange {

        public static int ipToInt(String ipAddress) {
            try {
                byte[] bytes = InetAddress.getByName(ipAddress).getAddress();
                int octet1 = (bytes[0] & 0xFF) << 24;
                int octet2 = (bytes[1] & 0xFF) << 16;
                int octet3 = (bytes[2] & 0xFF) << 8;
                int octet4 = bytes[3] & 0xFF;
                int address = octet1 | octet2 | octet3 | octet4;

                return address;
            } catch (Exception e) {
                e.printStackTrace();

                return 0;
            }
        }

        public static String intToIp(int ipAddress) {
            int octet1 = (ipAddress & 0xFF000000) >>> 24;
            int octet2 = (ipAddress & 0xFF0000) >>> 16;
            int octet3 = (ipAddress & 0xFF00) >>> 8;
            int octet4 = ipAddress & 0xFF;

            return new StringBuffer().append(octet1).append('.').append(octet2).append('.').append(octet3).append('.').append(octet4).toString();
        }
    }

    static String ip() //		throws Exception
    {
        String eruit = "";

//        InetAddress thisIp = null;
//        try {
//            thisIp = InetAddress.getLocalHost();
////             thisIp.
////            System.out.println("Your computers IP number is :" + thisIp.getHostAddress());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//



        try {
            Enumeration<NetworkInterface> e1 = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
//            System.out.println("your network configuration");

            while (e1.hasMoreElements()) {
                NetworkInterface ni = e1.nextElement();
                if (ni.isLoopback()) {
                    continue;
                }
                if (ni.isVirtual()) {
                    continue;
                }
                if (!ni.isUp()) {
                    continue;//                System.out.print(ni.getName());
//                System.out.print(" : [");
                }
                Enumeration<InetAddress> e2 = ni.getInetAddresses();
                while (e2.hasMoreElements()) {
                    InetAddress ia = e2.nextElement();
                    if (ia.toString().startsWith("/172.16")) {
                        eruit = ia.toString();

                    }
                    if (ia.toString().startsWith("/192.168")) {
                        eruit = ia.toString();
                    }

                    if (ia.toString().startsWith("/10.")) {
                        eruit = ia.toString();

                    }
                    if (e2.hasMoreElements()) {
//                        System.out.print(",");
                        }
                }
            }
//                System.out.println("]");

        } catch (SocketException ex) {
//            Logger.getLogger(gdServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("This should be your IP address: "+eruit);

        return eruit.replace("/", "");
    }
}
