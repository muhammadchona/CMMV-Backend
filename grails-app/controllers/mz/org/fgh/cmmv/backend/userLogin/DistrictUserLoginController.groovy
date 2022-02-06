package mz.org.fgh.cmmv.backend.userLogin

import grails.converters.JSON
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.clinic.Clinic
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.protection.SecRole
import mz.org.fgh.cmmv.backend.protection.SecUserSecRole
import mz.org.fgh.cmmv.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class DistrictUserLoginController {

    DistrictUserLoginService districtUserLoginService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond districtUserLoginService.list(params), model:[districtUserLoginCount: districtUserLoginService.count()]
    }

    def show(Long id) {
        respond districtUserLoginService.get(id)
    }

    @Transactional
    def save(DistrictUserLogin districtUserLogin) {
        SecRole secRole = SecRole.findByAuthority('ROLE_USER_DISTRICT')
        if (districtUserLogin == null) {
            render status: NOT_FOUND
            return
        }
        if (districtUserLogin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond districtUserLogin.errors
            return
        }

        try {
            districtUserLoginService.save(districtUserLogin)
            SecUserSecRole.create districtUserLogin, secRole
        } catch (ValidationException e) {
            respond districtUserLogin.errors
            return
        }

        respond districtUserLogin, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DistrictUserLogin districtUserLogin) {
        if (districtUserLogin == null) {
            render status: NOT_FOUND
            return
        }
        if (districtUserLogin.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond districtUserLogin.errors
            return
        }

        try {
            districtUserLoginService.save(districtUserLogin)
        } catch (ValidationException e) {
            respond districtUserLogin.errors
            return
        }

        respond districtUserLogin, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || districtUserLoginService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def searchAllUsersByDistrictId(Long districtId){
        District district = District.findById(districtId)
        render JSONSerializer.setObjectListJsonResponse(DistrictUserLogin.findAllByDistrict(district)) as JSON
        // respond communityMobilizerService.getAllByDistrictId(districtId)
    }
}
