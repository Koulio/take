/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
