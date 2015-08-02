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
	private DemonTracer _tracer;

	public static DemonTracer getInstance(Class<?> c) {
		return new DemonTracer(c);
	}

	public DemonTracer(Class<?> c) {
		_tracer = DemonTracer.getInstance(c);
	}

	public void debug(String info) {
		_tracer.debug(info);
	}

	public void debug(String info, DemonMessage msg) {
		_tracer.debug(info + "\r\n" + msg.toString());
	}

	public void info(String info) {
		_tracer.info(info);
	}

	public void info(String info, DemonMessage msg) {
		_tracer.info(info + "\r\n" + msg.toString());
	}

	public void warn(String info) {
		_tracer.warn(info);
	}

	public void warn(String warn, Throwable t) {
		_tracer.warn(warn, t);
	}

	public void warn(String error, DemonMessage msg) {
		_tracer.warn(error + "\r\n" + msg.toString());
	}

	public void warn(String warn,DemonMessage msg, Throwable t) {
		_tracer.warn(warn + "\r\n" + msg.toString(), t);
	}

	public void error(String error) {
		_tracer.error(error);
	}

	public void error(String error, Throwable t) {
		_tracer.error(error, t);
	}

	public void error(String error, DemonMessage msg) {
		_tracer.error(error + "\r\n" + msg.toString());
	}

	public void error(String error, DemonMessage msg, Throwable t) {
		_tracer.error(error + "\r\n" + msg.toString(), t);
	}

	public void fatal(String fatal) {
		_tracer.fatal(fatal);
	}

	public void fatal(String fatal, Throwable t) {
		_tracer.fatal(fatal, t);
	}

	public void fatal(String fatal, DemonMessage msg) {
		_tracer.fatal(fatal + "\r\n" + msg.toString());
	}

	public void fatal(String fatal, DemonMessage msg, Throwable t) {
		_tracer.fatal(fatal + "\r\n" + msg.toString(), t);
	}



}
