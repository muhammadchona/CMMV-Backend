package mz.org.fgh.cmmv.backend.address

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonManagedReference
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.utente.Utente

class Address {

    String neighboorhood
    String city
    String residence
    String latitude
    String longitude
    District district

    static belongsTo = [utente: Utente]

    static constraints = {
        neighboorhood(nullable: true, blank: true)
        city(nullable: true, blank: true)
        residence(nullable: false, blank: false)
        latitude(nullable: false, blank: false)
        longitude(nullable: false,blank: false)
        utente nullable: true
    }
}
