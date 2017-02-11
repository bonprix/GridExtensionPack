package org.vaadin.teemusa.gridextensions.client.tableselection;

import com.vaadin.client.widget.grid.CellReference;
import com.vaadin.client.widget.grid.events.BodyClickHandler;
import com.vaadin.client.widget.grid.events.GridClickEvent;
import com.vaadin.client.widget.grid.selection.SelectionModel;
import com.vaadin.client.widget.grid.selection.SelectionModel.Multi;
import com.vaadin.client.widgets.Grid;

import elemental.json.JsonObject;

public class SingleClickSelectionHandler implements BodyClickHandler {

	protected Grid<JsonObject> grid;
	protected JsonObject currentSelectedRow;

	public SingleClickSelectionHandler(Grid<JsonObject> grid) {
		this.grid = grid;
	}

	@Override
	public void onClick(GridClickEvent event) {
		SelectionModel<JsonObject> selectionModel = this.grid.getSelectionModel();
		if (!(selectionModel instanceof Multi)) {
			// Not multiselecting.
			return;
		}

		Multi<JsonObject> model = (Multi<JsonObject>) selectionModel;
		CellReference<JsonObject> cell = this.grid.getEventCell();

		singleClickSelect(model, cell, event);
	}

	protected void singleClickSelect(Multi<JsonObject> model, CellReference<JsonObject> cell, GridClickEvent event) {
		JsonObject row = cell.getRow();

		boolean wasSelected = model.isSelected(row);

		model.deselectAll();

		if (!wasSelected) {
			model.select(row);
		}
	}

}
