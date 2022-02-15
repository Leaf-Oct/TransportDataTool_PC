import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JOptionPane;

public class GetAllLocal_IP_FromMachineInterface {
//	这段代码引用自 https://segmentfault.com/a/1190000007462741
//	感谢前辈赐教
	public static void main() throws Exception {
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
        
        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();
        
            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
        
                if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
//                    System.out.println("网卡接口名称：" + nif.getName());
//                    System.out.println("网卡接口地址：" + addr.getHostAddress());
//                    System.out.println();
                	allIP.add(addr.getHostAddress()+'\n');
                }
            }
        }
	}
	private static List<String> allIP;
	public static List<String> getAllIPv4(){
		allIP=new ArrayList<>();
		try {
			main();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取本机ip出现异常","TPC WARNING",JOptionPane.WARNING_MESSAGE);
		}
		return allIP;
	}
}
