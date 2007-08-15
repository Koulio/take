package example.nz.org.take.compiler.userv.domainmodel;

import java.util.Collection;
import java.util.HashSet;

public class HighTheftProbabilityAutoList {
	
	private static Collection<String> list = null;
	
	public static Collection<String> getList() {
		if (list==null) {
			list = new HashSet<String>();
			list.add("Mini");	
			list.add("VW Beetle");
			list.add("VW Phaeton");
			list.add("Audi A3");
			list.add("Mercedes Benz A class");
		}
		return list;
	}

}
