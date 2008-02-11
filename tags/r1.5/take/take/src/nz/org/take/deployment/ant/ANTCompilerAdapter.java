/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.deployment.ant;

import java.io.File;
import java.util.StringTokenizer;
import nz.org.take.TakeException;
import nz.org.take.deployment.CompilerAdapter;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Javac;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.selectors.FileSelector;


/**
 * Compiler adapter for ANT. It uses the JAVAC task to compiler the files.
 * This class is similar to the ANT Compiler Adapter in Jasper.
 * The compiler class can be explicitely set, otherwise ANT will try to find one.
 * If SUNs JDK is used, this will be com.sun.tools.javac.Main .
 * IMPORTANT: For this class to be found, tools.jar must be in the classpath!
 * The main purpose of using this adapter (instead of JSR199) is JDK5 compatibility. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ANTCompilerAdapter implements CompilerAdapter {

	public static final Category LOGGER = Logger.getInstance(Javac.class);
	// an explicit compiler class can be set here
	private String compiler = null;

	@Override
	public void compile(final String packageName,final String srcFolder,final String binFolder) throws TakeException {
		
		Project project = getProject();
		Javac javac = (Javac) project.createTask("javac");
		Target target = new Target();
		target.setName("compile");
		target.setProject(project);
	    target.addTask(javac);
	    
        // Initializing classpath
	    String sep = System.getProperty("path.separator" );
	    String classpath = ""; // TODO give user the option to configure the
								// classpath
        Path path = new Path(project);
        path.setPath(System.getProperty("java.class.path"));
        // info.append(" cp=" + System.getProperty("java.class.path") + "\n");
        StringTokenizer tokenizer = new StringTokenizer(classpath, sep);
        while (tokenizer.hasMoreElements()) {
            String pathElement = tokenizer.nextToken();
            File repository = new File(pathElement);
            path.setLocation(repository);
            // ` info.append(" cp=" + repository + "\n");
        }
        
        // Initializing sourcepath
        Path srcPath = new Path(project);
        srcPath.setLocation(new File(srcFolder));
        
        if (LOGGER.isDebugEnabled()) {
        	LOGGER.debug("using tmp folder " + srcFolder);
        }
        
        // Initialize and set java extensions
        String exts = System.getProperty("java.ext.dirs");
        if (exts != null) {
            Path extdirs = new Path(project);
            extdirs.setPath(exts);
            javac.setExtdirs(extdirs);
            if (LOGGER.isDebugEnabled()) {
            	LOGGER.debug("using extension dir " + exts);
            }
        }
        
	    javac.setSrcdir(srcPath);
	    javac.setProject(project);
	    javac.setDestdir(new File(binFolder));
	    FileSelector selector = new FileSelector() {
	    	String srcDir = new File(srcFolder+packageName.replace('.','/')).getAbsolutePath();
			public boolean isSelected(File baseDir, String fileName, File file)
					throws BuildException {
				
				if (file.getParentFile().getAbsolutePath().equals(srcDir) && fileName.endsWith(".java")) {
					LOGGER.debug("include file " + fileName + " in baseDir " + baseDir);
					return true;
				}
				else {
					return false;
				}
			}
	    };
	    javac.add(selector);
	    
	    // project.executeTarget("compile");
	    javac.setVerbose(true);
	    
	    try {
	    	javac.execute();
	    }
	    catch (Exception x) {
	    	LOGGER.error("cannot compile sources", x);
	    }

	}

	protected Project getProject() {
		Project project = new Project();
		BuildListener l = new BuildListener() {

			@Override
			public void buildFinished(BuildEvent arg0) {
				LOGGER.info("finished build");
			}

			@Override
			public void buildStarted(BuildEvent arg0) {
				LOGGER.info("starting build");
			}

			@Override
			public void messageLogged(BuildEvent e) {
				if (e.getException() != null) {
					LOGGER.error(e.getMessage(), e.getException());
				} else {
					// TODO use log priority in message
					LOGGER.info(e.getMessage());
				}
			}

			@Override
			public void targetFinished(BuildEvent e) {
				LOGGER.info("target finished: " + e.getTarget());
				LOGGER.info(e.getMessage());
			}

			@Override
			public void targetStarted(BuildEvent e) {
				LOGGER.info("target started: " + e.getTarget());
				LOGGER.info(e.getMessage());
			}

			@Override
			public void taskFinished(BuildEvent e) {
				LOGGER.info("task finished: " + e.getTask());
				LOGGER.info(e.getMessage());
			}

			@Override
			public void taskStarted(BuildEvent e) {
				LOGGER.info("task started: " + e.getTask());
				LOGGER.info(e.getMessage());
			}
		};
		project.addBuildListener(l);
		if (compiler!=null)
			project.setProperty("build.compiler",compiler);
		try {
			project.init();
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
		return project;
	}

	public String getCompiler() {
		return compiler;
	}

	public void setCompiler(String compiler) {
		this.compiler = compiler;
	}

}
