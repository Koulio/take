/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
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
