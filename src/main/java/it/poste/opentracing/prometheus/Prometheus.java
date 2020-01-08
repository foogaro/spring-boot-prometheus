package it.poste.opentracing.prometheus;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;

@SpringBootApplication
@RestController
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector

public class Prometheus {

	static final Counter requests = Counter.build().name("requests_total").help("Total number of requests.").register();
	static final Counter hits = Counter.build().name("hits_like_requests_total").help("Total number of requests.").register();
	static final Histogram requestLatency = Histogram.build().name("requests_latency_seconds").help("Request latency in seconds.").register();

	@RequestMapping("/")
	String home() {
		requests.inc();
		hits.inc();
		Histogram.Timer requestTimer = requestLatency.startTimer();
		try {
			return "Welcome Home!";
		} finally {
			requestTimer.observeDuration();
		}
	}
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Prometheus.class, args);
	}
}

