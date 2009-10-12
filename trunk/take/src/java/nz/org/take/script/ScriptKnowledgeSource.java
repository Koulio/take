/**
 * Copyright 2009 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take.script;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import nz.org.take.KnowledgeBase;
import nz.org.take.KnowledgeSource;
import nz.org.take.TakeException;

/**
 * Simple wrapper for the parser.
 * @author jens
 */
public class ScriptKnowledgeSource implements KnowledgeSource {

	private Reader reader = null; 
	private KnowledgeBase kb = null;
	private boolean useCache = false;
	
	public ScriptKnowledgeSource(Reader source,boolean useCache) {
		super();
		this.reader = source;
		this.useCache = useCache;
	}
	
	public ScriptKnowledgeSource(Reader source) {
		this(source,false);
	}

	public ScriptKnowledgeSource(InputStream source,boolean useCache) {
		super();
		this.reader = new InputStreamReader(source);
		this.useCache = useCache;
	}
	
	public ScriptKnowledgeSource(InputStream source) {
		this(source,false);
	}
	

	
	@Override
	public KnowledgeBase getKnowledgeBase() throws TakeException {
		if (this.useCache&&this.kb!=null) {
			return kb;
		}
		try {
			KnowledgeBase k = new Parser().parse(reader);
			reader.close();
			if (this.useCache) {
				kb = k;
			}
			return k;
		}
		catch (Exception x) {
			throw new TakeException("Error reading knowledge base",x);
		}
	}

}
