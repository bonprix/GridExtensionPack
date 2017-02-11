package org.vaadin.teemusa.gridextensions.client.tableselection;

import org.vaadin.teemusa.gridextensions.tableselection.TableSelectionModel;

import com.google.gwt.event.shared.HandlerRegistration;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.annotations.OnStateChange;
import com.vaadin.client.connectors.MultiSelectionModelConnector;
import com.vaadin.client.renderers.ComplexRenderer;
import com.vaadin.client.widget.grid.events.BodyClickHandler;
import com.vaadin.client.widget.grid.selection.SelectionModel.Multi;
import com.vaadin.client.widgets.Grid;
import com.vaadin.shared.ui.Connect;

import elemental.json.JsonObject;

@Connect(TableSelectionModel.class)
public class TableSelectionModelConnector extends MultiSelectionModelConnector {

	private HandlerRegistration clickHandler;

	@Override
	protected Multi<JsonObject> createSelectionModel() {
		return new MultiSelectionModel() {

			@Override
			protected ComplexRenderer<Boolean> createSelectionColumnRenderer(Grid<JsonObject> grid) {
				return null;
			}
		};
	}

	@Override
	protected void extend(ServerConnector target) {
		super.extend(target);

		setSelectionMode();
	}

	@OnStateChange("selectionMode")
	void setSelectionMode() {
		if (this.clickHandler != null) {
			this.clickHandler.removeHandler();
			this.clickHandler = null;
		}

		BodyClickHandler handler;
		Grid<JsonObject> grid = getGrid();
		switch (getState().selectionMode) {
		case CTRL:
			handler = new CtrlClickSelectionHandler(grid);
			break;
		case SINGLE:
			handler = new SingleClickSelectionHandler(grid);
			break;
		case SIMPLE:
			handler = new SimpleClickSelectionHandler(grid);
			break;
		case SHIFT:
			handler = new ShiftCtrlClickSelectionHandler(grid, getRpcProxy(ShiftSelectRpc.class));
			break;
		case NONE:
		default:
			return;
		}

		this.clickHandler = grid.addBodyClickHandler(handler);
	}

	@Override
	public TableSelectionState getState() {
		return (TableSelectionState) super.getState();
	}

	@Override
	public void onUnregister() {
		if (this.clickHandler != null) {
			this.clickHandler.removeHandler();
			this.clickHandler = null;
		}

		super.onUnregister();
	}
}
