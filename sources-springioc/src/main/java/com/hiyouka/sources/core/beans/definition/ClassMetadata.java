package com.hiyouka.sources.core.beans.definition;

/**
 * @author hiyouka
 * Date: 2019/1/28
 * @since JDK 1.8
 */
public interface ClassMetadata {

   	/**
	 * Return the name of the underlying class.
	 */
	String getClassName();

	/**
	 * Return whether the underlying class represents an interface.
	 */
	boolean isInterface();

	/**
	 * Return whether the underlying class represents an annotation.
	 * @since 4.1
	 */
	boolean isAnnotation();

	/**
	 * Return whether the underlying class is marked as abstract.
	 */
	boolean isAbstract();

	/**
	 * Return whether the underlying class has a super class.
	 */
	boolean hasSuperClass();

	/**
	 * Return the name of the super class of the underlying class,
	 * or {@code null} if there is no super class defined.
	 */
	String getSuperClassName();

	/**
	 * Return the names of all interfaces that the underlying class
	 * implements, or an empty array if there are none.
	 */
	String[] getInterfaceNames();

}