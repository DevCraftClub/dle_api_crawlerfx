module de.maharder.dbcrawler {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            
    opens de.maharder.dbcrawler to javafx.fxml;
    exports de.maharder.dbcrawler;
}