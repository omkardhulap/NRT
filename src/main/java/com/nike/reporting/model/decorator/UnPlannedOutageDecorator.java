/**
 * 
 */
package com.nike.reporting.model.decorator;

import org.displaytag.decorator.TableDecorator;

import com.nike.reporting.model.dto.OutageDTO;
import com.nike.reporting.util.ReportingConstants;


/**
 * @author Sachin_Ainapure
 *
 */
public class UnPlannedOutageDecorator extends TableDecorator {
	
	
	
	public String getOutageType() {
		OutageDTO UPOutageData = (OutageDTO) getCurrentRowObject();
		
		if(UPOutageData.getOutageType().equalsIgnoreCase(ReportingConstants.UNPLANNED_OUTAGE_STRING)){
			//return "<a href=\"mailto:"+UPOutageData.getOutageType()+"\">"+UPOutageData.getOutageType()+ "</a>";	
			//return "<a href=\"${pageContext.request.contextPath}/createEOSPptx/${"+UPOutageData.getId()+"}\">" + UPOutageData.getOutageType() + "</a>";
			//return "<a href=\"\\$\\{/createEOSPptx/"+UPOutageData.getId()+"\\}\">" + UPOutageData.getOutageType() + "</a>";
			return UPOutageData.getOutageType();
		}
		return UPOutageData.getOutageType();
	}

}
