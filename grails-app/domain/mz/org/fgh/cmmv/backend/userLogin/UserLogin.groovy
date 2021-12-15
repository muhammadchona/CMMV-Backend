package mz.org.fgh.cmmv.backend.userLogin

import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.protection.SecUser

class UserLogin extends SecUser{

   static belongsTo = [clinic: Clinic]

}
