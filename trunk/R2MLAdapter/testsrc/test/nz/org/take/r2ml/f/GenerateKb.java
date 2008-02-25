package test.nz.org.take.r2ml.f;

import nz.org.take.KnowledgeBase;
import nz.org.take.compiler.reference.DefaultCompiler;
import nz.org.take.compiler.util.DefaultLocation;
import nz.org.take.compiler.util.DefaultNameGenerator;
import nz.org.take.compiler.util.jalopy.JalopyCodeFormatter;
import nz.org.take.r2ml.R2MLDriver;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;

import org.apache.log4j.BasicConfigurator;

import example.nz.org.take.r2ml.userv.domain.UServDatatypeMapper;
import example.nz.org.take.r2ml.userv.domain.UServQueryGenerator;

public class GenerateKb {

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
			R2MLKnowledgeSource kSrc = new R2MLKnowledgeSource(GenerateKb.class.getResourceAsStream("/test/nz/org/take/r2ml/f/rules3.r2ml"));
			kSrc.setQueryGenerator(new UServQueryGenerator());
			kSrc.setDatatypeMapper(new UServDatatypeMapper());
			kSrc.setPropertyMode(R2MLDriver.INFER_PROPERTIES_MODE);
			kb = kSrc.getKnowledgeBase();
		} catch (R2MLException e) {
			e.printStackTrace();
		}
		compiler.add(new JalopyCodeFormatter());
		compiler.setLocation(new DefaultLocation("testsrc"));
		compiler.setNameGenerator(new DefaultNameGenerator());
		compiler.setPackageName("test.nz.org.take.r2ml.f.generatedKb");
		compiler.setClassName("UServKB");
		compiler.compile(kb);

	}
}
