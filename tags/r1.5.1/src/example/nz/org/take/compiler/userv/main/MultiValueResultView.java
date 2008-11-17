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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;

/**
 * Component to visualise a result set with multiple results.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class MultiValueResultView extends JPanel {
	private JList list = new JList();
	private JButton logButton = new JButton(" ? ");
	private List<Integer> values = new ArrayList<Integer>();
	private List<List<DerivationLogEntry>> logs = new ArrayList<List<DerivationLogEntry>>();
	
	public MultiValueResultView() {
		super();
		setLayout(new BorderLayout(5,5));
		add(new JScrollPane(list),BorderLayout.CENTER);
		JPanel bPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		logButton.setBorder(null);
		bPane.add(logButton);
		add(bPane,BorderLayout.SOUTH);
		logButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int pos = list.getSelectedIndex();
						if (pos<logs.size()) {
							DerivationLogViewer.displayUsedRules(logs.get(pos),MultiValueResultView.this);
						}
					}
				}
		);
		logButton.setEnabled(false);
		list.addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						int pos = list.getSelectedIndex();
						logButton.setEnabled(pos<logs.size());
					}
				}
		);
		list.addMouseListener(
				new MouseAdapter() {
					// to open derivation log on double click
					public void mouseClicked(MouseEvent e) {
						int pos = list.getSelectedIndex();
						if (e.getClickCount()>1 && pos<logs.size()) {
							DerivationLogViewer.displayUsedRules(logs.get(pos),MultiValueResultView.this);
						}
					}
				}
		);
		
	}
	
	public abstract int extractValue(Object rs);
	
	public void setResultSet(ResultSet rs) {
		int total = 0;
		DefaultListModel model = new DefaultListModel();
		values.clear();
		logs.clear();
		while (rs.hasNext()) {
			int next = extractValue(rs.next());
			total = total+next;
			model.addElement(next);
			values.add(next);
			logs.add(rs.getDerivationLog());
		}
		rs.close();
		model.addElement("total: " + total);
		list.setModel(model);
		logButton.setEnabled(false);
	}
	
	
}
