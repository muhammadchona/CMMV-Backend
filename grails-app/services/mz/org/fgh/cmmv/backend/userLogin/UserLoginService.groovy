package mz.org.fgh.cmmv.backend.userLogin

import grails.gorm.services.Service

@Service(UserLogin)
interface UserLoginService {

    UserLogin get(Serializable id)

    List<UserLogin> list(Map args)

    Long count()

    UserLogin delete(Serializable id)

    UserLogin save(UserLogin userLogin)

}
