package mz.org.fgh.cmmv.backend.userLogin

import grails.gorm.services.Service

@Service(DistrictUserLogin)
interface DistrictUserLoginService {

    DistrictUserLogin get(Serializable id)

    List<DistrictUserLogin> list(Map args)

    Long count()

    DistrictUserLogin delete(Serializable id)

    DistrictUserLogin save(DistrictUserLogin districtUserLogin)

}
