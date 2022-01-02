package mz.org.fgh.cmmv.backend.userLogin

import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer
import mz.org.fgh.cmmv.backend.protection.SecUser

class MobilizerLogin extends SecUser{

    Province province
    District district

    static belongsTo = [mobilizer: CommunityMobilizer]

    static constraints = {
        mobilizer nullable: true
        province nullable: true
        district nullable: true
    }
}
