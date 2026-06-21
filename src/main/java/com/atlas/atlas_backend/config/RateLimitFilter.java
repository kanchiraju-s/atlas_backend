package com.atlas.atlas_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private record RuleKey(String ip, String category) {}
    private final Map<RuleKey, Deque<Instant>> windows = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if ("POST".equals(request.getMethod())) {
            String ip = getClientIp(request);
            String path = request.getRequestURI();
            int limit = limitFor(path);

            if (limit > 0 && isRateLimited(ip, categoryFor(path), limit)) {
                response.setStatus(429);
                response.getWriter().write("{\"success\":false,\"message\":\"Rate limit exceeded. Please slow down.\"}");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isRateLimited(String ip, String category, int limit) {
        RuleKey key = new RuleKey(ip, category);
        Instant now = Instant.now();
        Instant cutoff = now.minusSeconds(3600);

        windows.compute(key, (k, deque) -> {
            if (deque == null) deque = new ArrayDeque<>();
            while (!deque.isEmpty() && deque.peekFirst().isBefore(cutoff)) deque.pollFirst();
            deque.addLast(now);
            return deque;
        });

        return windows.get(key).size() > limit;
    }

    private int limitFor(String path) {
        if (path.contains("/topics") && !path.contains("/drops")) return 10;
        if (path.contains("/drops") && !path.contains("/discussions")) return 60;
        if (path.contains("/discussions")) return 120;
        if (path.contains("/search")) return 300;
        return 0;
    }

    private String categoryFor(String path) {
        if (path.contains("/topics")) return "topics";
        if (path.contains("/drops")) return "drops";
        if (path.contains("/discussions")) return "discussions";
        if (path.contains("/search")) return "search";
        return "default";
    }

    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        return xff != null ? xff.split(",")[0].trim() : request.getRemoteAddr();
    }
}
