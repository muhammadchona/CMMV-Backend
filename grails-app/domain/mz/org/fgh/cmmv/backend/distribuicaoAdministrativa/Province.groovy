package mz.org.fgh.cmmv.backend.distribuicaoAdministrativa

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonManagedReference

class Province {

    String code
    String description

   // @JsonManagedReference
    static hasMany = [districts: District]

    static constraints = {
        code nullable: false, unique: true
        description nullable: false
    }

    static mapping = {
        //code type: GormEncryptedStringType
        //description type: GormEncryptedStringType
//        birthDate type: GormEncryptedDateAsStringType
//        anniversary type: GormEncryptedCalendarAsStringType
//        hasInsurance type: GormEncryptedBooleanType
//        latitude type: GormEncryptedDoubleAsStringType
//        cashBalance type: GormEncryptedFloatAsStringType
//        bdBalance type: GormEncryptedBigDecimalType
//        bdBalance type: GormEncryptedBigDecimalAsStringType
//        weight type: GormEncryptedShortAsStringType
//        height type: GormEncryptedIntegerAsStringType
//        patientId type: GormEncryptedLongAsStringType
//        biteMe type: GormEncryptedByteAsStringType
    }
}
