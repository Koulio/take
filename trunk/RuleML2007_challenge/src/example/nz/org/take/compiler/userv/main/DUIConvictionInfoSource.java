/*
 * Copyright (C) 2007 <A href="http://www-ist.massey.ac.nz/JBDietrich" target="_top">Jens Dietrich</a>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package example.nz.org.take.compiler.userv.main;

import java.io.IOException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import nz.org.take.rt.EmptyIterator;
import nz.org.take.rt.ResourceIterator;
import nz.org.take.rt.SingletonIterator;
import example.nz.org.take.compiler.userv.domainmodel.Driver;
import example.nz.org.take.compiler.userv.spec.ExternalFactStore4hasBeenConvictedOfaDUI;
import example.nz.org.take.compiler.userv.spec.hasBeenConvictedOfaDUI;

/**
 * External data source for DUI Conviction lookup.
 * Based on querying a web resource with an HTTP client, simulating a (REST) web service.
 * @author <a href="http://www-ist.massey.ac.nz/JBDietrich/">Jens Dietrich</a>
 */

public class DUIConvictionInfoSource implements ExternalFactStore4hasBeenConvictedOfaDUI {

	public static final String URL = "http://localhost:8080/DUILookup/DUILookup";
	public DUIConvictionInfoSource() {
		super();
	}
	
	public static void main(String[] args) throws Exception {
		
		Driver driver = new Driver();
		driver.setId("5342");
		new DUIConvictionInfoSource().fetch(driver);
		
	}

	@Override
	public ResourceIterator<hasBeenConvictedOfaDUI> fetch(Driver driver) {
		
		HttpClient client = new HttpClient(new MultiThreadedHttpConnectionManager());
        client.getHttpConnectionManager().getParams().setConnectionTimeout(30000);
        GetMethod get = new GetMethod(URL);
        get.setFollowRedirects(true);
        get.setQueryString(new NameValuePair[]{new NameValuePair("id",driver.getId())});        
		try {
			System.out.println("DUI conviction lookup " + get.getURI());
			client.executeMethod(get);
			String response = get.getResponseBodyAsString();
			System.out.println("DUI conviction lookup result: " + response);
			if (response!=null && "true".equals(response.trim())) {
				hasBeenConvictedOfaDUI record = new hasBeenConvictedOfaDUI();
				record.slot1 = driver;
				get.releaseConnection();
				return new SingletonIterator<hasBeenConvictedOfaDUI>(record);	
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EmptyIterator.DEFAULT;
        

	}

}
