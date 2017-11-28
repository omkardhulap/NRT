/**
 * 
 */
package com.nike.reporting.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.nike.reporting.dao.ProblemManagementDAO;
import com.nike.reporting.exceptions.DateParsingException;
import com.nike.reporting.model.ProblemManagement;
import com.nike.reporting.model.SearchProblemManagement;

/**
 * Problem Management Service Impl class.
 *
 */
public class ProblemManagementServiceImpl implements ProblemManagementService {
	
	private ProblemManagementDAO problemManagementDAO;

	public void setProblemManagementDAO(ProblemManagementDAO problemManagementDAO) {
		this.problemManagementDAO = problemManagementDAO;
	}

	/* (non-Javadoc)
	 * @see com.nike.reporting.service.ProblemService#addProblemManagement(com.nike.reporting.model.ProblemManagement)
	 */
	@Override
	@Transactional
	public void addProblemManagement(ProblemManagement p) {
		this.problemManagementDAO.addProblemManagement(p);
	}

	/* (non-Javadoc)
	 * @see com.nike.reporting.service.ProblemService#updateProblemManagement(com.nike.reporting.model.ProblemManagement)
	 */
	@Override
	@Transactional
	public void updateProblemManagement(ProblemManagement p) {
		this.problemManagementDAO.updateProblemManagement(p);
	}

	/* (non-Javadoc)
	 * @see com.nike.reporting.service.ProblemService#getProblemManagementById(int)
	 */
	@Override
	@Transactional
	public ProblemManagement getProblemManagementById(int id) {
		return this.problemManagementDAO.getProblemManagementById(id);
	}

	@Override
	@Transactional
	public List<ProblemManagement> getProblemManagementBySearchCriteria(
			SearchProblemManagement spm) throws DateParsingException {
		return this.problemManagementDAO.getProblemManagementBySearchCriteria(spm);
	}

}
