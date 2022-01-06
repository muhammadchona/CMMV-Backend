// Added by the Spring Security Core plugin:

grails.plugin.springsecurity.password.algorithm = 'bcrypt'
grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/'

grails.plugin.springsecurity.userLookup.userDomainClassName = 'mz.org.fgh.cmmv.backend.protection.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'mz.org.fgh.cmmv.backend.protection.SecUserSecRole'
grails.plugin.springsecurity.authority.className = 'mz.org.fgh.cmmv.backend.protection.SecRole'

grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/', 					access: ['permitAll']],
        [pattern: '/error', 			access: ['permitAll']],
        [pattern: '/index', 			access: ['permitAll']],
        [pattern: '/index.gsp', 		access: ['permitAll']],
        [pattern: '/shutdown', 			access: ['permitAll']],
        [pattern: '/assets/**', 		access: ['permitAll']],
        [pattern: '/**/js/**', 			access: ['permitAll']],
        [pattern: '/**/css/**', 		access: ['permitAll']],
        [pattern: '/**/images/**', 		access: ['permitAll']],
        [pattern: '/**/favicon.ico', 	access: ['permitAll']],
        [pattern: '/api/province', 		access: ['IS_AUTHENTICATED_ANONYMOUSLY']],
        [pattern: '/**/**', 			access: ['permitAll']],
        [pattern: '/api/', 				access: ['permitAll']],
        [pattern: '/api/management/**', access: ['isAuthenticated()']],
        [pattern: '/api/login', 		access: ['IS_AUTHENTICATED_ANONYMOUSLY']],
        [pattern: '/api/logout', 		access: ['isAuthenticated()']],
        [pattern: '/dbconsole/**',		access: ['permitAll']],
        [pattern: '/plugins/**', 		access: ['permitAll']],
        [pattern: '/secUser/**', 		access: ['permitAll']],
        [pattern: '/user/**', 			access: ['ROLE_ADMIN']],
        [pattern: '/secRole/**', 		access: ['ROLE_ADMIN']],
        [pattern: '/role/**', 			access: ['ROLE_ADMIN']],
        [pattern: '/securityInfo/**', 	access: ['ROLE_ADMIN']],
        [pattern: '/registationCode/**', access: ['ROLE_ADMIN']]

]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/auth', 			 filters: 'none'],
        [pattern: '/**',             filters: 'JOINED_FILTERS'],


]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/api/province',   filters:'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
        [pattern: '/api/district',   filters:'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
        [pattern: '/api/clinic',   filters:'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
        [pattern: '/api/**', 	filters:'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter'],
        [pattern: '/**', 		filters:'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter']
]



grails.plugin.springsecurity.rest.token.storage.useJwt=true
grails.plugin.springsecurity.rest.token.storage.jwt.useSignedJwt=true
grails.plugin.springsecurity.rest.token.storage.jwt.useEncryptedJwt = true
grails.plugin.springsecurity.rest.token.storage.jwt.secret = 'edK2k1P0D4770W56B6Rckf1TErImPWcu'

grails.plugin.springsecurity.rest.token.validation.useBearerToken = false
grails.plugin.springsecurity.rest.token.validation.headerName = 'X-Auth-Token'

grails.plugin.springsecurity.rest.token.rendering.usernamePropertyName='id'

grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/logout'

grails.plugin.springsecurity.logout.postOnly = false

grails.plugin.springsecurity.rejectIfNoRule = false

grails.plugins.springsecurity.password.bcrypt.logrounds = 12
