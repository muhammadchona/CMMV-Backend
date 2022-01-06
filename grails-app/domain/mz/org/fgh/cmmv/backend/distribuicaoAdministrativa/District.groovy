package mz.org.fgh.cmmv.backend.distribuicaoAdministrativa

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import mz.org.fgh.cmmv.backend.address.Address
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer

class District {

    String code
    String description
  //  @JsonBackReference
  //  Province province
  //  @JsonBackReference
  //  Province province


    static hasMany = [addresses: Address, mobilizers: CommunityMobilizer]
  //  @JsonIgnoreProperties("districts")
    static belongsTo = [province: Province]

    static constraints = {
        code nullable: false, unique: ['province']
        description nullable: false
    }
}
