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
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		org.apache.log4j.BasicConfigurator.configure();
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
		JPanel pane = new JPanel(new BorderLayout(5,5));
		JToolBar toolbar = new JToolBar();
		pane.add(toolbar,BorderLayout.NORTH);
		toolbar.setFloatable(false);
		toolbar.add(new AbstractAction("exit"){
			public void actionPerformed(ActionEvent e) {
				Main.this.dispose();
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
	
}
