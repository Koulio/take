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
