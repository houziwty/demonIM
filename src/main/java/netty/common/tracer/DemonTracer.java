package netty.common.tracer;

import netty.common.message.DemonMessage;
import netty.common.transaction.DemonTransaction;

import org.apache.log4j.PropertyConfigurator;

public class DemonTracer {

	static {
		try {
			PropertyConfigurator.configure("log4j.properties");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	private DemonTracer tracer;

	public static DemonTracer getInstance(Class<?> c) {
		return new DemonTracer(c);
	}

	public DemonTracer(Class<?> c) {
		tracer = DemonTracer.getInstance(c);
	}

	public void debug(String info) {
		tracer.debug(info);
	}

	public void debug(String info, DemonMessage msg) {
		tracer.debug(info + "\r\n" + msg.toString());
	}

	public void info(String info) {
		tracer.info(info);
	}

	public void info(String info, DemonMessage msg) {
		tracer.info(info + "\r\n" + msg.toString());
	}

	public void warn(String info) {
		tracer.warn(info);
	}

	public void warn(String warn, Throwable t) {
		tracer.warn(warn, t);
	}

	public void warn(String error, DemonMessage msg) {
		tracer.warn(error + "\r\n" + msg.toString());
	}

	public void warn(String warn,DemonMessage msg, Throwable t) {
		tracer.warn(warn + "\r\n" + msg.toString(), t);
	}

	public void error(String error) {
		tracer.error(error);
	}

	public void error(String error, Throwable t) {
		tracer.error(error, t);
	}

	public void error(String error, DemonMessage msg) {
		tracer.error(error + "\r\n" + msg.toString());
	}

	public void error(String error, DemonMessage msg, Throwable t) {
		tracer.error(error + "\r\n" + msg.toString(), t);
	}

	public void fatal(String fatal) {
		tracer.fatal(fatal);
	}

	public void fatal(String fatal, Throwable t) {
		tracer.fatal(fatal, t);
	}

	public void fatal(String fatal, DemonMessage msg) {
		tracer.fatal(fatal + "\r\n" + msg.toString());
	}

	public void fatal(String fatal, DemonMessage msg, Throwable t) {
		tracer.fatal(fatal + "\r\n" + msg.toString(), t);
	}



}
