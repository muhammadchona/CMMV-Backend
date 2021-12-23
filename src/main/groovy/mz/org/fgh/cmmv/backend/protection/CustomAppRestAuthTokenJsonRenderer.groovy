package mz.org.fgh.cmmv.backend.protection

import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.rendering.AccessTokenJsonRenderer
import groovy.json.JsonBuilder
import mz.org.fgh.cmmv.backend.userLogin.MobilizerLogin
import mz.org.fgh.cmmv.backend.userLogin.UserLogin
import mz.org.fgh.cmmv.backend.userLogin.UtenteLogin
import mz.org.fgh.cmmv.backend.utente.Utente
import org.springframework.security.core.GrantedAuthority

/**
 * Created by Prakash Thete on 17/04/2018
 */
class CustomAppRestAuthTokenJsonRenderer implements AccessTokenJsonRenderer  {

//    @Override
    String generateJson(AccessToken accessToken){
        def mainEntityAssociated = null
        def secUser = null
        SecUser.withTransaction {
            secUser = SecUser.get(accessToken.principal.id)

            if(MobilizerLogin.get(secUser.id))
                mainEntityAssociated = MobilizerLogin.get(secUser.id).district
            else
                if(UserLogin.get(secUser.id))
                    mainEntityAssociated = UserLogin.get(secUser.id)
            else
                    if(UtenteLogin.get(secUser.id))
                        mainEntityAssociated = "EMPTY - USER SELF REGISTRATION"
            else mainEntityAssociated = "ADMIN/TESTER"

        }
        // Add extra custom parameters if you want in this map to be rendered in login response
        Map response = [
                id           : accessToken.principal.id,
                username     : accessToken.principal.username,
                access_token : accessToken.accessToken,
                token_type   : "Bearer",
                refresh_token: accessToken.refreshToken,
                password : secUser.password,
                roles        : accessToken.authorities.collect { GrantedAuthority role -> role.authority },
                mainEntity   : mainEntityAssociated
        ]

        return new JsonBuilder( response ).toPrettyString()
    }
}
