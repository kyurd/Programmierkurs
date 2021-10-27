package pk.lkarten.ui;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pk.lkarten.MehrfachantwortKarte;

public class MehrfachantwortErfassungView extends ErfassungView {

	private CheckBox[] c;
	private TextArea[] t;
	private Label[] l;
	private int[] deneme;

	public MehrfachantwortErfassungView(MehrfachantwortKarte karte, Stage stage) {
		super(karte, null);
		this.karte = karte;
		c=new CheckBox[5];
		t=new TextArea[5];
		l=new Label[5];
	}

	private boolean istRichtig(int i, int[]ar) {
		if (karte != null)
			for (int x : ar) {
				if (x == i)
					return true;
			}
		return false;
	}

	public void showView() {
		
		super.showView();
		
		for(int i=0;i<=4;i++) {
			l[i]=new Label("Antwort" + (i+1) + ":");
		}
		
		String[] antworten=karte != null && ((MehrfachantwortKarte)karte).getMoeglicheAntworten() != null ? ((MehrfachantwortKarte)karte).getMoeglicheAntworten() : new String[0];
		
		for(int i=0;i<=4;i++) {
				t[i]=new TextArea(antworten.length>0 ? antworten[i] : "");
		}
		
		int[] richtige=karte!=null && ((MehrfachantwortKarte)karte).getRichtigeAntworten() != null ? 
				((MehrfachantwortKarte)karte).getRichtigeAntworten() : new int[0];
		
		for(int i=0;i<=4;i++) {
			c[i]=new CheckBox("Richtig?");
			c[i].setSelected(istRichtig((i+1),richtige));
		}


		GridPane gp = getGrid();
		BorderPane bp = getBorder();

		for(int i=0;i<=4;i++) {
			gp.addRow((i+3), l[i],t[i], c[i]);
		}

		bp.setCenter(gp);

		Scene scene = new Scene(bp);
		setTitle("MehrfachantwortKarte");
		setScene(scene);
		showAndWait();
	}
	

	public void neu() {
		ArrayList<String> liste=new ArrayList<String>();
		ArrayList<Integer> right=new ArrayList<>();

		super.neu();
	
		for(int i=0;i<=4;i++) {
			if(t[i].getText()==null||t[i].getText().trim().isEmpty()) {
				//Nothing, anders hat es leider nicht funktioniert!
			}else {
				liste.add(t[i].getText());
			}
		}
		
		for(int i=0;i<=4;i++) {
			if(c[i].isSelected()) {
				right.add(i);
			}
		}
		
		Integer[] a=right.toArray(new Integer[right.size()]);
		deneme=new int[right.size()];
		for(int i=0;i<right.size();i++) {
			deneme[i]=a[i];
		}
		
		String[] array=liste.toArray(new String[liste.size()]);
		((MehrfachantwortKarte) karte).setMoeglicheAntworte(array);
		((MehrfachantwortKarte) karte).setRichtigeAntworte(deneme);
	}
}
