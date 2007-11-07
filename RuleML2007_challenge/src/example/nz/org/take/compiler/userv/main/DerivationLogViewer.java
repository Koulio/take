/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */
 

package example.nz.org.take.compiler.userv.main;


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
import example.nz.org.take.compiler.userv.spec.UservRules;
import nz.org.take.rt.DerivationLogEntry;

/**
 * Utility to show the derivation log in a pop up window.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationLogViewer {

	public static void displayUsedRules(List<DerivationLogEntry> log,JComponent parentComponent,UservRules kb) {
		
		if (log.size()==0) 
			JOptionPane.showMessageDialog(parentComponent, "There are no applicable rules", "", JOptionPane.WARNING_MESSAGE);
		else {
			DerivationModel treeModel = new DerivationModel(log,kb);
			JTree tree = new JTree(treeModel);
			tree.setShowsRootHandles(true);
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
