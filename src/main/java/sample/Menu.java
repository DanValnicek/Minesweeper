package sample;

import java.io.IOException;

public class Menu extends AppSubScene {
	public static void init() throws IOException {
		init("/menuTab.fxml");
	}
//	@FXML
//	public void playOnClickEvent(MouseEvent event) throws IOException {
//		System.out.println(((Control) event.getSource()).getID());
//		if (((Control) event.getSource()).getID().endsWith("Tab")) {
//			init("/" + ((Control) event.getSource()).getID() + ".fxml");
//		}
//	}
//	@FXML
//	public void playOnClickEvent(MouseEvent event) {
////		Main.overlay.showMessage("Menu entered!", 10);
//		System.out.println(((Control) event.getSource()).getId());
//		try {
//			switch (((Control) event.getSource()).getId()) {
//				case "playButton":
//					GameSettings.init();
////					new javafx.scene.SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/gameSettingsTab.fxml"))), 525, 269);
//					break;
//				case "accountButton":
//					new javafx.scene.SubScene(FXMLLoader.load(Objects.requireNonNull(Menu.class.getResource("/loginTab.fxml"))), 525, 269);
//					break;
//				case "howToPlayButton":
//					break;
//				case "settingsButton":
//					break;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
