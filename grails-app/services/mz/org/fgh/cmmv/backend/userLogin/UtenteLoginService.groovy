package mz.org.fgh.cmmv.backend.userLogin

import grails.gorm.services.Service

@Service(UtenteLogin)
interface UtenteLoginService {

    UtenteLogin get(Serializable id)

    List<UtenteLogin> list(Map args)

    Long count()

    UtenteLogin delete(Serializable id)

    UtenteLogin save(UtenteLogin utenteLogin)

}
