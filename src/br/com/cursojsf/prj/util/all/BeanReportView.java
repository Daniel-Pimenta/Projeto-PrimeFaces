package br.com.cursojsf.prj.util.all;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.primefaces.model.StreamedContent;
import org.springframework.stereotype.Component;

import br.com.cursojsf.prj.report.util.ReportUtil;

@Component
public abstract class BeanReportView extends BeanViewAbstract {

	private static final long serialVersionUID = 1L;
	
	protected StreamedContent arqReport;
	protected int tipoReport;
	protected List<?> lista;
	protected HashMap<Object, Object> parReport;
	protected String nomeReportJasper = "default";
	protected String nomeReportSaida  = "default";
	
	@Resource
	private ReportUtil reportUtil;

	public BeanReportView() {
		parReport = new HashMap<Object, Object>();
		lista = new ArrayList();
	}
	
	public ReportUtil getReportUtil() {
		return reportUtil;
	}

	public StreamedContent getArqReport() throws Exception {
		return getReportUtil().getReport(lista, parReport, nomeReportJasper, nomeReportSaida, tipoReport);
	}

	public void setArqReport(StreamedContent arqReport) {
		this.arqReport = arqReport;
	}

	public int getTipoReport() {
		return tipoReport;
	}

	public void setTipoReport(int tipoReport) {
		this.tipoReport = tipoReport;
	}

	public List<?> getLista() {
		return lista;
	}

	public void setLista(List<?> lista) {
		this.lista = lista;
	}

	public HashMap<Object, Object> getParReport() {
		return parReport;
	}

	public void setParReport(HashMap<Object, Object> parReport) {
		this.parReport = parReport;
	}

	public String getNomeReportJasper() {
		return nomeReportJasper;
	}

	public void setNomeReportJasper(String nomeReportJasper) {
		this.nomeReportJasper = nomeReportJasper; 
		if (nomeReportJasper == null || nomeReportJasper.isEmpty()) {
			this.nomeReportJasper = "default";
		}
	}

	public String getNomeReportSaida() {
		return nomeReportSaida;
	}

	public void setNomeReportSaida(String nomeReportSaida) {
		this.nomeReportSaida = nomeReportSaida;
		this.nomeReportSaida = nomeReportSaida; 
		if (nomeReportSaida == null || nomeReportSaida.isEmpty()) {
			this.nomeReportSaida = "default";
		}
	}
	

}
