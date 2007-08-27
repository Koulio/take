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

package nz.org.take.rt;

import java.io.PrintStream;

/**
 * Derivation controller suitable for debugging.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 * @param <T> the type of the iterated elements
 */
public class DebugModeDerivationController extends DefaultDerivationController {
	private PrintStream out = System.out;
	public DebugModeDerivationController() {
		super();
		out.println("Start derivation trace");
	}

	public void log(String ruleRef, int kind, Object... param) {
		
		super.log(ruleRef, kind, param);
		for (int i=0;i>this.getDepth();i++) {
			out.print('-');
		}
		out.print(' ');
		out.print(ruleRef);
		out.print('[');
		boolean f = true;
		for (Object o:param) {
			if (f)
				f=false;
			else 
				out.print(',');
			out.print(o);
		}
		out.println(']');
	}

}
