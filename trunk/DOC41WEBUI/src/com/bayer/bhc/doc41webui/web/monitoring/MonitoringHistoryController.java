/**
 * 
 */
package com.bayer.bhc.doc41webui.web.monitoring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.bayer.bhc.doc41webui.common.paging.PagingResult;
import com.bayer.bhc.doc41webui.container.PagingForm;
import com.bayer.bhc.doc41webui.domain.Monitor;
import com.bayer.bhc.doc41webui.domain.User;
import com.bayer.bhc.doc41webui.usecase.MonitoringUC;
import com.bayer.bhc.doc41webui.web.PagingListController;

/**
 * @author MBGHY
 *
 */
public class MonitoringHistoryController extends PagingListController {

	private static final String INTERFACE_NAME = "serviceName";
	private static final String MONITORING_HISTORY_BY_INTERFACE = "monitoringHistoryByInterface";
	private static final String INTERFACE_DETAILS = "service";
	
	/**
     * monitoringUC The spring managed bean<code>MonitoringUC</code>.
     */
    private MonitoringUC monitoringUC;

    /**
     * @return the monitoringUC
     */
    public MonitoringUC getMonitoringUC() {
        return monitoringUC;
    }

    /**
     * @param monitoringUC the monitoringUC to set
     */
    public void setMonitoringUC(final MonitoringUC monitoringUC) {
        this.monitoringUC = monitoringUC;
    }
	
	
	/* (non-Javadoc)
	 * @see com.bayer.bhc.doc41webui.web.Doc41Controller#hasRolePermission(com.bayer.bhc.doc41webui.domain.User)
	 */
	@Override
	protected boolean hasRolePermission(User usr) {
		return usr.isBusinessAdmin() || usr.isTechnicalAdmin();
	}
	
	@Override
	protected ModelAndView getListModel(PagingForm pform,HttpServletRequest request, Object command) throws Exception {
        PagingResult<Monitor> result = getMonitoringUC().findMonitoringHistoryByInterface(getInterfaceName(request),pform);
        pform.setTotalSize(result.getTotalSize());
        return new ModelAndView()
        		.addObject(MONITORING_HISTORY_BY_INTERFACE, result.getResult())
        			.addObject(INTERFACE_DETAILS,getMonitoringUC().findInterfaceDetailsByName(getInterfaceName(request)));
	}
	
	/**
	 * Extracts The InterfaceName from request if it is null, from session.
	 * @param request  The <code>HttpServletRequest</code>
	 * @return Returns The <code>String</code> pInterfaceName
	 */
	private String getInterfaceName(HttpServletRequest request) {
		String pInterfaceName = request.getParameter(INTERFACE_NAME);
		if(pInterfaceName!=null){
			request.getSession().setAttribute(INTERFACE_NAME,pInterfaceName);
		}else{
			pInterfaceName = (String) request.getSession().getAttribute(INTERFACE_NAME);
		}
		return pInterfaceName;
	}
	
}
