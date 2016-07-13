package gui;

import java.awt.Dimension;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class VersionList extends JComboBox<String> {

	public VersionList() {
		super(new String[] {"1.0.0 alpha1", "1.0.0 alpha2", "1.1.14"});
		setPreferredSize(new Dimension((int) (0.95 * Downloader.WIDTH), 30));
	}
	
}


