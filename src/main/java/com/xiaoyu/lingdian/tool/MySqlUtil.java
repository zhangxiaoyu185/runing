package com.xiaoyu.lingdian.tool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUtil {
	
	// 插入数据，返回插入个数
	public static int insert(String sql, Connection conn) throws Exception {
		System.out.println(sql);
		int num = 0;// 计数
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			num = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new SQLException("insert data Exception: "
					+ sqle.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				throw new Exception("ps close exception: " + e.getMessage());
			}
		}
		return num;
	}

	// 插入数据,返回主键
	public static Long insert(String sql, int oper, Connection conn) throws Exception {
		System.out.println(sql);
		Long id = 0l;// 计数
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql, oper);
			ps.executeUpdate();
			// 检索由于执行此 Statement 对象而创建的所有自动生成的键
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (SQLException sqle) {
			throw new SQLException("insert data Exception: "
					+ sqle.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (Exception e) {
				throw new Exception("rs close exception: " + e.getMessage());
			}
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				throw new Exception("ps close exception: " + e.getMessage());
			}
		}
		return id;
	}
		
	// 删除数据
	public static int delete(String sql, Connection conn) throws Exception {
		System.out.println(sql);
		int num = 0;// 计数
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			num = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new SQLException("delete data Exception: "
					+ sqle.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				throw new Exception("ps close exception: " + e.getMessage());
			}
		}
		return num;
	}

	// 修改数据
	public static int update(String sql, Connection conn) throws Exception {
		System.out.println(sql);
		PreparedStatement ps = null;
		int num = 0;// 计数
		try {
			ps = conn.prepareStatement(sql);
			num = ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new SQLException("update data Exception: "
					+ sqle.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
					ps = null;
				}
			} catch (Exception e) {
				throw new Exception("ps close exception: " + e.getMessage());
			}
		}
		return num;
	}
	
}