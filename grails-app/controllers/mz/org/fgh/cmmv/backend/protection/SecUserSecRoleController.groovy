package mz.org.fgh.cmmv.backend.protection

import grails.rest.RestfulController
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class SecUserSecRoleController extends RestfulController{

    SecUserSecRoleService secUserSecRoleService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    SecUserSecRoleController() {
        super(SecUserSecRole)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secUserSecRoleService.list(params), model:[secUserSecRoleCount: secUserSecRoleService.count()]
    }

    def show(Long id) {
        respond secUserSecRoleService.get(id)
    }

    @Transactional
    def save(SecUserSecRole secUserSecRole) {
        if (secUserSecRole == null) {
            render status: NOT_FOUND
            return
        }
        if (secUserSecRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secUserSecRole.errors
            return
        }

        try {
            secUserSecRoleService.save(secUserSecRole)
        } catch (ValidationException e) {
            respond secUserSecRole.errors
            return
        }

        respond secUserSecRole, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SecUserSecRole secUserSecRole) {
        if (secUserSecRole == null) {
            render status: NOT_FOUND
            return
        }
        if (secUserSecRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secUserSecRole.errors
            return
        }

        try {
            secUserSecRoleService.save(secUserSecRole)
        } catch (ValidationException e) {
            respond secUserSecRole.errors
            return
        }

        respond secUserSecRole, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || secUserSecRoleService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
