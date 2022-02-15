import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Bootloader extends JFrame {
	private Container con;
	private JTextField ip,port;
	private JButton open;
	private JLabel ip_label,port_label;

	public Bootloader() {
		// TODO Auto-generated constructor stub
		super("打开客户端");
		setSize(300, 200);
		setLocation(200, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		con=getContentPane();
		con.setLayout(null);
		
		ip=new JTextField();
		port=new JTextField("2333");
		open=new JButton("!建立通讯!");
		ip.setBounds(50, 30, 200, 30);
		open.setBounds(50, 120, 150, 30);
		port.setBounds(50, 80, 200, 30);
		con.add(ip);
		con.add(open);
		con.add(port);

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
				if(Pattern.matches("192\\.168\\.\\d+\\.\\d+", ip.getText())&&!port.getText().equals("")) {
					dispose();
					Main m=new Main(ip.getText(), Integer.valueOf(port.getText()));
					m.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(null, "有问题啊","TPC WARNING",JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Bootloader();
	}

}
