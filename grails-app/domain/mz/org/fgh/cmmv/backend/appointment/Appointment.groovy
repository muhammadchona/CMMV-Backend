package mz.org.fgh.cmmv.backend.appointment

import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.utente.Utente

class Appointment {

    Date appointmentDate
    String time
    boolean hasHappened
    int orderNumber
    String status
    Date visitDate
    Clinic clinic

    static belongsTo = [utente: Utente]

    static constraints = {
        appointmentDate(nullable: false, blank: true)
        time nullable:false
        orderNumber nullable:false
        visitDate(nullable: true, blank: true, validator: { visitDate, urc ->
            return visitDate != null ? visitDate <= new Date() : null
        })
        status nullable: false , inList: ['PENDENTE','CONFIRMADO','REMARCADO']
    }
}
