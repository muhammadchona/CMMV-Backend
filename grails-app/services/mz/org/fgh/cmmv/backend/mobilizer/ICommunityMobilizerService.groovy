package mz.org.fgh.cmmv.backend.mobilizer

import grails.gorm.services.Service
import mz.org.fgh.cmmv.backend.utente.Utente


interface ICommunityMobilizerService {

    CommunityMobilizer get(Serializable id)

    List<CommunityMobilizer> list(Map args)

    Long count()

    CommunityMobilizer delete(Serializable id)

    CommunityMobilizer save(CommunityMobilizer communityMobilizer)

    List<CommunityMobilizer> getAllByDistrictId(Long districtId)

}
