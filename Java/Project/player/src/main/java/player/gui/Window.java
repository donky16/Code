package player.gui;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import player.client.clientdownload.DownloadMusic;
import player.client.httpclient.Music;
import player.client.httpclient.MusicSheet;
import player.client.httpclient.UpdateDatabase;
import player.dao.MusicDao;
import player.dao.MusicSheetDao;
import player.play.MP3Player;

import java.awt.Color;

public class Window {
	private Boolean networkConnected = true;

	private UpdateDatabase ud = new UpdateDatabase();
	private MusicDao md = new MusicDao();
	private MusicSheetDao msd = new MusicSheetDao();
	private DownloadMusic dm = new DownloadMusic();

	private JFrame frame;
	private JTextField textField;
	private Object[][] localMusicSheetData = new Object[100][2];
	private Object[][] shareMusicSheetData = new Object[300][2];
	private Object[][] searchMusicSheetData = new Object[100][2];
	private Object[][] playMusicListData = new Object[20][2];

	private List<MusicSheet> searchMusicSheetList = new ArrayList<MusicSheet>();

	private JTable localMusicSheetTable;
	private JTable shareMusicSheetTable;
	private JTable playMusicTable;
	private JTable searchMusicSheetTable;

	private Map<Integer, Thread> PlayThreadMap = new HashMap<Integer, Thread>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		try {
			ud.updateMusicAndMusicSheet();
		} catch (Exception e) {
			networkConnected = false;
			Thread thread = new Thread() {
				public void run() {
					JOptionPane.showMessageDialog(null, "网络连接出现错误，与服务器失联，数据不能及时更新...");
				}
			};
			thread.start();
		}
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "serial", "unchecked", "rawtypes" })
	private void initialize() {
		frame = new JFrame("RedYellowBluePlayer");
		frame.setResizable(false);
		frame.setBounds(450, 300, 833, 517);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		/**
		 * 本地歌单列表的设置与事件处理
		 */

		// 初始化本地歌单
		final List<MusicSheet> localMusicSheetList = msd.findMusicSheet("16020032029");
		for (int i = 0; i < localMusicSheetList.size(); i++) {
			MusicSheet ms = localMusicSheetList.get(i);
			localMusicSheetData[i][0] = ms.getName();
			localMusicSheetData[i][1] = "ZhuHaojie";
		}
		DefaultTableModel localMusicSheetModel = new DefaultTableModel(localMusicSheetData,
				new Object[] { "歌单名", "创建者" }) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		JLabel lblNewLabel = new JLabel("本地歌单");
		lblNewLabel.setBounds(37, 2, 100, 15);
		frame.add(lblNewLabel);
		localMusicSheetTable = new JTable(localMusicSheetModel);
		localMusicSheetTable.setBackground(new Color(255, 99, 71));
		localMusicSheetTable.setBounds(10, 35, 218, 251);
		localMusicSheetTable.setBorder(null);
		// 本地歌单的标签
		JScrollPane localMusicSheetPane = new JScrollPane(localMusicSheetTable);
		localMusicSheetPane.setBounds(10, 21, 238, 308);
		frame.getContentPane().add(localMusicSheetPane);

		// 点击歌单，事件处理
		localMusicSheetTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// 获取点击的列表中所在行数
					int rowNum = localMusicSheetTable.getSelectedRow();
					String msName = (String) localMusicSheetTable.getModel().getValueAt(rowNum, 0);
					// 查询数据库中点击歌单的歌曲
					List<Music> addMusicList = md.selectMusicByMusicSheet(msName, "ZhuHaojie");
					DefaultTableModel model = (DefaultTableModel) playMusicTable.getModel();
					// 将原先的播放歌单中Table中数据清空
					model.setRowCount(0);
					// 逐行增加数据
					for (int i = 0; i < addMusicList.size(); i++) {
						Music ms = addMusicList.get(i);
						Object[] addMusicRowData = new Object[2];
						addMusicRowData[0] = i;
						addMusicRowData[1] = ms.getName();
						model.addRow(addMusicRowData); // 增加一行
					}
					// 更新数据
					localMusicSheetTable.invalidate();
				}
			}
		});

		/**
		 * 分享歌单列表的设置与事件处理 具体方法见本地歌单处理
		 */

		// 分享歌单的标签设置
		JLabel lblNewLabel_1 = new JLabel("分享歌单");
		lblNewLabel_1.setBounds(570, 2, 100, 15);
		frame.add(lblNewLabel_1);

		final List<MusicSheet> shareMusicSheetList = msd.findMusicSheet(null);
		for (int i = 0; i < shareMusicSheetList.size(); i++) {
			MusicSheet ms = shareMusicSheetList.get(i);
			shareMusicSheetData[i][0] = ms.getName();
			shareMusicSheetData[i][1] = ms.getCreator();
		}
		shareMusicSheetTable = new JTable();
		DefaultTableModel shareMusicSheetModel = new DefaultTableModel(shareMusicSheetData,
				new Object[] { "歌单名", "分享者" }) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		shareMusicSheetTable.setModel(shareMusicSheetModel);
		shareMusicSheetTable.setBounds(10, 35, 218, 236);
		shareMusicSheetTable.setBackground(Color.YELLOW);
		shareMusicSheetTable.setCellSelectionEnabled(true);
		shareMusicSheetTable.setBorder(null);

		JScrollPane shareMusicSheetPane = new JScrollPane(shareMusicSheetTable);
		shareMusicSheetPane.setBounds(569, 21, 238, 302);

		frame.getContentPane().add(shareMusicSheetPane);
		shareMusicSheetTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// 获取点击的列表中所在行数
					int rowNum = shareMusicSheetTable.getSelectedRow();
					String msName = shareMusicSheetList.get(rowNum).getName();
					String msCreator = shareMusicSheetList.get(rowNum).getCreator();
					// 查询数据库中点击歌单的歌曲
					List<Music> addMusicList = md.selectMusicByMusicSheet(msName, msCreator);
					DefaultTableModel model = (DefaultTableModel) playMusicTable.getModel();
					// 将原先的播放歌单中Table中数据清空
					model.setRowCount(0);
					// 逐行增加数据
					for (int i = 0; i < addMusicList.size(); i++) {
						Music ms = addMusicList.get(i);
						Object[] addMusicRowData = new Object[2];
						addMusicRowData[0] = i;
						addMusicRowData[1] = ms.getName();
						model.addRow(addMusicRowData); // 增加一行
					}
					// 更新数据
					shareMusicSheetTable.invalidate();
				}

			}
		});
		// 下载歌单的按钮
		JButton downloadAllButton = new JButton("下载歌单");
		downloadAllButton.setBounds(474, 269, 90, 21);
		frame.getContentPane().add(downloadAllButton);
		downloadAllButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if (networkConnected) {
						// 获取点击的列表中所在行数
						int rowNum = shareMusicSheetTable.getSelectedRow();
						String msName = null;
						String msCreator = null;
						if (rowNum != -1) {
							msName = shareMusicSheetList.get(rowNum).getName();
							msCreator = shareMusicSheetList.get(rowNum).getCreator();
						} else {
							rowNum = searchMusicSheetTable.getSelectedRow();
							if (rowNum != -1) {
								msName = searchMusicSheetList.get(rowNum).getName();
								msCreator = searchMusicSheetList.get(rowNum).getCreator();
							}
						}
						// 查询数据库中点击歌单的歌曲
						if (rowNum != -1) {
							final List<Music> addMusicList = md.selectMusicByMusicSheet(msName, msCreator);
							int result = JOptionPane.showConfirmDialog(null, "确定要下载" + msName + "歌单中所有歌曲吗？", "Message",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
							if (result == JOptionPane.YES_OPTION) {
								for (int i = 0; i < addMusicList.size(); i++) {
									final Music music = addMusicList.get(i);
									final String md5Value = music.getMd5Value();
									Thread t = new Thread() {
										public void run() {
											try {
												dm.downloadMusic(md5Value);
											} catch (Exception e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											JOptionPane.showMessageDialog(null, "已下载" + music.getName());
										}
									};
									t.start();
								}
								msd.addServerMusicSheetLocal(msName, msCreator, "ZhuHaojie", "16020032029");
								DefaultTableModel model = (DefaultTableModel) localMusicSheetTable.getModel();
								model.setRowCount(0);
								Object[] addLocalMusicSheetData = new Object[2];
								addLocalMusicSheetData[0] = msName;
								addLocalMusicSheetData[1] = "ZhuHaojie";
								model.addRow(addLocalMusicSheetData);
								for (int i = 0; i < localMusicSheetList.size(); i++) {
									MusicSheet ms = localMusicSheetList.get(i);
									addLocalMusicSheetData[0] = ms.getName();
									addLocalMusicSheetData[1] = "ZhuHaojie";
									model.addRow(addLocalMusicSheetData);
								}
								localMusicSheetTable.invalidate();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "网络未连接，请检查网络后重试...");
					}
				}
			}
		});

		// 搜索歌单

		searchMusicSheetTable = new JTable();
		final DefaultTableModel searchMusicSheetModel = new DefaultTableModel(new Object[100][3],
				new String[] { "学号", "创建者", "歌单" }) {
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		searchMusicSheetTable.setModel(searchMusicSheetModel);
		searchMusicSheetTable.setBounds(268, 72, 279, 176);
		JScrollPane jScrollPane = new JScrollPane(searchMusicSheetTable);
		jScrollPane.setBounds(261, 72, 298, 182);
		frame.getContentPane().add(jScrollPane);

		final JComboBox searchComnoBox = new JComboBox();
		searchComnoBox.setModel(new DefaultComboBoxModel(new String[] { "女同学姓名", "女同学学号" }));
		searchComnoBox.setBounds(258, 40, 90, 21);
		frame.getContentPane().add(searchComnoBox);
		textField = new JTextField();
		textField.setBounds(365, 40, 108, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		JButton btnNewButton_3 = new JButton("检索");
		btnNewButton_3.setBounds(483, 39, 76, 23);
		frame.getContentPane().add(btnNewButton_3);

		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					searchMusicSheetList.clear();
					if (searchComnoBox.getSelectedItem().equals("女同学学号")) {
						String creatorId = textField.getText();
						searchMusicSheetList = msd.findMusicSheet(creatorId);
					} else if (searchComnoBox.getSelectedItem().equals("女同学姓名")) {
						String name = textField.getText();
						searchMusicSheetList = msd.searchMusicSheetByCreatorName(name);
					}
					searchMusicSheetModel.setRowCount(0);
					for (int i = 0; i < searchMusicSheetList.size(); i++) {
						MusicSheet ms = searchMusicSheetList.get(i);
						String[] addSearchMusicSheetData = new String[3];
						addSearchMusicSheetData[0] = ms.getCreatorId();
						addSearchMusicSheetData[1] = ms.getCreator();
						addSearchMusicSheetData[2] = ms.getName();

						searchMusicSheetModel.addRow(addSearchMusicSheetData);
					}

					searchMusicSheetTable.invalidate();
				}
			}
		});

		searchMusicSheetTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					// 获取点击的列表中所在行数
					int rowNum = searchMusicSheetTable.getSelectedRow();
					String msName = searchMusicSheetList.get(rowNum).getName();
					String msCreator = searchMusicSheetList.get(rowNum).getCreator();
					// 查询数据库中点击歌单的歌曲
					List<Music> addMusicList = md.selectMusicByMusicSheet(msName, msCreator);
					DefaultTableModel model = (DefaultTableModel) playMusicTable.getModel();
					// 将原先的播放歌单中Table中数据清空
					model.setRowCount(0);
					// 逐行增加数据
					for (int i = 0; i < addMusicList.size(); i++) {
						Music ms = addMusicList.get(i);
						Object[] addMusicRowData = new Object[2];
						addMusicRowData[0] = i;
						addMusicRowData[1] = ms.getName();
						model.addRow(addMusicRowData); // 增加一行
					}
					// 更新数据
					playMusicTable.invalidate();
				}
			}
		});

		// 音乐列表的事件处理
		final List<Music> musicList = md.selectMusicByMusicSheet("Other", "ZhuHaojie");
		for (int i = 0; i < musicList.size(); i++) {
			Music music = musicList.get(i);
			playMusicListData[i][0] = i;
			playMusicListData[i][1] = music.getName();
		}
		DefaultTableModel playMusicModel = new DefaultTableModel(playMusicListData, new String[] { "序号", "歌曲名" }) {
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		playMusicTable = new JTable(playMusicModel);
		playMusicTable.setBorder(null);
		playMusicTable.setBounds(10, 35, 723, 74);
		playMusicTable.setBackground(Color.CYAN);

		final JScrollPane musicListPane = new JScrollPane(playMusicTable);
		musicListPane.setBounds(37, 349, 758, 119);
		frame.getContentPane().add(musicListPane);

		playMusicTable.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int rowNum = playMusicTable.getSelectedRow();
					String name = (String) playMusicTable.getModel().getValueAt(rowNum, 1);
					String filename = "/home/donky16/Music/" + name;
					File file = new File(filename);
					if (file.exists()) {
						System.out.println(filename);
						final MP3Player mp3_1 = new MP3Player(filename);
						if (PlayThreadMap.size() == 0) {
							Thread thread_1 = new Thread() {
								public void run() {
									mp3_1.run();
								}
							};
							thread_1.start();
							PlayThreadMap.put(rowNum, thread_1);
						} else {

							Iterator it = PlayThreadMap.values().iterator();
							Thread thread = (Thread) it.next();
							thread.stop();
							Thread thread_3 = new Thread() {
								public void run() {
									mp3_1.run();
								}
							};
							PlayThreadMap.clear();
							PlayThreadMap.put(rowNum, thread_3);
							thread_3.start();
						}
					} else {
						if (networkConnected) {
							int result = JOptionPane.showConfirmDialog(null, "本地不存在，是否下载？", "Message",
									JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
							if (result == JOptionPane.YES_OPTION) {
								final String md5Value = md.findMusicMd5ByName(name);
								JOptionPane.showMessageDialog(null, "正在下载，不要着急");
								Thread t = new Thread() {
									public void run() {
										try {
											dm.downloadMusic(md5Value);
										} catch (Exception e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										JOptionPane.showMessageDialog(null, "已下载......");
									}
								};
								t.start();
							}
						} else {
							JOptionPane.showMessageDialog(null, "网络未连接，请检查网络后重试...");
						}
					}
				}
			}
		});
		// 添加音乐到歌单的相关处理
		final JComboBox localMusicSheetComboBox = new JComboBox();
		localMusicSheetComboBox.setBounds(258, 269, 100, 20);
		frame.getContentPane().add(localMusicSheetComboBox);
		Object[] optionalLocalMusicSheetName = new Object[localMusicSheetList.size()];
		for (int i = 0; i < localMusicSheetList.size(); i++) {
			optionalLocalMusicSheetName[i] = localMusicSheetList.get(i).getName();
		}
		localMusicSheetComboBox.setModel(new DefaultComboBoxModel(optionalLocalMusicSheetName));

		JButton addMusicToLocalMusicSheetButton = new JButton("添加到歌单");
		addMusicToLocalMusicSheetButton.setBounds(358, 269, 100, 20);
		frame.getContentPane().add(addMusicToLocalMusicSheetButton);
		// 添加音乐到歌单的事件处理
		addMusicToLocalMusicSheetButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {

					int rowNum = playMusicTable.getSelectedRow();
					if (rowNum != -1) {
						String musicName = (String) playMusicTable.getModel().getValueAt(rowNum, 1);
						String musicMd5 = md.findMusicMd5ByName(musicName);
						String musicSheetName = (String) localMusicSheetComboBox.getSelectedItem();
						int result = JOptionPane.showConfirmDialog(null,
								"确定添加" + musicName + "到" + musicSheetName + "歌单吗？", "Message",
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							msd.insertMusicIntoMusicSheet(musicMd5, musicSheetName);
						}
					}
				}
			}
		});

		// 播放相关按钮的设置
		JButton btnNewButton = new JButton("播放");
		btnNewButton.setBackground(Color.RED);
		btnNewButton.setBounds(365, 305, 93, 23);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					String name = (String) playMusicTable.getModel().getValueAt(0, 1);
					String filename = "/home/donky16/Music/" + name;
					File file = new File(filename);
					if (file.exists()) {
						final MP3Player mp3_1 = new MP3Player(filename);
						if (PlayThreadMap.size() == 0) {
							Thread thread_1 = new Thread() {
								public void run() {
									mp3_1.run();
								}
							};
							thread_1.start();
							PlayThreadMap.put(0, thread_1);
						} else {

							Iterator it = PlayThreadMap.values().iterator();
							Thread thread = (Thread) it.next();
							thread.stop();
							Thread thread_3 = new Thread() {

								public void run() {
									mp3_1.run();
								}
							};
							PlayThreadMap.clear();
							PlayThreadMap.put(0, thread_3);
							thread_3.start();
						}
					} else {
						JOptionPane.showMessageDialog(null, "此歌单未下载，请双击音乐进行下载");
					}
				}
			}
		});

		// JSlider slider = new JSlider();
		// slider.setBackground(Color.LIGHT_GRAY);
		// slider.setBounds(258, 269, 200, 26);
		// frame.getContentPane().add(slider);

		JButton btnNewButton_1 = new JButton("上一曲");
		btnNewButton_1.setBackground(Color.CYAN);
		btnNewButton_1.setBounds(255, 305, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if (PlayThreadMap.size() == 0) {
						String name = (String) playMusicTable.getModel().getValueAt(0, 1);
						String filename = "/home/donky16/Music/" + name;
						File file = new File(filename);
						if (file.exists()) {
							final MP3Player mp3_1 = new MP3Player(filename);
							Thread thread_1 = new Thread() {
								public void run() {
									mp3_1.run();
								}
							};
							thread_1.start();
							PlayThreadMap.put(0, thread_1);
						} else {
							JOptionPane.showMessageDialog(null, "此歌单未下载，请双击音乐进行下载");
						}

					} else {
						int row = 0;
						for (Integer n : PlayThreadMap.keySet()) {
							row = n;
						}
						if (row != 0) {
							Iterator it2 = PlayThreadMap.values().iterator();
							Thread thread = (Thread) it2.next();
							thread.stop();
							String name = (String) playMusicTable.getModel().getValueAt(row - 1, 1);
							String filename = "/home/donky16/Music/" + name;
							File file = new File(filename);
							if (file.exists()) {
								final MP3Player mp3_1 = new MP3Player(filename);
								Thread thread_3 = new Thread() {

									public void run() {
										mp3_1.run();
									}
								};
								PlayThreadMap.clear();
								PlayThreadMap.put(row - 1, thread_3);
								thread_3.start();
							} else {
								JOptionPane.showMessageDialog(null, "此歌单未下载，请双击音乐进行下载");

							}
						}
					}

				}

			}
		});

		JButton btnNewButton_2 = new JButton("下一曲");
		btnNewButton_2.setBackground(Color.CYAN);
		btnNewButton_2.setBounds(473, 305, 93, 23);
		frame.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addMouseListener(new MouseAdapter() {

			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					if (PlayThreadMap.size() == 0) {
						String name = (String) playMusicTable.getModel().getValueAt(0, 1);
						String filename = "/home/donky16/Music/" + name;
						File file = new File(filename);
						if (file.exists()) {
							final MP3Player mp3_1 = new MP3Player(filename);
							Thread thread_1 = new Thread() {
								public void run() {
									mp3_1.run();
								}
							};
							thread_1.start();
							PlayThreadMap.put(0, thread_1);
						} else {
							JOptionPane.showMessageDialog(null, "此歌单未下载，请双击音乐进行下载");
						}
					} else {
						int row = 0;
						int allRow = playMusicTable.getModel().getRowCount() - 1;
						System.out.println(allRow);
						for (Integer n : PlayThreadMap.keySet()) {
							row = n;
						}
						if (row != allRow) {
							Iterator it2 = PlayThreadMap.values().iterator();
							Thread thread = (Thread) it2.next();
							thread.stop();
							String name = (String) playMusicTable.getModel().getValueAt(row + 1, 1);
							String filename = "/home/donky16/Music/" + name;
							File file = new File(filename);
							if (file.exists()) {
								final MP3Player mp3_1 = new MP3Player(filename);
								Thread thread_3 = new Thread() {

									public void run() {
										mp3_1.run();
									}
								};
								PlayThreadMap.clear();
								PlayThreadMap.put(row + 1, thread_3);
								thread_3.start();
							} else {
								JOptionPane.showMessageDialog(null, "此歌单未下载，请双击音乐进行下载");
							}
						}
					}
				}
			}
		});
	}
}
