package example.nz.org.take.compiler.userv.main;
/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import nz.org.take.rt.DerivationLogEntry;

/**
 * Utility to show the derivation log in a pop up window.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationLogViewer {

	public static void displayUsedRules(List<DerivationLogEntry> log,JComponent parentComponent) {
		
		if (log.size()==0) 
			JOptionPane.showMessageDialog(parentComponent, "There are no applicable rules", "", JOptionPane.WARNING_MESSAGE);
		else {
			DerivationModel treeModel = new DerivationModel(log);
			JTree tree = new JTree(treeModel);
			tree.setRootVisible(false);
			tree.setPreferredSize(new Dimension(700,400));
			final JDialog win = new JDialog();
			win.setResizable(true);
			JPanel pane = new JPanel();
			pane.setLayout(new BorderLayout(5,5));
			win.setContentPane(pane);
			pane.add(new JScrollPane(tree),BorderLayout.CENTER);
			JPanel bPane = new JPanel(new FlowLayout());
			JButton cButton = new JButton("close");
			cButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					win.dispose();
				}});
			bPane.add(cButton);
			pane.add(bPane,BorderLayout.SOUTH);
			win.setTitle("rules used");
			win.setSize(700,300);
			win.setLocation(100,100);
			win.setVisible(true);
		}
	}
	
}
