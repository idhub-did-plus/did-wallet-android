package com.idhub.magic.clientlib.event;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.clientlib.http.RetrofitAccessor;
import com.idhub.magic.clientlib.interfaces.EventListenerService;
import com.idhub.magic.clientlib.interfaces.EventService;
import com.idhub.magic.common.event.MagicEvent;
import com.idhub.magic.common.parameter.MagicResponse;


public class EventFetcher implements EventListenerService {
	static EventFetcher instance = new EventFetcher();

	static public EventFetcher getInstance() {
		return instance;
	}

	static String url = "http://localhost:8080/chainevent/getChainEvent";
	ScheduledExecutorService pool;
	EventListener listener;
	private EventFetcher() {
		final String identity = ProviderFactory.getProvider().getDefaultCredentials().getAddress();
		EventService eventService = RetrofitAccessor.getInstance().getEventService();
		pool = Executors.newScheduledThreadPool(1);
		pool.scheduleAtFixedRate(() -> {

			MagicResponse<List<MagicEvent>> evs;
			try {
				evs = eventService.queryEvents(identity).execute().body();
				for (MagicEvent e : evs.getData()) {
					if(listener != null)
						listener.onEvent(e);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			

		}, 0, 15, TimeUnit.SECONDS);

	}

	

	@Override
	public void listen(EventListener l) {
		listener = l;
	}

	
}
