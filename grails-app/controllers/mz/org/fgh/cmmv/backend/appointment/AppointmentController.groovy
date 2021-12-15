package mz.org.fgh.cmmv.backend.appointment

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.clinic.ClinicService
import mz.org.fgh.cmmv.backend.utente.Utente

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

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

        JSON.use('deep'){
            render appointmentService.list(params) as JSON
        }
    }

    def getAllOfUtente(Long utenteId) {
        params.max = Math.min(max ?: 10, 100)
        Utente utente
        utente.setId(utenteId)
        JSON.use('deep'){
            render Appointment.findAllByUtente(utente) as JSON
        }
    }

    def show(Long id) {

        JSON.use('deep'){
            render appointmentService.get(id) as JSON
        }
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

    def searcAppointmentsByClinicId(Long id){
        JSON.use('deep'){
            Clinic clinic = clinicService.get(id)
        render Appointment.findAllByClinic(clinic) as JSON
        }
    }
}
