package mz.org.fgh.cmmv.backend.mobilizer

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer

@Transactional
@Service(CommunityMobilizer)
abstract class CommunityMobilizerService implements ICommunityMobilizerService{

    @Override
    List<CommunityMobilizer> getAllByDistrictId(Long districtId) {
        return CommunityMobilizer.findAllByDistrict(Clinic.findById(districtId))
    }
}
