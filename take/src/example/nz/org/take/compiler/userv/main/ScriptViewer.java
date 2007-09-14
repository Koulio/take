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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * Script viewer. Contains some basic filtering and syntax highlighting.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ScriptViewer extends JFrame {

	static String[] KEYWORDS = {"var","ref","if","then","and","query","not","aggregation"};
	static SimpleAttributeSet PLAIN = new SimpleAttributeSet();
	static SimpleAttributeSet KEYWORD = new SimpleAttributeSet();
	static SimpleAttributeSet ID = new SimpleAttributeSet();
	static SimpleAttributeSet ANNOTATION = new SimpleAttributeSet();
	static SimpleAttributeSet COMMENT = new SimpleAttributeSet();
	  
  	static {
		    StyleConstants.setForeground(PLAIN, Color.black);
		    StyleConstants.setFontFamily(PLAIN, "Helvetica");
		    StyleConstants.setFontSize(PLAIN, 14);
		    
		    StyleConstants.setForeground(ANNOTATION, new Color(60,160,90));
		    StyleConstants.setItalic(ANNOTATION, true);
		    StyleConstants.setFontFamily(ANNOTATION, "Helvetica");
		    StyleConstants.setFontSize(ANNOTATION, 14);
		    
		    StyleConstants.setForeground(COMMENT,new Color(160,90,60));
		    StyleConstants.setItalic(COMMENT, true);
		    StyleConstants.setFontFamily(COMMENT, "Helvetica");
		    StyleConstants.setFontSize(COMMENT, 14);
		    
		    StyleConstants.setForeground(KEYWORD, new Color(90,60,160));
		    StyleConstants.setFontFamily(KEYWORD, "Helvetica");
		    StyleConstants.setFontSize(KEYWORD, 14);
		    StyleConstants.setBold(KEYWORD, true);
    }
	  
	  
	// components
	private JTextPane textPane = new JTextPane();;
	
	// models
	private boolean showAnnotations = false;
	private boolean showComments = true;
	
	public static void showScript() {
		ScriptViewer viewer = new ScriptViewer();
		int W=700,H=700;
		viewer.setSize(W,H);
		viewer.setTitle("UServ rules: rules.take");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		viewer.setLocation((screen.width-W)/2,(screen.height-H)/2);
		viewer.setVisible(true);
	}
	
	public ScriptViewer() throws HeadlessException {
		super();
		init();
	}

	public ScriptViewer(GraphicsConfiguration gc) {
		super(gc);
		init();
	}

	public ScriptViewer(String title, GraphicsConfiguration gc) {
		super(title, gc);
		init();
	}

	public ScriptViewer(String title) throws HeadlessException {
		super(title);
		init();
	}
	private void init() {
		JPanel pane = new JPanel(new BorderLayout());
		
		JPanel nPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JCheckBox checkAnn = new JCheckBox("show annotations");
		checkAnn.setSelected(this.showAnnotations);
		checkAnn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showAnnotations = checkAnn.isSelected();
				loadScript();
			}
		});
		nPanel.add(checkAnn);
		
		final JCheckBox checkComm = new JCheckBox("show comments");
		checkComm.setSelected(this.showComments);
		checkComm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showComments = checkComm.isSelected();
				loadScript();
			}
		});
		nPanel.add(checkComm);
		pane.add(nPanel,BorderLayout.NORTH);
		
		JPanel sPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		final JButton butExit = new JButton("close");
		butExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		sPanel.add(butExit);
		pane.add(sPanel,BorderLayout.SOUTH);
		
		pane.add(new JScrollPane(textPane),BorderLayout.CENTER);
		
		this.setContentPane(pane);
		loadScript();
	}
	
	private void loadScript() {
		try {
			InputStream in = this.getClass().getResourceAsStream("/example/nz/org/take/compiler/userv/rules/userv.take");
			java.io.LineNumberReader reader = new java.io.LineNumberReader(new java.io.InputStreamReader(in));
			String line = null;
			textPane.setText(""); // clear
			while ((line=reader.readLine())!=null) {
				if (doShow(line)) { 
					// line formatting
					
					if (this.isAnnotation(line)) 
						insertText(line+"\n",this.ANNOTATION);
					else if (this.isComment(line)) 
						insertText(line+"\n",this.COMMENT);
					else {
						// tokenize
						StringTokenizer tok = new StringTokenizer(line," ");
						while (tok.hasMoreTokens()) {
							String token=tok.nextToken();
							if (isKeyword(token)) 
								insertText(token,this.KEYWORD);
							else 
								insertText(token,this.PLAIN);
							insertText(" ",this.PLAIN);
						}
						insertText("\n",this.PLAIN);
					}
				}
			}
		}
		catch (IOException x) {
			this.textPane.setText("Error - cannot read script. Check console for details");
			x.printStackTrace();
		}
	}
	private boolean isKeyword(String token) {
		String s = token.trim();
		for (String k:KEYWORDS) {
			if (k.equals(s))
				return true;
		}
		return false;
	}

	// whether to show a line
	private boolean doShow(String line) {
		if (!this.showAnnotations && isAnnotation(line))
			return false;
		else if (!this.showComments && isComment(line)) 
			return false;
		else return true;
	}
	private boolean isAnnotation(String line) {
		return line.startsWith("@");
	}
	private boolean isComment(String line) {
		return line.startsWith("//");
	}
	
	private void insertText(String text, AttributeSet set) {
		try {
			textPane.getDocument().insertString(textPane.getDocument().getLength(), text, set);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
