package test.nz.org.take.strelka;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import nz.org.take.r2ml.R2MLException;
import nz.org.take.r2ml.util.RuleBaseFilter;
import nz.org.take.r2ml.util.TypeVariablesFilter;
import nz.org.take.strelka.filter.StrelkaDisjunctionBugFilter;
import nz.org.take.strelka.filter.SubstituteRealFilter;

import org.apache.log4j.BasicConfigurator;

import de.tu_cottbus.r2ml.RuleBase;

public class FilterRules {

	/**
	 * @param args
	 * @throws JAXBException 
	 * @throws R2MLException 
	 */
	public static void main(String[] args) throws JAXBException, R2MLException {
		BasicConfigurator.configure();
		JAXBContext jc = JAXBContext
				.newInstance("de.tu_cottbus.r2ml:de.tu_cottbus.r2ml.r2mlv:de.tu_cottbus.dc");//
		Unmarshaller um = jc.createUnmarshaller();

		
		InputStream is = FilterTests.class.getResourceAsStream("AD_01_raw.xml");
		Reader reader = new InputStreamReader(is);
		RuleBase ruleBase = (RuleBase) ((JAXBElement) um.unmarshal(reader)).getValue();

		List<RuleBaseFilter> filters = new ArrayList<RuleBaseFilter>(3);
		filters.add(new StrelkaDisjunctionBugFilter());
		filters.add(new TypeVariablesFilter());
		filters.add(new SubstituteRealFilter());
		for (RuleBaseFilter filter : filters) {
			filter.repair(ruleBase);
		}
		
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(ruleBase, System.out);

	}

}
