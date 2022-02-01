package mz.org.fgh.cmmv.backend.userLogin

import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.Province
import mz.org.fgh.cmmv.backend.protection.SecUser

class DistrictUserLogin extends SecUser{

    Province province
    District district


    static constraints = {
        province nullable: true
        district nullable: true
    }
}
