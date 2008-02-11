/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
 */

package nz.org.take;

import java.text.DateFormat;
import java.util.Date;

/**
 * Tools that can be used to automatically annotate knowledge elements.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class AutoAnnotationService {
	public static void annotate(KnowledgeElement e) {
		e.addAnnotation(AnnotationKeys.TAKE_AUTO_DATE,DateFormat.getDateTimeInstance().format(new Date()));
		e.addAnnotation(AnnotationKeys.TAKE_AUTO_CREATOR,System.getProperty("user.name"));
		e.addAnnotation(AnnotationKeys.TAKE_AUTO_TOSTRING,e.toString());
	}
	public static void annotateAll(KnowledgeBase kb) {
		for (KnowledgeElement e:kb.getElements()) {
			annotate(e);
		}
	}
}
