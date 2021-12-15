package mz.org.fgh.cmmv.backend.messages

import mz.org.fgh.cmmv.backend.utente.Utente

class Message {

    String description
    String messageType
    Date smsDate

    static belongsTo = [user: Utente]

    static constraints = {

        smsDate(nullable: false, blank: true, validator: { smsDate, urc ->
            return smsDate != null ? smsDate <= new Date() : null
        })
    }
}
