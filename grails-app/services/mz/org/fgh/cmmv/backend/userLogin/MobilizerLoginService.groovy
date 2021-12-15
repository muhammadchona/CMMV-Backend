package mz.org.fgh.cmmv.backend.userLogin

import grails.gorm.services.Service

@Service(MobilizerLogin)
interface MobilizerLoginService {

    MobilizerLogin get(Serializable id)

    List<MobilizerLogin> list(Map args)

    Long count()

    MobilizerLogin delete(Serializable id)

    MobilizerLogin save(MobilizerLogin mobilizerLogin)

}
