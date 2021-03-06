package org.jabref.gui.fieldeditors;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

import org.jabref.gui.DialogService;
import org.jabref.gui.util.ControlHelper;
import org.jabref.gui.util.TaskExecutor;
import org.jabref.logic.l10n.Localization;
import org.jabref.model.entry.BibEntry;
import org.jabref.model.entry.FieldName;


public class IdentifierEditor extends HBox implements FieldEditorFX {

    private final String fieldName;
    @FXML private IdentifierEditorViewModel viewModel;
    @FXML private EditorTextArea textArea;
    @FXML private Button fetchInformationByIdentifierButton;
    @FXML private Button lookupIdentifierButton;
    private Optional<BibEntry> entry;

    public IdentifierEditor(String fieldName, TaskExecutor taskExecutor, DialogService dialogService) {
        this.fieldName = fieldName;
        this.viewModel = new IdentifierEditorViewModel(fieldName, taskExecutor, dialogService);

        ControlHelper.loadFXMLForControl(this);

        viewModel.textProperty().bind(textArea.textProperty());

        fetchInformationByIdentifierButton.setTooltip(
                new Tooltip(Localization.lang("Get BibTeX data from %0", FieldName.getDisplayName(fieldName))));
        lookupIdentifierButton.setTooltip(
                new Tooltip(Localization.lang("Look up %0", FieldName.getDisplayName(fieldName))));
    }

    public IdentifierEditorViewModel getViewModel() {
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
    private void fetchInformationByIdentifier(ActionEvent event) {
        entry.ifPresent(bibEntry -> viewModel.fetchInformationByIdentifier(bibEntry));
    }

    @FXML
    private void lookupIdentifier(ActionEvent event) {
        entry.ifPresent(bibEntry -> viewModel.lookupIdentifier(bibEntry));
    }

    @FXML
    private void openExternalLink(ActionEvent event) {
        viewModel.openExternalLink();
    }


}
