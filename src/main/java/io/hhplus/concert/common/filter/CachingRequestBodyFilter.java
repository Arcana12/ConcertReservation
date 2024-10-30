package io.hhplus.concert.common.filter;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import org.springframework.util.StreamUtils;

@Getter
public class CachingRequestBodyFilter extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public CachingRequestBodyFilter(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new  CachedBodyServletInputStream(this.cachedBody);
    }


}
