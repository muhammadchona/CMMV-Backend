package mz.org.fgh.cmmv.backend.docsOrImages

import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer
import mz.org.fgh.cmmv.backend.utente.Utente

class InfoDocsOrImages {

    String title
    Date createdDate
    Date publishedDate
    byte[] blop
    boolean forMobilizer

//    static hasMany = [users: Utente, mobilizers: CommunityMobilizer]
//    static belongsTo = [users: Utente, mobilizers: CommunityMobilizer]

    static constraints = {

        title nullable: false
        createdDate(nullable: false, blank: false, validator: { createdDate, urc ->
            return createdDate != null ? createdDate <= new Date() : null
        })
        publishedDate(nullable: false, blank: false)

    }
}
