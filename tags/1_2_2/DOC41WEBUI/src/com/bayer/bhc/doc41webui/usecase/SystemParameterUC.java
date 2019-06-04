package com.bayer.bhc.doc41webui.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.exception.Doc41RepositoryException;
import com.bayer.bhc.doc41webui.common.logging.Doc41Log;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.service.Doc41MonitorService;
import com.bayer.bhc.doc41webui.service.repository.SystemParameterRepository;

@Component
public class SystemParameterUC {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @Autowired
    private Doc41MonitorService monitorService;

    public Doc41MonitorService getMonitorService() {
        return monitorService;
    }



    /**
     * @return new unique Correlation-ID
     * @throws Doc41BusinessException
     */
    public String getDOC41CorrelationId() throws Doc41BusinessException {
        Long oid;
        try {
            oid = systemParameterRepository.getOID();
            return ""+oid;
        } catch (Doc41RepositoryException e) {
            throw new Doc41BusinessException("getDOC41CorrelationId", e);
        }
    }

    public Boolean isDBSessionPersistence() {
        try {			
            return systemParameterRepository.isDBSessionPersistence();
        } catch (Doc41RepositoryException e) {
            Doc41Log.get().error(getClass(), UserInSession.getCwid(), e);
            return false;
        }
    }


}
