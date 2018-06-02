package test.junit;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import br.com.cursojsf.prj.report.util.DateUtil;

public class TesteData {

	@Test
	public void testeData() {
		try {
		assertEquals("29052018", DateUtil.getDate());
		
		assertEquals("'2018-05-29'", DateUtil.formatSqlDate(Calendar.getInstance().getTime()));
		//fail("Not yet implemented");
		
		}catch(Exception e) {
			System.out.println(e.getMessage() + " - " + DateUtil.getDate());
		}
	}

}
