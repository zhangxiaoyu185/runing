package com.xiaoyu.test;

public class ArrayTest {

	public static void main(String[] args) {
		String[] zhongwen = new String[]{"yi","er","san","si", "wu"};
		String[] zimu = new String[]{"A","B","C","D"};
		String[] chicun = new String[]{"大","中","小"};
		String[] yanse = new String[]{"红","白"};
		
		int total = zhongwen.length * zimu.length * chicun.length * yanse.length;
		
		String[][] jieguo = new String[total][4];
		
		int firstCount = total/zhongwen.length;
		jieguo = jisuan(zhongwen, jieguo, firstCount, 0, 1);
		
		int secondCount = firstCount/zimu.length;
		jieguo = jisuan(zimu, jieguo, secondCount, 1, zhongwen.length);
		
		int threeCount = secondCount/chicun.length;
		jieguo = jisuan(chicun, jieguo, threeCount, 2, zhongwen.length * zimu.length);
		
		int fourCount = threeCount/yanse.length;
		jieguo = jisuan(yanse, jieguo, fourCount, 3, zhongwen.length * zimu.length * chicun.length);
		
		for (int i = 0; i < jieguo.length; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(jieguo[i][j]);
			}
			System.out.println();
		}
	}
	
	public static String[][] jisuan(String[] childsz, String[][] totalsz, int count, int jiaobiao, int cishu){
		int h = 0;
		for (int x = 0; x < cishu; x++) {
			for (int i = 0; i < childsz.length; i++) {
				for (int j = 0; j < count; j++) {
					totalsz[h+j][jiaobiao] = childsz[i];
				}
				h=h+count;
			}
		}	
		return totalsz;
	}

}
