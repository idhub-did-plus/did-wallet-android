package wallet.idhub.com.clientlib.event;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idhub.magic.center.event.MagicEvent;
import com.idhub.magic.center.parameter.MagicResponse;
import wallet.idhub.com.clientlib.ProviderFactory;
import wallet.idhub.com.clientlib.http.RetrofitAccessor;
import wallet.idhub.com.clientlib.interfaces.EventListenerService;
import wallet.idhub.com.clientlib.interfaces.EventService;


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
