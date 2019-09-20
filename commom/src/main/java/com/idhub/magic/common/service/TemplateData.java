package com.idhub.magic.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idhub.magic.common.ustorage.entity.component.AddressElement;

public class TemplateData {
	
		public Map<String, List<AddressElement>> templates = new HashMap<String, List<AddressElement>>();
		public Map<String, List<String>> enumsets = new HashMap<String, List<String>>();
		
}
