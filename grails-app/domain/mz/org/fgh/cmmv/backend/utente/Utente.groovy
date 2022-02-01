package mz.org.fgh.cmmv.backend.utente


import mz.org.fgh.cmmv.backend.address.Address
import mz.org.fgh.cmmv.backend.appointment.Appointment
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.docsOrImages.InfoDocsOrImages
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer
import mz.org.fgh.cmmv.backend.userLogin.UserLogin

class Utente {

    String firstNames
    String lastNames
    Date birthDate
    String cellNumber
    String whatsappNumber
    String preferedLanguage
    String documentType
    String documentNumber
    String systemNumber
    Date registerDate
    String status
    String syncStatus

   // @JsonBackReference
    CommunityMobilizer communityMobilizer

   // @JsonIgnore
    UserLogin user

    boolean haspartner

  //  @JsonManagedReference
    static belongsTo = [clinic: Clinic]

    static hasMany = [infoDocsImages: InfoDocsOrImages, addresses:Address , appointments: Appointment]

    static fetchMode = [address: 'eager']

    static constraints = {
        lastNames(nullable: false, blank: false)
        birthDate(nullable: false, blank: true, validator: { birthDate, urc ->
            return birthDate != null ? birthDate <= new Date() : null
        })
        cellNumber(nullable: false, matches: /\d+/, maxSize: 12, minSize: 9)
        whatsappNumber(nullable: true, matches: /\d+/, maxSize: 12, minSize: 9)
        documentType(nullable: true, blank: true)
        documentNumber(nullable: true, blank: true)
        systemNumber(nullable: true, blank: true, unique: true)
        syncStatus(nullable: true)
        preferedLanguage(nullable: true)
        infoDocsImages(nullable: true)
        communityMobilizer(nullable: true)
        clinic(nullable: true)
        user(nullable: true)
    }
}

