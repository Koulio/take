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

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Utility class to locate properties using reflection. 
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class PropertyFinder {
	public static PropertyDescriptor findProperty(Class type, String p)  {
		PropertyDescriptor[] properties = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); 
			properties = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property:properties) {
				if (property.getName().equals(p)) {
					return property;
				}
			}
			// nothing found - if type is an interface, try superinterfaces
			// this is not done automatically by the bean Introspector!
			// see issue23, also http://stackoverflow.com/questions/185004/javabeansintrospector-getbeaninfo-does-not-pickup-any-superinterfaces-properties
			PropertyDescriptor property = null;
			for (Class i:type.getInterfaces()) {
				property = findProperty(i,p);
				if (property!=null) {
					return property;
				}
			}
		}
		catch (IntrospectionException x) {}
		
		return null;
	}
}
