package mz.org.fgh.cmmv.backend.utente

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.clinic.ClinicService
import mz.org.fgh.cmmv.backend.messages.MessageService
import mz.org.fgh.cmmv.backend.mobilizer.ICommunityMobilizerService

//import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class UtenteController extends RestfulController{

    IUtenteService utenteService
    ClinicService clinicService
    ICommunityMobilizerService communityMobilizerService
    def smsService
    MessageService messageService;

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    String codePrefixMz ="+258"
    String twilioPhoneNumber = "+12055486394"

    UtenteController() {
        super(Utente)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        //     JSON.use('deep'){
        respond utenteService.list(params)
        //    }
    }

    def show(Long id) {
        //      JSON.use('deep'){
        respond utenteService.get(id)
        //      }
    }

    def search(String systemNumber){
        System.println("Passa por aqui "+systemNumber)
        JSON.use('deep'){
            render Utente.findBySystemNumber(systemNumber) as JSON
        }
    }

    @Transactional
    def save(Utente utente) {
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
            utente.setSystemNumber(utente.getFirstNames().substring(0,1)+utente.getLastNames().substring(0,1)+"-"+utente.getCellNumber())
            utenteService.save(utente)
            /*  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
              System.out.println(message.getSid());*/

            //     String messaging = "Muito Obrigado por ter se cadastrado na Aplicação de circuncisão masculina. O seu codigo de utente é:"+""+utente.getSystemNumber();

            String messaging = "Muito Obrigado por ter se cadastrado na Aplicacao de circuncisao masculina.O seu codigo de utente:"+utente.getSystemNumber();

            mz.org.fgh.cmmv.backend.messages.Message message =  buildMessage(utente , messaging)
            messageService.save(message);

            //    def map = [to:"+2588444644422",from:"+12055486394",body:messaging]
            //    smsService.send(map)
            Message.creator(new PhoneNumber(codePrefixMz+utente.getCellNumber()),
                    new PhoneNumber(twilioPhoneNumber),
                    messaging).create();
        } catch (ValidationException e) {
            respond utente.errors
            return
        }

        respond utente, [status: CREATED, view:"show"]
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
            utenteService.save(utente)
        } catch (ValidationException e) {
            respond utente.errors
            return
        }

        respond utente, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || utenteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def updateUtentes (List<Utente> users) {
        try {
            Utente.list()
        } catch (ValidationException e) {
            respond users.errors
            return
        }
        respond users, [status: OK, view:"show"]
    }

    def searchByClinicId(Long id){
        JSON.use('deep'){
            Clinic clinic = clinicService.get(id)
            render Utente.findAllByClinic(clinic) as JSON
        }
    }
    def searchByMobilizerId(Long communityMobilizerId){
        /*     JSON.use('deep'){
                 CommunityMobilizer communityMobilizer = communityMobilizerService.get(id)
                 render Utente.findAllByCommunityMobilizer(communityMobilizer) as JSON
             }*/
        //   UtenteService.fin
        //    def utentes = utenteService.getAllByMobilizerId(communityMobilizerId)
        //   render Utilities.parseToJSON(utentes)
        respond utenteService.getAllByMobilizerId(communityMobilizerId)
        //JSON.use('deep') {
        //    render utenteService.getAllByMobilizerId(communityMobilizerId) as JSON
        // }
        //   render utenteService.getAllByMobilizerId(communityMobilizerId) as GSON
    }

    private mz.org.fgh.cmmv.backend.messages.Message buildMessage(Utente utente,String sms) {
        mz.org.fgh.cmmv.backend.messages.Message message = new mz.org.fgh.cmmv.backend.messages.Message();
        message.setUser(utente);
        message.setDescription(sms);
        message.setMessageType("SMS")
        message.setSmsDate(new Date())
        return message;
    }
}