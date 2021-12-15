package mz.org.fgh.cmmv.backend.protection

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class SecRole implements Serializable {

	private static final long serialVersionUID = 1

	String authority

	SecRole(String authority){
		this()
		this.authority = authority
	}

	static constraints = {
		authority nullable: false, blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
