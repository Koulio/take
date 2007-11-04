/*
 * Copyright 2007 Jens Dietrich 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
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
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Executable UServ application. Starts a user interface to interact with the rules.
 * Static compilation is used, i.e. the rules are compiled at design time.
 * This could be done at runtime as well, see take test cases for an example.
 * http://www.businessrulesforum.com/2005_Product_Derby.pdf 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Main extends JFrame {

	public static void main(String[] args) {
		Main app = new Main();
		app.setVisible(true);
	}

	public Main() throws HeadlessException {
		super();
		init();
	}

	public Main(GraphicsConfiguration gc) {
		super(gc);
		init();
	}

	public Main(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}

	public Main(String title) throws HeadlessException {
		super(title);
		init();
	}
	
	private void init() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("UServ");
		JPanel pane = new JPanel(new BorderLayout(5,5));
		JToolBar toolbar = new JToolBar();
		pane.add(toolbar,BorderLayout.NORTH);
		toolbar.setFloatable(false);
		toolbar.add(new AbstractAction("exit"){
			public void actionPerformed(ActionEvent e) {
				Main.this.dispose();
			}});
		toolbar.addSeparator();
		/**
		toolbar.add(new AbstractAction("validate rules"){
			public void actionPerformed(ActionEvent e) {
				validateRules();
			}});
		**/
		toolbar.add(new AbstractAction("show rules"){
			public void actionPerformed(ActionEvent e) {
				ScriptViewer.showScript();
			}});
		toolbar.addSeparator();
		toolbar.add(new AbstractAction("about"){
			public void actionPerformed(ActionEvent e) {
				about();
			}});
		
		
		JPanel butPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel lab = new JLabel("UServ Product Derby Case Study is Copyright Business Rules Forum (http://www.businessrulesforum.com/)");
		lab.setHorizontalAlignment(JLabel.RIGHT);
		butPane.add(lab);
		pane.add(butPane,BorderLayout.SOUTH);
		
		pane.add(new UServPanel(),BorderLayout.CENTER);
		
		setContentPane(pane);
		int W=800,H=700;
		setSize(W,H);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screen.width-W)/2,(screen.height-H)/2);
	}
	/** removed - requires signed lib to run with webstart
	private void validateRules() {
		try {
			TestSuite suite = new TestSuite();
			suite.addTest(new UservTestCases());
			junit.swingui.TestRunner r = new junit.swingui.TestRunner() {
				public void terminate() {
					this.fFrame.setDefaultCloseOperation(fFrame.DISPOSE_ON_CLOSE);
					this.fFrame.dispose();
				}
			};
			r.start(new String[]{UservTestCases.class.getName()});
		}
		catch (SecurityException x) {
			JOptionPane.showMessageDialog(this,"Validation uses the JUnit library that interacts with the local file system.\nFor security reasons, this function is only available in offline mode");
		}
	}
	*/
	
	private void about() {
		String about = 	"<html>"+
						"UServ Implementation based on take. See <tt>http://code.google.com/p/take</tt> for details.<br>" + 
						"&copy; Jens Dietrich, Massey University (<tt>http://www-ist.massey.ac.nz/jbdietrich</tt>) 2007. (software)<br>" + 
						"&copy; Business Rules Forum (<tt>http://www.businessrulesforum.com</tt>) 2005. (UServ Product Derby Case Study)<br>" + 
						"The software is licensed under the GNU Lesser General Public License (LGPL) version 2" + 
						"</html>";
		JOptionPane.showMessageDialog(this,about,"About UServ",JOptionPane.INFORMATION_MESSAGE);
	}
	
}
