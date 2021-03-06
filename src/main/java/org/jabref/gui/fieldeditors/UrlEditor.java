package org.jabref.gui.fieldeditors;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import org.jabref.gui.DialogService;
import org.jabref.gui.util.ControlHelper;
import org.jabref.model.entry.BibEntry;


public class UrlEditor extends HBox implements FieldEditorFX {

    private final String fieldName;
    @FXML private UrlEditorViewModel viewModel;
    @FXML private EditorTextArea textArea;
    private Optional<BibEntry> entry;

    public UrlEditor(String fieldName, DialogService dialogService) {
        this.fieldName = fieldName;
        this.viewModel = new UrlEditorViewModel(dialogService);

        ControlHelper.loadFXMLForControl(this);

        viewModel.textProperty().bindBidirectional(textArea.textProperty());
    }

    public UrlEditorViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public void bindToEntry(BibEntry entry) {
        this.entry = Optional.of(entry);
        textArea.bindToEntry(fieldName, entry);
    }

    @Override
    public Parent getNode() {
        return this;
    }

    @FXML
    private void openExternalLink(ActionEvent event) {
        viewModel.openExternalLink();
    }
}
