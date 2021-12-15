package mz.org.fgh.cmmv.backend.clinic

import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.Province

class Clinic {

    String code
    String name
    String type
    double latitude
    double longitude
  //  Province province

    static belongsTo = [district: District]

    static constraints = {
        code nullable: false, unique: true
        name nullable: false
        type nullable: false
        latitude nullable: false
        longitude nullable: false
    }
}
