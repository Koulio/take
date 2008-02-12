package test.nz.org.take.r2ml.scenario5;

import nz.org.take.TakeException;
import nz.org.take.compiler.Compiler;
import nz.org.take.compiler.CompilerException;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.r2ml.R2MLKnowledgeSource;

public class PrepareKB {

	/**
	 * @param args
	 * @throws TakeException 
	 * @throws CompilerException 
	 */
	public static void main(String[] args) throws CompilerException, TakeException {
		
		R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(PrepareKB.class.getResourceAsStream("/test/nz/org/take/r2ml/scenario5/rules.xml"));
		kSrc.setDatatypeMapper(new MyDatatypeMapper05());
		kSrc.setSlotNameGenerator(null);
		
		Compiler compiler = new DefaultCompiler();
		compiler.setLocation(new DefaultLocation("testsrc"));
		compiler.compileInterface(kSrc.getKnowledgeBase());
	}

}
