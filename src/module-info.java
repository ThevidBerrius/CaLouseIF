module calouseif {
	requires javafx.graphics;
	requires java.sql;
	requires javafx.controls;
	
	opens model to javafx.base;
	opens main;
}