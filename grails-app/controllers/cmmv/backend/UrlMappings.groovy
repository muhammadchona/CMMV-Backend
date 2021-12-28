package cmmv.backend

class UrlMappings {

    static mappings = {
        delete "/api/$controller/$id(.$format)?"(action:"delete")
        get "/api/$controller(.$format)?"(action:"index")
        get "/api/$controller/$id(.$format)?"(action:"show")
        post "/api/$controller(.$format)?"(action:"save")
        put "/api/$controller/$id(.$format)?"(action:"update")
        patch "/api/$controller/$id(.$format)?"(action:"patch")

        // add mapping for Utente search by param systemNumber clinjc and appointment
        get "/api/utente/$systemNumber(.$format)?"(controller:'utente', action:'search')
        get "/api/utente/clinic/$id(.$format)?"(controller:'utente', action:'searchByClinicId')
        get "/api/appointment/clinic/$id(.$format)?"(controller:'appointment', action:'searcAppointmentsByClinicId')
        get "/api/utente/communityMobilizer/$communityMobilizerId(.$format)?"(controller:'utente', action:'searchByMobilizerId')
        get "/api/communityMobilizer/district/$districtId(.$format)?"(controller:'communityMobilizer', action:'searchByDistrictId')
        "/"(view:"/login/auth")
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
