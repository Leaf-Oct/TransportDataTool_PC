import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Main extends JFrame{
	private InetAddress target;
	private JPanel contentPane; 
	private JLabel lblThisName; // show user name
	private JTextArea transformMessage; 
	private JScrollPane containTransforMessage,containTextReceive;
	private JScrollBar scroll_bar_of_containTextReceive;
	private JButton buttonSend; 
	private JTextArea textReceive; 
	private Container con;
	private DatagramSocket receive;
	private int port;
	public Main(String ip,int port) {
		// TODO Auto-generated constructor stub
		super("绝对安全的手机电脑文本信息传输工具");
		this.port=port;
		try {
			target = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("捏麻麻的这是什么ip");
			dispose();
			System.exit(0);
		}
		try {
			receive=new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("反思一下是不是端口被占用了");
			dispose();
			System.exit(0);
		}
		setSize(1024, 768);
		setLocation(100, 100);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		con = getContentPane();
		con.setLayout(null);
		setLayout();
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				receive.close();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		ReadMessageThread rmt = new ReadMessageThread(receive);
		rmt.start();
		List<String> allIP=GetAllLocal_IP_FromMachineInterface.getAllIPv4();
		textReceive.append("本机所有ipv4地址。看看另一方需要连接哪一个ip\n");
		for(String s:allIP) {
			textReceive.append(s);
		}
	}
	private void setLayout() {

		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBounds(0, 650, 1024, 68);
		con.add(contentPane);

		lblThisName = new JLabel("本机");
		lblThisName.setBounds(20, 0, 80, 68);
		contentPane.add(lblThisName);

		transformMessage = new JTextArea();
		containTransforMessage=new JScrollPane(transformMessage);
		containTransforMessage.setBounds(100, 0, 800, 68);
		contentPane.add(containTransforMessage);

		buttonSend = new JButton("Send");
		buttonSend.setBounds(900, 0, 100, 68);
		contentPane.add(buttonSend);

		textReceive = new JTextArea();
		containTextReceive=new JScrollPane(textReceive);
		containTextReceive.setBounds(10, 20, 984, 600);
		textReceive.setEditable(false);
		JPanel show_massage = new JPanel();
		show_massage.setLayout(null);
		show_massage.setBounds(10, 10, 1004, 630);
		show_massage.add(containTextReceive);
		show_massage.setBorder(BorderFactory.createTitledBorder("Chat Area"));
		con.add(show_massage);
		scroll_bar_of_containTextReceive=containTextReceive.getVerticalScrollBar();
		
		buttonSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				sendMessage(transformMessage.getText());
				transformMessage.setText("");
				scroll_bar_of_containTextReceive.setValue(scroll_bar_of_containTextReceive.getMaximum());
			}
		});
	}
	private void sendMessage(String s) {
		
		try {
			byte[] b=Security.encrypt(s);
			DatagramPacket datagramPacket=new DatagramPacket(b, b.length, target, port);
			receive.send(datagramPacket);
			System.out.println(new String(b));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		textReceive.append("/本机"+"\n"+s+"\n");
	}
	private class ReadMessageThread extends Thread {
		DatagramSocket receiver;

		public ReadMessageThread(DatagramSocket ds) {
			// TODO Auto-generated constructor stub
			receiver = ds;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			
			byte b[] = new byte[8192];
			DatagramPacket pack = new DatagramPacket(b, b.length);
			while(true) {
				try {
					if(receive.isClosed()) {
						break;
					}
					receiver.receive(pack);
					byte secret[]=Arrays.copyOf(b, pack.getLength());
//					System.out.println(pack.getLength());
//					System.out.println(secret.length);
					System.out.println(new String(secret));
					try {
						textReceive.append(pack.getAddress().toString()+"\n"+Security.decrypt(secret)+"\n");
					} catch (GeneralSecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (SocketException e) {
					// TODO: handle exception
					System.err.println("连接关闭");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
		}
	}



}
