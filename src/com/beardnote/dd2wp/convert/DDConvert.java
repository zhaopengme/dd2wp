package com.beardnote.dd2wp.convert;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jodd.io.FileUtil;
import jodd.jerry.Jerry;
import jodd.jerry.JerryFunction;
import jodd.util.StringUtil;

public class DDConvert {

	final static Map imageMap = new HashMap();

	public static boolean convert(String ddfile, String wpfile) {
		try {
			final RssBuildFactory builder = new RssBuildFactory();
			Jerry.JerryParser jerryParser = Jerry.jerry();

			jerryParser.enableXmlMode();
			String xml = FileUtil.readString(ddfile);
			Jerry doc = jerryParser.parse(xml);

			Jerry images = doc.$("Image");
			images.each(new JerryFunction() {
				@Override
				public boolean onNode(Jerry $this, int index) {
					String id = $this.$("Id").text();
					String url = $this.$("Url").text();
					imageMap.put(id, url);
					return true;
				}
			});

			Jerry post = doc.$("Post");

			post.each(new JerryFunction() {
				@Override
				public boolean onNode(Jerry $this, int index) {

					Jerry createTime = $this.$("CreateTime");
					Jerry title = $this.$("Title");
					Jerry text = $this.$("Text");

					if (StringUtil.isNotBlank(title.text())) {
						ChannelEItem item = new ChannelEItem();
						item.setTitle(title.text());
						item.setLink("");

						String content = parseImg(text.text());

						item.setDescription(content);
						item.setPubDate(new Date(Long.parseLong(createTime
								.text())));
						item.setCategory("");
						item.setAuthor("zhaopeng");
						item.setEnclosure("");

						builder.buildItems(item);
					}

					return true;
				}
			});

			// 建立Rss的Channel信息
			builder.buildChannel("diandian", "diandian", "diandian", "zh-cn",
					new Date(), "diandian");

			// 设置Rss文件的生成路径
			builder.buildChannel(wpfile + File.separatorChar + "wp.xml");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String parseImg(String content) {
		Jerry.JerryParser jerryParser = Jerry.jerry();
		jerryParser.enableXmlMode();
		Jerry doc = jerryParser.parse(content);
		Jerry images = doc.$("img");
		images.each(new JerryFunction() {
			@Override
			public boolean onNode(Jerry $this, int index) {

				String id = $this.attr("id");
				if (StringUtil.isNotBlank(id)) {
					String src = (String) imageMap.get(id);
					if (StringUtil.isNotBlank(id)) {
						$this.attr("src", src);
					}

				}
				return true;
			}
		});
		return doc.html();
	}
}
