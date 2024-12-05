package com.intzdata.product.observaibility.core.interceptors;

import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class ObservabilityInterceptor implements HandlerInterceptor {

    private final MetricRepository metricRepository;
    private final ExceptionLogRepository exceptionLogRepository;

    public ObservabilityInterceptor(MetricRepository metricRepository, ExceptionLogRepository exceptionLogRepository) {
        this.metricRepository = metricRepository;
        this.exceptionLogRepository = exceptionLogRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Capture request metrics
        metricRepository.save(new MetricEntity(null, "request_start_time", (double) System.currentTimeMillis(), LocalDateTime.now()));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Capture response metrics
        metricRepository.save(new MetricEntity(null, "response_time", (double) System.currentTimeMillis(), LocalDateTime.now()));

        // Capture exceptions
        if (ex != null) {
            ExceptionLogEntity log = new ExceptionLogEntity();
            log.setType(ex.getClass().getSimpleName());
            log.setMessage(ex.getMessage());
            log.setStackTrace(Arrays.toString(ex.getStackTrace()));
            log.setTimestamp(LocalDateTime.now());
            exceptionLogRepository.save(log);
        }
    }
}
