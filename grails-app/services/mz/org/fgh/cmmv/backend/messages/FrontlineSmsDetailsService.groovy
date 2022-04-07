package mz.org.fgh.cmmv.backend.messages

import grails.gorm.services.Service

@Service(FrontlineSmsDetails)
interface FrontlineSmsDetailsService {

    FrontlineSmsDetails get(Serializable id)

    List<FrontlineSmsDetails> list(Map args)

    Long count()

    FrontlineSmsDetails delete(Serializable id)

    FrontlineSmsDetails save(FrontlineSmsDetails frontlineSmsDetails)

}
