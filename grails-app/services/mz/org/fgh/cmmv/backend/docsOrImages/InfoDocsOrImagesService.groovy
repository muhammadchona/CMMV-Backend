package mz.org.fgh.cmmv.backend.docsOrImages

import grails.gorm.services.Service

@Service(InfoDocsOrImages)
interface InfoDocsOrImagesService {

    InfoDocsOrImages get(Serializable id)

    List<InfoDocsOrImages> list(Map args)

    Long count()

    InfoDocsOrImages delete(Serializable id)

    InfoDocsOrImages save(InfoDocsOrImages infoDocsOrImages)

}
