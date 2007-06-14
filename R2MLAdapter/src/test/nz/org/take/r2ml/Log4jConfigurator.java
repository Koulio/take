package test.nz.org.take.r2ml;

import java.net.URL;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

public class Log4jConfigurator {

    private static boolean configured = false; 

    public synchronized static void configure() { 
        if (!configured) { 
        	URL propertyurl = Log4jConfigurator.class.getResource("/test/nz/org/take/r2ml/log4j.prop");
            PropertyConfigurator.configure(propertyurl); 
            configured = true; 
        } 
    } 

    public synchronized static void shutdown() { 
        if (configured) { 
            LogManager.shutdown(); 
            configured = false; 
        } 
    } 

}