package example;

/**
 * @author Taha Kocer
 */
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class Main extends JFrame {
	private JPanel contentPane;
	private JComboBox comboBox_Adet;
	private JComboBox comboBox;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		Selenium selenium = new Selenium();
		
		// Günleri tutacak dizi
		Integer[] adet = new Integer[20];
		for (int i = 0; i < 20; i++) {
			adet[i] = i + 1;
		}
		Integer[] days = new Integer[31];
		for (int i = 0; i < 31; i++) {
			days[i] = i + 1;
		}
		//--------------------------icon--------------------------
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 425, 680);
		setTitle(" Auto-Visa ");
		ImageIcon icon = new ImageIcon("src/main/resources/icon.png");
		setIconImage(icon.getImage());

		//---------------------------MENU--------------------------
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnTools = new JMenu("Araçlar");
		menuBar.add(mnTools);

		JMenuItem itemSetPriority = new JMenuItem("Chrome'a öncelik ver (Set Priority)");
		itemSetPriority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Selenium.setPriority();

			}
		});
		mnTools.add(itemSetPriority);

		JMenuItem itemTaskKill = new JMenuItem("Tüm driverları durdur (TaskKill)");
		itemTaskKill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Selenium.taskKill();

			}
		});
		mnTools.add(itemTaskKill);

		JSeparator separator = new JSeparator();
		mnTools.add(separator);

		JMenuItem itemExit = new JMenuItem("Çıkış");
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		mnTools.add(itemExit);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setResizable(false);
		//--------------------------BAŞLIK----------------------------
		JLabel lblOtomatikVizeRandevu = new JLabel("Otomatik Vize Randevu Botu");
		lblOtomatikVizeRandevu.setOpaque(true);
		lblOtomatikVizeRandevu.setHorizontalTextPosition(SwingConstants.CENTER);
		lblOtomatikVizeRandevu.setHorizontalAlignment(SwingConstants.CENTER);
		lblOtomatikVizeRandevu.setForeground(Color.WHITE);
		lblOtomatikVizeRandevu.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblOtomatikVizeRandevu.setBackground(new Color(14, 16, 120));
		lblOtomatikVizeRandevu.setAlignmentX(0.5f);
		lblOtomatikVizeRandevu.setBounds(0, 0, 404, 91);
		contentPane.add(lblOtomatikVizeRandevu);
        
		//--------------------BİLGİLENDİRME LABELİ-------------------------
		final JLabel lblInfo = new JLabel(
				"<html> Browser'ı debugging mod'ta başlatmak için <b>\"Browser'ı Başlat\"</b> butonuna tıklayın.<br>(Standart kullandığınız browser'da çalışmaz.) </html>");
		lblInfo.setHorizontalTextPosition(SwingConstants.CENTER);
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setForeground(new Color(204, 0, 0));
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInfo.setBounds(32, 535, 352, 60);
		contentPane.add(lblInfo);
		//-----------------------COMBOBOXLAR---------------------------------
		comboBox = new JComboBox(days);
		comboBox.setBounds(196, 248, 100, 40);
		contentPane.add(comboBox);

		comboBox_Adet = new JComboBox(adet);
		comboBox_Adet.setForeground(new Color(0, 0, 0));
		comboBox_Adet.setBounds(196, 118, 100, 40);
		contentPane.add(comboBox_Adet);
		
		//----------------------BROWSERI BAŞLAT BUTONU-------------------------
		JButton btnStartBrowser = new JButton("BROWSER'I\r\n BAŞLAT");
		btnStartBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selenium.runBrowser();
				lblInfo.setText("<html>Çift Faktörlü Doğrulamayı geçiniz.</html>");
				Selenium.startDriver();
				Integer adetInteger = Integer.parseInt(comboBox_Adet.getSelectedItem().toString());
				Selenium.fillFirstPage(adetInteger);
			}
		});
		btnStartBrowser.setBounds(98, 171, 203, 50);
		contentPane.add(btnStartBrowser);

		//-------------------------BAŞLAT BUTONU-------------------------------
		JButton btnStart = new JButton("BAŞLAT");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String selectedDay = comboBox.getSelectedItem().toString();
				String count = comboBox_Adet.getSelectedItem().toString();
				Integer countInteger = Integer.valueOf(count);
				System.out.println(selectedDay);
	//			Selenium.fillAppointment(countInteger);
				try {
					Selenium.running = true;
					Selenium.startBot(selectedDay, lblInfo, countInteger);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStart.setBounds(209, 398, 90, 50);
		contentPane.add(btnStart);

		//---------------------------DURDUR BUTONU----------------------------
		JButton btnStop = new JButton("DURDUR");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Selenium.running = false;
				lblInfo.setText("Durduruldu.");
			}
		});
		btnStop.setBounds(98, 398, 90, 50);
		contentPane.add(btnStop);
		
		//---------------------BİR SONRAKİ AY CHECKBOXI------------------------
		final JCheckBox chckboxNextMonth = new JCheckBox("Bir Sonraki Ay");
		chckboxNextMonth.setFont(new Font("Tahoma", Font.PLAIN, 9));
		chckboxNextMonth.setBounds(196, 295, 103, 23);
		chckboxNextMonth.setSelected(false);
		chckboxNextMonth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Selenium.next = chckboxNextMonth.isSelected();
			}
		});
		contentPane.add(chckboxNextMonth);


		//----------------------------GÜN LABELİ---------------------------
		JLabel lblDay = new JLabel("Gün : ");
		lblDay.setHorizontalAlignment(SwingConstants.CENTER);
		lblDay.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblDay.setBounds(98, 245, 90, 40);
		contentPane.add(lblDay);
		
		//------------------------ADET LABELI---------------------------
		JLabel lblAdet = new JLabel("Adet : ");
		lblAdet.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdet.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAdet.setBounds(96, 115, 90, 40);
		contentPane.add(lblAdet);
		
		//---------------------OTOMATİK AL CHECKBOX-----------------
		final JCheckBox chckboxGetApp = new JCheckBox("Randevuyu Otomatik Al");
		chckboxGetApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selenium.autoGet = chckboxGetApp.isSelected();
				System.out.println("autoget : " + Selenium.autoGet);
			}
		});
		chckboxGetApp.setSelected(false);
		chckboxGetApp.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chckboxGetApp.setBounds(98, 335, 200, 23);
		contentPane.add(chckboxGetApp);
		
		ImageIcon icon1 = new ImageIcon("src/main/resources/info.png");
		Image scaledImage = icon1.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
		 ImageIcon scaledIcon = new ImageIcon(scaledImage);
		
		//-----------------------FORMU DOLDUR BUTONU----------------------------
		JButton btnFillApp = new JButton("Formu Doldur");
		btnFillApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Integer adet = Integer.parseInt(comboBox_Adet.getSelectedItem().toString());
				Selenium.fillAppointment(adet);
			}
		});
		btnFillApp.setBounds(140, 465, 120, 50);
		contentPane.add(btnFillApp);
		
		final JCheckBox chckboxGetForm = new JCheckBox("Formu Otomatik Doldur");
		chckboxGetForm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Selenium.autoFill = chckboxGetForm.isSelected();
				System.out.println("autoFill : " + Selenium.autoFill);				
			}
		});
		chckboxGetForm.setSelected(false);
		chckboxGetForm.setFont(new Font("Tahoma", Font.PLAIN, 10));
		chckboxGetForm.setBounds(98, 361, 200, 23);
		contentPane.add(chckboxGetForm);

	}
}
