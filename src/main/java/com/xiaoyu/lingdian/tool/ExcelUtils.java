package com.xiaoyu.lingdian.tool;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelUtils {

	public interface ExcelMapper<T> {

		String[] mapHeader();

		String[] mapRowData(T obj);
	}

	public ExcelUtils() {
	}

	private static final String POINT_ZERO = ".0";

	public static <T> List<String[]> buildExcelList(List<T> data, ExcelMapper<T> mapper) {
		List<String[]> contents = new ArrayList<>();
		String header[] = mapper.mapHeader();
		contents.add(header);
		for (T vo : data) {
			contents.add(mapper.mapRowData(vo));
		}

		if(contents.size() > 1 && (contents.get(0)).length != (contents.get(1)).length)
			throw new IllegalStateException("size of header / row must be the same");
		else
			return contents;
	}

	private static String buildExcelFileName(String prefix, String suffix) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		return (new StringBuilder(String.valueOf(prefix))).append('_').append(dateString).append(suffix).toString();
	}

	/**
	 * 导出xls文件
	 *
	 * @param response
	 * @param contentList
	 * @param fileNamePrefix
	 * @throws IOException
	 */
	public static void writeXls(HttpServletResponse response, List<String[]> contentList, String fileNamePrefix) throws IOException {
		if(response == null || contentList == null || StringUtils.isBlank(fileNamePrefix)) {
			throw new IllegalArgumentException("arguments required");
		} else {
			String finalFileName = URLEncoder.encode(buildExcelFileName(fileNamePrefix,".xls"),"UTF8");
			response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=\"")).append(finalFileName).append("\"").toString());
			OutputStream out = response.getOutputStream();

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			for (int i = 0; i < contentList.size(); i++) {
				HSSFRow row = sheet.createRow(i);
				for (int j = 0; j < contentList.get(i).length; j++) {
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(contentList.get(i)[j]);
				}
			}
			workbook.write(out); // 把相应的excel工作表保存到硬盘上
			out.flush();
			out.close();
		}
	}

	/**
	 * 导出xlsx文件
	 *
	 * @param response
	 * @param contentList
	 * @param fileNamePrefix
	 * @throws IOException
	 */
	public static void writeXlsx(HttpServletResponse response, List<String[]> contentList, String fileNamePrefix) throws IOException {
		if(response == null || contentList == null || StringUtils.isBlank(fileNamePrefix)) {
			throw new IllegalArgumentException("arguments required");
		} else {
			String finalFileName = URLEncoder.encode(buildExcelFileName(fileNamePrefix,".xlsx"),"UTF8");
			response.setHeader("Content-Disposition", (new StringBuilder("attachment;filename=\"")).append(finalFileName).append("\"").toString());
			OutputStream out = response.getOutputStream();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			for (int i = 0; i < contentList.size(); i++) {
				XSSFRow row = sheet.createRow(i);
				for (int j = 0; j < contentList.get(i).length; j++) {
					XSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(contentList.get(i)[j]);
				}
			}
			workbook.write(out); // 把相应的excel工作表保存到硬盘上
			out.flush();
			out.close();
		}
	}

	/**
	 * 导入xls文件
	 */
	public static List<String[]> importXls(InputStream inputStream) {
		List<String[]> dataList = new ArrayList<String[]>();
		Workbook rwb;
		try {
			rwb = Workbook.getWorkbook(inputStream);
			Sheet rs = rwb.getSheet(0);
			int colNum = rs.getColumns();
			int rowNum = rs.getRows();
			for (int i = 1; i < rowNum; i++) {
				String[] data = new String[colNum];
				for (int j = 0; j < colNum; j++) {
					Cell cell = rs.getCell(j, i);
					data[j] = cell.getContents();
				}
				dataList.add(data);
			}
		} catch (BiffException e) {
			throw new IllegalStateException("only support xls");
		} catch (IOException e) {
			throw new IllegalStateException("file IOException");
		}

		return dataList;
	}

	/**
	 * 导入xlsx文件
	 */
	public static List<String[]> importXlsx(InputStream inputStream) {
		List<String[]> dataList = new ArrayList<String[]>();
		XSSFWorkbook rwb;
		try {
			rwb = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = rwb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();

			for (int i = 1; i <= rowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				int colNum = row.getLastCellNum();
				String[] data = new String[colNum];
				for (int j = 0; j < colNum; j++) {
					XSSFCell cell = row.getCell(j);
					if (cell == null) {
						continue;
					}
					data[j] = getCellContent(cell);
				}
				dataList.add(data);
			}
		} catch (IOException e) {
			throw new IllegalStateException("fle IOException");
		}

		return dataList;
	}

	/**
	 * 获取单元格的内容
	 *
	 * @param cell 单元格
	 * @return 返回单元格内容
	 */
	private static String getCellContent(XSSFCell cell) {
		StringBuffer buffer = new StringBuffer();
		switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_NUMERIC :
				String doubleValue = String.valueOf(cell.getNumericCellValue());
				if (doubleValue.endsWith(POINT_ZERO)) { //int
					DecimalFormat df = new DecimalFormat("0");
					buffer.append(df.format(cell.getNumericCellValue()));
				} else { //double
					buffer.append(cell.getNumericCellValue());
				}
				break;
			case XSSFCell.CELL_TYPE_BOOLEAN : // 布尔
				buffer.append(cell.getBooleanCellValue());
				break;
			case XSSFCell.CELL_TYPE_FORMULA : // 公式
				buffer.append(cell.getCellFormula());
				break;
			case XSSFCell.CELL_TYPE_STRING : // 字符串
				buffer.append(cell.getStringCellValue());
				break;
			case XSSFCell.CELL_TYPE_BLANK : // 空值
			case XSSFCell.CELL_TYPE_ERROR : // 故障
			default :
				break;
		}
		return buffer.toString();
	}

}