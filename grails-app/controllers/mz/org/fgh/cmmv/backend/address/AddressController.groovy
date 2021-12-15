package mz.org.fgh.cmmv.backend.address

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

class AddressController extends RestfulController{

    AddressService addressService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    AddressController() {
        super(Address)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        JSON.use('deep'){
            render addressService.list(params) as JSON
        }
    }

    def show(Long id) {
        JSON.use('deep'){
            render addressService.get(id) as JSON
        }
    }

    @Transactional
    def save(Address address) {
        if (address == null) {
            render status: NOT_FOUND
            return
        }
        if (address.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond address.errors
            return
        }

        try {
            addressService.save(address)
        } catch (ValidationException e) {
            respond address.errors
            return
        }

        respond address, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Address address) {
        if (address == null) {
            render status: NOT_FOUND
            return
        }
        if (address.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond address.errors
            return
        }

        try {
            addressService.save(address)
        } catch (ValidationException e) {
            respond address.errors
            return
        }

        respond address, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || addressService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
