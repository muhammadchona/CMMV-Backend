package mz.org.fgh.cmmv.backend.mobilizer

import grails.gorm.services.Service

@Service(CommunityMobilizer)
interface CommunityMobilizerService {

    CommunityMobilizer get(Serializable id)

    List<CommunityMobilizer> list(Map args)

    Long count()

    CommunityMobilizer delete(Serializable id)

    CommunityMobilizer save(CommunityMobilizer communityMobilizer)

}
