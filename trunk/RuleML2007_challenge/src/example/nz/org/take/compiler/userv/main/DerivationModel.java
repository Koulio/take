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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import example.nz.org.take.compiler.userv.spec.UservRules;
import nz.org.take.rt.DerivationController;
import nz.org.take.rt.DerivationLogEntry;


/**
 * Tree model used to visualise the rules used and their annotations.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DerivationModel  implements TreeModel {
	private List<DerivationLogEntry> derivationLog = new ArrayList<DerivationLogEntry>();
	private Map<String,List<Annotation>> annotations = new HashMap<String,List<Annotation>>();
	private UservRules kb = null;
	class Annotation {
		String owner,key,value;
		public Annotation(String owner, String key, String value) {
			super();
			this.owner = owner;
			this.key = key;
			this.value = value;
		}
		public String toString() {
			return key+": "+value;
		}
	}
	
	public DerivationModel(List<DerivationLogEntry> log, UservRules kb) {
		
		for (DerivationLogEntry e:log) {
			if (e.getKind()==DerivationController.RULE || e.getKind()==DerivationController.FACT || e.getKind()==DerivationController.EXTERNAL_FACT_SET) {
				derivationLog.add(e);
			}
		}
		this.kb=kb;
	}


	private List<Annotation> getAnnotations(String id) {
		List<Annotation>  l = this.annotations.get(id);
		if (l==null) {
			l = new ArrayList<Annotation>();
			Map<String,String> map = kb.getAnnotations(id);
			if (map!=null) {
				for (Entry<String,String> e:map.entrySet()) {
					Annotation n = new Annotation(id,e.getKey(),e.getValue());
					l.add(n);
				}
			}
			annotations.put(id,l);
		}
		return l;
	}
	public void addTreeModelListener(TreeModelListener l) {
	}

	public Object getChild(Object parent, int index) {
		if (parent==derivationLog) {
			return derivationLog.get(index).getName();
		}
		else if (parent instanceof String) {
			return getAnnotations((String)parent).get(index);
		}
		return null;
	}

	public int getChildCount(Object parent) {
		if (parent==derivationLog) {
			return derivationLog.size();
		}
		else if (parent instanceof String) { 
			List<Annotation> l = getAnnotations((String)parent);
			return l==null?0:l.size();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent==derivationLog)
			return derivationLog.indexOf(child);
		else if (parent instanceof String) {
			return getAnnotations((String)parent).indexOf(child);
		}
		return 0;
	}

	@Override
	public Object getRoot() {
		return derivationLog;
	}

	public boolean isLeaf(Object node) {
		return this.getChildCount(node)==0;
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}


}
