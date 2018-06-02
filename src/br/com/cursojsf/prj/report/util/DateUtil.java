package br.com.cursojsf.prj.report.util;

import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static String getDate() {
		DateFormat df = new SimpleDateFormat("ddMMyyyy");
		return df.format(Calendar.getInstance().getTime());
	}
	
	public static String formatSqlDate(Date data) {
		StringBuffer saida = new StringBuffer();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		saida.append("'");
		saida.append(df.format(data));
		saida.append("'");
		return saida.toString();
	}

}
