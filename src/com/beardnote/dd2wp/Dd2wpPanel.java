package com.beardnote.dd2wp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;

import com.beardnote.dd2wp.convert.DDConvert;

public class Dd2wpPanel extends JPanel {
	private JFrame frame = null;

	// 默认窗体大小
	private static final int DEFAULT_PANEL_WIDTH = 400;
	private static final int DEFAULT_PANE_HEIGHT = 260;

	final JTextField dddatapath = new JTextField();
	final JTextField wpdatapath = new JTextField();

	public Dd2wpPanel(GraphicsConfiguration gc) {
		_initFrame(gc);
	}

	private void _initFrame(GraphicsConfiguration gc) {
		frame = new JFrame(gc);
		setLayout(new BorderLayout());
		
		frame.setTitle("dd2wp");

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel optionPanel = new JPanel(new GridLayout(2, 1));

		optionPanel.add(this.getDDdatafilePanel());
		optionPanel.add(this.getSaveDataFilePanel());

		mainPanel.add(optionPanel, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();

		JButton cancelBtn = new JButton("取消");

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		JButton confirmBtn = new JButton("确定");
		confirmBtn.setUI(new BEButtonUI()
				.setNormalColor(BEButtonUI.NormalColor.green));
		confirmBtn.setForeground(Color.white);

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean result = DDConvert.convert(dddatapath.getText(), wpdatapath.getText());
				if(result){
					JOptionPane.showMessageDialog(null, "成功", "结果", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "失败！请联系下我吧！imzhpe@qq.com", "结果", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		btnPanel.add(cancelBtn);
		btnPanel.add(confirmBtn);

		mainPanel.add(btnPanel, BorderLayout.SOUTH);
		frame.setPreferredSize(new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_PANE_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		GUIUtil.setFrameCenter(frame);

		frame.setVisible(true);

	}

	/**
	 * 点点文件
	 * 
	 * @return
	 */
	public JComponent getDDdatafilePanel() {
		final JPanel datapathPanel = new JPanel(new BorderLayout());

		datapathPanel.setBorder(new TitledBorder("点点文件"));
		datapathPanel.setLayout(new BorderLayout());

		dddatapath.setSize(200, 30);
		JButton btnSelect = new JButton("选择");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				int intRetVal = fc.showOpenDialog(datapathPanel);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					dddatapath.setText(fc.getSelectedFile().getPath());
				}
			}
		});
		datapathPanel.add(dddatapath, BorderLayout.CENTER);
		datapathPanel.add(btnSelect, BorderLayout.EAST);
		return datapathPanel;
	}

	/**
	 * WP文件保存
	 * 
	 * @return
	 */
	public JComponent getSaveDataFilePanel() {
		final JPanel datapathPanel = new JPanel(new BorderLayout());

		datapathPanel.setBorder(new TitledBorder("导出文件保存"));
		datapathPanel.setLayout(new BorderLayout());

		wpdatapath.setSize(200, 30);
		JButton btnSelect = new JButton("选择");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int intRetVal = fc.showOpenDialog(datapathPanel);
				if (intRetVal == JFileChooser.APPROVE_OPTION) {
					wpdatapath.setText(fc.getSelectedFile().getPath());
				}

			}
		});
		datapathPanel.add(wpdatapath, BorderLayout.CENTER);
		datapathPanel.add(btnSelect, BorderLayout.EAST);
		return datapathPanel;
	}

}
