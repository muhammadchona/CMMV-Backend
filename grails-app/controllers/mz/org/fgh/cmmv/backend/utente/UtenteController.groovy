package mz.org.fgh.cmmv.backend.utente

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.cmmv.backend.sms.PayloadSms
import mz.org.cmmv.backend.sms.RecipientSms
import mz.org.cmmv.backend.sms.RestFrontlineSms
import mz.org.cmmv.backend.sms.SmsRequest
import mz.org.fgh.cmmv.backend.address.Address
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.clinic.ClinicService
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.messages.FrontlineSmsDetailsService
import mz.org.fgh.cmmv.backend.messages.MessageService
import mz.org.fgh.cmmv.backend.mobilizer.ICommunityMobilizerService

//import com.twilio.Twilio;

import mz.org.fgh.cmmv.backend.utilities.JSONSerializer
import mz.org.fgh.cmmv.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class UtenteController extends RestfulController {

    IUtenteService utenteService
    ClinicService clinicService
    ICommunityMobilizerService communityMobilizerService
    def smsService
    MessageService messageService;
    FrontlineSmsDetailsService frontlineSmsDetailsService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    String codePrefixMz = "+258"
    String defaultMessage= "Muito obrigado por se cadastrar na aplicacao de Circuncisao Masculina. O seu codigo de utente e: "

    UtenteController() {
        super(Utente)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(utenteService.list(params)) as JSON

    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(utenteService.get(id)) as JSON
    }

    def search(String systemNumber) {
        Utente utente = Utente.findBySystemNumber(systemNumber);
        if (utente != null) {
            render JSONSerializer.setJsonObjectResponse(utente) as JSON
        } else {
            render status: NOT_FOUND
            return
        }


    }

    @Transactional
    def save(Utente utente) {
        utente.beforeInsert()
        if (utente == null) {
            render status: NOT_FOUND
            return
        }
        if (utente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond utente.errors
            return
        }

        try {
            // utente.setUser(new UtenteLogin())
//            utente.getUser().setUsername(utente.getFirstNames())
            //      utente.getUser().setPassword(utente.getLastNames())
            //     utente.getUser().setUtente(utente)
          //  utente.setSystemNumber(utente.getFirstNames().substring(0, 1) + utente.getLastNames().substring(0, 1) + "-" + utente.getCellNumber())
            String messaging = defaultMessage+""+utente.getSystemNumber()
         //   buildSmsFrontline(utente,messaging)
            utenteService.save(utente)
            mz.org.fgh.cmmv.backend.messages.Message message =  buildMessage(utente , messaging)
         //   messageService.save(message)
            /*  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
              System.out.println(message.getSid());*/

            //
         //   mz.org.fgh.cmmv.backend.messages.Message message =  buildMessage(utente , messaging)
            /*   String messaging = "Muito Obrigado por ter se cadastrado na Aplicacao de circuncisao masculina.O seu codigo de utente:"+utente.getSystemNumber();



               //    def map = [to:"+2588444644422",from:"+12055486394",body:messaging]
               //    smsService.send(map)
               Message.creator(new PhoneNumber(codePrefixMz+utente.getCellNumber()),
                       new PhoneNumber(twilioPhoneNumber),
                       messaging).create();*/
        } catch (ValidationException e) {
            respond utente.errors
            return
        }

        respond utente, [status: CREATED, view: "show"]
    }

    @Transactional
    def update(Utente utente) {
        if (utente == null) {
            render status: NOT_FOUND
            return
        }
        if (utente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond utente.errors
            return
        }

        try {
            utente.setSystemNumber(utente.getFirstNames().substring(0, 1) + utente.getLastNames().substring(0, 1) + "-" + utente.getCellNumber())
            utenteService.save(utente)
        } catch (ValidationException e) {
            respond utente.errors
            return
        }

        respond utente, [status: OK, view: "show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || utenteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def searchByClinicId(Long id) {
        Clinic clinic = clinicService.get(id)
        render JSONSerializer.setObjectListJsonResponse(Utente.findAllByClinic(clinic)) as JSON
    }

    def searchByMobilizerId(Long communityMobilizerId) {
        render JSONSerializer.setObjectListJsonResponse(utenteService.getAllByMobilizerId(communityMobilizerId)) as JSON
    }

    def searchUtenteById(Long utenteId) {
        def utente = utenteService.get(utenteId)
        println(utente as JSON)
        render 'utente'
    }

    def searchClinicByUtente(Long utenteId) {
        def utente = utenteService.get(utenteId)
        println(utente?.clinic as JSON)
        render 'utente?.clinic as JSON'
    }

    def searchAddressesForUtente(Long utenteId) {
        def utente = utenteService.get(utenteId)
        println(utente.address as JSON)
        render utente.address as JSON
    }

    def searchByAddressDistrictId(Long districtId) {
        District district = District.findById(districtId)
        List<Address> addresses = Address.findAllByDistrict(district)
        Set<Utente> utentes = new HashSet<>()
        for (Address address : addresses) {
            utentes.add(address.getUtente())
        }
        List<Utente> utentesList = new ArrayList<>(utentes)
        render JSONSerializer.setObjectListJsonResponse(utentesList) as JSON
    }

    private mz.org.fgh.cmmv.backend.messages.Message buildMessage(Utente utente, String sms) {
        mz.org.fgh.cmmv.backend.messages.Message message = new mz.org.fgh.cmmv.backend.messages.Message();
        message.setUser(utente);
        message.setDescription(sms);
        message.setMessageType("SMS")
        message.setSmsDate(new Date())
        return message;
    }

    private SmsRequest buildSmsFrontline(Utente utente, String sms) {
        def frontLineSmsDetail = frontlineSmsDetailsService.list().get(0)

        SmsRequest smsRequest = new SmsRequest()
        PayloadSms payloadSms = new PayloadSms()
        RecipientSms recipientSMS = new RecipientSms()
        recipientSMS.setType("mobile")
        recipientSMS.setValue(codePrefixMz+utente.getCellNumber())
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
