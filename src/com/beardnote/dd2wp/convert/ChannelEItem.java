package com.beardnote.dd2wp.convert;
/**
 * 用于添加频道的子项 当存在流媒体播放文件时使用此类
 * 
 * @author jackZhang
 * 
 */
public class ChannelEItem extends ChannelItem {
	private String enclosure;// 流媒体文件

	public String getEnclosure() {
		return enclosure;
	}

	public void setEnclosure(String enclosure) {
		this.enclosure = enclosure;
	}
}
