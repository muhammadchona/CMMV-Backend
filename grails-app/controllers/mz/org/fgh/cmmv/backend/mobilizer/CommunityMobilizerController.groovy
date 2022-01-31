package mz.org.fgh.cmmv.backend.mobilizer

import grails.converters.JSON
import grails.rest.RestfulController
import grails.validation.ValidationException
import mz.org.fgh.cmmv.backend.distribuicaoAdministrativa.District
import mz.org.fgh.cmmv.backend.protection.ISecRoleService
import mz.org.fgh.cmmv.backend.protection.SecRole
import mz.org.fgh.cmmv.backend.protection.SecRoleController
import mz.org.fgh.cmmv.backend.protection.SecUser
import mz.org.fgh.cmmv.backend.protection.SecUserSecRole
import mz.org.fgh.cmmv.backend.protection.SecUserService
import mz.org.fgh.cmmv.backend.userLogin.MobilizerLogin
import mz.org.fgh.cmmv.backend.utilities.JSONSerializer

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.Transactional

class CommunityMobilizerController extends RestfulController{

    ICommunityMobilizerService communityMobilizerService
    SecRoleController secRoleController
    SecUserService secUserService

   // Utente utenteService
    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    CommunityMobilizerController() {
        super(CommunityMobilizer)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        render JSONSerializer.setObjectListJsonResponse(communityMobilizerService.list(params)) as JSON
    }

    def show(Long id) {
        render JSONSerializer.setJsonObjectResponse(communityMobilizerService.get(id)) as JSON
    }

    @Transactional
    def save(CommunityMobilizer communityMobilizer) {
        if (communityMobilizer == null) {
            render status: NOT_FOUND
            return
        }
        if (communityMobilizer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond communityMobilizer.errors
            return
        }

        try {
            communityMobilizerService.save(communityMobilizer)

            MobilizerLogin mobilizerLogin = new MobilizerLogin()
            mobilizerLogin.setUsername(communityMobilizer.getFirstNames().substring(0,1)+''+communityMobilizer.getLastNames())
            mobilizerLogin.setFullName(communityMobilizer.getFirstNames() +' '+communityMobilizer.getLastNames())
            mobilizerLogin.setPassword('admin')
            mobilizerLogin.setMobilizer(communityMobilizer)
            mobilizerLogin.setProvince(communityMobilizer.getDistrict().getProvince())
            mobilizerLogin.setDistrict(communityMobilizer.getDistrict())
            SecRole secRole = SecRole.findByAuthority('ROLE_MOBILIZER')
            secUserService.save(mobilizerLogin)
            SecUserSecRole.create mobilizerLogin, secRole
        } catch (ValidationException e) {
            respond communityMobilizer.errors
            return
        }

        respond communityMobilizer, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(CommunityMobilizer communityMobilizer) {
        if (communityMobilizer == null) {
            render status: NOT_FOUND
            return
        }
        if (communityMobilizer.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond communityMobilizer.errors
            return
        }

        try {
            communityMobilizerService.save(communityMobilizer)
        } catch (ValidationException e) {
            respond communityMobilizer.errors
            return
        }

        respond communityMobilizer, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || communityMobilizerService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }

    def searchByDistrictId(Long districtId){
        District district = District.findById(districtId)
        render JSONSerializer.setObjectListJsonResponse(CommunityMobilizer.findAllByDistrict(district)) as JSON
       // respond communityMobilizerService.getAllByDistrictId(districtId)
    }
}
