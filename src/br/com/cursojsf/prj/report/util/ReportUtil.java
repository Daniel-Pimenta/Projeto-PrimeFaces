package br.com.cursojsf.prj.report.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Component
public class ReportUtil implements Serializable {

	private static final long serialVersionUID    = 1L;
	private static final String UNDERLINE         = "_";
	private static final String PONTO             = ".";
	
	private static final String FOLDER_RELATORIOS     = "/relatorios";
	private static final String SUBREPORT_DIR         = "SUBREPORT_DIR";
	private              String REPORT_PARAMETERS_IMG = null;
	private static final String EXTENCION_ODS         = "ods";
	private static final String EXTENCION_XLS         = "xls";
	private static final String EXTENCION_HTML        = "html";
	private static final String EXTENCION_PDF         = "pdf";
	
	private              String SEPARATOR         = File.separator; 
	
	private static final int    RELATORIO_PDF     = 1;
	private static final int    RELATORIO_EXCEL   = 2;
	private static final int    RELATORIO_HTML    = 3;
	private static final int    RELATORIO_PLA_OPEN= 4;
	
	private StreamedContent     returnFile        = null;
	private String              reportPath        = null;
	private String              jasperPath        = null;
	private JRExporter          reportType        = null;
	private String              reportExt         = "";
	private String              subReportPath     = "";  

	private File                reportFile        = null;
	private String              reportFilePath    = null;
	
  public StreamedContent getReport(
  		List<?> lista, 
  		HashMap parametros, 
  		String  nomeRelJasper,
  		String  nomeRelSaida,
  		int     tipoRelatorio
  		) throws Exception 
  {
  	// Cria a lista de collection de bean que carrega para o relatorio 
  	JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(lista);
  	/*
  	 * Fornece o caminho fisico ate a pasta que contem os relatorios
  	 **/
  	FacesContext fc = FacesContext.getCurrentInstance();
  	fc.responseComplete();
  	ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
  	reportPath = sc.getRealPath(FOLDER_RELATORIOS);
  	
  	//  Testa se esta tudo oK com o arquivo
  	File arquivo = new File(reportPath + SEPARATOR + nomeRelJasper + PONTO + "jasper");
  	if (reportPath == null || reportPath.isEmpty() || !arquivo.exists()) {
  		reportPath = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
  		SEPARATOR = "";
  	}
  	
  	// Caminho para Imagens
  	parametros.put(REPORT_PARAMETERS_IMG, reportPath);
  	
  	// Caminho para o arquivo .jasper
  	jasperPath = reportPath + SEPARATOR + nomeRelJasper + PONTO + "jasper";
  	
  	// Carrega o relatorio Jasper
  	JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(jasperPath); 
  	
  	//Seta parametros SUBREPORT_DIR como caminho fisico apar sub-reports.
  	subReportPath = jasperPath + SEPARATOR;
  	parametros.put(SUBREPORT_DIR, subReportPath);
  	
  	//Carrega o arquivo compilado para a memoria
  	JasperPrint jp = JasperFillManager.fillReport(jr, parametros, jrBean);
  	reportType = new JRPdfExporter();
  	switch (tipoRelatorio) {
  	case RELATORIO_PDF:
  		reportExt  = EXTENCION_PDF;
  		break;
  	case RELATORIO_EXCEL:
  		reportExt  = EXTENCION_XLS;
  		break;
  	case RELATORIO_HTML:
  		reportExt  = EXTENCION_HTML;
  		break;
  	case RELATORIO_PLA_OPEN:
  		reportExt  = EXTENCION_ODS;
  		break;
  	default:
  		reportExt  = EXTENCION_PDF;
  		break;
  	}
  	
  	nomeRelSaida += UNDERLINE + DateUtil.getDate();
  	
  	//Caminho do relatorio exportado
    reportFilePath = reportPath + SEPARATOR + nomeRelSaida + PONTO + reportExt;
    
    //Criar novo arquivo 
    reportFile = new File(reportFilePath);
    
    //Preparar impressao...
    reportType.setParameter(JRExporterParameter.JASPER_PRINT, jp);
    
    //Nome do arquivo fisico 
    reportType.setParameter(JRExporterParameter.OUTPUT_FILE, jp);
    
    //Executa a exportação
    reportType.exportReport();
    reportFile.deleteOnExit();
    InputStream is = new FileInputStream(reportFile);
    returnFile = new DefaultStreamedContent(is,"application/"+reportExt,nomeRelSaida+PONTO+reportExt);
    
  	return returnFile;
  }
	
	
}
