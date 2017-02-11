package org.vaadin.teemusa.gridextensions.client.tableselection;

import com.vaadin.shared.ui.grid.selection.MultiSelectionModelState;

public class TableSelectionState extends MultiSelectionModelState {

	public enum TableSelectionMode {
		NONE, SINGLE, SIMPLE, CTRL, SHIFT
	}

	public TableSelectionMode selectionMode = TableSelectionMode.NONE;

}
