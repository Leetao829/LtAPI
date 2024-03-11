// package com.leetao.usercenter.once;
//
// import com.alibaba.excel.EasyExcel;
//
// import java.util.ArrayList;
// import java.util.List;
//
// public class ImportExcel {
//
// 	public static void main(String[] args) {
// 		String fileName = "E:\\CodeWork\\idea2021Project\\ltapi\\src\\main\\resources\\testExcel.xlsx";
// 		// readByListener(fileName);
// 		synchronousRead(fileName);
// 	}
//
// 	/**
// 	 * 通过使用监听器读取excel文件
// 	 * @param fileName 文件路径
// 	 */
// 	public static void readByListener(String fileName){
// 		EasyExcel.read(fileName,XingQiuTableUserInfo.class,new TableListener()).sheet().doRead();
// 	}
//
// 	/**
// 	 * 同步读取excel文件
// 	 * @param fileName 文件路径
// 	 */
// 	public static void synchronousRead(String fileName){
// 		List<XingQiuTableUserInfo> list = EasyExcel.read(fileName).head(XingQiuTableUserInfo.class).sheet().doReadSync();
// 		for(XingQiuTableUserInfo user : list){
// 			System.out.println(user);
// 		}
// 	}
//
// }
