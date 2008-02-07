/*
+---------------------------------------------------------------------------+
| OpenX v${RELEASE_MAJOR_MINOR}                                                               |
| ======${RELEASE_MAJOR_MINOR_DOUBLE_UNDERLINE}                                                                 |
|                                                                           |
| Copyright (c) 2003-2008 m3 Media Services Ltd                             |
| For contact details, see: http://www.openx.org/                         |
|                                                                           |
| This program is free software; you can redistribute it and/or modify      |
| it under the terms of the GNU General Public License as published by      |
| the Free Software Foundation; either version 2 of the License, or         |
| (at your option) any later version.                                       |
|                                                                           |
| This program is distributed in the hope that it will be useful,           |
| but WITHOUT ANY WARRANTY; without even the implied warranty of            |
| MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             |
| GNU General Public License for more details.                              |
|                                                                           |
| You should have received a copy of the GNU General Public License         |
| along with this program; if not, write to the Free Software               |
| Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA |
+---------------------------------------------------------------------------+
$Id:$
*/

package org.openads.zone;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.openads.config.GlobalSettings;
import org.openads.publisher.PublisherTestCase;

/**
 * Base class for all zone web service tests
 *
 * @author     Andriy Petlyovanyy <apetlyovanyy@lohika.com>
 */
public class ZoneTestCase extends PublisherTestCase {
	protected static final String GET_ZONE_LIST_BY_PUBLISHER_ID_METHOD = "getZoneListByPublisherId";
	protected static final String GET_ZONE_METHOD = "getZone";
	protected static final String ADD_ZONE_METHOD = "addZone";
	protected static final String MODIFY_ZONE_METHOD = "modifyZone";
	protected static final String DELETE_ZONE_METHOD = "deleteZone";
	protected final static String ZONE_ADVERTISER_STATISTICS_METHOD = "zoneAdvertiserStatistics";
	protected static final String ZONE_DAILY_STATISTICS_METHOD = "zoneDailyStatistics";
	protected final static String ZONE_CAMPAIGN_STATISTICS_METHOD = "zoneCampaignStatistics";
	protected final static String ZONE_BANNER_STATISTICS_METHOD = "zoneBannerStatistics";

	protected static final String CAMPAIGN_ID = "campaignId";
	protected static final String PUBLISHER_ID = "publisherId";
	protected static final String ZONE_ID = "zoneId";
	protected static final String ZONE_NAME = "zoneName";
	protected static final String HEIGHT = "height";
	protected static final String WIDTH = "width";
	protected static final String TYPE = "type";

	protected Integer publisherId = null;

	protected void setUp() throws Exception {
		super.setUp();

		publisherId = createPublisher();

		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getZoneServiceUrl()));
	}

	protected void tearDown() throws Exception {

		deletePublisher(publisherId);

		super.tearDown();
	}

	/**
	 * @return Zone id
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public Integer createZone() throws XmlRpcException, MalformedURLException {
		return createZone(getZoneParams("test"));
	}

	/**
	 * @return zone id
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public Integer createZone(Map<String, Object> params)
			throws XmlRpcException, MalformedURLException {
		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getZoneServiceUrl()));

		Object[] paramsWithId = new Object[] { sessionId, params };
		final Integer result = (Integer) client.execute(ADD_ZONE_METHOD, paramsWithId);

		return result;
	}

	/**
	 * @param id -
	 *            id of zone you want to remove
	 * @throws XmlRpcException
	 * @throws MalformedURLException
	 */
	public boolean deleteZone(Integer id) throws XmlRpcException,
			MalformedURLException {
		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getZoneServiceUrl()));
		return (Boolean) client.execute(DELETE_ZONE_METHOD, new Object[] {
				sessionId, id });
	}

	public Object execute(String method, Object[] params)
			throws XmlRpcException, MalformedURLException {
		// set URL
		((XmlRpcClientConfigImpl) client.getClientConfig())
				.setServerURL(new URL(GlobalSettings.getZoneServiceUrl()));

		return client.execute(method, params);
	}

	/**
	 * @param prefix
	 * @return
	 */
	public Map<String, Object> getZoneParams(String prefix) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PUBLISHER_ID, publisherId);
		params.put(ZONE_NAME, prefix + ZONE_NAME);
		params.put(TYPE, 0);
		params.put(WIDTH, 200);
		params.put(HEIGHT, 200);

		return params;
	}
}
