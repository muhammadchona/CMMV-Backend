package mz.org.fgh.cmmv.backend.protection

import grails.gorm.services.Service

@Service(SecUser)
interface SecUserService {

    SecUser get(Serializable id)

    List<SecUser> list(Map args)

    Long count()

    SecUser delete(Serializable id)

    SecUser save(SecUser secUser)

}
