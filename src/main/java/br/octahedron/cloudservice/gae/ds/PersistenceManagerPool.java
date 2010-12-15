/*
 *  This file is part of Cotopaxi.
 *
 *  Cotopaxi is free software: you can redistribute it and/or modify
 *  it under the terms of the Lesser GNU General Public License as published
 *  by the Free Software Foundation, either version 3 of the License, or
 *  any later version.
 *
 *  Cotopaxi is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  Lesser GNU General Public License for more details.
 *
 *  You should have received a copy of the Lesser GNU General Public License
 *  along with Cotopaxi. If not, see <http://www.gnu.org/licenses/>.
 */
package br.octahedron.cloudservice.gae.ds;

import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import br.octahedron.cloudservice.gae.ds.DatastoreFacadeImpl.PMFWrapper;

/**
 * @see PersistenceManagerPool
 * 
 * @author Danilo Penna Queiroz - daniloqueiroz@octahedron.com.br
 * 
 */
public class PersistenceManagerPool {

	private static final PersistenceManagerPool instance = new PersistenceManagerPool();

	protected static PersistenceManagerPool getInstance() {
		return instance;
	}

	// Object stuff
	private final Logger logger = Logger.getLogger(PersistenceManagerPool.class.getName());

	private ThreadLocal<PersistenceManager> pool = new PMThreadLocal();

	private PersistenceManagerPool() {
		// private constructor
	}

	protected boolean isPersistenceManagerOpened() {
		PersistenceManager pm = this.pool.get();
		return (pm != null) && !pm.isClosed();
	}

	protected void close() {
		this.logger.fine("Closing PersistenceManager.");
		PersistenceManager pm = this.pool.get();
		pm.close();
		this.pool.remove();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.octahedron.cs.google.PersistenceManagerPool#getPersistenceManager()
	 */
	public PersistenceManager getPersistenceManagerForThread() {
		this.logger.fine("Getting a PersistenceManager.");
		PersistenceManager pm = this.pool.get();
		if (pm.isClosed()) {
			// if pm is closed remove. a new one will be created
			this.logger.fine("Removing old PersistenceManager.");
			this.pool.remove();
		}
		return this.pool.get();
	}

	/**
	 * A <code>ThreadLocal</code> thats create a PersistenceManager as initial value.
	 * 
	 * @see ThreadLocal
	 */
	private class PMThreadLocal extends ThreadLocal<PersistenceManager> {
		@Override
		protected PersistenceManager initialValue() {
			PersistenceManagerPool.this.logger.fine("Creating a new PersistenceManager.");
			return PMFWrapper.getPersistenceManager();
		}
	}
}
