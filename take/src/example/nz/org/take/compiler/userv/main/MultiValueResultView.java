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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import nz.org.take.Annotatable;
import nz.org.take.rt.DerivationLogEntry;
import nz.org.take.rt.ResultSet;

/**
 * Component to visualise a result set with multiple results.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public abstract class MultiValueResultView extends JPanel {
	private JList list = new JList();
	private int totalValue = 0;
	private JButton logButton = new JButton(" ? ");
	private List<Integer> values = new ArrayList<Integer>();
	private List<List<DerivationLogEntry>> logs = new ArrayList<List<DerivationLogEntry>>();
	private Map<String,Annotatable> rulesById = null;
	
	public MultiValueResultView(Map<String,Annotatable> rulesById) {
		super();
		this.rulesById=rulesById;
		setLayout(new BorderLayout(5,5));
		add(new JScrollPane(list),BorderLayout.CENTER);
		JPanel bPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bPane.add(logButton);
		add(bPane,BorderLayout.SOUTH);
		logButton.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int pos = list.getSelectedIndex();
						if (pos<logs.size()) {
							DerivationLogViewer.displayUsedRules(logs.get(pos), MultiValueResultView.this.rulesById,MultiValueResultView.this);
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
							DerivationLogViewer.displayUsedRules(logs.get(pos), MultiValueResultView.this.rulesById,MultiValueResultView.this);
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
