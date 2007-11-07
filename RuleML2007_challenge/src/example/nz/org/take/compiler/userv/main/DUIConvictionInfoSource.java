/**
 * Copyright 2007 Jens Dietrich Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions 
 * and limitations under the License.
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
