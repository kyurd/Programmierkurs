module pk{
	requires java.desktop;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	
	opens pk.lkarten.ui to javafx.graphics;
}