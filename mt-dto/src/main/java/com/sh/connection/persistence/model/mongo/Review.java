package com.sh.connection.persistence.model.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Review extends AbstractDocument {

	private String title;
	private boolean positive;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isPositive() {
		return positive;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}

}
