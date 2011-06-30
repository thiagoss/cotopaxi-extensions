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
package br.octahedron.cotopaxi.eventbus;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.notNull;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.taskqueue.Queue;

/**
 * @author Danilo Queiroz
 */
public class EventBusTest {

	private Queue queue;

	@Before
	public void setUp() {
		EventBus.reset();
		this.queue = createMock(Queue.class);
		EventBus.setEventPublisher(new AppEngineEventPublisher(this.queue));
	}

	@Test
	public void consumeTest() {
		Event event = new EventOne();
		new AppEngineEventPublisher.PublishTask(SubscriberOne.class, event).run();
		assertEquals(EventOne.class, SubscriberOne.receivedEvent.getClass());
	}
}