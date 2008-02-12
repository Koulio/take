package test.nz.org.take.r2ml.d;

import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;

import org.apache.log4j.BasicConfigurator;

public class GenerateKbIf {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		nz.org.take.compiler.Compiler compiler = new DefaultCompiler();
		compiler.add(new JalopyCodeFormatter());
		
		// generate kb
		KnowledgeBase kb = null;
		try {
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(GenerateKbIf.class.getResourceAsStream("/test/nz/org/take/r2ml/d/properties2.r2ml"));
			kSrc.setQueryGenerator(new ThingQueryGenerator());
			kSrc.setDatatypeMapper(new ThingMapper());
			kb = kSrc.getKnowledgeBase();
		} catch (R2MLException e) {
			e.printStackTrace();
		}
		compiler.add(new JalopyCodeFormatter());
		compiler.setLocation(new DefaultLocation("testsrc"));
		compiler.setNameGenerator(new DefaultNameGenerator());
		compiler.setPackageName("test.nz.org.take.r2ml.d.generated");
		compiler.setClassName("ThingKB");
		compiler.compileInterface(kb);

	}
}
