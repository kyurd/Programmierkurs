package pk.lkarten.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pk.lkarten.Lernkarte;import pk.lkarten.UngueltigeKarteException;

public abstract class ErfassungView extends Stage {

	protected Lernkarte karte;
	private GridPane gp;
	private BorderPane bp;
	private TextField t1;
	private TextField t2;
	private TextField t3;
	private boolean btn=true;

	public ErfassungView(Lernkarte karte, Stage stage) {
		super();
		this.karte = karte;
		this.initOwner(stage);
		this.initModality(Modality.WINDOW_MODAL);
		this.gp = new GridPane();
		this.bp = new BorderPane();
	}

	public GridPane getGrid() {
		return gp;
	}

	public BorderPane getBorder() {
		return bp;
	}

	public void showView() {

		gp.setPadding(new Insets(10.0));
		gp.setVgap(5.0);
		gp.setHgap(5.0);

		Label l1 = new Label("Kategorie:");
		Label l2 = new Label("Titel:");
		Label l3 = new Label("Frage:");

		t1 = new TextField(karte != null ? karte.getKategorie() : "");
		t2 = new TextField(karte != null ? karte.getTitel() : "");
		t3 = new TextField(karte != null ? karte.getFrage() : "");

		Button b1 = new Button("OK");
		b1.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				neu();
				close();
				btn=true;
			}
		});
		Button b2 = new Button("Abbrechen");
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				close();
				btn=false;
			}

		});
		gp.addRow(0, l1, t1);
		gp.addRow(1, l2, t2);
		gp.addRow(2, l3, t3);

		GridPane.setHgrow(t1, Priority.ALWAYS);
		GridPane.setHgrow(t2, Priority.ALWAYS);
		GridPane.setHgrow(t3, Priority.ALWAYS);

		GridPane.setHalignment(l1, HPos.RIGHT);
		GridPane.setHalignment(l2, HPos.RIGHT);
		GridPane.setHalignment(l3, HPos.RIGHT);

		HBox hb = new HBox();
		hb.setSpacing(10);
		hb.setPadding(new Insets(25, 0, 0, 0));
		hb.setAlignment(Pos.CENTER);
		hb.getChildren().addAll(b1, b2);

		bp.setPadding(new Insets(10, 10, 20, 10));
		bp.setBottom(hb);
	}
	
	public void neu() {
		karte.setTitel(t2.getText());
		karte.setKategorie(t1.getText());
		karte.setFrage(t3.getText());
	}
	
	public Lernkarte getNeueKarte() {
		return this.karte;
	}
	
	public boolean getBtn() {
		return btn;
	}
}