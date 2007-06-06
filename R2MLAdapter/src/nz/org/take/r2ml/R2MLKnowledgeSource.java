package nz.org.take.r2ml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;

import test.nz.org.take.r2ml.scenario2.MyDatatypeMapper;
import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeSource;
import nz.org.take.Predicate;
import nz.org.take.Query;
import nz.org.take.TakeException;
import nz.org.take.script.ScriptKnowledgeSource;
import nz.org.take.script.parser.Parser;

public class R2MLKnowledgeSource implements KnowledgeSource {

	public Logger LOGGER = Logger.getLogger(R2MLKnowledgeSource.class);

//	private ClassLoader classloader = R2MLKnowledgeSource.class.getClassLoader();

	private Reader reader = null;
	
	private R2MLDriver driver = null;

	private KnowledgeBase kb = null;
	private List<Query> querries = null;

	private R2MLKnowledgeSource() {
		super();
		driver = new R2MLDriver();
	}

	public R2MLKnowledgeSource(Reader reader) {
		this();
		this.reader = reader;
	}

	public R2MLKnowledgeSource(InputStream in) {
		this();
		this.reader = new InputStreamReader(in);
	}

	public R2MLKnowledgeSource(File file) throws FileNotFoundException {
		this();
		this.reader = new FileReader(file);
	}


	public R2MLKnowledgeSource(String fileName) throws FileNotFoundException {
		this();
		File file = new File(fileName);
		this.reader = new FileReader(file);
	}

	/**
	 * Get a knowledge base.
	 * 
	 * @return a knowledge base
	 */
	public KnowledgeBase getKnowledgeBase() throws TakeException {
		if (kb == null) {
			driver.initialize();
			kb = driver.importKB(reader);
		}
		return kb;
	}

	public void setSlotNameGenerator(NameMapper nameMapper) {
		driver.setNameMapper(nameMapper);
	}
	
	public void setDatatypeMapper(DatatypeMapper datatypeMapper) {
		driver.setDatatypeMapper(datatypeMapper);
	}

	public void setQuerries(List<Query> querries) {
		for (Query q : querries)
			kb.add(q);
	}
	
}
