package com.servicelive.integrationtest.domain;

import java.util.ArrayList;
import java.util.List;

public class QueryRow {
	
	private List<QueryCell> row = new ArrayList<QueryCell>();

	public void setResultCells(List<QueryCell> row) {
		this.row = row;
	}

	public List<QueryCell> getResultCells() {
		return row;
	}
	
	public void addQueryCell(QueryCell queryCell) {
		row.add(queryCell);
	}
}
