package com.idhub.magic.clientlib.local;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idhub.magic.clientlib.ApiFactory;
import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.common.service.TemplateData;
import com.idhub.magic.common.ustorage.entity.component.AddressElement;

public class AddressTemplate {
	static TemplateData data;
	static {
	/*	String identity = ProviderFactory.getProvider().getDefaultCredentials().getAddress();
		try {
			data = ApiFactory.getTemplateService().templateData(identity).execute().body().getData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			data = fromStorage();
		}*/
		data = new TemplateData();
		data.templates.put("default", createTemplate("state","city","neighbourhood","streetNumber", "houseNumber", "apartmentNumber"));
		data.templates.put("us", createTemplate("state","city","neighbourhood","streetNumber", "houseNumber", "apartmentNumber"));
		data.templates.put("china", createTemplate("province","city","county","street", "residence","menpaihao"));
		//....
		data.enumsets.put("province@china", Arrays.asList("beijing","shanghai","tianjin"));
		//.....
	
	}
	
	static public List<AddressElement> template(String country){
		List<AddressElement> temp = data.templates.get(country);
		if(temp != null)
			return temp;
		return data.templates.get("default");
	}
	private static TemplateData fromStorage() {
		// TODO Auto-generated method stub
		return null;
	}
	static List<AddressElement> createTemplate(String... names){
		List<AddressElement> rst = new ArrayList<AddressElement>();
		for(String name : names) {
			rst.add(new AddressElement(name));
		}
		return rst;
	}
	static List<String> enumset(String country, String element){
		return data.enumsets.get(element + "@" + country);
	}
}
