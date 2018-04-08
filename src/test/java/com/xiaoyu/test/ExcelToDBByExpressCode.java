package com.xiaoyu.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import com.xiaoyu.lingdian.tool.MySqlConn;
import com.xiaoyu.lingdian.tool.MySqlUtil;
import com.xiaoyu.lingdian.tool.RandomUtil;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelToDBByExpressCode {

	public static void main(String[] args) {
		try {
	        InputStream in = new FileInputStream(new File("D:\\ExpressCode.xls"));         
			Workbook rwb = Workbook.getWorkbook(in);
			Sheet rs = rwb.getSheet(0);
			int rowNum = rs.getRows();
			for (int i = 1; i < rowNum; i++) {
				Cell cell = rs.getCell(0, i);
				Cell cell1 = rs.getCell(1, i);
				Connection conn = MySqlConn.getConn(i);
				System.out.println(conn);
				String strSql = "INSERT INTO BUSI_EXPRESS_CODE(BSECE_UUID,BSECE_CODE,BSECE_NAME) VALUES ('"+RandomUtil.generateString(16)+"','"+cell.getContents()+"','"+cell1.getContents()+"')";
				MySqlUtil.insert(strSql, conn);
				System.out.println(i);
				MySqlConn.removeConn(i);
			}
		} catch (BiffException e) {
			throw new IllegalStateException("only support xls");
		} catch (IOException e) {
			throw new IllegalStateException("file IOException");
		} catch (Exception e) {
			throw new IllegalStateException("file Exception");
		}
	}
}
