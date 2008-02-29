package test.nz.org.take.r2ml.h;


import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.reference.DefaultQueryGenerator;

import org.apache.log4j.BasicConfigurator;

public class GenerateKb {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		DefaultCompiler compiler = new DefaultCompiler();
		compiler.add(new JalopyCodeFormatter());
		
		// generate kb
		KnowledgeBase kb = null;
		try {
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(GenerateKb.class.getResourceAsStream("/test/nz/org/take/r2ml/h/datatypepredicateTest.r2ml"));
			kSrc.setPropertyMode(R2MLDriver.INFER_PROPERTIES_MODE);
			kSrc.setQueryGenerator(new DefaultQueryGenerator());
			kb = kSrc.getKnowledgeBase();
		} catch (R2MLException e) {
			e.printStackTrace();
		}
		
		compiler.setDerivationControllerClass("DebugModeDerivationController");
		compiler.add(new JalopyCodeFormatter());
		compiler.setLocation(new DefaultLocation("testsrc"));
		compiler.setNameGenerator(new DefaultNameGenerator());
		compiler.setPackageName("test.nz.org.take.r2ml.h.generatedKb");
		compiler.setClassName("TestKB");
		compiler.compile(kb);

	}
}
