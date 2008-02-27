package nz.org.take.strelka;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import nz.org.take.r2ml.DatatypeMapper;
import nz.org.take.r2ml.NameMapper;
import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.R2MLKnowledgeSource;
import nz.org.take.r2ml.reference.DefaultQueryGenerator;
import nz.org.take.r2ml.util.QueryGenerator;
import de.tu_cottbus.r2ml.RuleBase;
import example.nz.org.take.r2ml.userv.domain.UServQueryGenerator;

public class DomainClassLoader {

	ClassLoader customClassLoader = null;

	public DomainClassLoader(String domainJarPath) {
		super();
		ClassLoader thisClassLoader = this.getClass().getClassLoader();
		URL domainJarURL = null;
		try {
			domainJarURL = new File(domainJarPath).toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.customClassLoader = new URLClassLoader(new URL[] { domainJarURL },
				thisClassLoader);
	}

	/**
	 * @param rb
	 *            the RuleBase
	 * @return a KnowledgeSource
	 */
	@SuppressWarnings("unchecked")
	public R2MLKnowledgeSource getKnowledgeSource(RuleBase rb) {
		R2MLKnowledgeSource kSource = null;

		// instanciate the knowledge source
		try {
			Class<R2MLKnowledgeSource> clazz = (Class<R2MLKnowledgeSource>) customClassLoader
					.loadClass("nz.org.take.r2ml.R2MLKnowledgeSource");
			Constructor<R2MLKnowledgeSource> constructor = clazz
					.getConstructor(new Class[] { RuleBase.class });
			kSource = constructor.newInstance(new Object[] { rb });

			TakePlugin.logger.debug("R2MLKnowledgeSource instanziated.");

		} catch (Exception e) {
			TakePlugin
					.get()
					.addMessage(
							"Unable to instantiate knowledge source for the R2MLAdapter",
							TakePlugin.ERROR_CATEGORY);
		}

		return kSource;
	}

	@SuppressWarnings("unchecked")
	public DatatypeMapper getDatatypeMapper(String datatypeMapperClazz) {

		DatatypeMapper datty = null;

		try {
			Class<? extends DatatypeMapper> clazz = (Class<? extends DatatypeMapper>) customClassLoader
					.loadClass(datatypeMapperClazz);
			Constructor<? extends DatatypeMapper> constructor = clazz
					.getConstructor((Class[]) null);
			datty = constructor.newInstance((Object[]) null);

			TakePlugin.logger.debug("DatatypeMapper instantiated.");
		} catch (Exception e) {
			TakePlugin.get().addMessage(
					"Unable to instantiate DatatypeMapper for the R2MLAdapter",
					TakePlugin.WARN_CATEGORY);
			TakePlugin.logger
					.warn("Unable to instantiate DatatypeMapper for the R2MLAdapter");
		}

		return datty;
	}

	@SuppressWarnings("unchecked")
	public NameMapper getNameMapper(String nameMapperClazz) {
		NameMapper namy = null;

		try {
			Class<? extends NameMapper> clazz = (Class<? extends NameMapper>) customClassLoader
					.loadClass(nameMapperClazz);
			Constructor<? extends NameMapper> constructor = clazz
					.getConstructor((Class[]) null);
			namy = constructor.newInstance((Object[]) null);
		} catch (Exception e) {
			TakePlugin.logger.warn(
					"Unable to instabtiate NameMapper for the R2MLAdapter", e);
		}
		return namy;
	}

	@SuppressWarnings("unchecked")
	public QueryGenerator getQueryGenerator(String queryGeneratorClazz)
			throws R2MLException {
		QueryGenerator qGen = null;
//		if (queryGeneratorClazz == null) {
//			//TakePlugin.logger.debug("Loading UServQueryGenerator as default");
//			QueryGenerator g = new DefaultQueryGenerator();
//			TakePlugin.logger.debug("DefaultQueryGenerator loaded");
//			return g;
//		} else {
			try {
				Class<? extends QueryGenerator> clazz = (Class<? extends QueryGenerator>) customClassLoader
						.loadClass(queryGeneratorClazz);
				Constructor<? extends QueryGenerator> constructor = clazz
						.getConstructor((Class[]) null);
				qGen = constructor.newInstance((Object[]) null);
			} catch (Exception e) {
				TakePlugin.logger.warn(
						"Unable to instantiate QueryGenerator (\"" + queryGeneratorClazz + "\") for the R2MLAdapter.", e);
				//throw new R2MLException("Unable to load queryGenerator.", e);
				qGen = new DefaultQueryGenerator();
			}
			return qGen;

//		}
	}

}
