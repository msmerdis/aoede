package com.aoede.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.aoede.commons.base.component.BaseComponent;

@Component
@Profile("dev")
public class DelayFilter extends BaseComponent implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		delay(2, TimeUnit.SECONDS);

		chain.doFilter(req, res);
	}

	private void delay (long value, TimeUnit tunit) {
		try {
			tunit.sleep(value);
			logger.error("wasted " + value + " " + tunit.name());
		} catch (InterruptedException e) {
		}
	}

}



