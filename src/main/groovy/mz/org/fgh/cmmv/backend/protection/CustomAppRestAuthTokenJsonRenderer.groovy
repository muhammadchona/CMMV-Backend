package mz.org.fgh.cmmv.backend.protection

import grails.converters.JSON
import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.rendering.AccessTokenJsonRenderer
import groovy.json.JsonBuilder
import mz.org.fgh.cmmv.backend.userLogin.MobilizerLogin
import mz.org.fgh.cmmv.backend.userLogin.UserLogin
import mz.org.fgh.cmmv.backend.userLogin.UtenteLogin
import mz.org.fgh.cmmv.backend.utente.Utente
import org.springframework.security.core.GrantedAuthority

import javax.transaction.Transactional

/**
 * Created by Prakash Thete on 17/04/2018
 */
class CustomAppRestAuthTokenJsonRenderer implements AccessTokenJsonRenderer  {

//    @Override
    @Transactional
    String generateJson(AccessToken accessToken){
        def mainEntityAssociated = ''
        def secUser = null
        def source = ''

        SecUser.withTransaction {
            secUser = SecUser.get(accessToken.principal.id)
            mainEntityAssociated = 1
        }

        MobilizerLogin.withTransaction {
            if(MobilizerLogin.get(secUser.id)?.mobilizer?.id != null) {
                mainEntityAssociated = MobilizerLogin.get(secUser.id).mobilizer.id
                source = 'Mobilizer'
            }
        }

        UserLogin.withTransaction {
            if(UserLogin.get(secUser.id)?.clinic?.id != null) {
                mainEntityAssociated = UserLogin.get(secUser.id).clinic.id
                source = 'Clinic'
            }
        }
        UtenteLogin.withTransaction {
            if(UtenteLogin.get(secUser.id)?.utente?.id != null) {
                mainEntityAssociated = UtenteLogin.get(secUser.id).utente.id
                source = 'Utente'
            }
        }
        /*
        UserLogin.withTransaction {
            mainEntityAssociated = "Clinic:"+ UserLogin.get(secUser.id)?.fullName
        }
        /*
        UtenteLogin.withTransaction {
            mainEntityAssociated = "Utente:"+ UtenteLogin.get(secUser.id)?.fullName
        }/*
          /*  if(MobilizerLogin.get(secUser.id)) {
                mainEntityAssociated = MobilizerLogin.get(secUser.id).district

                println(mainEntityAssociated.description)
                println("Por Mobilizer")
            } else
                if(UserLogin.get(secUser.id)) {
                    mainEntityAssociated = UserLogin.get(secUser.id)
                    println("UserLogin")
                }else
                    if(UtenteLogin.get(secUser.id)) {
                        mainEntityAssociated = "EMPTY - USER SELF REGISTRATION"
                        println("UtenteLogin")
                    }else
                        mainEntityAssociated = "ADMIN/TESTER"
*/
        // Add extra custom parameters if you want in this map to be rendered in login response
        Map response = [
                id           : accessToken.principal.id,
                username     : accessToken.principal.username,
                access_token : accessToken.accessToken,
                token_type   : "Bearer",
                refresh_token: accessToken.refreshToken,
                password : secUser.password,
                roles        : accessToken.authorities.collect { GrantedAuthority role -> role.authority },
                mainEntity   : mainEntityAssociated,
                source       : source
        ]

        return new JsonBuilder( response ).toPrettyString()
    }
}
