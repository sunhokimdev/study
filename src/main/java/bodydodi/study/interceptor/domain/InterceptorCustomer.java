package bodydodi.study.interceptor.domain;

import bodydodi.study.interceptor.repository.InterceptorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
@RequiredArgsConstructor
public class InterceptorCustomer implements HandlerInterceptor {
    private final InterceptorJwtTokenProvider jwtTokenProvider;
    private final InterceptorRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[preHandle] [" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI() + getParameters(request));
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        InterceptorUrl interceptorUrl = InterceptorUrl.findUrlList(request.getRequestURI());

        if (interceptorUrl != null && interceptorUrl.isListUrl()) {
            InterceptorUserAuth auth = jwtTokenProvider.getUserAuth(jwtToken);

            if (auth.isRoleUser()) throw new Exception("권한이 없습니다.");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("[postHandle] [" + request + "]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) ex.printStackTrace();

        log.info("[afterCompletion] [" + request + "][exception: " + ex + "]");
    }

    private String getParameters(HttpServletRequest request) {
        StringBuilder posted = new StringBuilder();
        Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
        }
        while (true) {
            assert e != null;
            if (!e.hasMoreElements()) break;
            if (posted.length() > 1) {
                posted.append("&");
            }
            String curr = (String) e.nextElement();
            posted.append(curr).append("=");
            if (curr.contains("password")
                    || curr.contains("pass")
                    || curr.contains("pwd")) {
                posted.append("*****");
            } else {
                posted.append(request.getParameter(curr));
            }
        }
        String ip = request.getHeader("X-FORWARDED-FOR");
        String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
        if (ipAddr!=null && !ipAddr.equals("")) {
            posted.append("&_psip=").append(ipAddr);
        }
        return posted.toString();
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if (ipFromHeader != null && ipFromHeader.length() > 0) {
            log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }
}
