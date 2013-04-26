package com.beardnote.dd2wp;

import java.awt.GraphicsEnvironment;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class Dd2wp {
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
			UIManager.put("RootPane.setupButtonVisible", false);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Dd2wpPanel dd2wpPanel = new Dd2wpPanel(
							GraphicsEnvironment
									.getLocalGraphicsEnvironment()
									.getDefaultScreenDevice()
									.getDefaultConfiguration());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
