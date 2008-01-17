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

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizer with support for string literals.
 * TODO: add support for escape characters.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class Tokenizer {
	
	static class Recognizer {
		String word = null;
		int counter = 0;
		Recognizer(String word) {
			super();
			this.word = word;
		}
		int length() {
			return word.length();
		}
		boolean push(char c) {
			if (word.charAt(counter)==c) {
				counter = counter+1;
				if (counter==word.length()) {
					counter=0;
					return true;
				}
			} 
			else {
				counter = 0;
			}
			return false;	
		}
		void reset() {
			counter=0;
		}
	}
	
	public static List<String> tokenize(String s,String... separators) {
		List<String> tokens = new ArrayList<String>();
		boolean literalMode = false;
		StringBuffer b = new StringBuffer();
		boolean append = false;
		Recognizer[] regs = new Recognizer[separators.length];
		for (int i=0;i<separators.length;i++)
			regs[i] = new Recognizer(separators[i]);
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			append=true;
			if (c=='\'') {
				literalMode = !literalMode;
			}
			else if (!literalMode) {
				boolean t = false;
				for (Recognizer r:regs) {					
					if (r.push(c)) {
						tokens.add(b.toString().substring(0,1+b.length()-r.length()));
						b.setLength(0);
						append = false;
						t = true;
					}
				}
				if (t) {
					for (Recognizer r:regs)
						r.reset();
				}
				
			} 
			if (append) 
				b.append(c);
		}
		tokens.add(b.toString().trim());
		
		return tokens;
		
	}

}
