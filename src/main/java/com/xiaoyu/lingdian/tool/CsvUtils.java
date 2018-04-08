package com.xiaoyu.lingdian.tool;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * utility class for CSV
 */
public class CsvUtils {

	/**
	 * private constructor
	 */
	private CsvUtils() {

	}

	/**
	 * CSV Mapper to build header & row data
	 * @param <T>
	 */
	public interface CsvMapper<T> {

		/**
		 * generate csv headers
		 * @return headers
		 */
		String[] mapHeader();

		/**
		 * generate csv row strings using given row source data
		 * @param t row data
		 * @return csv line build from given data
		 */
		String[] mapRowData(T t);
	}

	/**
	 * build the csv contents from given data with mapper
	 * @param data source data list
	 * @param mapper csv mapper
	 * @param <T> source data type
	 * @return csv data list in List&lt;String[]&gt;
	 */
	public static <T> List<String[]> buildCsvList(List<T> data, CsvMapper<T> mapper) {
		List<String[]> contents = new ArrayList<>();
		String[] header = mapper.mapHeader();
		contents.add(header);
		for (T vo : data) {
			contents.add(mapper.mapRowData(vo));
		}

		// check the size of header & body
		if (contents.size() > 1) {
			if (contents.get(0).length != contents.get(1).length) {
				throw new IllegalStateException("size of header / row must be the same");
			}
		}

		return contents;
	}

	/**
	 * generate csv file name like ${prefix}_20150101020304.csv
	 * @param prefix prefix
	 * @return file name
	 */
	private static String buildCsvFileName(String prefix) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());

		return prefix + '_' + dateString + ".csv";
	}

	/**
	 * write the csv data list into response
	 * @param response http response
	 * @param contentList csv data list
	 * @param fileNamePrefix csv file name prefix
	 * @throws IOException
	 */
	public static void writeCsv (
			HttpServletResponse response,
			List<String[]> contentList,
			String fileNamePrefix
	) throws IOException {

		if (response == null
				|| contentList == null
				|| StringUtils.isBlank(fileNamePrefix)){

			throw new IllegalArgumentException("arguments required");
		}
		// set header for attachment
		response.setHeader("Content-Disposition", "attachment;filename=" + buildCsvFileName(fileNamePrefix));

		// write content into response using csv writer
		OutputStream out = response.getOutputStream();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(out, "GBK"), ',');
		writer.writeAll(contentList);
		writer.close();//here close out, so don't need to close again
//		out.close();
	}
}
