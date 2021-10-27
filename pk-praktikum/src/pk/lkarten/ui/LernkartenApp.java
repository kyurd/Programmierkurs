package pk.lkarten.ui;

import java.io.File;
import java.util.Iterator;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pk.lkarten.Lernkartei;
import pk.lkarten.MehrfachantwortKarte;
import pk.lkarten.UngueltigeKarteException;
import pk.lkarten.ui.util.DialogUtil;
import pk.lkarten.EinzelantwortKarte;
import pk.lkarten.Lernkarte;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class LernkartenApp extends Application {

	private Lernkartei lk;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		lk = new Lernkartei();
		ObservableList<String> ol = FXCollections.<String>observableArrayList();
		ListView<String> listView = new ListView<>(ol);
		BorderPane bp = new BorderPane(listView);
		MenuBar mb = new MenuBar();
		Menu datei = new Menu("Datei");

		MenuItem laden = new MenuItem("Laden");
		laden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					lk.laden();
					Iterator<Lernkarte> kerteii=lk.getIterator();
					while(kerteii.hasNext()){
						 Lernkarte k=kerteii.next();
						 ol.add(k.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		laden.setAccelerator(KeyCombination.valueOf("Ctrl+L"));

		MenuItem speichern = new MenuItem("Speichern");
		speichern.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					lk.speichern();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		speichern.setAccelerator(KeyCombination.valueOf("Ctrl+S"));

		MenuItem csv = new MenuItem("CSV-Export");
		csv.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean ok=false;
				try {
					while(ok==false) {
						String dateiname=DialogUtil.showInputDialog("Name eingeben", "Bitte einen Dateinamen eingeben");
						boolean result;
						if(dateiname.isBlank())
							DialogUtil.showMessageDialog("Fehler!", "Die Eingabe ist leer!");
						else {
							File file=new File(dateiname+".csv");
							if(file.exists()) {
								result=DialogUtil.showConfirmDialog("Überschreiben?", "Soll die bereits existierende Datei überschrieben werden?");
								if(result) {
									lk.exportiereEintraegeAlsCsv(file);
									ok=true;
								}
							}else {
								lk.exportiereEintraegeAlsCsv(file);
								ok=true;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		csv.setAccelerator(KeyCombination.valueOf("Ctrl+C"));

		MenuItem beenden = new MenuItem("Beenden");
		beenden.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		beenden.setAccelerator(KeyCombination.valueOf("Ctrl+B"));

		datei.getItems().addAll(laden, speichern, new SeparatorMenuItem(), csv, new SeparatorMenuItem(), beenden);
		mb.getMenus().add(datei);

		Menu lernkartei = new Menu("Lernkartei");
		MenuItem ek = new MenuItem("Einzelantwortkarte hinzufügen");
		ek.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				EinzelantwortErfassungView ev = new EinzelantwortErfassungView(new EinzelantwortKarte(), primaryStage);
				ev.showView();
				if (ev.getNeueKarte() != null && ev.getBtn()) {
					try {
						lk.hinzufuegen(ev.getNeueKarte());
						ol.add(ev.getNeueKarte().toString());
					} catch (UngueltigeKarteException e) {
						DialogUtil.showErrorDialog(null, e.getFehlerAusgabe());
					}
				} else {
					ev.close();
				}
			}
		});
		ek.setAccelerator(KeyCombination.valueOf("Ctrl+E"));

		MenuItem mk = new MenuItem("Mehrfachantwortkarte hinzufügen");
		mk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
//					MehrfachantwortKarte mv2 = new MehrfachantwortKarte("Mathe", "Zahlen", "Zahlen, durch die 12 teilbar ist",
//							new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }, new int[] { 1, 2, 3, 4 });
				MehrfachantwortErfassungView mv = new MehrfachantwortErfassungView(new MehrfachantwortKarte(),
						primaryStage);
				mv.showView();
				if (mv.getNeueKarte() != null && mv.getBtn()) {
					try {
						lk.hinzufuegen(mv.getNeueKarte());
						ol.add(mv.getNeueKarte().toString());
					} catch (UngueltigeKarteException e) {
						DialogUtil.showErrorDialog(null, e.getFehlerAusgabe());
					}
				}else {
					mv.close();
				}
			}
		});
		mk.setAccelerator(KeyCombination.valueOf("Ctrl+M"));

		lernkartei.getItems().addAll(ek, mk);
		mb.getMenus().add(lernkartei);
		
		Spinner<Integer> spin = new Spinner<Integer>();
		final int value=5;
		SpinnerValueFactory<Integer> sp = new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 15, value);
		spin.setValueFactory(sp);

		Button b3 = new Button("Lernen!");
		b3.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				int anzKartenDeck= spin.getValue();
				if(lk.gibAnzahlKarten()>0) {
				Lernkarte[]deck=lk.erzeugeDeck(anzKartenDeck);
				for(Lernkarte karte:deck) {
					karte.zeigeVorderseite();
					karte.zeigeRueckseite();
						} 
					}else {
						DialogUtil.showErrorDialog("Lernkartei ist leer!", "Es befindet sich derzeit keine Lernkarte im Deck!");
				} 
			}
		});


		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setPadding(new Insets(25, 0, 0, 0));
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(b3, spin);

		bp.setBottom(hb);
		bp.setTop(mb);
		Scene scene = new Scene(bp, 700, 500);
		primaryStage.setTitle("Lernkarten-App");
		primaryStage.setScene(scene);
		primaryStage.show();

//		ev = new EinzelantwortErfassungView(
//				new EinzelantwortKarte("Idiot", "Cem", "Ist sie ein Idiot?", "Ja, das ist sie!"), primaryStage);
//		ev.showView();
//
//		MehrfachantwortErfassungView mv = new MehrfachantwortErfassungView(
//				new MehrfachantwortKarte("Mathe", "Zahlen", "Zahlen, durch die 12 teilbar ist",
//						new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }, new int[] { 1, 2, 3, 4 }),
//				primaryStage);
//		mv.showView();
	}
}
