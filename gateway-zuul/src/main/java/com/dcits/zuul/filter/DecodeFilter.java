package com.dcits.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/*
 * 可以做对接入参数进行加解密操作
 */
@Component
public class DecodeFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(DecodeFilter.class);

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		try {
			RequestContext context = RequestContext.getCurrentContext();
			InputStream in = (InputStream) context.get("requestEntity");
			if (in == null) {
				in = context.getRequest().getInputStream();
			}
			String body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
			body = "动态增加一段内容到body中: " + body;
			final byte[] bytes = body.getBytes("UTF-8");
			context.setRequest(new HttpServletRequestWrapper(RequestContext.getCurrentContext().getRequest()) {
				@Override
				public ServletInputStream getInputStream() throws IOException {
					return new ServletInputStreamWrapper(bytes);
				}

				@Override
				public int getContentLength() {
					return bytes.length;
				}

				@Override
				public long getContentLengthLong() {
					return bytes.length;
				}
			});
		} catch (IOException e) {

		}
		return null;
	}

}
