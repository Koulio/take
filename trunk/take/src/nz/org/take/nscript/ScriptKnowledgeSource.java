/**
 * Copyright 2008 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.nscript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeSource;
import nz.org.take.TakeException;

/**
 * Knowledge source based on the new scripting framework.
 * Very similar to the old framework (script packages), but uses
 * JSP EL expressions in complex terms and prerequisites. 
 * These expressions are parsed using JUEL.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class ScriptKnowledgeSource implements KnowledgeSource {

	private Reader reader = null; 
	private ClassLoader classLoader = this.getClass().getClassLoader();
	
	public KnowledgeBase getKnowledgeBase() throws TakeException {
		Parser parser = new Parser();
		parser.setClassLoader(classLoader);
		return parser.parse(reader);
	}

	public ScriptKnowledgeSource (Reader reader) {
		super();
		this.reader = reader;
	}	
	
	public ScriptKnowledgeSource (InputStream in) {
		super();
		this.reader = new InputStreamReader(in);
	}	
	
	public ScriptKnowledgeSource (File file) throws FileNotFoundException {
		super();
		this.reader = new FileReader(file);
	}	
	
	public ScriptKnowledgeSource (String fileName) throws FileNotFoundException {
		super();
		this.reader = new FileReader(new File(fileName));
	}

	public ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}	
	
}
