import mz.org.fgh.cmmv.backend.protection.*
// Place your Spring DSL code here
beans = {
    secUserPasswordEncoderListener(SecUserPasswordEncoderListener)
    corsFilterTest(CorsFilter)
    accessTokenJsonRenderer(CustomAppRestAuthTokenJsonRenderer)
    customerSecurityEventListener(CustomSecurityEventListener)
}
