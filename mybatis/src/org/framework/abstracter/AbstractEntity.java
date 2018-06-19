package org.framework.abstracter;

import java.io.Serializable;

public abstract class AbstractEntity<ID extends Serializable> {

	/**
	 * get ID
	 * @return
	 */
	public abstract ID getId();
	
	/**
	 * set ID
	 * @param id
	 */
	public abstract void setId(final ID id);
}
