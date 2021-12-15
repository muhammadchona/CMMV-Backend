package mz.org.fgh.cmmv.backend.userLogin

import mz.org.fgh.cmmv.backend.protection.SecUser
import mz.org.fgh.cmmv.backend.utente.Utente

class UtenteLogin extends SecUser{

    static belongsTo = [utente: Utente]
    static constraints = {
        utente nullable: true
    }
}
