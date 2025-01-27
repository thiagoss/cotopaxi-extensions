/*
 *  Straight - A system to manage financial demands for small and decentralized
 *  organizations.
 *  Copyright (C) 2011  Octahedron 
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.octahedron.cotopaxi.eventbus;


/**
 * Subscribers receive notifications each time an {@link Event} of this {@link Subscriber} interest
 * is published. This interface defines this notification mechanism.
 * 
 * However, to start receive notifications, the subscriber should subscribe to {@link EventBus}. See
 * the {@link EventBus#subscribe(Subscriber, Class...)} documentation.
 * 
 * @author Danilo Penna Queiroz
 */
public interface Subscriber {

	/**
	 * Notify about a published {@link Event}
	 */
	public void eventPublished(Event event);
}
