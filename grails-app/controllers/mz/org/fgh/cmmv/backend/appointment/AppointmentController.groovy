package mz.org.fgh.cmmv.backend.appointment

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
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer
import mz.org.fgh.cmmv.backend.utente.Utente
import mz.org.fgh.cmmv.backend.utente.UtenteController
import mz.org.fgh.cmmv.backend.utente.IUtenteService
import mz.org.fgh.cmmv.backend.utente.UtenteService
import mz.org.fgh.cmmv.backend.utilities.JSONSerializer
import mz.org.fgh.cmmv.backend.utilities.Utilities
import org.hibernate.Hibernate

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import org.apache.commons.lang3.time.DateUtils;
import grails.gorm.transactions.Transactional

class AppointmentController extends RestfulController {

    AppointmentService appointmentService
    ClinicService clinicService
    FrontlineSmsDetailsService frontlineSmsDetailsService
    UtenteService utenteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    String codePrefixMz = "+258"

    AppointmentController() {
        super(Appointment)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(appointmentService.list(params)) as JSON
    }

    def getAllOfUtente(Long utenteId) {
        params.max = Math.min(max ?: 10, 100)
        Utente utente
        utente.setId(utenteId)
        render JSONSerializer.setObjectListJsonResponse(Appointment.findAllByUtente(utente)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(appointmentService.get(id)) as JSON
    }

    @Transactional
    def save(Appointment appointment) {
        if (appointment == null) {
            render status: NOT_FOUND
            return
        }
        if (appointment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond appointment.errors
            return
        }

        try {
            if (appointment.getId() <= 0) {

                Collection<Appointment> dbappointments = Appointment.findAllByAppointmentDate(appointment.getAppointmentDate())
                if (dbappointments != null && dbappointments.size() > 0) {
                    appointment.setOrderNumber(dbappointments.size() + 1)
                } else {
                    appointment.setOrderNumber(1)
                }
            }
            appointmentService.save(appointment)
        } catch (ValidationException e) {
            respond appointment.errors
            return
        }

        respond appointment, [status: CREATED, view: "show"]
    }

    @Transactional
    def update(Appointment appointment) {
        if (appointment == null) {
            render status: NOT_FOUND
            return
        }
        if (appointment.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond appointment.errors
            return
        }

        try {
            if(appointment.getStatus() == 'CONFIRMADO' && !appointment.isHasHappened() && !appointment.isSmsSent()) {
                appointment.setSmsSent(true)
                buildSmsFrontline(appointment)
            }
            appointmentService.save(appointment)
        } catch (ValidationException e) {
            respond appointment.errors
            return
        }

        respond appointment, [status: OK, view: "show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || appointmentService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    // Retornar as consultas duma determinada clinic/US
    def searcAppointmentsByClinicId(Long id) {
        Clinic clinic = clinicService.get(id)
        render JSONSerializer.setObjectListJsonResponse(Appointment.findAllByClinic(clinic)) as JSON
    }

    // Retornar o utente da consulta
    def getUtenteForAppointment(Long appointmentId) {
        Appointment appointment1 = appointmentService.get(appointmentId)
        render JSONSerializer.setJsonObjectResponse(Utente.get(appointment1.utenteId)) as JSON
    }

    def searchAppointmentsByClinicAndDates(Long id) {
        Date currentDate = Utilities.getCurrentDate()
        Date startDate = Utilities.getDateOfPreviousDays(currentDate, 90)
        Date endDate = Utilities.getDateOfForwardDays(currentDate, 90)
        Clinic clinic = clinicService.get(id)
        render JSONSerializer.setObjectListAppointmentJsonResponse(Appointment.findAllByClinicAndAppointmentDateBetween(clinic, startDate, endDate)) as JSON
    }

    def searchUtenteById(Long utenteId) {
        def utente = utenteService.get(utenteId)
        println(utente as JSON)
        render 'utente'
    }


    def searchAppointmentsByClinicDistrictId(Long districtId) {
        District district = District.findById(districtId)
        List<Clinic> clinics = Clinic.findAllByDistrict(district)
        List<Appointment> appointments = Appointment.findAllByClinicInList(clinics)
        render JSONSerializer.setObjectListJsonResponse(appointments) as JSON
    }


    def searchAppointmentsByUtenteId(Long utenteId) {
        Utente utente = Utente.findById(utenteId)
        Appointment appointments = Appointment.findByUtente(utente)
        if (appointments != null) {
            render JSONSerializer.setJsonObjectResponse(appointments) as JSON
        } else {
            render status: NOT_FOUND
            return
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
