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
/**
 * Constants representing annotation keys.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public interface AnnotationKeys {
	// annotation on queries used to define the method
	public static final String TAKE_GENERATE_SLOTS = "take.compilerhint.slots";
	// annotation on queries used to define the method
	public static final String TAKE_GENERATE_METHOD = "take.compilerhint.method";
	// annotation on queries used to define the local class name
	public static final String TAKE_GENERATE_CLASS = "take.compilerhint.class";
	// auto annotation for the current date
	public static final String TAKE_AUTO_DATE = "take.auto.date";
	// auto annotation for the string representation of a rule
	public static final String TAKE_AUTO_TOSTRING= "take.auto.name";
	// auto annotation for the author of a rule
	public static final String TAKE_AUTO_CREATOR= "take.auto.creator";
}
