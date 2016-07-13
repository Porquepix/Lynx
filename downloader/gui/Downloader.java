package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class Downloader extends JFrame implements Observer {

	public static final int WIDTH = 450;
	public static final int HEIGHT = 150;
	
	private VersionList versions;
	private JButton launchButton;
	private JButton settingsButton;

	public Downloader() {
		
		try {
			UIManager.setLookAndFeel(
			        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		setTitle("Lynx Donwloader !");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		requestFocus();

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				actionExit();
			}
		});

		versions = new VersionList();
		JPanel versionsList = new JPanel();
		versionsList.setLayout(new BorderLayout());
		versionsList.add(new Label("Pick up a version: "), BorderLayout.NORTH);
		versionsList.add(versions, BorderLayout.SOUTH);		
		
		launchButton = new JButton("Launch");
		launchButton.setPreferredSize(new Dimension((int) (0.60 * WIDTH), 40));
		launchButton.requestFocusInWindow();
		launchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				settingsButton.setEnabled(false);
				launchButton.setEnabled(false);
				
				launchButton.setText("Launching");
				
				getContentPane().remove(versionsList);
				
				int size = 1457; // bytes
				JProgressBar progress = new JProgressBar(0, size);
				progress.setPreferredSize(new Dimension((int) (0.95 * Downloader.WIDTH), 30));
				
				JPanel data = new JPanel();
				JLabel file = new JLabel("Downloading: v1.0.0 alpha1");
				JLabel bytes = new JLabel("0 / " + size + " bytes");
				data.setLayout(new BorderLayout());
				data.add(file, BorderLayout.WEST);
				data.add(bytes, BorderLayout.EAST);
				
				JPanel newPanel = new JPanel();
				BorderLayout layout = new BorderLayout();
				layout.setVgap(5);
				newPanel.setLayout(layout);
				newPanel.add(data, BorderLayout.NORTH);
				newPanel.add(progress, BorderLayout.SOUTH);
				
				getContentPane().add(newPanel, BorderLayout.NORTH);
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						for (int i = 0; i < size; i++) {
							progress.setValue(i);
							bytes.setText(i + " / " + size + " bytes");
							i += Math.random() * 150;
							try {
								Thread.sleep(100);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						progress.setIndeterminate(true);
						bytes.setText("");
						file.setText("Extracting: v1.0.0 alpha1");
						
						try {
							Thread.sleep(2500);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						try {
							Runtime.getRuntime().exec("run.bat");
							actionExit();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
				
			}
		});
		
		settingsButton = new JButton("Settings");
		settingsButton.setPreferredSize(new Dimension((int) (0.30 * WIDTH), 40));
		settingsButton.setEnabled(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(launchButton, BorderLayout.WEST);
		buttonPanel.add(settingsButton, BorderLayout.EAST);

		BorderLayout mainLayout = new BorderLayout();
		mainLayout.setHgap(10);
		mainLayout.setVgap(10);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(mainLayout);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		setContentPane(mainPanel);
		getContentPane().add(versionsList, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		
		launchButton.requestFocus();
	}

	private void actionExit() {
		System.exit(0);
	}
	
	@Override
	public void update(Observable o, Object arg) {

	}

}
