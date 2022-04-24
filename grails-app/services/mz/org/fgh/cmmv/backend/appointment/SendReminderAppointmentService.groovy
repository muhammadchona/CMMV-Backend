package mz.org.fgh.cmmv.backend.appointment

import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import mz.org.cmmv.backend.sms.PayloadSms
import mz.org.cmmv.backend.sms.RecipientSms
import mz.org.cmmv.backend.sms.RestFrontlineSms
import mz.org.cmmv.backend.sms.SmsRequest
import mz.org.fgh.cmmv.backend.messages.FrontlineSmsDetailsService
import mz.org.fgh.cmmv.backend.utilities.Utilities
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

import java.text.SimpleDateFormat


@Slf4j
//@CompileStatic
@EnableScheduling
class SendReminderAppointmentService {

    String codePrefixMz = "+258"
    FrontlineSmsDetailsService frontlineSmsDetailsService

    static lazyInit = false

    @Scheduled(cron = "0 0 8 * * *")
    void schedulerRequestRunning() {
        Appointment.withTransaction {
        Date currentDate = Utilities.getCurrentDate()
        Date startDate = Utilities.getDateBegining(Utilities.getDateOfPreviousDays(currentDate, 2))
        Date endDate = Utilities.getDateEnding(Utilities.getDateOfPreviousDays(currentDate, 2))
             List<Appointment> appointmentsList = Appointment.findAllByAppointmentDateBetweenAndStatus(startDate, endDate,'CONFIRMADO')
          // List<Appointment> appointmentsList = Appointment.findAll().findAll { it.appointmentDate > startDate && it.appointmentDate < endDate }
            System.out.println('saving {} at {}' + new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()))
            for (Appointment appointment : appointmentsList) {

                System.out.println('appointmentId' + appointment.getId())

                try {
                    buildSmsFrontline(appointment)
                } catch (Exception e) {
                    e.printStackTrace()
                } finally {
                    continue
                }
            }
        }
    }

    SmsRequest buildSmsFrontline(Appointment appointment) {
        def frontLineSmsDetail = frontlineSmsDetailsService.list(null).get(0)
        String sms = "A sua consulta de circuncisao esta marcada para o dia "+ Utilities.parseDateToYYYYMMDDString(appointment.getAppointmentDate()) +", na Unidade Sanitaria : "+appointment.getClinic().getName()
        SmsRequest smsRequest = new SmsRequest()
        PayloadSms payloadSms = new PayloadSms()
        RecipientSms recipientSMS = new RecipientSms()
        recipientSMS.setType("mobile")
        recipientSMS.setValue(codePrefixMz+appointment.utente.getCellNumber())
        payloadSms.setMessage(sms)
        payloadSms.setRecipients(recipientSMS)
        smsRequest.setPayload(payloadSms)
        smsRequest.setApiKey(frontLineSmsDetail.getApiKey())
        println(smsRequest)
        def obj = Utilities.parseToJSON(smsRequest)
        println(obj)
        RestFrontlineSms.requestSmsSender(obj,frontLineSmsDetail)
        return smsRequest
    }
}
