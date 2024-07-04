package com.francescoceliento.ytdljextension.test;

import com.francescoceliento.ytdljextension.utils.SplitMP3Utils;

public class SplitTest {

	private static String file = "ypoq2LVQ0q0";
	private static String split = "00:30:00";
	
	public static void main(String[] args) {
		SplitMP3Utils utils = new SplitMP3Utils();
		utils.split("/home/francesco/YTDLJExtension", file, split);
	}
	
}
