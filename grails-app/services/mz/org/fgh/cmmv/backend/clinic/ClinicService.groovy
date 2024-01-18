package mz.org.fgh.cmmv.backend.clinic

import grails.gorm.services.Service
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer


@Service(Clinic)
interface ClinicService {

    Clinic get(Serializable id)

    List<Clinic> list(Map args)

    Long count()

    Clinic delete(Serializable id)

    Clinic save(Clinic clinic)

}
