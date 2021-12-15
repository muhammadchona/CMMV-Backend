package mz.org.fgh.cmmv.backend.messages

import grails.gorm.services.Service

@Service(TwilioDetail)
interface TwilioDetailService {

    TwilioDetail get(Serializable id)

    List<TwilioDetail> list(Map args)

    Long count()

    TwilioDetail delete(Serializable id)

    TwilioDetail save(TwilioDetail twilioDetail)

}
