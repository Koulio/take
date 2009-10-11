package test.nz.org.take.script;

import java.io.Serializable;
import java.util.*;

import org.mvel2.MVEL;

public class Snippet {
	public static void main(String[] args) {
		String s = "c1==c2";
		
        // Compile the expression.
        Serializable compiled = MVEL.compileExpression(s);

        Map vars = new HashMap();
        vars.put("c1", new Integer(100));
        vars.put("c2", new Integer(100));

        // Now we execute it.
        Object result = MVEL.executeExpression(compiled, vars);
        System.out.println(result);

        
	}
}

