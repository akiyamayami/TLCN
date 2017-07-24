package com.tlcn.report;

import java.util.HashMap;
import java.util.List;

import com.tlcn.model.Car;
import com.tlcn.model.Proposal;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ProposalReport {
	 public void generatePdfReport(List<Proposal> proposals) throws JRException {
		 String report = "src/main/webapp/WEB-INF/report/report_proposal.jrxml";
		 JasperReport jreport = JasperCompileManager.compileReport(report);
		 JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(proposals);
		 HashMap params = new HashMap();
		 JasperPrint jprint = JasperFillManager.fillReport(jreport, params, ds);
		 
		 JasperExportManager.exportReportToPdfFile(jprint,
	                "src/main/resources/report2.pdf");
	 }
}
