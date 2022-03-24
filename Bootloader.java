import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Bootloader extends JFrame {
	private Container con;
	private JTextField ip,port,secret;
	private JButton open;
	private JLabel ip_label,port_label,secret_lable;

	public Bootloader() {
		// TODO Auto-generated constructor stub
		super("打开客户端");
		setSize(300, 200);
		setLocation(200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		con=getContentPane();
		con.setLayout(null);
		secret=new JTextField("leafoct");
		ip=new JTextField();
		port=new JTextField("2333");
		open=new JButton("!建立通讯!");
		secret.setBounds(150, 80, 80, 30);
		ip.setBounds(50, 30, 200, 30);
		open.setBounds(50, 120, 150, 30);
		port.setBounds(50, 80, 50, 30);
		con.add(ip);
		con.add(open);
		con.add(port);
		con.add(secret);
		
		secret_lable=new JLabel("密钥");
		secret_lable.setBounds(120, 80, 30, 30);
		con.add(secret_lable);
		ip_label=new JLabel("对方IP");
		ip_label.setBounds(0, 30, 50, 30);
		con.add(ip_label);
		port_label=new JLabel("对方端口");
		port_label.setBounds(0, 80, 50, 30);
		con.add(port_label);
		setVisible(true);
		open.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Pattern.matches("\\d+\\.\\d+\\.\\d+\\.\\d+", ip.getText())&&!port.getText().equals("")) {
					dispose();
					try {
						MessageDigest md=MessageDigest.getInstance("SHA-256");
						md.update(secret.getText().getBytes("UTF-8"));
						Security.initial(md.digest());
					} catch (NoSuchAlgorithmException e1) {
						JOptionPane.showMessageDialog(null, "理论上不会发生的错误。不支持SHA-256哈希编码技术","为什么会弹这个？",JOptionPane.WARNING_MESSAGE);
					} catch (UnsupportedEncodingException e2) {
						JOptionPane.showMessageDialog(null, "理论上不会发生的错误。不支持UTF-8编码","为什么会弹这个？",JOptionPane.WARNING_MESSAGE);
					}
					Main m=new Main(ip.getText(), Integer.valueOf(port.getText()));
					m.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "ip有问题啊","TPC WARNING",JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Bootloader();
	}

}
