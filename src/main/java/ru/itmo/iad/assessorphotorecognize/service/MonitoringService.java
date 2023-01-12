package ru.itmo.iad.assessorphotorecognize.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {
    Counter helpCounter;
    Counter helpErrorCounter;
    Counter nextCounter;
    Counter nextErrorCounter;

    public MonitoringService(MeterRegistry meterRegistry) {
        helpCounter = Counter.builder("help_counter")
                .register(meterRegistry);
        helpErrorCounter = Counter.builder("help_error_counter")
                .register(meterRegistry);
        nextCounter = Counter.builder("next_counter")
                .register(meterRegistry);
        nextErrorCounter = Counter.builder("next_error_counter")
                .register(meterRegistry);
    }

    public void incrementHelpCounter() {
        helpCounter.increment();
    }

    public void incrementHelpErrorCounter() {
        helpErrorCounter.increment();
    }

    public void incrementNextCounter() {
        nextCounter.increment();
    }

    public void incrementNextErrorCounter() {
        nextErrorCounter.increment();
    }
}
