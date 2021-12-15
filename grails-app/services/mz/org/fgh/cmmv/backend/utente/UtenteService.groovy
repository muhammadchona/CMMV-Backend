package mz.org.fgh.cmmv.backend.utente

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import mz.org.fgh.cmmv.backend.mobilizer.CommunityMobilizer

@Transactional
@Service(Utente)
abstract class UtenteService  implements IUtenteService{

    @Override
    List<Utente> getAllByMobilizerId(Long communityMobilizerId) {
        return Utente.findAllByCommunityMobilizer(CommunityMobilizer.findById(communityMobilizerId))
    }
}
