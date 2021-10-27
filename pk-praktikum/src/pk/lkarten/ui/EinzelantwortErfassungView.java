package pk.lkarten.ui;

import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import pk.lkarten.EinzelantwortKarte;

public class EinzelantwortErfassungView extends ErfassungView {

	private TextArea t4;

	public EinzelantwortErfassungView(EinzelantwortKarte karte, Stage stage) {
		super(karte, stage);
	}

	public void showView() {
		super.showView();

		Label l4 = new Label("Antowort:");
		t4 = new TextArea(karte != null ? ((EinzelantwortKarte) karte).getAntwort() : "");

		GridPane gp = getGrid();
		BorderPane bp = getBorder();

		gp.addRow(3, l4, t4);

		GridPane.setHalignment(l4, HPos.RIGHT);
		GridPane.setHgrow(t4, Priority.ALWAYS);

		bp.setCenter(gp);

		Scene scene = new Scene(bp);
		setTitle("EinzelantwortKarte");
		setScene(scene);
		showAndWait();
	}

	public void neu() {
		((EinzelantwortKarte) karte).setAntwort(t4.getText());
		super.neu();
	}

}
