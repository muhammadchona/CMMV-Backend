package mz.org.fgh.cmmv.backend.appointment

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.clinic.ClinicService
import mz.org.fgh.cmmv.backend.utente.Utente
import mz.org.fgh.cmmv.backend.utilities.JSONSerializer
import mz.org.fgh.cmmv.backend.utilities.Utilities

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import org.apache.commons.lang3.time.DateUtils;
import grails.gorm.transactions.Transactional

class AppointmentController extends RestfulController{

    AppointmentService appointmentService
    ClinicService clinicService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

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
            if (appointment.getId() <= 0){

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

        respond appointment, [status: CREATED, view:"show"]
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
            appointmentService.save(appointment)
        } catch (ValidationException e) {
            respond appointment.errors
            return
        }

        respond appointment, [status: OK, view:"show"]
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
    def searcAppointmentsByClinicId(Long id){
        Clinic clinic = clinicService.get(id)
        render JSONSerializer.setObjectListJsonResponse(Appointment.findAllByClinic(clinic)) as JSON
    }

    // Retornar o utente da consulta
    def getUtenteForAppointment(Long appointmentId){
        Appointment appointment1 = appointmentService.get(appointmentId)
        render JSONSerializer.setJsonObjectResponse(Utente.get(appointment1.utenteId)) as JSON
    }

    def searchAppointmentsByClinicAndDates(Long id){
        Date currentDate = Utilities.getCurrentDate()
        Date startDate= Utilities.getDateOfPreviousDays(currentDate , 90)
        Date endDate= Utilities.getDateOfForwardDays(currentDate , 90)
        Clinic clinic = clinicService.get(id)
        render JSONSerializer.setObjectListJsonResponse(Appointment.findAllByClinicAndAppointmentDateBetween(clinic,startDate,endDate)) as JSON
    }




}
