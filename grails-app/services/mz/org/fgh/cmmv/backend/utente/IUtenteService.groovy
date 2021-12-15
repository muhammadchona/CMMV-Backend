package mz.org.fgh.cmmv.backend.utente

import grails.gorm.services.Service


interface IUtenteService {

    Utente get(Serializable id)

    List<Utente> list(Map args)

    Long count()

    Utente delete(Serializable id)

    Utente save(Utente utente)

    List<Utente> getAllByMobilizerId(Long communityMobilizerId)

}
