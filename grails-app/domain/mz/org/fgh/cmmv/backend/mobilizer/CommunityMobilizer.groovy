package mz.org.fgh.cmmv.backend.mobilizer

import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.docsOrImages.InfoDocsOrImages
import mz.org.fgh.cmmv.backend.userLogin.MobilizerLogin
import mz.org.fgh.cmmv.backend.utente.Utente

class CommunityMobilizer {

    String firstNames
    String lastNames
    String cellNumber
    String cellNumber2
    String uuid

    @JsonManagedReference
    static hasMany = [docsOrImages: InfoDocsOrImages, utentes:Utente]


    static belongsTo = [district : District]
//    static hasOne = [user: MobilizerLogin]

    static mapping = {
        utentes lazy: true
    }
    static constraints = {
        firstNames(nullable: false, blank: false)
        lastNames(nullable: false, blank: false)
        cellNumber(nullable: false, matches: /\d+/, maxSize: 12, minSize: 9)
        cellNumber2(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
//        user(nullable: true)
    }
}
