package mz.org.fgh.cmmv.backend.docsOrImages

import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer
import mz.org.fgh.cmmv.backend.utente.Utente
import org.hibernate.annotations.Type

class InfoDocsOrImages {

    String title
    Date createdDate
    Date publishedDate
    @Type(type="org.hibernate.type.BinaryType")
    byte[] blop
    boolean forMobilizer

//    static hasMany = [users: Utente, mobilizers: CommunityMobilizer]
//    static belongsTo = [users: Utente, mobilizers: CommunityMobilizer]

    static constraints = {

        title nullable: false
        createdDate(nullable: true, blank: true, validator: { createdDate, urc ->
            return createdDate != null ? createdDate <= new Date() : null
        })
        publishedDate(nullable: true, blank: true)

    }
}
