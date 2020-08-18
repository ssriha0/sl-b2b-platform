package com.newco.marketplace.translator.dao;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "client",  uniqueConstraints = {@UniqueConstraint(columnNames={"name"})})
public class Client extends AbstractClient {
	// intentionally blank
}
